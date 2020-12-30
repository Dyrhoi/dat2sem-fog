package domain.svg;

import domain.carport.carportMaterials.MaterialCalculations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaterialSpecs {

    public static final double PILLAR_LENGTH = 9.7;
    public static final double PILLAR_WIDTH = 9.7;
    public static final double STRAP_WIDTH = 4.5;
    public static final double RAFTER_WIDTH = 4.5;
    public static final double SHED_CLADDING_WIDTH = 3.8;
    public static final double PERFORATED_TAPE_WIDTH = 2.0;
    public static final double ROOF_FRAME_WIDTH = 2.5;
    public static final double PILLAR_FRONT_EDGE_DISTANCE = 110;
    public static final double PILLAR_BACK_EDGE_DISTANCE = 30;
    public static final double PILLAR_SIDE_EDGE_DISTANCE = 35;

    private int strapLength;
    private int shedCladdingBox;
    private int perforatedTapeLength;
    private int roofFrameBox;

    /*
    Roof box:
    length x width.

    Pillars:
    First pillar 110 from Roof box edge front and 35 from side edge.
    Last pillar 30 from Roof box edge back and 35 from side edge.
    If other pillars, distance between first and last pillar divided by number of pillars + 1.

    Straps:
    Goes from end to end and runs through pillars

    Rafters:
    1 in each end. Distance between length from ends divided by number of rafters + 1.

     */

    public static List<Coordinate> getStraps(int length, int width) {
        List<Coordinate> coordinates = new ArrayList<>();

        int pillarRows = MaterialCalculations.BaseCarport.pillarRows(width);
        int remainingPillarRows = pillarRows - 2;

        for (int i = 1; i <= pillarRows; i++) {

            //int y = 400 * i +PILLAR_SIDE_EDGE_DISTANCE;
            double y = (width - PILLAR_SIDE_EDGE_DISTANCE * 2) / remainingPillarCalcSpace(remainingPillarRows) * (pillarRows - i) + PILLAR_SIDE_EDGE_DISTANCE;
            if (i == 1) y = PILLAR_SIDE_EDGE_DISTANCE;
            if (i == pillarRows) y = width - PILLAR_SIDE_EDGE_DISTANCE;
            y -= STRAP_WIDTH / 2;

            coordinates.add(new Coordinate(0, y));
        }

        return coordinates;
    }

    public static List<Coordinate> getPillars(int length, int width) {
        List<Coordinate> coordinates = new ArrayList<>();

        int pillarRows = MaterialCalculations.BaseCarport.pillarRows(width);
        int pillarAmount = MaterialCalculations.BaseCarport.pillars(length, width);

        System.out.println(pillarRows);
        System.out.println(pillarAmount);

        int pillarsPerRow = pillarAmount/pillarRows;

        int remainingPillarRows = pillarRows - 2;

        for (int i = 1; i <= pillarRows; i++) {

            //int y = 400 * i +PILLAR_SIDE_EDGE_DISTANCE;
            double y = (width - PILLAR_SIDE_EDGE_DISTANCE * 2) / remainingPillarCalcSpace(remainingPillarRows) * (pillarRows - i) + PILLAR_SIDE_EDGE_DISTANCE;
            if (i == 1) y = PILLAR_SIDE_EDGE_DISTANCE;
            if (i == pillarRows) y = width - PILLAR_SIDE_EDGE_DISTANCE;
            y -= PILLAR_WIDTH / 2;

            //Set first pillar.
            coordinates.add(new Coordinate(PILLAR_FRONT_EDGE_DISTANCE, y));

            //Set remaining pillars.
            int remainingPillarsPerRow = pillarsPerRow - 2;

            for (int j = 1; j <= remainingPillarsPerRow; j++) {
                double x = (length - PILLAR_FRONT_EDGE_DISTANCE - PILLAR_BACK_EDGE_DISTANCE) / remainingPillarCalcSpace(remainingPillarsPerRow) * j + PILLAR_FRONT_EDGE_DISTANCE;
                x -= PILLAR_LENGTH / 2;
                coordinates.add(new Coordinate(x, y));
            }

            //Set last pillar.
            coordinates.add(new Coordinate(length - PILLAR_BACK_EDGE_DISTANCE, y));
        }

        return coordinates;
    }

    public static List<Coordinate> getRafters(int length, int width) {
        List<Coordinate> coordinates = new ArrayList<>();

        double raftersAmount = MaterialCalculations.BaseCarport.rafters(length, width);
        double distance = (width / raftersAmount) - (RAFTER_WIDTH / 2);
        double x = 0;
        for(int i = 1; i <= raftersAmount; i++) {
            x = (distance * i);
            //x += distance;
            coordinates.add(new Coordinate(x, 0));
        }
        System.out.println(coordinates);
        return coordinates;
    }

    public static List<HashMap<String, Double>> getPerforatedTape(int length, int width) {
        List<HashMap<String, Double>> coordinateSets = new ArrayList<>();

        List<Coordinate> rafters = getRafters(length, width);
        Coordinate firstRafter = getRafters(length, width).get(0);
        Coordinate lastRafter = getRafters(length, width).get(rafters.size() - 1);

        HashMap<String, Double> firstTapeLine= new HashMap<>();
        firstTapeLine.put("x1", firstRafter.x);
        firstTapeLine.put("x2", lastRafter.x);
        firstTapeLine.put("y1", PILLAR_SIDE_EDGE_DISTANCE);
        firstTapeLine.put("y2", width - PILLAR_SIDE_EDGE_DISTANCE);


        HashMap<String, Double> secondTapeLine = new HashMap<>();
        secondTapeLine.put("x1", firstRafter.x);
        secondTapeLine.put("x2", lastRafter.x);
        secondTapeLine.put("y1", width - PILLAR_SIDE_EDGE_DISTANCE);
        secondTapeLine.put("y2", PILLAR_SIDE_EDGE_DISTANCE);

        coordinateSets.add(firstTapeLine);
        coordinateSets.add(secondTapeLine);

        return coordinateSets;
    }

    private static int remainingPillarCalcSpace(int pillarAmount){
        return pillarAmount + 1;
    }

    public static class Coordinate {
        private final double x;
        private final double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
