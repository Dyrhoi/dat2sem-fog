package domain.carport;

public class Carport  {

    private final int id;

    private final int width;
    private final int length;
    private final roofTypes roof;
    private Integer roofAngle;
    private final int roof_material;
    private Shed shed;

    public static final int minWidth = 240;
    public static final int maxWidth = 750;
    public static final int minLength = 240;
    public static final int maxLength = 780;
    public static final int minAngle = 15;
    public static final int maxAngle = 45;


    public Carport(int id, Shed shed, int width, int length, roofTypes roof, Integer roofAngle, int roof_material) {
        this.shed = shed;
        this.id = id;
        this.width = width;
        this.length = length;
        this.roof = roof;
        this.roofAngle = roofAngle;
        this.roof_material = roof_material;
    }

    public void setRoofAngle(int roofAngle) {
        this.roofAngle = roofAngle;
    }

    public void setShed(Shed shed) {
        this.shed = shed;
    }

    public int getWidth() { return width; }

    public int getLength() { return length; }

    public roofTypes getRoof() { return roof; }

    public Integer getRoofAngle() { return roofAngle; }

    public int getRoof_material() {
        return roof_material;
    }

    public Shed getShed() {
        return shed;
    }

    @Override
    public String toString() {
        return "Carport{" +
                "id=" + id +
                ", width=" + width +
                ", length=" + length +
                ", roof='" + roof + '\'' +
                ", roofAngle=" + roofAngle +
                '}';
    }

    public static enum roofTypes {
        FLAT,
        ANGLED
    }
}
