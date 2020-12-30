package domain.svg;

import domain.carport.Carport;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

public class DrawCarport {
    private static final MaterialSpecs MATERIAL_SPECS;

    static {
        MATERIAL_SPECS = new MaterialSpecs();
    }

    private final Carport carport;
    private final SVGGraphics2D g2;

    public DrawCarport (Carport carport) {
        this.carport = carport;
        this.g2 = new SVGGraphics2D(carport.getWidth(), carport.getLength());
    }

    public String drawSVG() {
        drawRoof();
        drawStraps();
        drawPerforatedTape();
        drawRafters();
        drawPillars();

        return g2.getSVGElement();
    }

    private void drawRoof() {
        Rectangle2D roof = new Rectangle2D.Double(0, 0, carport.getLength(), carport.getWidth());
        addObject(roof);
    }

    private void drawPillars() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getPillars(carport.getWidth(), carport.getLength());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Rectangle2D pillar = new Rectangle2D.Double(coordinate.getX(), coordinate.getY(), MaterialSpecs.PILLAR_LENGTH, MaterialSpecs.PILLAR_WIDTH);
            addObject(pillar);
        }
    }

    private void drawStraps() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getStraps(carport.getWidth(), carport.getLength());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Rectangle2D strap = new Rectangle2D.Double(coordinate.getX(), coordinate.getY(), carport.getWidth(), MaterialSpecs.STRAP_WIDTH);
            addObject(strap);
        }
    }

    private void drawRafters() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getRafters(carport.getWidth(), carport.getLength());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Rectangle2D rafter = new Rectangle2D.Double(coordinate.getX(), coordinate.getY(), MaterialSpecs.RAFTER_WIDTH, carport.getLength());
            addObject(rafter);
        }
    }

    private void drawPerforatedTape() {
        List<HashMap<String, Double>> coordinates = MaterialSpecs.getPerforatedTape(carport.getWidth(), carport.getLength());
        for (HashMap<String, Double> coordinateSets : coordinates) {
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {20.0f, 20.0f}, 0.0f));
            Line2D line = new Line2D.Double(coordinateSets.get("x1"), coordinateSets.get("y1"), coordinateSets.get("x2"), coordinateSets.get("y2"));
            g2.draw(line);
        }
    }

    private void addObject(Shape shape) {
        g2.setColor(Color.WHITE);
        g2.fill(shape);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(1));
        g2.draw(shape);
    }

}