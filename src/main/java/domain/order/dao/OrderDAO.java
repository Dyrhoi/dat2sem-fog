package domain.order.dao;

import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import infrastructure.Database;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class OrderDAO implements OrderRepository {
    private Database database;
    public OrderDAO (Database database) {
        this.database = database;
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public Order getOrder(UUID uuid) {
        return null;
    }

    @Override
    public OrderFactory createOrder() {
        return new OrderFactory() {
            @Override
            protected Order commit() {
                try(Connection conn = database.getConnection()) {
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

                        if(rs.next()) {
                            customerId = rs.getInt(1);
                        }
                        else {
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
                        if(rs.next()) {
                            carportId = rs.getInt(1);
                        }
                        else {
                            throw new SQLException("Couldn't insert carport.");
                        }

                        //Shed
                        int shedId = -1;
                        if(getShed() != null) {
                            stmt = conn.prepareStatement("INSERT INTO sheds (width, length, carports_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                            stmt.setInt(1, getShed().getWidth());
                            stmt.setInt(2, getShed().getLength());
                            stmt.setInt(3, carportId);

                            stmt.executeUpdate();
                            rs = stmt.getGeneratedKeys();
                            if(rs.next()) {
                                shedId = rs.getInt(1);
                            }
                            else {
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
