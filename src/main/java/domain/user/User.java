package domain.user;

import java.util.Objects;

public class User {
    private final int id;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phone;
    private final Address address;
    private final Type type;

    public User(int id, String firstname, String lastname, String email, String phone, Address address, Type type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
    }

    public int getId() {
        return id;
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

    public Address getAddress() {
        return address;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public String toString() {
        return "User{" +
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

    public static class Address {
        private final String address;
        private final String city;
        private final String postalCode;

        public Address(String address, String city, String postalCode) {
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

    public enum Type {
        SALESMAN,
        CLIENT
    }
}
