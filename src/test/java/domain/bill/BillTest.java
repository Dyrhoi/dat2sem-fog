package domain.bill;

import domain.carport.Carport;
import domain.carport.Shed;
import domain.material.dao.MaterialDAO;
import domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BillTest {
    MaterialDAO md;
    Carport carport;
    Order order;
    Bill bill = new Bill();

    BillTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        carport = new Carport(-1, new Shed(-1, 400, 400), 600, 780, Carport.roofTypes.FLAT, null, 16);
        order = new Order(null, carport, carport.getShed(), null, null);
    }

    @Test
    void testPDF() throws IOException {
        //bill.generatePDF(order);
    }
}