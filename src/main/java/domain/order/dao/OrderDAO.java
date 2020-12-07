package domain.order.dao;

import domain.carport.Carport;
import domain.carport.CarportNotFoundException;
import domain.customer.Customer;
import domain.customer.CustomerNotFoundException;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import domain.shed.Shed;
import domain.shed.ShedNotFoundException;
import infrastructure.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDAO implements OrderRepository {
    private final Database database;

    public OrderDAO(Database database) {
        this.database = database;
    }

    @Override
    public List<Order> getOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        Carport carport;
        Shed shed = null;
        Customer customer;
        UUID uuid;
        try (Connection conn = database.getConnection()) {
            try {
                PreparedStatement stmt;
                ResultSet rs;

                //Get ID's
                int customerId;
                int carportId;
                PreparedStatement orderStmt = conn.prepareStatement("SELECT * FROM orders");
                ResultSet orderRs = orderStmt.executeQuery();

                while (orderRs.next()) {
                    uuid = UUID.fromString(orderRs.getString("uuid"));
                    carportId = orderRs.getInt("carports_id");
                    customerId = orderRs.getInt("customers_id");

                    stmt = conn.prepareStatement("SELECT * FROM carports WHERE id = ?");
                    stmt.setInt(1, carportId);

                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        int carportWidth = rs.getInt("width");
                        int carportLength = rs.getInt("length");
                        Carport.roofTypes roofType = Carport.roofTypes.valueOf(rs.getString("roof_type"));
                        carport = new Carport(carportId, carportWidth, carportLength, roofType);
                    } else {
                        throw new CarportNotFoundException();
                    }

                    stmt = conn.prepareStatement("SELECT * FROM sheds WHERE carports_id = ?");
                    stmt.setInt(1, carportId);

                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        int shedId = rs.getInt("id");
                        int shedWidth = rs.getInt("width");
                        int shedLength = rs.getInt("length");
                        shed = new Shed(shedId, shedWidth, shedLength);
                    }

                    stmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
                    stmt.setInt(1, customerId);

                    rs = stmt.executeQuery();

                    if (rs.next()){
                        String name = rs.getString("name");
                        String[] fullName = name.split("\\s");
                        StringBuilder firstNameString = new StringBuilder();
                        for (int i = 0 ; i < fullName.length ; i++){
                            firstNameString.append(fullName[i]).append(" ");
                        }
                        String firstName = firstNameString.toString();
                        String lastName = fullName[fullName.length - 1];
                        String email = rs.getString("email");
                        String phone = rs.getString("phone_number");
                        String address = rs.getString("address");
                        String postalCode = String.valueOf(rs.getInt("postal_code"));
                        String city = rs.getString("city");

                        Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
                        customer = new Customer(customerId, firstName, lastName, email, phone, customerAddress);
                    } else {
                        throw new CustomerNotFoundException();
                    }

                    Order order = new Order(uuid, carport, shed, customer);
                    orders.add(order);
                }

            } catch (RuntimeException | CustomerNotFoundException | CarportNotFoundException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }


    //TODO: THIS IS HARDCODED NEED FIX
    @Override
    public Order getOrder(UUID uuid) throws SQLException, CarportNotFoundException, CustomerNotFoundException {
        Carport carport = null;
        Shed shed = null;
        Customer customer = null;
        try (Connection conn = database.getConnection()) {
            try {
                PreparedStatement stmt;
                ResultSet rs;

                //Get ID's
                int customerId;
                int carportId;
                stmt = conn.prepareStatement("SELECT * FROM orders WHERE uuid = ?");
                stmt.setString(1, uuid.toString());

                rs = stmt.executeQuery();

                if (rs.next()) {
                    customerId = rs.getInt("customers_id");
                    carportId = rs.getInt("carports_id");
                } else {
                    throw new SQLException("DER SKETE EN FEJL");
                }

                //Get Carport
                stmt = conn.prepareStatement("SELECT * FROM carports WHERE id = ?");
                stmt.setInt(1, carportId);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    int carportWidth = rs.getInt("width");
                    int carportLength = rs.getInt("length");
                    Carport.roofTypes roofType = Carport.roofTypes.valueOf(rs.getString("roof_type"));
                    carport = new Carport(carportId, carportWidth, carportLength, roofType);
                } else {
                    throw new CarportNotFoundException();
                }

                //Get Shed
                stmt = conn.prepareStatement("SELECT * FROM sheds WHERE carports_id = ?");
                stmt.setInt(1, carportId);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    int shedId = rs.getInt("id");
                    int shedWidth = rs.getInt("width");
                    int shedLength = rs.getInt("length");
                    shed = new Shed(shedId, shedWidth, shedLength);
                }

                //Get Customer
                stmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
                stmt.setInt(1, customerId);

                rs = stmt.executeQuery();

                if (rs.next()){
                    String name = rs.getString("name");
                    String[] fullName = name.split("\\s");
                    StringBuilder firstNameString = new StringBuilder();
                    for (int i = 0 ; i < fullName.length ; i++){
                        firstNameString.append(fullName[i]).append(" ");
                    }
                    String firstName = firstNameString.toString();
                    String lastName = fullName[fullName.length - 1];
                    String email = rs.getString("email");
                    String phone = rs.getString("phone_number");
                    String address = rs.getString("address");
                    String postalCode = String.valueOf(rs.getInt("postal_code"));
                    String city = rs.getString("city");

                    Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
                    customer = new Customer(customerId, firstName, lastName, email, phone, customerAddress);
                } else {
                    throw new CustomerNotFoundException();
                }

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return new Order(uuid, carport, shed, customer);
    }

    @Override
    public OrderFactory createOrder() {
        return new OrderFactory() {
            @Override
            protected Order commit() throws CarportNotFoundException, ShedNotFoundException, CustomerNotFoundException {
                try (Connection conn = database.getConnection()) {
                    //Being transaction:
                    conn.setAutoCommit(false);
                    try {

                        PreparedStatement stmt;
                        ResultSet rs;
                        //Customer:
                        int customerId = -1;
                        stmt = conn.prepareStatement("INSERT INTO customers (name, email, phone_number, postal_code, city, address) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        stmt.setString(1, getCustomer().getFullName());
                        stmt.setString(2, getCustomer().getEmail());
                        stmt.setString(3, getCustomer().getPhone());
                        stmt.setString(4, getCustomer().getAddress().getPostalCode());
                        stmt.setString(5, getCustomer().getAddress().getCity());
                        stmt.setString(6, getCustomer().getAddress().getAddress());

                        stmt.executeUpdate();
                        rs = stmt.getGeneratedKeys();

                        if (rs.next()) {
                            customerId = rs.getInt(1);
                        } else {
                            throw new SQLException("Couldn't insert customer.");
                        }


                        //Carport
                        int carportId = -1;
                        stmt = conn.prepareStatement("INSERT INTO carports (width, length, roof_type) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        stmt.setInt(1, getCarport().getWidth());
                        stmt.setInt(2, getCarport().getLength());
                        stmt.setString(3, getCarport().getRoof().toString());

                        stmt.executeUpdate();
                        rs = stmt.getGeneratedKeys();
                        if (rs.next()) {
                            carportId = rs.getInt(1);
                        } else {
                            throw new SQLException("Couldn't insert carport.");
                        }

                        //Shed
                        int shedId = -1;
                        if (getShed() != null) {
                            stmt = conn.prepareStatement("INSERT INTO sheds (width, length, carports_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                            stmt.setInt(1, getShed().getWidth());
                            stmt.setInt(2, getShed().getLength());
                            stmt.setInt(3, carportId);

                            stmt.executeUpdate();
                            rs = stmt.getGeneratedKeys();
                            if (rs.next()) {
                                shedId = rs.getInt(1);
                            } else {
                                throw new SQLException("Couldn't insert Shed");
                            }
                        }

                        //Order
                        stmt = conn.prepareStatement("INSERT INTO orders (uuid, customers_id, carports_id, token) VALUES (?, ?, ?, ?)");
                        stmt.setString(1, getUuid().toString());
                        stmt.setInt(2, customerId);
                        stmt.setInt(3, carportId);
                        stmt.setString(4, getToken());

                        stmt.executeUpdate();

                        conn.commit();

                        return getOrder(getUuid());
                    } catch (SQLException e) {
                        //Error in SQL
                        e.printStackTrace();
                        System.out.println("Rolling back DB...");
                        conn.rollback();
                    } finally {
                        conn.setAutoCommit(true);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    throw new RuntimeException(throwables);
                }
                throw new RuntimeException("Unknown error.");
            }
        };
    }
}
