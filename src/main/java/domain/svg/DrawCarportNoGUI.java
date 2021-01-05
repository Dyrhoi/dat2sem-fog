package domain.svg;

import api.Util;
import domain.carport.Carport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

public class DrawCarportNoGUI {
    private static final MaterialSpecs MATERIAL_SPECS;

    static {
        MATERIAL_SPECS = new MaterialSpecs();
    }

    private final Carport carport;
    private final Document svg;
    private final Element root;

    private final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    private final DocumentBuilder DOCUMENT_BUILDER = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();

    public DrawCarportNoGUI(Carport carport) throws ParserConfigurationException, IOException, SAXException {
        this.carport = carport;

        InputSource is = new InputSource(new StringReader("<svg></svg>"));

        this.svg = DOCUMENT_BUILDER.parse(is);
        svg.getDocumentElement().normalize();

        this.root = svg.getDocumentElement();
        root.setAttribute("viewBox", "0 0 " + carport.getLength() + " " + carport.getWidth());
        System.out.println(svg);
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
        return Util.toString(svg);
    }

    public String drawSVGWithViewBox() {
        return drawSVG();
    }

    private void drawRoof() {
        Element roof = createSquare(0, 0, carport.getLength(), carport.getWidth());
        root.appendChild(roof);
    }

    private void drawShedBox() {
        Element shed = createSquare(
                MaterialSpecs.topRightPillar.getX() - carport.getShed().getLength() + MaterialSpecs.PILLAR_LENGTH,
                MaterialSpecs.topRightPillar.getY(),
                carport.getShed().getLength(),
                carport.getShed().getWidth() + MaterialSpecs.PILLAR_WIDTH);
        root.appendChild(shed);
    }

    private void drawShedPillars() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getShedPillars(carport.getShed().getLength(), carport.getShed().getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Element shedPillar = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    MaterialSpecs.PILLAR_LENGTH,
                    MaterialSpecs.PILLAR_WIDTH);
            root.appendChild(shedPillar);
        }
    }

    private void drawPillars() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getPillars(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Element pillar = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    MaterialSpecs.PILLAR_LENGTH,
                    MaterialSpecs.PILLAR_WIDTH);
            root.appendChild(pillar);
        }
    }

    private void drawStraps() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getStraps(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Element strap = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    carport.getLength(),
                    MaterialSpecs.STRAP_WIDTH);
            root.appendChild(strap);
        }
    }

    private void drawRafters() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getRafters(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            Element rafter = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    MaterialSpecs.RAFTER_WIDTH,
                    carport.getWidth());
            root.appendChild(rafter);
        }
    }

    private void drawPerforatedTape() {
        List<HashMap<String, Double>> coordinates = MaterialSpecs.getPerforatedTape(carport.getLength(), carport.getWidth());
        for (HashMap<String, Double> coordinateSets : coordinates) {
            Element line = createLine(coordinateSets.get("x1"), coordinateSets.get("y1"), coordinateSets.get("x2"), coordinateSets.get("y2"));
            root.appendChild(line);
        }
    }

    private Element createSquare(double x, double y, double length, double width) {
        Element el = svg.createElement("rect");
        el.setAttribute("x", String.valueOf(x));
        el.setAttribute("y", String.valueOf(y));
        el.setAttribute("height", String.valueOf(width));
        el.setAttribute("width", String.valueOf(length));
        return el;
    }

    private Element createLine(double x1, double y1, double x2, double y2) {
        Element el = svg.createElement("line");
        el.setAttribute("stroke-dasharray", "10 10");
        el.setAttribute("x1", String.valueOf(x1));
        el.setAttribute("y1", String.valueOf(y1));
        el.setAttribute("x2", String.valueOf(x2));
        el.setAttribute("y2", String.valueOf(y2));
        return el;
    }

}