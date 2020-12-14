package domain.carport.carportMaterials;

import domain.carport.Carport;
import domain.carport.Shed;

public class MaterialCalculations {
    private static final int NUMBER_OF_SIDES = 2;

    public static class BaseCarport {

        private int pillars;
        private int strops;
        private int rafters;
        private int topSternsSides;
        private int buttomSternsSides;
        private int topSternsEnds;
        private int buttomSternsEnds;
        private int rafterFittingLeft;
        private int rafterFittingRight;
        private int fittingScrewPackages;
        private int boardBolts;
        private int squareDiscs;
        private int perforatedTape = 2;
        private int sternScrewsPackages = 1;

        private static final int MINIMUM_NUMBER_OF_PILLARS = 2;
        private static final int MAXIMUM_PILLAR_DISTANCE_SIDES = 300;
        private static final int MAXIMUM_PILLAR_DISTANCE_ENDS = 600;
        private static final int MAXIMUM_STROP_DISTANCE = 600;
        private static final int MAXIMUM_RAFTER_DISTANCE = 60;
        private static final int STERN_SIDES_LENGTH = 540;
        private static final int STERN_ENDS_LENGTH = 360;
        private static final int FITTING_SCREW_PACKAGE_SIZE = 250;
        private static final int NUMBER_OF_SCREWS_PER_FITTING = 6;
        private static final int BOARD_BOLTS_PER_PILLAR = 2;
        private static final int SQUARE_DISCS_PER_PILLAR = 2;
        private static final int NUMBER_OF_SIDES = 2;

        public static int pillars(double length, double width) {
            return (int) Math.ceil((length / MAXIMUM_PILLAR_DISTANCE_SIDES) + MINIMUM_NUMBER_OF_PILLARS) * pillarRows(width);
        }

        public static int pillarRows(double width) {
            return (int) Math.ceil((width / MAXIMUM_PILLAR_DISTANCE_ENDS) + MINIMUM_NUMBER_OF_PILLARS);
        }

        public static int strops(double length, double width) {
            return (int) Math.ceil((length / MAXIMUM_STROP_DISTANCE) * pillarRows(width));
        }

        public static int rafterRows(double width) {
            return (int) Math.ceil((width / MAXIMUM_PILLAR_DISTANCE_ENDS) - 1);
        }

        public static int rafters(double length, double width) {
            return (int) Math.ceil((length / MAXIMUM_RAFTER_DISTANCE) * rafterRows(width));
        }

        public static int sideSterns(double length) {
            return (int) Math.ceil((length / STERN_SIDES_LENGTH) * NUMBER_OF_SIDES);
        }

        public static int endSterns(double width) {
            return (int) Math.ceil((width / STERN_ENDS_LENGTH) * NUMBER_OF_SIDES);
        }

        public static int rafterFitting(int rafters) {
            return rafters / NUMBER_OF_SIDES;
        }

        public int getPillars() {
            return pillars;
        }

        public int getStrops() {
            return strops;
        }

        public int getRafters() {
            return rafters;
        }

        public int getTopSternsSides() {
            return topSternsSides;
        }

        public int getButtomSternsSides() {
            return buttomSternsSides;
        }

        public int getTopSternsEnds() {
            return topSternsEnds;
        }

        public int getButtomSternsEnds() {
            return buttomSternsEnds;
        }

        public int getRafterFittingLeft() {
            return rafterFittingLeft;
        }

        public int getRafterFittingRight() {
            return rafterFittingRight;
        }

        public int getFittingScrewPackages() {
            return fittingScrewPackages;
        }

        public int getBoardBolts() {
            return boardBolts;
        }

        public int getSquareDiscs() {
            return squareDiscs;
        }

        public int getPerforatedTape() {
            return perforatedTape;
        }

        public int getSternScrewsPackages() {
            return sternScrewsPackages;
        }

        public static int fittingScrews(int rafters) {
            return (int) Math.ceil((rafters * NUMBER_OF_SCREWS_PER_FITTING)) / FITTING_SCREW_PACKAGE_SIZE;
        }

        public static int boardBolts(int pillars) {
            return pillars * BOARD_BOLTS_PER_PILLAR;
        }

        public static int squareDiscs(int pillars) {
            return pillars * SQUARE_DISCS_PER_PILLAR;
        }

        public BaseCarport(Carport carport) {
            double length = carport.getLength();
            double width = carport.getWidth();

            this.pillars = pillars(length, width);
            this.strops = strops(length, width);
            this.rafters = rafters(length, width);
            this.topSternsSides = sideSterns(length);
            this.buttomSternsSides = sideSterns(length);
            this.topSternsEnds = endSterns(width);
            this.buttomSternsEnds = endSterns(width);
            this.rafterFittingLeft = rafterFitting(rafters);
            this.rafterFittingRight = rafterFitting(rafters);
            this.perforatedTape = perforatedTape;
            this.sternScrewsPackages = sternScrewsPackages;
            this.fittingScrewPackages = fittingScrews(rafters);

            this.boardBolts = boardBolts(pillars);
            this.squareDiscs = squareDiscs(pillars);
        }
    }

    public static class FlatRoof {
        private int numberOfLargeRoofPlates;
        private int numberOfSmallRoofPlates;
        private int roofScrewPackages;

        private static final int ROOF_PLATE_WIDTH = 106;
        private static final int ROOF_SCREWS_PER_PLATE = 50;
        private static final int ROOF_SCREW_PACKAGE_SIZE = 200;

        public static int roofPlates(double length) {
            return (int) Math.ceil(length / ROOF_PLATE_WIDTH);
        }

        public static int roofScrews(double largeRoofPlates, double smallRoofPlates) {
            return (int) Math.ceil(((largeRoofPlates + smallRoofPlates) * ROOF_SCREWS_PER_PLATE) / ROOF_SCREW_PACKAGE_SIZE);
        }

        public int getNumberOfLargeRoofPlates() {
            return numberOfLargeRoofPlates;
        }

        public int getNumberOfSmallRoofPlates() {
            return numberOfSmallRoofPlates;
        }

        public int getRoofScrewPackages() {
            return roofScrewPackages;
        }

        public FlatRoof(Carport carport) {
            double length = carport.getLength();
            double width = carport.getWidth();

            this.numberOfLargeRoofPlates = roofPlates(length);
            this.numberOfSmallRoofPlates = 0;
            if (width > 600) {
                numberOfSmallRoofPlates = roofPlates(length);
            }
            this.roofScrewPackages = roofScrews(numberOfLargeRoofPlates, numberOfSmallRoofPlates);
        }
    }

    public static class AngledRoof{
        private int waterBoard;

        private static final int WATERBOARD_LENGTH = 480;

        public static int waterBoard(double length){
            return (int) Math.ceil(length / WATERBOARD_LENGTH);
        }

        public AngledRoof(Carport carport){
            double length = carport.getLength();
            double width = carport.getWidth();

            this.waterBoard = waterBoard(length);
        }
    }

    public static class ShedMaterials {
        private int looseWoodSides;
        private int looseWoodEnds;
        private int looseWoodFittings;
        private int pillars;
        private int cladding;
        private int doorWood;
        private int doorHinges;
        private int doorHandle;
        private int innerCladdingScrewPackages;
        private int outerCladdingScrewPackages;

        private static final int SHED_PILLAR_FRAME = 4;
        private static final int MINIMUM_LOOSE_WOOD_DISTANCE_ENDS = 270;
        private static final int MINIMUM_LOOSE_WOOD_DISTANCE_SIDES = 240;
        private static final int LOOSE_WOOD_FITTINGS_PER_LOOSE_WOOD = 2;
        private static final int INNER_CLADDING_WIDTH = 44;
        private static final int OUTER_CLADDING_DIVISION_NUMBER = 2;
        private static final int DOOR_WOOD_PIECE_Z = 1;
        private static final int DOOR_HINGES = 2;
        private static final int DOOR_HANDLE = 1;
        private static final int INNERCLADDING_SCREW_PACKAGE_SIZE = 300;
        private static final int INNERCLADDING_SCREWS_PER_CLADDING = 3;
        private static final int OUTERCLADDING_SCREW_PACKAGE_SIZE = 400;
        private static final int OUTERCLADDING_SCREWS_PER_CLADDING = 6;

        public static int looseWoodSides(double length) {
            return (int) Math.ceil((length / MINIMUM_LOOSE_WOOD_DISTANCE_SIDES) * NUMBER_OF_SIDES);
        }

        public static int looseWoodsEnds(double width) {
            return (int) Math.ceil((width / MINIMUM_LOOSE_WOOD_DISTANCE_ENDS) * NUMBER_OF_SIDES);
        }

        public static int loosWoodFittings(int looseWoodEnds, int looseWoodSides) {
            return (looseWoodEnds + looseWoodSides) * LOOSE_WOOD_FITTINGS_PER_LOOSE_WOOD;
        }

        public static int innerCladding(double length, double width) {
            return (int) Math.ceil(((length + width) * NUMBER_OF_SIDES) / INNER_CLADDING_WIDTH);
        }

        public static int outerCladding(int innerCladding) {
            return (int) Math.ceil(innerCladding / OUTER_CLADDING_DIVISION_NUMBER);
        }

        public static int sumOfCladding(double length, double width) {
            int innerCladding = innerCladding(length, width);
            int outerCladding = outerCladding(innerCladding);
            return innerCladding + outerCladding;
        }

        public static int innerCladdingScrews(int innerCladding) {
            return (int) Math.ceil((innerCladding * INNERCLADDING_SCREWS_PER_CLADDING) / INNERCLADDING_SCREW_PACKAGE_SIZE);
        }

        public static int outerCladdingScrews(int outerCladding) {
            return (int) Math.ceil((outerCladding * OUTERCLADDING_SCREWS_PER_CLADDING) / OUTERCLADDING_SCREW_PACKAGE_SIZE);
        }

        public int getLooseWoodSides() {
            return looseWoodSides;
        }

        public int getLooseWoodEnds() {
            return looseWoodEnds;
        }

        public int getLooseWoodFittings() {
            return looseWoodFittings;
        }

        public int getPillars() {
            return pillars;
        }

        public int getCladding() {
            return cladding;
        }

        public int getDoorWood() {
            return doorWood;
        }

        public int getDoorHinges() {
            return doorHinges;
        }

        public int getDoorHandle() {
            return doorHandle;
        }

        public int getInnerCladdingScrewPackages() {
            return innerCladdingScrewPackages;
        }

        public int getOuterCladdingScrewPackages() {
            return outerCladdingScrewPackages;
        }

        public ShedMaterials(Shed shed) {
            double length = shed.getLength();
            double width = shed.getWidth();

            this.pillars = SHED_PILLAR_FRAME;
            this.looseWoodSides = looseWoodSides(length);
            this.looseWoodEnds = looseWoodsEnds(width);
            this.looseWoodFittings = loosWoodFittings(looseWoodEnds, looseWoodSides);
            this.cladding = sumOfCladding(length, width);
            this.doorWood = DOOR_WOOD_PIECE_Z;
            this.doorHinges = DOOR_HINGES;
            this.doorHandle = DOOR_HANDLE;
            this.innerCladdingScrewPackages = innerCladdingScrews(innerCladding(length, width));
            this.outerCladdingScrewPackages = outerCladdingScrews(outerCladding(innerCladding(length, width)));
        }
    }


}