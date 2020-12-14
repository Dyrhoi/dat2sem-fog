package domain.user.dao;

import domain.user.User;
import domain.user.customer.Customer;
import domain.user.exceptions.UserNotFoundException;
import domain.user.UserRepository;
import domain.user.salesman.Salesman;
import infrastructure.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserRepository {

    private final Database database;

    public UserDAO(Database database) {
        this.database = database;
    }

    private Salesman loadSalesman(int id, ResultSet rs) throws SQLException {
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
        return new Salesman(id, firstname, lastname, email, phone, customerAddress, salt, secret );
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

                stmt = conn.prepareStatement("SELECT * FROM sales_representative INNER JOIN users ON sales_representative.user_id = users.id = users.id WHERE user_id = ?");
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

                stmt = conn.prepareStatement("SELECT * FROM sales_representative INNER JOIN users ON sales_representative.user_id = users.id = users.id WHERE user_id = ?");
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
}
