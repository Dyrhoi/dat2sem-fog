package domain.shed;

public class Shed {

    private final int id;

    private final int width;
    private final int length;

    public static final int minWidth = 210;
    public static final int maxWidth = 720;
    public static final int minLength = 150;
    public static final int maxLength = 690;

    //TODO: - Static max length and widths, for validation.

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
