package domain.order;

import domain.carport.Carport;
import domain.customer.Customer;
import domain.shed.Shed;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.routines.EmailValidator;
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
        ValidationErrorException valEx = new ValidationErrorException();

        //What can go wrong?
        //Customer:
        if(customer == null)
            valEx.addProblem("customer", "We did not receive all expected inputs.");
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(!emailValidator.isValid(customer.getEmail()))
            valEx.addProblem("email", "");


        valEx.validate();
    }

    public Order validateAndCommit() throws ValidationErrorException {
        validate();
        return commit();
    }

    protected abstract Order commit();
}
