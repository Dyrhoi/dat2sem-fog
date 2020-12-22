package domain.user.customer;

import domain.user.User;

public class Customer extends User {
    public Customer(int id, String firstname, String lastname, String email, String phone, Address address) {
        super(id, firstname, lastname, email, phone, address, Type.CUSTOMER);
    }
}
