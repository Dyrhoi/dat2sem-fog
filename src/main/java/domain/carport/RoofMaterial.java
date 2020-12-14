package domain.carport;

public class RoofMaterial {
    String name;
    String type;

    public RoofMaterial(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
