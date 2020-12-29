package domain.carport;

import java.util.ArrayList;

public class ConstructionMaterial {

    private int id;
    private String name;
    private String materialType;
    private String description;

    public ConstructionMaterial(int id, String name, String materialType, String description) {
        this.id = id;
        this.name = name;
        this.materialType = materialType;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ConstructionMaterial{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", materialType='" + materialType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
