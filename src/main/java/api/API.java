package api;

import domain.material.MaterialRepository;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import domain.order.exceptions.OrderNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class API {
    private final MaterialRepository materialRepository;
    private final OrderRepository orderRepository;

    public API(MaterialRepository materialRepository, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.materialRepository = materialRepository;
    }

    public List<String> getRoofTypes() {
        return materialRepository.getRootTypes();
    }

    public OrderFactory createOrder() {
        return orderRepository.createOrder();
    }

    public List<Order> getOrders () {
        return orderRepository.getOrders();
    }

    public Order getOrder(UUID uuid) throws OrderNotFoundException {
        return orderRepository.getOrder(uuid);
    }
}