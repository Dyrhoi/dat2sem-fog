package domain.user.customer;

import domain.user.customer.exceptions.CustomerNotFoundException;

public interface CustomerRepository {
    Customer getCustomer(int customerId) throws CustomerNotFoundException;
    Customer getCustomer(String email) throws CustomerNotFoundException;
}
