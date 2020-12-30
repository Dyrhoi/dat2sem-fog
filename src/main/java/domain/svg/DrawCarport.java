package domain.svg;

import api.Util;
import domain.carport.Carport;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.ViewBox;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

public class DrawCarport {
    private static final MaterialSpecs MATERIAL_SPECS;

    static {
        MATERIAL_SPECS = new MaterialSpecs();
    }

    private final Carport carport;
    private final SVGGraphics2D g2;

    public DrawCarport(Carport carport) {
        this.carport = carport;
        this.g2 = new SVGGraphics2D(carport.getLength(), carport.getWidth());
    }

    public String drawSVG() {
        drawRoof();
        drawPillars();
        if (carport.getShed() != null) {
            drawShedBox();
            drawShedPillars();
        }
        if (carport.getRoof() == Carport.roofTypes.FLAT) {
            drawPerforatedTape();
        }
        drawStraps();
        drawRafters();
        drawPillars();
        return g2.getSVGElement();
    }

    public String drawSVGWithViewBox() {
        String svgString = drawSVG();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(svgString));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            doc.getDocumentElement().setAttribute("viewBox", "0 0 " + carport.getLength() + " " + carport.getWidth());
            return Util.toString(doc);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            return svgString;
        }
    }

    private void drawRoof() {
        Rectangle2D roof = new Rectangle2D.Double(0, 0, carport.getLength(), carport.getWidth());
        addObject(roof);
    }

    private void drawShedBox() {
        Rectangle2D shed = new Rectangle2D.Double(MaterialSpecs.topRightPillar.getX() - carport.getShed().getLength() + MaterialSpecs.PILLAR_LENGTH, MaterialSpecs.topRightPillar.getY(), carport.getShed().getLength(), carport.getShed().getWidth() + MaterialSpecs.PILLAR_WIDTH);
        addObject(shed);
    }

    private void drawShedPillars() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getShedPillars(carport.getShed().getLength(), carport.getShed().getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Rectangle2D pillar = new Rectangle2D.Double(coordinate.getX(), coordinate.getY(), MaterialSpecs.PILLAR_LENGTH, MaterialSpecs.PILLAR_WIDTH);
            addObject(pillar);
        }
    }

    private void drawPillars() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getPillars(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Rectangle2D pillar = new Rectangle2D.Double(coordinate.getX(), coordinate.getY(), MaterialSpecs.PILLAR_LENGTH, MaterialSpecs.PILLAR_WIDTH);
            addObject(pillar);
        }
    }

    private void drawStraps() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getStraps(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Rectangle2D strap = new Rectangle2D.Double(coordinate.getX(), coordinate.getY(), carport.getLength(), MaterialSpecs.STRAP_WIDTH);
            addObject(strap);
        }
    }

    private void drawRafters() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getRafters(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Rectangle2D rafter = new Rectangle2D.Double(coordinate.getX(), coordinate.getY(), MaterialSpecs.RAFTER_WIDTH, carport.getWidth());
            addObject(rafter);
        }
    }

    private void drawPerforatedTape() {
        List<HashMap<String, Double>> coordinates = MaterialSpecs.getPerforatedTape(carport.getLength(), carport.getWidth());
        for (HashMap<String, Double> coordinateSets : coordinates) {
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{20.0f, 20.0f}, 0.0f));
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