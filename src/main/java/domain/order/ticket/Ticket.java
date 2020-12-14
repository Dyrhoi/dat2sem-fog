package domain.order.ticket;

import domain.order.Order;

import java.util.List;

public class Ticket {
    private final Order order;
    private final List<Object> events;

    public Ticket(Order order, List<Object> events) {
        this.order = order;
        this.events = events;
    }

    public List<Object> getEvents() {
        return events;
    }

    public Order getOrder() {
        return order;
    }
}
