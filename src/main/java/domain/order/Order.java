package domain.order;

import domain.carport.Carport;
import domain.carport.Shed;
import domain.user.customer.Customer;

import java.util.Objects;
import java.util.UUID;

public class Order {
    private final UUID uuid;
    private final Carport carport;
    private final Shed shed;
    private final Customer customer;
    private String date;
    private String token;
    private int offer;

    public Order(UUID uuid, Carport carport, Shed shed, Customer customer) {
        this.uuid = uuid;
        this.carport = carport;
        this.shed = shed;
        this.customer = customer;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToken() {
        return token;
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
                '}';
    }
}
