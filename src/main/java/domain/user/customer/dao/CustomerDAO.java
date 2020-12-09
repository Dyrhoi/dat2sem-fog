package domain.user.customer.dao;

import domain.user.customer.Customer;
import domain.user.customer.CustomerRepository;
import domain.user.customer.exceptions.CustomerNotFoundException;
import infrastructure.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO implements CustomerRepository {
    private final Database database;

    public CustomerDAO(Database database) {
        this.database = database;
    }

    private Customer loadCustomer(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
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
    public Customer getCustomer(int customerId) throws CustomerNotFoundException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt;
            ResultSet rs;
            stmt = conn.prepareStatement("SELECT * FROM customers INNER JOIN users ON user_id = users.id WHERE customers.id = ?");
            stmt.setInt(1, customerId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return loadCustomer(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new CustomerNotFoundException();
    }

    @Override
    public Customer getCustomer(String email) throws CustomerNotFoundException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt;
            ResultSet rs;
            stmt = conn.prepareStatement("SELECT * FROM customers INNER JOIN users ON user_id = users.id WHERE users.email = ?");
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return loadCustomer(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new CustomerNotFoundException();
    }
}
