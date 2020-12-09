package domain.order.ticket;

import domain.order.Order;
import domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class Ticket {
    private final Order order;
    private final List<Message> messages;

    public Ticket(Order order, List<Message> messages) {
        this.order = order;
        this.messages = messages;
    }

    public Order getOrder() {
        return order;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public static class Message {
        private final String content;
        private final User author;
        private final LocalDateTime date;

        public Message(String content, User author, LocalDateTime date) {
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
}
