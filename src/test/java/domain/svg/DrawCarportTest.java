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
        carport = new Carport(-1, new Shed(-1, 400, 400), 600, 780, Carport.roofTypes.FLAT, null, 16);
        //drawCarport = new DrawCarport(carport);
    }

    @Test
    void drawSVG() {
        //System.out.println(drawCarport.drawSVGWithViewBox());
    }
}
