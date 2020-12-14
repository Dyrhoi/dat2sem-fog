package domain.user.salesman.dao;

import infrastructure.Database;

public class SalesmanDAO {
    private final Database database;


    public SalesmanDAO(Database database) {
        this.database = database;
    }
}
