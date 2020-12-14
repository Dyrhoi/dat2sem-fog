package domain.order.ticket;

import domain.user.User;

import java.time.LocalDateTime;

public class TicketEvent {
    private final EventType scope;
    private final User author;
    private final LocalDateTime date;

    public TicketEvent(EventType scope, User author, LocalDateTime date) {
        this.scope = scope;
        this.author = author;
        this.date = date;
    }

    public EventType getScope() {
        return scope;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public enum EventType {
        ORDER_CREATE,
        ORDER_UPDATE,
        ORDER_CLOSE,

        OFFER_SENT,
        OFFER_ACCEPT,
        OFFER_DECLINE
    }
}
