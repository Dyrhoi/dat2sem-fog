package domain.order.dao;

import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import infrastructure.Database;

import java.util.List;

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
    public Order getOrder(int id) {
        return null;
    }

    @Override
    public OrderFactory createOrder() {
        return null;
    }
}
