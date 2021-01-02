package domain.order;

import domain.carport.Carport;
import domain.carport.Shed;
import domain.user.customer.Customer;
import domain.user.sales_representative.SalesRepresentative;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {
    public static final double evenOffer = 20000;
    private final UUID uuid;
    private final Carport carport;
    private final Shed shed;
    private final Customer customer;
    private LocalDateTime date;
    private String token;
    private int offer;
    private SalesRepresentative salesRepresentative;
    private Status status;

    public Order(UUID uuid, Carport carport, Shed shed, Customer customer, Status status) {
        this.uuid = uuid;
        this.carport = carport;
        this.shed = shed;
        this.customer = customer;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Carport getCarport() {
        return carport;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Shed getShed() {
        return shed;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getToken() {
        return token;
    }

    public Status getStatus() {
        return status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public SalesRepresentative getSalesRepresentative() {
        return salesRepresentative;
    }

    public void setSalesRepresentative(SalesRepresentative salesRepresentative) {
        this.salesRepresentative = salesRepresentative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return uuid == order.uuid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid=" + uuid +
                ", carport=" + carport +
                ", shed=" + shed +
                ", customer=" + customer +
                ", date=" + date +
                ", token='" + token + '\'' +
                ", offer=" + offer +
                ", salesRepresentative=" + salesRepresentative +
                ", status=" + status +
                '}';
    }

    public static class Status {
        private final int id;
        private final String name;
        private final String color;

        public Status(int id, String name, String color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }

        public String getColorRGBA() {
            return "rgba(" + color + ", 1)";
        }

        public String getColorRGBA(double alpha) {
            return "rgba(" + color + ", " + alpha + ")";
        }
    }
}
