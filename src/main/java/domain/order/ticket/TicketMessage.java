package domain.order.ticket;

import domain.user.User;

import java.time.LocalDateTime;

public class TicketMessage {
    private final String content;
    private final User author;
    private final LocalDateTime date;

    public TicketMessage(String content, User author, LocalDateTime date) {
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
