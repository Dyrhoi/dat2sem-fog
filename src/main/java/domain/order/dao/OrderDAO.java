package domain.order.dao;

import domain.carport.Carport;
import domain.customer.Customer;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.OrderRepository;
import domain.shed.Shed;
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

    //TODO: This should be Inner Join - Dyrhoi

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        Carport carport = null;
        Shed shed = null;
        Customer customer = null;
        UUID uuid;
        try (Connection conn = database.getConnection()) {
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
                    int roofAngle = rs.getInt("angle");
                    Carport.roofTypes roofType = Carport.roofTypes.valueOf(rs.getString("roof_type"));
                    int roof_material = rs.getInt("roof_material");
                    carport = new Carport(carportId, carportWidth, carportLength, roofType, roofAngle, roof_material);
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

                if (rs.next()) {
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone_number");
                    String address = rs.getString("address");
                    String postalCode = String.valueOf(rs.getInt("postal_code"));
                    String city = rs.getString("city");

                    Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
                    customer = new Customer(customerId, firstname, lastname, email, phone, customerAddress);
                }

                Order order = new Order(uuid, carport, shed, customer);
                orders.add(order);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return orders;
    }


    //TODO: THIS IS HARDCODED NEED FIX
    //What do you mean hardcoded? !? What needs fixing?
    //Anyways this should be an inner join... - Dyrhoi

    @Override
    public Order getOrder(UUID uuid) throws OrderNotFoundException {
        Carport carport = null;
        Shed shed = null;
        Customer customer = null;
        try (Connection conn = database.getConnection()) {
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
                throw new OrderNotFoundException();
            }

            //Get Carport
            stmt = conn.prepareStatement("SELECT * FROM carports WHERE id = ?");
            stmt.setInt(1, carportId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                int carportWidth = rs.getInt("width");
                int carportLength = rs.getInt("length");
                int roofAngle = rs.getInt("angle");
                Carport.roofTypes roofType = Carport.roofTypes.valueOf(rs.getString("roof_type"));
                int roof_material = rs.getInt("roof_material");
                carport = new Carport(carportId, carportWidth, carportLength, roofType, roofAngle, roof_material);
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

            if (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                String address = rs.getString("address");
                String postalCode = String.valueOf(rs.getInt("postal_code"));
                String city = rs.getString("city");

                Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
                customer = new Customer(customerId, firstname, lastname, email, phone, customerAddress);
            }
            return new Order(uuid, carport, shed, customer);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unkown SQL error.");
    }

    @Override
    public OrderFactory createOrder() {
        return new OrderFactory() {
            @Override
            protected Order commit() {
                try (Connection conn = database.getConnection()) {
                    //Being transaction:
                    conn.setAutoCommit(false);
                    try {

                        PreparedStatement stmt;
                        ResultSet rs;
                        //Customer:
                        int customerId = -1;
                        stmt = conn.prepareStatement("INSERT INTO customers (firstname, lastname, email, phone_number, notes, address, postal_code, city) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        stmt.setString(1, getCustomer().getFirstname());
                        stmt.setString(2, getCustomer().getLastname());
                        stmt.setString(3, getCustomer().getEmail());
                        stmt.setString(4, getCustomer().getPhone());
                        stmt.setString(5, getCustomer().getComment());
                        stmt.setString(6, getCustomer().getAddress().getAddress());
                        stmt.setString(7, getCustomer().getAddress().getPostalCode());
                        stmt.setString(8, getCustomer().getAddress().getCity());

                        stmt.executeUpdate();
                        rs = stmt.getGeneratedKeys();

                        if (rs.next()) {
                            customerId = rs.getInt(1);
                        } else {
                            throw new SQLException("Couldn't insert customer.");
                        }


                        //Carport
                        int carportId = -1;
                        stmt = conn.prepareStatement("INSERT INTO carports (width, length, roof_type, roof_material, angle) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        stmt.setInt(1, getCarport().getWidth());
                        stmt.setInt(2, getCarport().getLength());
                        stmt.setString(3, getCarport().getRoof().toString());
                        stmt.setInt(4, getCarport().getRoof_material());
                        stmt.setInt(5, getCarport().getRoofAngle());

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
                    } catch (OrderNotFoundException e) {
                        throw new RuntimeException("Couldn't fetch created order... ? Throw runtime Exception.");
                    } finally {
                        conn.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                throw new RuntimeException("Unknown error.");
            }
        };
    }
}
