package domain.carport;

public class Carport {

    private final int id;

    private final int width;
    private final int length;
    private final roofTypes roof;
    private int roofAngle;

    public Carport(int id, int width, int length, roofTypes roof) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.roof = roof;
    }

    public void setRoofAngle(int roofAngle) {
        this.roofAngle = roofAngle;
    }

    public int getWidth() { return width; }

    public int getLength() { return length; }

    public roofTypes getRoof() { return roof; }

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
