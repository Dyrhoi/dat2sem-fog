package domain.customer;

import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID uuid;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    public Customer(UUID uuid, String firstname, String lastname, String email, String phone) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(uuid, customer.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
