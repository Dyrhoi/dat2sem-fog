package domain.order;

import api.Util;
import com.mysql.cj.util.StringUtils;
import domain.carport.Carport;
import domain.user.customer.Customer;
import domain.carport.Shed;
import org.apache.commons.validator.routines.EmailValidator;
import validation.ValidationErrorException;

import java.util.Objects;
import java.util.UUID;

public abstract class OrderFactory {
    private UUID uuid;
    private Customer customer;
    private Carport carport;
    private Shed shed;
    private String token;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = Objects.requireNonNullElse(note, "Ingen ekstra bemærkninger noteret.");
    }

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

    public UUID getUuid() {
        return uuid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Carport getCarport() {
        return carport;
    }

    public Shed getShed() {
        return shed;
    }

    public String getToken() {
        return token;
    }

    public void validate() throws ValidationErrorException {
        ValidationErrorException valEx = new ValidationErrorException();
        //TODO send fejlbesked til brugeren ved fejl.
        //What can go wrong?
        //Customer:
        validateCustomer(valEx);



        valEx.validate();
    }

    private void validateCustomer(ValidationErrorException valEx) {
        if(customer == null)
            valEx.addProblem("customer", "We did not receive all expected inputs.");
        EmailValidator emailValidator = EmailValidator.getInstance();

        if(!emailValidator.isValid(customer.getEmail()))
            valEx.addProblem("email", "E-mail is not valid.");

        if(StringUtils.isNullOrEmpty(customer.getFirstname()) || StringUtils.isNullOrEmpty(customer.getLastname()))
            valEx.addProblem("name", "First and last name should be set.");

        String phoneNumberStrip = Util.phoneNumberStrip(customer.getPhone());
        try {
            Integer.parseInt(phoneNumberStrip);
        } catch (NumberFormatException e) {
            valEx.addProblem("phone", "Not a valid phone number.");
        }

        if(StringUtils.isNullOrEmpty(customer.getAddress().getAddress()) ||
                StringUtils.isNullOrEmpty(customer.getAddress().getCity()) ||
                StringUtils.isNullOrEmpty(customer.getAddress().getPostalCode()))
            valEx.addProblem("address", "Invalid address, empty fields not allowed.");
    }

    private void validateCarport(ValidationErrorException valEx) {
        if(customer == null)
            valEx.addProblem("customer", "We did not receive all expected inputs.");
        EmailValidator emailValidator = EmailValidator.getInstance();

        if(!emailValidator.isValid(customer.getEmail()))
            valEx.addProblem("email", "E-mail is not valid.");

        if(StringUtils.isNullOrEmpty(customer.getFirstname()) || StringUtils.isNullOrEmpty(customer.getLastname()))
            valEx.addProblem("name", "First and last name should be set.");

        String phoneNumberStrip = Util.phoneNumberStrip(customer.getPhone());
        try {
            Integer.parseInt(phoneNumberStrip);
        } catch (NumberFormatException e) {
            valEx.addProblem("phone", "Not a valid phone number.");
        }

        if(StringUtils.isNullOrEmpty(customer.getAddress().getAddress()) ||
                StringUtils.isNullOrEmpty(customer.getAddress().getCity()) ||
                StringUtils.isNullOrEmpty(customer.getAddress().getPostalCode()))
            valEx.addProblem("address", "Invalid address, empty fields not allowed.");
    }

    public Order validateAndCommit() throws ValidationErrorException {
        validate();
        return commit();
    }

    protected abstract Order commit() ;
            //TODO Dyrhoi
}
