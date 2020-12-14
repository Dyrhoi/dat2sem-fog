package domain.order;

import domain.order.exceptions.OrderNotFoundException;
import domain.order.exceptions.TicketNotFoundException;
import domain.order.ticket.Ticket;
import domain.order.ticket.TicketMessage;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getOrders();
    Order getOrder(UUID uuid) throws OrderNotFoundException;
    Order getOrder(String orderToken) throws OrderNotFoundException;

    OrderFactory createOrder();

    Ticket getTicket(String orderToken) throws OrderNotFoundException;
    Ticket updateTicket(String token, TicketMessage ticketMessage) throws OrderNotFoundException;

}
