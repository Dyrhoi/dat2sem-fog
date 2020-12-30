package domain.carport.carportMaterials;

import domain.carport.*;
import domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class MaterialCalculations {
    private static final int NUMBER_OF_SIDES = 2;

    public static class BaseCarport {

        private final int pillars;
        private final int straps;
        private final int rafters;
        private final int topSternsSides;
        private final int bottomSternsSides;
        private final int topSternsEnds;
        private final int bottomSternsEnds;
        private final int rafterFittingLeft;
        private final int rafterFittingRight;
        private final int fittingScrewPackages;
        private final int boardBolts;
        private final int squareDiscs;
        private final int sternScrewsPackages = 1;

        private static final int MINIMUM_NUMBER_OF_PILLARS = 2;
        private static final int PILLAR_EDGE_DISTANCE_SIDES = 70;
        private static final int PILLAR_EDGE_DISTANCE_ENDS = 140;
        private static final int MAXIMUM_PILLAR_DISTANCE = 600;
        private static final int MAXIMUM_STRAP_DISTANCE = 600;
        private static final int MAXIMUM_RAFTER_DISTANCE = 60;
        private static final int STERN_SIDES_LENGTH = 540;
        private static final int STERN_ENDS_LENGTH = 360;
        private static final int FITTING_SCREW_PACKAGE_SIZE = 250;
        private static final int NUMBER_OF_SCREWS_PER_FITTING = 6;
        private static final int BOARD_BOLTS_PER_PILLAR = 2;
        private static final int SQUARE_DISCS_PER_PILLAR = 2;
        private static final int NUMBER_OF_SIDES = 2;

        public static int pillars(double length, double width) {
            return (int) Math.floor(((length - PILLAR_EDGE_DISTANCE_ENDS) / MAXIMUM_PILLAR_DISTANCE) + MINIMUM_NUMBER_OF_PILLARS) * pillarRows(width);
        }

        public static int pillarRows(double width) {
            return (int) Math.floor(((width - PILLAR_EDGE_DISTANCE_SIDES) / MAXIMUM_PILLAR_DISTANCE) + MINIMUM_NUMBER_OF_PILLARS);
        }

        public static int straps(double length, double width) {
            return (int) Math.ceil((length / MAXIMUM_STRAP_DISTANCE) * pillarRows(width));
        }

        public static int rafterRows(double width) {
            return (int) Math.floor((width / MAXIMUM_PILLAR_DISTANCE));
        }

        public static int rafters(double length, double width) {
            return (int) Math.ceil((length / MAXIMUM_RAFTER_DISTANCE)) * rafterRows(width);
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

        public int getStraps() {
            return straps;
        }

        public int getRafters() {
            return rafters;
        }

        public int getTopSternsSides() {
            return topSternsSides;
        }

        public int getBottomSternsSides() {
            return bottomSternsSides;
        }

        public int getTopSternsEnds() {
            return topSternsEnds;
        }

        public int getButtomSternsEnds() {
            return bottomSternsEnds;
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

        public int getSternScrewsPackages() {
            return sternScrewsPackages;
        }

        public int getBottomSternsEnds() {
            return bottomSternsEnds;
        }

        public static int fittingScrews(int rafters) {
            return (int) Math.ceil((double) FITTING_SCREW_PACKAGE_SIZE / (rafters * NUMBER_OF_SCREWS_PER_FITTING));
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
            this.straps = straps(length, width);
            this.rafters = rafters(length, width);
            this.topSternsSides = sideSterns(length);
            this.bottomSternsSides = sideSterns(length);
            this.topSternsEnds = endSterns(width);
            this.bottomSternsEnds = endSterns(width);
            this.rafterFittingLeft = rafterFitting(rafters);
            this.rafterFittingRight = rafterFitting(rafters);
            this.fittingScrewPackages = fittingScrews(rafters);
            this.boardBolts = boardBolts(pillars);
            this.squareDiscs = squareDiscs(pillars);
        }
    }

    public static class FlatRoof {
        private int numberOfLargeRoofPlates;
        private int numberOfSmallRoofPlates;
        private int roofScrewPackages;
        private int perforatedTape;

        private static final int ROOF_PLATE_WIDTH = 106;
        private static final int ROOF_SCREWS_PER_PLATE = 50;
        private static final int ROOF_SCREW_PACKAGE_SIZE = 200;
        private static final int ROLLS_OF_PERFORATED_TAPE = 2;

        public static int roofPlates(double length) {
            return (int) Math.ceil(length / ROOF_PLATE_WIDTH);
        }

        public static int roofScrews(double largeRoofPlates, double smallRoofPlates) {
            return (int) Math.ceil(((largeRoofPlates + smallRoofPlates) * ROOF_SCREWS_PER_PLATE) / ROOF_SCREW_PACKAGE_SIZE);
        }

        public int getPerforatedTape() {
            return perforatedTape;
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

            this.perforatedTape = ROLLS_OF_PERFORATED_TAPE;
            this.numberOfLargeRoofPlates = roofPlates(length);
            this.numberOfSmallRoofPlates = 0;
            if (width > 600) {
                numberOfSmallRoofPlates = roofPlates(length);
            }
            this.roofScrewPackages = roofScrews(numberOfLargeRoofPlates, numberOfSmallRoofPlates);
        }
    }

    public static class AngledRoof {
        private int waterBoard;
        private int roofFramePackage;
        private int roofTopBar;
        private int roofTileWood;
        private int roofRegularTiles;
        private int roofTopTiles;
        private int topTilesFittings;
        private int tileBindersAndHooks;
        private int roofWoodScrewPackages;

        private static final int WATERBOARD_LENGTH = 480;
        private static final int ROOF_FRAME_PACKAGE = 1;
        private static final int TOP_BAR_LENGTH = 420;
        private static final int ROOF_TILE_WOOD_LENGTH = 540;
        private static final int ROOF_TILE_WOOD_DISTANCE = 307;
        private static final double REGULAR_ROOF_TILES_PER_SQUARE_METER = 8.1;
        private static final int TOP_TILES_LENGTH = 42;
        private static final int TOP_TILES_FITTING_LENGTH = 8;
        private static final int TILEBINDER_PACKAGE_SIZE = 150;
        private static final int SCREWS_PER_ROOF_WOOD = 7;
        private static final int ROOF_WOOD_SCREW_PACKAGE_SIZE = 100;

        public static int waterBoard(double length) {
            return (int) Math.ceil(length / WATERBOARD_LENGTH);
        }

        public static int roofTileWood(double length, double width) {
            int lengthOfRoof = (int) Math.ceil(length / ROOF_TILE_WOOD_LENGTH);
            int sumRoofWidth = roofWoodMath(width);
            return (int) (Math.ceil(sumRoofWidth) / ROOF_TILE_WOOD_DISTANCE) * lengthOfRoof;
        }

        private static int roofWoodMath(double width) {
            double stepOne = Math.pow(width, 2);
            double stepTwo = stepOne / 2;
            double stepThree = Math.sqrt(stepTwo);
            return (int) stepThree * 2;
        }

        private static int topBar(double length) {
            return (int) Math.ceil(length / TOP_BAR_LENGTH);
        }

        private static int roofTileMath(double width) {
            double stepOne = Math.pow(width, 2);
            double stepTwo = stepOne / 2;
            return (int) Math.sqrt(stepTwo);
        }

        public static int roofTilesRegular(double length, double width) {
            double angledWidth = roofTileMath(width);
            double roofInSquareMeters = (angledWidth * length) * NUMBER_OF_SIDES;
            return (int) Math.ceil(roofInSquareMeters * REGULAR_ROOF_TILES_PER_SQUARE_METER);
        }

        public static int roofTilesTop(double length) {
            int topTileAdjustedLength = TOP_TILES_LENGTH - TOP_TILES_FITTING_LENGTH;
            return (int) Math.ceil(length / topTileAdjustedLength);
        }

        public static int tileBinders(int regularTiles, int topTiles) {
            return (int) Math.ceil(regularTiles * topTiles) / TILEBINDER_PACKAGE_SIZE;
        }

        public static int roofScrews(int roofTileWood, int roofTopWood) {
            double roofWood = roofTileWood + roofTopWood;
            return (int) Math.ceil((roofWood * SCREWS_PER_ROOF_WOOD) / ROOF_WOOD_SCREW_PACKAGE_SIZE);
        }

        public int getWaterBoard() {
            return waterBoard;
        }

        public int getRoofFramePackage() {
            return roofFramePackage;
        }

        public int getRoofTopBar() {
            return roofTopBar;
        }

        public int getRoofWoodScrewPackages() {
            return roofWoodScrewPackages;
        }

        public int getRoofTileWood() {
            return roofTileWood;
        }

        public int getRoofRegularTiles() {
            return roofRegularTiles;
        }

        public int getRoofTopTiles() {
            return roofTopTiles;
        }

        public int getTopTilesFittings() {
            return topTilesFittings;
        }

        public int getTileBindersAndHooks() {
            return tileBindersAndHooks;
        }

        public AngledRoof(Carport carport) {
            double length = carport.getLength();
            double width = carport.getWidth();

            this.waterBoard = waterBoard(length);
            this.roofFramePackage = ROOF_FRAME_PACKAGE;
            this.roofTopBar = topBar(length);
            this.roofTileWood = roofTileWood(length, width);
            this.roofRegularTiles = roofTilesRegular(length, width);
            this.roofTopTiles = roofTilesTop(length);
            this.topTilesFittings = roofTopTiles;
            this.tileBindersAndHooks = tileBinders(roofRegularTiles, roofTopTiles);
            this.roofWoodScrewPackages = roofScrews(roofTileWood, roofTopBar);
        }
    }

    public static class ShedConstructor {
        private final int looseWoodSides;
        private final int looseWoodEnds;
        private final int looseWoodFittings;
        private final int shedPillars;
        private final int cladding;
        private final int doorWood;
        private final int doorHinges;
        private final int doorHandle;
        private final int innerCladdingScrewPackages;
        private final int outerCladdingScrewPackages;

        public static final int STATIC_PILLAR_FOR_SHED = 1;
        public static final int MINIMUM_LOOSE_WOOD_DISTANCE_ENDS = 270;
        public static final int MINIMUM_LOOSE_WOOD_DISTANCE_SIDES = 240;
        public static final int LOOSE_WOOD_FITTINGS_PER_LOOSE_WOOD = 2;
        public static final int INNER_CLADDING_WIDTH = 44;
        public static final int OUTER_CLADDING_DIVISION_NUMBER = 2;
        public static final int DOOR_WOOD_PIECE_Z = 1;
        public static final int DOOR_HINGES = 2;
        public static final int DOOR_HANDLE = 1;
        public static final int INNERCLADDING_SCREW_PACKAGE_SIZE = 300;
        public static final int INNERCLADDING_SCREWS_PER_CLADDING = 3;
        public static final int OUTERCLADDING_SCREW_PACKAGE_SIZE = 400;
        public static final int OUTERCLADDING_SCREWS_PER_CLADDING = 6;

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
            return (int) Math.ceil(innerCladding) / OUTER_CLADDING_DIVISION_NUMBER;
        }

        public static int sumOfCladding(double length, double width) {
            int innerCladding = innerCladding(length, width);
            int outerCladding = outerCladding(innerCladding);
            return innerCladding + outerCladding;
        }

        public static int innerCladdingScrews(int innerCladding) {
            return (int) Math.ceil(innerCladding) * INNERCLADDING_SCREWS_PER_CLADDING / INNERCLADDING_SCREW_PACKAGE_SIZE;
        }

        public static int outerCladdingScrews(int outerCladding) {
            return (int) Math.ceil(outerCladding) * OUTERCLADDING_SCREWS_PER_CLADDING / OUTERCLADDING_SCREW_PACKAGE_SIZE;
        }

        public static int calcShedPillars(double length, double width) {
            return calcShedPillarsLength(length) * calcShedPillarsWidth(width);
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

        public int getShedPillars() {
            return shedPillars;
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

        public ShedConstructor(Shed shed) {
            double length = shed.getLength();
            double width = shed.getWidth();

            this.shedPillars = calcShedPillars(length, width);
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

    public static List<OrderMaterial> calcBaseCarport(ConstructionMaterialList repo, Order order){
        Carport carport = order.getCarport();
        ArrayList<OrderMaterial> list = new ArrayList<>();
        MaterialCalculations.BaseCarport tmpBaseCarport = new BaseCarport(carport);

        list.add(new OrderMaterial(tmpBaseCarport.getPillars(), repo.get(MaterialHandler.PILLARS_FLAT_ROOF)));
        list.add(new OrderMaterial(tmpBaseCarport.getStraps(), repo.get(MaterialHandler.STRAPS_FOR_SIDES)));
        list.add(new OrderMaterial(tmpBaseCarport.getRafters(), repo.get(MaterialHandler.RAFTERS_MOUNTED_IN_STRAP)));
        list.add(new OrderMaterial(tmpBaseCarport.getTopSternsSides(), repo.get(MaterialHandler.OVER_STERN_BOARD_FOR_SIDES)));
        list.add(new OrderMaterial(tmpBaseCarport.getBottomSternsSides(), repo.get(MaterialHandler.UNDER_STERN_BOARD_FOR_SIDES)));
        list.add(new OrderMaterial(tmpBaseCarport.getBottomSternsEnds(), repo.get(MaterialHandler.OVER_STERN_BOARD_FOR_FRONT_AND_BACK)));
        list.add(new OrderMaterial(tmpBaseCarport.getBottomSternsEnds(), repo.get(MaterialHandler.OVER_STERN_BOARD_FOR_FRONT_AND_BACK)));
        list.add(new OrderMaterial(tmpBaseCarport.getRafterFittingLeft(), repo.get(MaterialHandler.FOR_MOUNTING_RAFT_ON_STRAP_LEFT_FLAT)));
        list.add(new OrderMaterial(tmpBaseCarport.getRafterFittingRight(), repo.get(MaterialHandler.FOR_MOUNTING_RAFT_ON_STRAP_RIGHT_FLAT)));
        list.add(new OrderMaterial(tmpBaseCarport.getFittingScrewPackages(), repo.get(MaterialHandler.SCREWS_FOR_UNIVERSAL_FITTING_AND_PERFORATED_TAPE)));
        list.add(new OrderMaterial(tmpBaseCarport.getBoardBolts(), repo.get(MaterialHandler.BOLTS_FOR_MOUNTING_RAFTERS_ON_STRAPS_FLAT)));
        list.add(new OrderMaterial(tmpBaseCarport.getSquareDiscs(), repo.get(MaterialHandler.SQUARE_DISCS_FOR_MOUNTING_RAFTERS_ON_STRAPS_FLAT)));

        return list;
    }

    public static List<OrderMaterial> calcBaseCarportFlat(ConstructionMaterialList repo, Order order){
        Carport carport = order.getCarport();
        List<OrderMaterial> finalList = calcBaseCarport(repo, order);
        MaterialCalculations.FlatRoof tmpFlatRoof = new FlatRoof(carport);

        //Flat Roof
        finalList.add(new OrderMaterial(tmpFlatRoof.getPerforatedTape(), repo.get(MaterialHandler.PERFORATED_TAPE)));
        finalList.add(new OrderMaterial(tmpFlatRoof.getNumberOfLargeRoofPlates(), repo.get(MaterialHandler.ROOF_PLATE_LARGE)));
        if (tmpFlatRoof.getNumberOfSmallRoofPlates() != 0){
            finalList.add(new OrderMaterial(tmpFlatRoof.getNumberOfSmallRoofPlates(), repo.get(MaterialHandler.ROOF_PLATE_SMALL)));
        }
        finalList.add(new OrderMaterial(tmpFlatRoof.getRoofScrewPackages(), repo.get(MaterialHandler.SCREWS_FOR_ROOF_PLATE)));

        return finalList;
    }

    public static List<OrderMaterial> calcBaseCarportAngled(ConstructionMaterialList repo, Order order){
        Carport carport = order.getCarport();
        List<OrderMaterial> finalList = calcBaseCarport(repo, order);
        MaterialCalculations.AngledRoof tmpAngledRoof = new AngledRoof(carport);

        finalList.add(new OrderMaterial(tmpAngledRoof.getWaterBoard(), repo.get(MaterialHandler.WATER_BOARD)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getRoofFramePackage(), repo.get(MaterialHandler.DO_IT_YOURSELF_RAFTERS)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getRoofTopBar(), repo.get(MaterialHandler.TOP_BATTEN_FOR_ROOF_TILES)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getRoofTileWood(), repo.get(MaterialHandler.FOR_MOUNTING_ON_RAFTERS)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getRoofRegularTiles(), repo.get(MaterialHandler.ROOF_TILES_REGULAR)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getRoofTopTiles(), repo.get(MaterialHandler.ROOF_TILES_TOP)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getTopTilesFittings(), repo.get(MaterialHandler.FOR_ROOF_TILES_TOP_MOUNTING)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getTileBindersAndHooks(), repo.get(MaterialHandler.FOR_ROOF_TILES_REGULAR_MOUNTING)));
        finalList.add(new OrderMaterial(tmpAngledRoof.getRoofWoodScrewPackages(), repo.get(MaterialHandler.SCREWS_FOR_MOUNTING_LOOSE_WOOD)));

        return finalList;
    }

    public static List<OrderMaterial> calcShed(ConstructionMaterialList repo, Order order){
        List<OrderMaterial> list = new ArrayList<>();
        Shed shed = order.getCarport().getShed();

        ShedConstructor tmpShed = new ShedConstructor(shed);

        list.add(new OrderMaterial(tmpShed.getShedPillars(), repo.get(MaterialHandler.PILLARS_FLAT_ROOF)));
        list.add(new OrderMaterial(tmpShed.getLooseWoodSides(), repo.get(MaterialHandler.LOOSE_WOOD_SIDES)));
        list.add(new OrderMaterial(tmpShed.getLooseWoodEnds(), repo.get(MaterialHandler.LOOSE_WOOD_ENDS)));
        list.add(new OrderMaterial(tmpShed.getLooseWoodFittings(), repo.get(MaterialHandler.ANGLED_FITTING_FOR_LOOSE_WOOD)));
        list.add(new OrderMaterial(tmpShed.getCladding(), repo.get(MaterialHandler.CLADDING_FLAT_ROOF)));
        list.add(new OrderMaterial(tmpShed.getDoorWood(), repo.get(MaterialHandler.FOR_Z_BEHIND_DOOR_ANGLED)));
        list.add(new OrderMaterial(tmpShed.getDoorHinges(), repo.get(MaterialHandler.DOOR_HINGES)));
        list.add(new OrderMaterial(tmpShed.getDoorHandle(), repo.get(MaterialHandler.HANDLE_FOR_DOOR_IN_SHED)));
        list.add(new OrderMaterial(tmpShed.getInnerCladdingScrewPackages(), repo.get(MaterialHandler.SCREWS_FOR_MOUNTING_INNER_CLADDING_FLAT)));
        list.add(new OrderMaterial(tmpShed.getOuterCladdingScrewPackages(), repo.get(MaterialHandler.SCREWS_FOR_MOUNTING_OUTER_CLADDING_FLAT)));

        return list;
    }

    public static List<OrderMaterial> calcFlat(ConstructionMaterialList repo, Order order){
        return calcBaseCarportFlat(repo, order);
    }

    public static List<OrderMaterial> calcFlatShed(ConstructionMaterialList repo, Order order){
        List<OrderMaterial> baseCarportList = calcBaseCarportFlat(repo, order);
        List<OrderMaterial> shedList = calcShed(repo, order);

        List<OrderMaterial> finalList = new ArrayList<>(baseCarportList);
        finalList.addAll(shedList);

        return finalList;
    }

    public static List<OrderMaterial> calcAngled(ConstructionMaterialList repo, Order order){
        return calcBaseCarportAngled(repo, order);
    }

    public static List<OrderMaterial> calcAngledShed(ConstructionMaterialList repo, Order order){
        List<OrderMaterial> baseCarportList = calcBaseCarportAngled(repo, order);
        List<OrderMaterial> shedList = calcShed(repo, order);

        List<OrderMaterial> finalList = new ArrayList<>(baseCarportList);
        finalList.addAll(shedList);

        return finalList;
    }




}