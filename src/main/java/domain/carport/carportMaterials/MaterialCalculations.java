package domain.carport.carportMaterials;

public class MaterialCalculations {

    private static final int MINIMUM_NUMBER_OF_PILLARS = 2;
    private static final int MAXIMUM_PILLAR_DISTANCE_SIDES = 300;
    private static final int MAXIMUM_PILLAR_DISTANCE_ENDS = 600;
    private static final int MAXIMUM_STROP_DISTANCE = 600;
    private static final int MAXIMUM_RAFTER_DISTANCE = 60;
    private static final int STERN_SIDES_LENGTH = 540;
    private static final int STERN_ENDS_LENGTH = 360;
    private static final int FITTING_SCREW_PACKAGE_SIZE = 250;
    private static final int NUMBER_OF_SCREWS_PER_FITTING = 6;
    private static final int ROOF_PLATE_WIDTH = 106;
    private static final int ROOF_SCREWS_PER_PLATE = 50;
    private static final int ROOF_SCREW_PACKAGE_SIZE = 200;
    private static final int BOARD_BOLTS_PER_PILLAR = 3;
    private static final int SQUARE_DISCS_PER_PILLAR = 2;

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
        return (int) Math.ceil((length / STERN_SIDES_LENGTH) * 2);
    }

    public static int endSterns(double width) {
        return (int) Math.ceil((width / STERN_ENDS_LENGTH) * 2);
    }

    public static int rafterFitting(int rafters) {
        return rafters / 2;
    }

    public static int fittingScrews(int rafters) {
        return (int) Math.ceil((rafters * NUMBER_OF_SCREWS_PER_FITTING)) / FITTING_SCREW_PACKAGE_SIZE;
    }

    public static int roofPlates(double length) {
        return (int) Math.ceil(length / ROOF_PLATE_WIDTH);
    }

    public static int roofScrews(double largeRoofPlates, double smallRoofPlates) {
        return (int) Math.ceil(((largeRoofPlates + smallRoofPlates) * ROOF_SCREWS_PER_PLATE) / ROOF_SCREW_PACKAGE_SIZE);
    }

    public static int boardBolts(int pillars){
        return pillars * BOARD_BOLTS_PER_PILLAR;
    }

    public static int squareDiscs(int pillars){
        return pillars * SQUARE_DISCS_PER_PILLAR;
    }
}