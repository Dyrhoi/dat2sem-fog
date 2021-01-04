package domain.order;

import domain.carport.Carport;
import domain.carport.Shed;
import domain.order.exceptions.OfferNotFoundException;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.ticket.Ticket;
import domain.order.ticket.TicketMessage;
import domain.user.sales_representative.SalesRepresentative;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getOrders();
    Order getOrder(UUID uuid) throws OrderNotFoundException;
    Order getOrder(String orderToken) throws OrderNotFoundException;

    OrderFactory createOrder();

    int updateOrder(int id, Carport carport, Shed shed, Order order, SalesRepresentative updatedBy);

    int getCarportIdFromUuid(UUID uuid);
  
    Ticket getTicket(String orderToken) throws OrderNotFoundException;
    Ticket updateTicket(String token, TicketMessage ticketMessage) throws OrderNotFoundException;

    Order.Offer updateOffer(UUID uuid, Order.Offer offer) throws SQLException;
    Order.Offer getOffer(int id) throws OfferNotFoundException;

    int updateSalesRep(Order order, SalesRepresentative salesRepresentative) throws SQLException;
}
