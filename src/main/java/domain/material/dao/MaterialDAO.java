package domain.material.dao;

import domain.carport.RoofMaterial;
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
    public List<RoofMaterial> getRoofMaterials() {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM roof_materials");
            ResultSet resultSet = query.executeQuery();

            ArrayList<RoofMaterial> list = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                list.add(new RoofMaterial(name, type));
            }
            return list;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("Roof material could not be loaded");
    }

    @Override
    public String getRoofMaterial(int id) {
        String roof_material = null;
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT name FROM roof_materials WHERE id = ?");
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                roof_material = rs.getString("name");
            }
            return roof_material;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("Roof material could not be loaded");
    }
}
