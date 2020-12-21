package domain.user;

import domain.user.exceptions.UserNotFoundException;
import domain.user.sales_representative.*;

public interface UserRepository {
    User getUser(int id) throws UserNotFoundException;
    User getUser(String email) throws UserNotFoundException;

    SalesRepresentativeFactory createSalesRepresentative() throws SalesRepresentativeExistsException;
    SalesRepresentative authorizeSalesRepresentative(String email, String password) throws SalesRepresentativeNonMatchingPasswordException, SalesRepresentativeNotFoundException;
}
