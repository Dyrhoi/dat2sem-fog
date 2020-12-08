package domain.customer;

import java.util.Objects;

public class Customer {

    private final int id;

    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phone;
    private final Address address;
    private String comment;

    public Customer(int id, String firstname, String lastname, String email, String phone, Address address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getComment() { return comment; }

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

    public Address getAddress() {
        return address;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Address{
        private final String address;
        private final String city;
        private final String postalCode;

        public Address(String address, String city, String postalCode){
            this.address = address;
            this.city = city;
            this.postalCode = postalCode;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", postalCode='" + postalCode + '\'' +
                    '}';
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getPostalCode() {
            return postalCode;
        }
    }
}
