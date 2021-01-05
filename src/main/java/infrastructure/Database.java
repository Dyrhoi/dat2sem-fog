package infrastructure;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;

public class Database {
    private String url = "jdbc:mysql://localhost:3306/fog?serverTimezone=CET";
    private String user = "fog";
    private boolean logging = true;

    private final int version = 14;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (getDatabaseVersion() != getEnvironmentVersion()) {
                throw new IllegalStateException("Database in wrong state, expected:"
                        + getEnvironmentVersion() + ", got: " + getDatabaseVersion());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Database(boolean isMigrating) {
        if(isMigrating) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Should only really used for Test Database setup.
    public Database(String url, String user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.url = url;
            this.user = user;
            this.logging = false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, null);
    }

    public int getEnvironmentVersion() {
        return version;
    }

    public int getDatabaseVersion() {
        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT value FROM properties WHERE name = 'version';");
            if (rs.next()) {
                String column = rs.getString("value");
                return Integer.parseInt(column);
            } else {
                System.err.println("No version in properties.");
                return -1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public void runMigrations() throws IOException, SQLException {
        int version = this.getDatabaseVersion();
        while (version < this.getEnvironmentVersion()) {
            if(logging) System.out.printf("Current DB version %d is smaller than expected %d\n", version, this.getEnvironmentVersion());
            runMigration(version + 1);
            int new_version = this.getDatabaseVersion();
            if (new_version > version) {
                version = new_version;
                if(logging) System.out.println("Updated database to version: " + new_version);
            } else {
                throw new RuntimeException("Something went wrong, version not increased: " + new_version);
            }
        }
    }

    public void runMigration(int i) throws IOException, SQLException {
        String migrationFile = String.format("database/migrations/%d.sql", i);
        System.out.println("Running migration: " + migrationFile);
        InputStream stream = DatabaseMigrationEntry.class.getClassLoader().getResourceAsStream(migrationFile);
        if (stream == null) {
            System.out.println("Migration file, does not exist: " + migrationFile);
            throw new FileNotFoundException(migrationFile);
        }
        try(Connection conn = this.getConnection()) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            if (!logging)
                runner.setLogWriter(null);
            runner.setStopOnError(true);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            conn.commit();
        }
        System.out.println("Done running migration");
    }
}
