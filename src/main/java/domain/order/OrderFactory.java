package domain.order;

import domain.carport.Carport;
import domain.customer.Customer;
import domain.shed.Shed;
import validation.ValidationErrorException;

import java.util.UUID;

public abstract class OrderFactory {
    private UUID uuid;
    private Customer customer;
    private Carport carport;
    private Shed shed;
    private String token;

    public void setCarport(Carport carport) {
        this.carport = carport;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setShed(Shed shed) {
        this.shed = shed;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void validate() throws ValidationErrorException {
        ValidationErrorException validationErrorException = new ValidationErrorException();

        /*
        * What can go wrong?
        * */


        validationErrorException.validate();
    }

    public Order validateAndCommit() throws ValidationErrorException {
        validate();
        return commit();
    }

    protected abstract Order commit();
}
