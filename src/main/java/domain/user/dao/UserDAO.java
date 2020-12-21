package domain.user.dao;

import domain.user.User;
import domain.user.customer.Customer;
import domain.user.exceptions.UserNotFoundException;
import domain.user.UserRepository;
import domain.user.sales_representative.*;
import infrastructure.Database;

import java.sql.*;

public class UserDAO implements UserRepository {

    private final Database database;

    public UserDAO(Database database) {
        this.database = database;
    }

    private SalesRepresentative loadSalesman(int id, ResultSet rs) throws SQLException {
        String firstname = rs.getString("first_name");
        String lastname = rs.getString("last_name");
        String email = rs.getString("email");
        String phone = rs.getString("phone_number");
        String address = rs.getString("address");
        String postalCode = String.valueOf(rs.getInt("postal_code"));
        String city = rs.getString("city");
        byte[] salt = rs.getBytes("salt");
        byte[] secret = rs.getBytes("secret");

        Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
        return new SalesRepresentative(id, firstname, lastname, email, phone, customerAddress, salt, secret );
    }

    private Customer loadCustomer(int id, ResultSet rs) throws SQLException {
        //int id, String firstname, String lastname, String email, String phone, Address address
        String firstname = rs.getString("first_name");
        String lastname = rs.getString("last_name");
        String email = rs.getString("email");
        String phone = rs.getString("phone_number");
        String address = rs.getString("address");
        String postalCode = String.valueOf(rs.getInt("postal_code"));
        String city = rs.getString("city");

        Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
        return new Customer(id, firstname, lastname, email, phone, customerAddress);
    }

    @Override
    public User getUser(int id) throws UserNotFoundException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt;

            /*
            * One method to do this is to extract user.
            * Then try to extract from customer table, if not found, try sales rep.
            * Either way we can handle each outcome in that if statement (returning salesrep or customer object).
            *
            * */

            stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rsUser = stmt.executeQuery();

            if (rsUser.next()) {
                stmt = conn.prepareStatement("SELECT * FROM customers INNER JOIN users ON customers.user_id = users.id WHERE user_id = ?");
                stmt.setInt(1, id);

                ResultSet rsCustomer = stmt.executeQuery();

                if(rsCustomer.next()) {
                    return loadCustomer(id, rsCustomer);
                }

                stmt = conn.prepareStatement("SELECT * FROM sales_representative INNER JOIN users ON sales_representative.user_id = users.id WHERE user_id = ?");
                stmt.setInt(1, id);

                ResultSet rsSalesRep = stmt.executeQuery();
                if(rsSalesRep.next()) {
                    return loadSalesman(id, rsSalesRep);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUser(String email) throws UserNotFoundException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt;

            stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);

            ResultSet rsUser = stmt.executeQuery();

            if (rsUser.next()) {
                int id = rsUser.getInt("id");
                stmt = conn.prepareStatement("SELECT * FROM customers INNER JOIN users ON customers.user_id = users.id WHERE user_id = ?");
                stmt.setInt(1, id);

                ResultSet rsCustomer = stmt.executeQuery();

                if(rsCustomer.next()) {
                    return loadCustomer(id, rsCustomer);
                }

                stmt = conn.prepareStatement("SELECT * FROM sales_representative INNER JOIN users ON sales_representative.user_id = users.id WHERE user_id = ?");
                stmt.setInt(1, id);

                ResultSet rsSalesRep = stmt.executeQuery();

                if(rsSalesRep.next()) {
                    return loadSalesman(id, rsSalesRep);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new UserNotFoundException();
    }

    @Override
    public SalesRepresentativeFactory createSalesRepresentative() {
        return new SalesRepresentativeFactory() {
            @Override
            public SalesRepresentative commit() throws SalesRepresentativeExistsException {
                int id;
                String firstName = super.getFirstName();
                String lastName = super.getLastName();
                String email = super.getEmail();
                String phoneNumber = super.getPhone();
                User.Address address = super.getAddress();
                byte[] salt = SalesRepresentative.generateSalt();
                byte[] secret = SalesRepresentative.calculateSecret(salt, super.getPassword());

                try (Connection conn = database.getConnection()) {
                    conn.setAutoCommit(false);

                    try {
                        PreparedStatement ps =
                                conn.prepareStatement(
                                        "INSERT INTO users (first_name, last_name, email, phone_number, address, postal_code, city) " +
                                                "VALUE (?,?,?,?,?,?,?);",
                                        Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, firstName);
                        ps.setString(2, lastName);
                        ps.setString(3, email);
                        ps.setString(4, phoneNumber);
                        ps.setString(5, address.getAddress());
                        ps.setString(6, address.getPostalCode());
                        ps.setString(7, address.getCity());
                        ps.executeUpdate();

                        ResultSet rs = ps.getGeneratedKeys();
                        if(rs.next())
                            id = rs.getInt(1);
                        else
                            throw new SQLException("Couldn't insert user.");

                        ps = conn.prepareStatement("INSERT INTO sales_representative (salt, secret, user_id) VALUES (?, ?, ?)");
                        ps.setBytes(1, salt);
                        ps.setBytes(2, secret);
                        ps.setInt(3, id);
                        ps.executeUpdate();

                        conn.commit();
                        try {
                            User user = getUser(id);
                            if (user instanceof SalesRepresentative)
                                return (SalesRepresentative) user;
                            else throw new UserNotFoundException();
                        } catch (UserNotFoundException e) {
                            throw new RuntimeException("The created user was not found, probably an SQL connection loss.", e);
                        }
                    } catch (SQLIntegrityConstraintViolationException e) {
                        throw new SalesRepresentativeExistsException();
                    } catch (SQLException e) {
                        System.out.println("SQL Fired error, rolling back");
                        conn.rollback();
                        throw new RuntimeException(e);
                    }
                    finally {
                        conn.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };

    }

    @Override
    public SalesRepresentative authorizeSalesRepresentative(String email, String password) throws SalesRepresentativeNonMatchingPasswordException, SalesRepresentativeNotFoundException {
        try {
            User user = getUser(email);
            if(!(user instanceof SalesRepresentative))
                throw new SalesRepresentativeNotFoundException();

            SalesRepresentative salesRepresentative = (SalesRepresentative) user;
            if(!salesRepresentative.isPasswordCorrect(password))
                throw new SalesRepresentativeNonMatchingPasswordException();

            return salesRepresentative;
        } catch (UserNotFoundException e) {
            throw new SalesRepresentativeNotFoundException();
        }
    }
}
