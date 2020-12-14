package api;

import domain.carport.Carport;
import domain.carport.RoofMaterial;
import domain.carport.Shed;
import domain.material.MaterialRepository;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import domain.order.exceptions.OrderNotFoundException;

import java.util.List;
import java.util.UUID;

public class API {
    private final MaterialRepository materialRepository;
    private final OrderRepository orderRepository;

    public API(MaterialRepository materialRepository, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.materialRepository = materialRepository;
    }

    public List<RoofMaterial> getRoofMaterials() { return materialRepository.getRoofMaterials(); }

    public String getRoofMaterial(int id) { return materialRepository.getRoofMaterial(id); }

    public OrderFactory createOrder() { return orderRepository.createOrder(); }

    public List<Order> getOrders () { return orderRepository.getOrders(); }

    public Order getOrder(UUID uuid) throws OrderNotFoundException { return orderRepository.getOrder(uuid); }

    public int updateOrder(int id, Carport carport, Shed shed){ return orderRepository.updateOrder(id, carport, shed);}

    public int getCarportIdFromUuid(UUID uuid) {return orderRepository.getCarportIdFromUuid(uuid); }
}