package api;

import domain.material.MaterialRepository;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.exceptions.TicketNotFoundException;
import domain.order.ticket.Ticket;
import domain.order.ticket.TicketMessage;

import java.util.List;
import java.util.UUID;

public class API {
    private final MaterialRepository materialRepository;
    private final OrderRepository orderRepository;

    public API(MaterialRepository materialRepository, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.materialRepository = materialRepository;
    }

    public List<String> getRoofMaterials() { return materialRepository.getRoofMaterials(); }

    public String getRoofMaterial(int id) { return materialRepository.getRoofMaterial(id); }

    public OrderFactory createOrder() { return orderRepository.createOrder(); }

    public List<Order> getOrders () { return orderRepository.getOrders(); }

    public Order getOrder(UUID uuid) throws OrderNotFoundException { return orderRepository.getOrder(uuid); }

    public Ticket getTicket(String token) throws OrderNotFoundException {
        return orderRepository.getTicket(token);
    }

    public Ticket updateTicket(String token, TicketMessage ticketMessage) throws OrderNotFoundException {
        return orderRepository.updateTicket(token, ticketMessage);
    }
}