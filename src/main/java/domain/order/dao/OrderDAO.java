package domain.order.dao;

import domain.carport.Carport;
import domain.carport.Shed;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.ticket.Ticket;
import domain.order.ticket.TicketEvent;
import domain.order.ticket.TicketMessage;
import domain.user.User;
import domain.user.UserRepository;
import domain.user.customer.Customer;
import domain.user.exceptions.UserNotFoundException;
import infrastructure.Database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDAO implements OrderRepository {
    private final Database database;
    private final UserRepository userRepository;

    public OrderDAO(Database database, UserRepository userDAO) {
        this.database = database;
        this.userRepository = userDAO;
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
            String token;
            PreparedStatement orderStmt = conn.prepareStatement("SELECT * FROM orders ORDER BY timestamp DESC");
            ResultSet orderRs = orderStmt.executeQuery();

            while (orderRs.next()) {
                uuid = UUID.fromString(orderRs.getString("uuid"));
                carportId = orderRs.getInt("carports_id");
                customerId = orderRs.getInt("customers_id");
                token = orderRs.getString("token");

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

                stmt = conn.prepareStatement("SELECT * FROM users INNER JOIN customers ON user_id = users.id WHERE users.id = ?");
                stmt.setInt(1, customerId);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    String firstname = rs.getString("first_name");
                    String lastname = rs.getString("last_name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone_number");
                    String address = rs.getString("address");
                    String postalCode = String.valueOf(rs.getInt("postal_code"));
                    String city = rs.getString("city");

                    Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
                    customer = new Customer(customerId, firstname, lastname, email, phone, customerAddress);
                }

                Order order = new Order(uuid, carport, shed, customer);
                order.setDate(orderRs.getString("timestamp"));
                orders.add(order);
            }
        } catch (SQLException e) {
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
            String token;
            stmt = conn.prepareStatement("SELECT * FROM orders WHERE uuid = ?");
            stmt.setString(1, uuid.toString());

            rs = stmt.executeQuery();

            if (rs.next()) {
                customerId = rs.getInt("customers_id");
                carportId = rs.getInt("carports_id");
                token = rs.getString("token");
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
            stmt = conn.prepareStatement("SELECT * FROM users INNER JOIN customers ON user_id = users.id WHERE users.id = ?");
            stmt.setInt(1, customerId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                String address = rs.getString("address");
                String postalCode = String.valueOf(rs.getInt("postal_code"));
                String city = rs.getString("city");

                Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
                customer = new Customer(customerId, firstname, lastname, email, phone, customerAddress);
            }
            Order order = new Order(uuid, carport, shed, customer);
            order.setToken(token);
            return order;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unkown SQL error.");
    }

    @Override
    public Order getOrder(String token) throws OrderNotFoundException {
        Carport carport = null;
        Shed shed = null;
        Customer customer = null;
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt;
            ResultSet rs;

            //Get ID's
            int customerId;
            int carportId;
            UUID uuid;
            stmt = conn.prepareStatement("SELECT * FROM orders WHERE token = ?");
            stmt.setString(1, token);

            rs = stmt.executeQuery();

            if (rs.next()) {
                customerId = rs.getInt("customers_id");
                carportId = rs.getInt("carports_id");
                uuid = UUID.fromString(rs.getString("uuid"));
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
            stmt = conn.prepareStatement("SELECT * FROM users INNER JOIN customers ON user_id = users.id WHERE users.id = ?");
            stmt.setInt(1, customerId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                String address = rs.getString("address");
                String postalCode = String.valueOf(rs.getInt("postal_code"));
                String city = rs.getString("city");

                Customer.Address customerAddress = new Customer.Address(address, city, postalCode);
                customer = new Customer(customerId, firstname, lastname, email, phone, customerAddress);
            }
            Order order = new Order(uuid, carport, shed, customer);
            order.setToken(token);
            return order;
        } catch (SQLException e) {
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

                        //Let's see if customer already exists;
                        int userId = -1;
                        int customerId = -1;
                        try {
                            //Customer exists, set id to current customer.
                            User customer = userRepository.getUser(getCustomer().getEmail());
                            userId = customer.getId();
                            if (!(customer instanceof Customer))
                                throw new SQLException("Customer email is already in use by a Sales Rep");
                        } catch (UserNotFoundException e) {
                            stmt = conn.prepareStatement("INSERT INTO users (first_name, last_name, email, phone_number, address, postal_code, city) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                            stmt.setString(1, getCustomer().getFirstname());
                            stmt.setString(2, getCustomer().getLastname());
                            stmt.setString(3, getCustomer().getEmail());
                            stmt.setString(4, getCustomer().getPhone());
                            stmt.setString(5, getCustomer().getAddress().getAddress());
                            stmt.setString(6, getCustomer().getAddress().getPostalCode());
                            stmt.setString(7, getCustomer().getAddress().getCity());

                            stmt.executeUpdate();
                            rs = stmt.getGeneratedKeys();

                            if (rs.next()) {
                                userId = rs.getInt(1);
                            } else {
                                throw new SQLException("Couldn't insert users.");
                            }

                            //Customer Specific Info:
                            stmt = conn.prepareStatement("INSERT INTO customers (user_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                            stmt.setInt(1, userId);

                            stmt.executeUpdate();
                            rs = stmt.getGeneratedKeys();

                            if (rs.next()) {
                                customerId = rs.getInt(1);
                            } else {
                                throw new SQLException("Couldn't insert customers.");
                            }
                        }

                        //Carport
                        int carportId = -1;
                        stmt = conn.prepareStatement("INSERT INTO carports (width, length, roof_type, roof_material, angle) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        stmt.setInt(1, getCarport().getWidth());
                        stmt.setInt(2, getCarport().getLength());
                        stmt.setString(3, getCarport().getRoof().toString());
                        stmt.setInt(4, getCarport().getRoof_material());
                        if (getCarport().getRoofAngle() != null) {
                            stmt.setInt(5, getCarport().getRoofAngle());
                        }
                        else {
                            stmt.setNull(5, 0);
                        }

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
                        stmt.setInt(2, userId);
                        stmt.setInt(3, carportId);
                        stmt.setString(4, getToken());

                        stmt.executeUpdate();

                        //Ticket creation, add event and message (order note)
                        stmt = conn.prepareStatement("INSERT INTO order_events (author_id, scope, order_token) VALUES (?, ?, ?)");
                        stmt.setInt(1, userId);
                        stmt.setString(2, TicketEvent.EventType.ORDER_CREATED.toString());
                        stmt.setString(3, getToken());

                        stmt.executeUpdate();

                        stmt = conn.prepareStatement("INSERT INTO order_messages (author_id, content, order_token) VALUES (?, ?, ?)");
                        stmt.setInt(1, userId);
                        stmt.setString(2, getNote());
                        stmt.setString(3, getToken());

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

    @Override
    public int updateOrder(int id, Carport carport, Shed shed) {
        try (Connection conn = database.getConnection()) {
            System.out.println("Start");
            conn.setAutoCommit(false);
            PreparedStatement stmt;
            stmt = conn.prepareStatement("UPDATE carports SET length = ?, width = ?, roof_type = ?, roof_material = ?, angle = ? WHERE id =" + id);
            System.out.println(carport.getLength());
            stmt.setInt(1, carport.getLength());
            stmt.setInt(2, carport.getWidth());
            stmt.setString(3, carport.getRoof().toString());
            stmt.setInt(4, carport.getRoof_material());
            stmt.setInt(5, carport.getRoofAngle());
            stmt.executeUpdate();
            stmt.close();

            if (shed == null) {
                stmt = conn.prepareStatement("DELETE FROM sheds WHERE carports_id =" + id);
                stmt.executeUpdate();
                stmt.close();
            }
            else {
                stmt = conn.prepareStatement("UPDATE sheds SET length = ?, width = ? WHERE carports_id =" + id);
                stmt.setInt(1, shed.getLength());
                stmt.setInt(2, shed.getWidth());
                stmt.executeUpdate();
                stmt.close();
            }

            System.out.println("Done");
            conn.setAutoCommit(true);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int getCarportIdFromUuid(UUID uuid) {
        Integer carportId = null;
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt;
            stmt = conn.prepareStatement("SELECT * FROM orders WHERE uuid = ?");
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carportId = rs.getInt("carports_id");
            } else {
                throw new OrderNotFoundException();
            }
        } catch (SQLException | OrderNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return carportId;
    }
  
  private TicketMessage loadMessage(ResultSet rs) throws SQLException {
        User user = null;
        try {
            user = userRepository.getUser(rs.getInt("author_id"));
        } catch (UserNotFoundException e) {
            System.out.println("A message was loaded, but the user was has since been deleted from system, displaying a null user.");
            e.printStackTrace();
        }
        return new TicketMessage(
                rs.getString("content"),
                user,
                rs.getTimestamp("_date").toLocalDateTime()
        );
    }

    private TicketEvent loadEvent(ResultSet rs) throws SQLException {
        User user = null;
        try {
            user = userRepository.getUser(rs.getInt("author_id"));
        } catch (UserNotFoundException e) {
            System.out.println("A message was loaded, but the user was has since been deleted from system, displaying a null user.");
            e.printStackTrace();
        }
        return new TicketEvent(
                TicketEvent.EventType.valueOf(rs.getString("scope")),
                user,
                rs.getTimestamp("_date").toLocalDateTime()
        );
    }

    @Override
    public Ticket getTicket(String orderToken) throws OrderNotFoundException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt;
            ResultSet rs;
            List<Object> eventsOrMessages = new ArrayList<>();

            //TODO:Hvid - Gør det til en enkel SQL statement, tak :* - dyrhoi

            /*
             *
             * Messages:
             *
             * */
            stmt = conn.prepareStatement("SELECT * FROM order_messages WHERE order_token = ?");
            stmt.setString(1, orderToken);

            rs = stmt.executeQuery();

            while (rs.next()) {
                eventsOrMessages.add(loadMessage(rs));
            }

            /*
             *
             * Events:
             *
             * */

            stmt = conn.prepareStatement("SELECT * FROM order_events WHERE order_token = ?");
            stmt.setString(1, orderToken);

            rs = stmt.executeQuery();

            while (rs.next()) {
                eventsOrMessages.add(loadEvent(rs));
            }

            //Sort?
            eventsOrMessages.sort((o1, o2) -> {
                LocalDateTime date1 = null;
                if (o1 instanceof TicketMessage) {
                    date1 = ((TicketMessage) o1).getDate();
                } else {
                    date1 = ((TicketEvent) o1).getDate();
                }

                LocalDateTime date2 = null;
                if (o2 instanceof TicketMessage) {
                    date2 = ((TicketMessage) o2).getDate();
                } else {
                    date2 = ((TicketEvent) o2).getDate();
                }
                return date1.compareTo(date2);
            });

            Order order = getOrder(orderToken);

            return new Ticket(order, eventsOrMessages);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new OrderNotFoundException();
    }

    @Override
    public Ticket updateTicket(String token, TicketMessage ticketMessage) throws OrderNotFoundException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt;

            stmt = conn.prepareStatement("INSERT INTO order_messages (author_id, content, order_token) VALUE (?, ?, ?)");
            stmt.setInt(1, ticketMessage.getAuthor().getId());
            stmt.setString(2, ticketMessage.getContent());
            stmt.setString(3, token);
            stmt.executeUpdate();

            return getTicket(token);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new OrderNotFoundException();
    }
}
