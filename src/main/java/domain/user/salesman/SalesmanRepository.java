package domain.user.salesman;


public interface SalesmanRepository {

    SalesmanFactory createSalesman() throws SalesmanExistsException;

    Salesman authorizeSalesman(String email, String password) throws SalesmanNonMatchingPasswordException, SalesmanNotFoundException;

}
