package domain.shed;

public class Shed {

    private final int id;

    private final int width;
    private final int length;

    public Shed(int id, int width, int length) {
        this.id = id;
        this.width = width;
        this.length = length;
    }

    public int getWidth() { return width; }

    public int getLength() { return length; }

    @Override
    public String toString() {
        return "Shed{" +
                "id=" + id +
                ", width=" + width +
                ", length=" + length +
                '}';
    }
}
