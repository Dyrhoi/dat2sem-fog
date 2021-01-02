package domain.svg;

import domain.carport.Carport;
import domain.carport.Shed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrawCarportTest {

    Carport carport;
    DrawCarport drawCarport;

    @BeforeEach
    void setUp() {
        carport = new Carport(-1, new Shed (-1, 700, 700),800, 800, Carport.roofTypes.FLAT, null, 15);
        drawCarport = new DrawCarport(carport);
    }

    @Test
    void drawSVG() {
        System.out.println(drawCarport.drawSVGWithViewBox());
    }
}
