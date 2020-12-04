package api;

import domain.material.MaterialRepository;
import domain.order.OrderFactory;
import domain.order.OrderRepository;

import java.util.List;

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
}
