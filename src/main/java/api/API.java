package api;

import domain.carport.Carport;
import domain.carport.OrderMaterial;
import domain.carport.RoofMaterial;
import domain.carport.Shed;
import domain.material.MaterialRepository;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.OrderRepository;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.ticket.Ticket;
import domain.order.ticket.TicketMessage;
import domain.svg.DrawCarport;
import domain.user.UserRepository;
import domain.user.sales_representative.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class API {
    private final MaterialRepository materialRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public API(MaterialRepository materialRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.materialRepository = materialRepository;
        this.userRepository = userRepository;
    }

    public List<RoofMaterial> getRoofMaterials() { return materialRepository.getRoofMaterials(); }

    public String getRoofMaterial(int id) { return materialRepository.getRoofMaterial(id); }

    public OrderFactory createOrder() { return orderRepository.createOrder(); }

    public List<Order> getOrders () { return orderRepository.getOrders(); }

    public Order getOrder(UUID uuid) throws OrderNotFoundException { return orderRepository.getOrder(uuid); }

    public int updateOrder(int id, Carport carport, Shed shed, Order order, SalesRepresentative salesRepresentative){ return orderRepository.updateOrder(id, carport, shed, order, salesRepresentative);}

    public int getCarportIdFromUuid(UUID uuid) {return orderRepository.getCarportIdFromUuid(uuid); }
  
    public Ticket getTicket(String token) throws OrderNotFoundException {
        return orderRepository.getTicket(token);
    }

    public Ticket updateTicket(String token, TicketMessage ticketMessage) throws OrderNotFoundException {
        return orderRepository.updateTicket(token, ticketMessage);
    }

    public SalesRepresentativeFactory createSalesRepresentative() throws SalesRepresentativeExistsException {
        return userRepository.createSalesRepresentative();
    }

    public SalesRepresentative authorizeSalesRepresentative(String email, String password) throws SalesRepresentativeNotFoundException, SalesRepresentativeNonMatchingPasswordException {
        return userRepository.authorizeSalesRepresentative(email, password);
    }

    public int updateOffer(UUID uuid, int offer) throws SQLException { return orderRepository.updateOffer(uuid, offer); }

    public int updateSalesRep(Order order, SalesRepresentative salesRepresentative) throws SQLException { return orderRepository.updateSalesRep(order, salesRepresentative);}

    public List<OrderMaterial> getOrderMaterials(Order order){return materialRepository.getOrderMaterials(order);}

    public String getSVGDrawing(Carport carport) {
        return new DrawCarport(carport).drawSVGWithViewBox();
    }

    public Order getOrder(String token) throws OrderNotFoundException {
        return orderRepository.getOrder(token);
    }
}