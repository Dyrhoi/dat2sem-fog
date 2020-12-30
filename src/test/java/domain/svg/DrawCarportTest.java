package domain.svg;

import domain.carport.Carport;
import domain.carport.Shed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawCarportTest {

    Carport carport;
    DrawCarport drawCarport;

    @BeforeEach
    void setUp() {
        carport = new Carport(-1, 600, 800, null, null, -1);
        drawCarport = new DrawCarport(carport);
    }

    @Test
    void drawSVG() {
        System.out.println(drawCarport.drawSVG());
    }
}
