package domain.material.dao;

import domain.carport.Carport;
import domain.carport.OrderMaterial;
import domain.order.Order;
import infrastructure.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialDAOTest {
    MaterialDAO md;
    Carport carport;
    Order order;
    @BeforeEach
    void setUp() {
        carport = new Carport(-1, null, 600, 780, Carport.roofTypes.FLAT, null, 6);
        order = new Order(null, carport, null, null, null);
        md = new MaterialDAO(new Database());
    }

    @Test
    void getOrderMaterials() {
        for (OrderMaterial orderMaterial : md.getOrderMaterials(order)) {
            System.out.println(orderMaterial);
        }
    }
}