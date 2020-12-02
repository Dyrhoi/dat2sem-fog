package domain.material.dao;

import domain.material.MaterialRepository;
import infrastructure.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO implements MaterialRepository {
    private final Database db;

    public MaterialDAO(Database db) {
        this.db = db;
    }

    @Override
    public List<String> getRootTypes() {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM roof_materials");
            ResultSet resultSet = query.executeQuery();

            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
            return list;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("SHIT HAPPENS");
    }
}
