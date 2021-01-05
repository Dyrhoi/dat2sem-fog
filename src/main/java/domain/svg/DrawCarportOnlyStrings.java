package domain.svg;

import domain.carport.Carport;

import java.util.HashMap;
import java.util.List;

public class DrawCarportOnlyStrings {
    private static final MaterialSpecs MATERIAL_SPECS;

    static {
        MATERIAL_SPECS = new MaterialSpecs();
    }

    private final Carport carport;
    private final StringBuilder elements;

    public DrawCarportOnlyStrings(Carport carport)  {
        this.carport = carport;

        elements = new StringBuilder();
    }

    public String drawSVG() {
        StringBuilder root = new StringBuilder("<svg viewBox=\"0 0 " + carport.getLength() + " " + carport.getWidth() + "\">");
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
        root.append(elements).append("</svg>");
        return root.toString();
    }

    public String drawSVGWithViewBox() {
        return drawSVG();
    }

    private void drawRoof() {
        String roof = createSquare(0, 0, carport.getLength(), carport.getWidth());
        elements.append(roof);
    }

    private void drawShedBox() {
        String shed = createSquare(
                MaterialSpecs.topRightPillar.getX() - carport.getShed().getLength() + MaterialSpecs.PILLAR_LENGTH,
                MaterialSpecs.topRightPillar.getY(),
                carport.getShed().getLength(),
                carport.getShed().getWidth() + MaterialSpecs.PILLAR_WIDTH);
        elements.append(shed);
    }

    private void drawShedPillars() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getShedPillars(carport.getShed().getLength(), carport.getShed().getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            String shedPillar = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    MaterialSpecs.PILLAR_LENGTH,
                    MaterialSpecs.PILLAR_WIDTH);
            elements.append(shedPillar);
        }
    }

    private void drawPillars() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getPillars(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            String pillar = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    MaterialSpecs.PILLAR_LENGTH,
                    MaterialSpecs.PILLAR_WIDTH);
            elements.append(pillar);
        }
    }

    private void drawStraps() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getStraps(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            String strap = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    carport.getLength(),
                    MaterialSpecs.STRAP_WIDTH);
            elements.append(strap);
        }
    }

    private void drawRafters() {
        List<MaterialSpecs.Coordinate> coordinates = MaterialSpecs.getRafters(carport.getLength(), carport.getWidth());
        for (MaterialSpecs.Coordinate coordinate : coordinates) {
            String rafter = createSquare(
                    coordinate.getX(),
                    coordinate.getY(),
                    MaterialSpecs.RAFTER_WIDTH,
                    carport.getWidth());
            elements.append(rafter);
        }
    }

    private void drawPerforatedTape() {
        List<HashMap<String, Double>> coordinates = MaterialSpecs.getPerforatedTape(carport.getLength(), carport.getWidth());
        for (HashMap<String, Double> coordinateSets : coordinates) {
            String line = createLine(coordinateSets.get("x1"), coordinateSets.get("y1"), coordinateSets.get("x2"), coordinateSets.get("y2"));
            elements.append(line);
        }
    }

    private String createSquare(double x, double y, double length, double width) {
        return "<rect x=\"" + String.valueOf(x)
                + "\" y=\"" + String.valueOf(y)
                + "\" height=\"" + String.valueOf(width)
                + "\" width=\"" + String.valueOf(length)
                + "\" />";
    }

    private String createLine(double x1, double y1, double x2, double y2) {
        return "<line x1=\"" + String.valueOf(x1)
                + "\" y1=\"" + String.valueOf(y1)
                + "\" x2=\"" + String.valueOf(x2)
                + "\" y2=\"" + String.valueOf(y2)
                + "\" stroke-dasharray=\"10 10\" />";
    }
}
