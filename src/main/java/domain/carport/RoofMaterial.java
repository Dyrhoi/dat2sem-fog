package domain.carport;

public class RoofMaterial {
    int id;
    String name;
    Carport.roofTypes type;

    public RoofMaterial(int id, String name, Carport.roofTypes type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Carport.roofTypes getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
