package domain.user.sales_representative;

import domain.user.User;
import org.apache.commons.validator.routines.EmailValidator;
import validation.ValidationErrorException;

public abstract class SalesRepresentativeFactory {
    private String firstName;
    private String lastName;
    private String phone;
    private User.Address address;
    private String email;
    private String password;
    private String passwordConfirm;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User.Address getAddress() {
        return address;
    }

    public void setAddress(User.Address address) {
        this.address = address;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void validate() throws ValidationErrorException {
        ValidationErrorException validationErrorException = new ValidationErrorException();
        if(password == null || password.length() < 3)
            validationErrorException.addProblem("password", "Adgangskoden kan ikke være mindre end 3 tegn.");
        if(!password.equals(passwordConfirm))
            validationErrorException.addProblem("password", "Adgangskoderne matcher ikke.");
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(email == null || !emailValidator.isValid(email))
            validationErrorException.addProblem("email", "Denne e-mail er ikke gyldig.");

        validationErrorException.validate();
    }

    public SalesRepresentative validateAndCommit() throws ValidationErrorException, SalesRepresentativeExistsException {
        validate();
        return commit();
    }

    protected abstract SalesRepresentative commit() throws SalesRepresentativeExistsException;
}