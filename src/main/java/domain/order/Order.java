package domain.order;

import domain.carport.Carport;
import domain.carport.Shed;
import domain.user.customer.Customer;
import domain.user.sales_representative.SalesRepresentative;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Order {
    public static final double evenOffer = 20000;
    private final UUID uuid;
    private final Carport carport;
    private final Shed shed;
    private final Customer customer;
    private LocalDateTime date;
    private String token;
    private List<Offer> offers;
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

    public List<Offer> getOffers() {
        return offers;
    }

    public Offer getMostRecentOffer() {
        try {
            return Collections.max(offers, Comparator.comparing(Offer::getCreatedAt));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Offer hasAcceptedOffer() {
        return offers.stream().filter(Offer::isAccepted).findFirst().get();
    }

    public void addOffer(Offer offer) {
        this.offers.add(offer);
    }

    public Offer getOfferByArrayId(int arrayId) {
        try {
            return offers.get(arrayId);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
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

    public static class Offer {
        private final int id;
        private final LocalDateTime createdAt;
        private final int price;
        private boolean isAccepted;

        public Offer(int id, LocalDateTime createdAt, int price, boolean isAccepted) {
            this.id = id;
            this.createdAt = createdAt;
            this.price = price;
            this.isAccepted = isAccepted;
        }

        public void setAccepted(boolean accepted) {
            isAccepted = accepted;
        }

        public int getId() {
            return id;
        }

        public boolean isAccepted() {
            return isAccepted;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Offer offer = (Offer) o;
            return id == offer.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
