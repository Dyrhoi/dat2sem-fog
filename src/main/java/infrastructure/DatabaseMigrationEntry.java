package infrastructure;

import api.API;
import domain.user.User;
import domain.user.dao.UserDAO;
import domain.user.sales_representative.SalesRepresentativeExistsException;
import domain.user.sales_representative.SalesRepresentativeFactory;
import validation.ValidationErrorException;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseMigrationEntry {
    private final Database db;

    public DatabaseMigrationEntry() {
        this.db = new Database(true);
    }

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseMigrationEntry migrate = new DatabaseMigrationEntry();
        migrate.db.runMigrations();
        /*
        * Create sales rep account: sales_andersen@fog.dk - password123
        * */
        API api = new API(null, null, new UserDAO(migrate.db));
        try {
            SalesRepresentativeFactory salesRepresentativeFactory = api.createSalesRepresentative();
            salesRepresentativeFactory.setFirstName("Anders");
            salesRepresentativeFactory.setLastName("Andersen");

            salesRepresentativeFactory.setEmail("sales_andersen@fog.dk");
            salesRepresentativeFactory.setPhone("+4588888888");

            salesRepresentativeFactory.setPassword("password123");
            salesRepresentativeFactory.setPasswordConfirm("password123");

            User.Address address = new User.Address("Kongevejen 27 A", "Helsingør", "3000");
            salesRepresentativeFactory.setAddress(address);

            salesRepresentativeFactory.validateAndCommit();
            System.out.println("Could not find sales representative in Database, creating one: sales_andersen@fog.dk - password123");
        } catch (SalesRepresentativeExistsException | ValidationErrorException e) {
            System.out.println("Did not generate sales representative, one already exists. sales_andersen@fog.dk - password123");
        }
    }
}
