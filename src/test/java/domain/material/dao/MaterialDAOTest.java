package domain.material.dao;

import domain.carport.Carport;
import domain.carport.OrderMaterial;
import domain.carport.Shed;
import domain.order.Order;
import infrastructure.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaterialDAOTest {
    MaterialDAO md;
    Carport carport;
    Order order;
    @BeforeEach
    void setUp() {
        carport = new Carport(-1, new Shed(-1, 400, 400), 600, 780, Carport.roofTypes.FLAT, null, 16);
        order = new Order(null, carport, carport.getShed(), null, null);
        md = new MaterialDAO(new Database());
    }

    @Test
    void getOrderMaterials() {

        for (int i = 1; i <= 4; i++) {
            for (OrderMaterial orderMaterial : md.getOrderMaterials(order)) {
                int id = orderMaterial.getConstructionMaterial().getMaterial_types_id();

                if (id == i) {
                    System.out.println(orderMaterial);
                }
            }
        }
    }
}