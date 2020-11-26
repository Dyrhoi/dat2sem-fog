package infrastructure;

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
    }
}
