package domain.material.dao;

import domain.carport.*;
import domain.carport.carportMaterials.MaterialCalculations;
import domain.material.MaterialRepository;
import domain.order.Order;
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
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Carport.roofTypes type = Carport.roofTypes.valueOf(resultSet.getString("type"));
                list.add(new RoofMaterial(id, name, type));
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

    public ConstructionMaterialList materialsRepo() {

        ArrayList<ConstructionMaterial> repo = new ArrayList<>();

        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM materials inner join material_types on materials.material_types_id = material_types.id");
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("materials.id");
                String name = resultSet.getString("materials.name");
                String materialType = resultSet.getString("material_types.description");
                String description = resultSet.getString("materials.description");
                ConstructionMaterial tmpCM = new ConstructionMaterial(id, name, materialType, description);
                repo.add(tmpCM);
            }
        } catch (RuntimeException | SQLException e) {
            e.printStackTrace();
        }
        return new ConstructionMaterialList(repo);
    }

    public List<OrderMaterial> getOrderMaterials(Order order) {
        Carport carport = order.getCarport();
        List<OrderMaterial> orderList = new ArrayList<>();

        if (carport.getRoof() == Carport.roofTypes.ANGLED) {
            if (order.getShed() != null) {
                orderList = MaterialCalculations.calcAngledShed(materialsRepo(), order);
            } else {
                orderList = MaterialCalculations.calcAngled(materialsRepo(), order);
            }
        } else if (carport.getRoof() == Carport.roofTypes.FLAT) {
            if (order.getShed() != null) {
                orderList = MaterialCalculations.calcFlatShed(materialsRepo(), order);
            } else {
                orderList = MaterialCalculations.calcFlat(materialsRepo(), order);
            }
        }

        return orderList;
    }
}


