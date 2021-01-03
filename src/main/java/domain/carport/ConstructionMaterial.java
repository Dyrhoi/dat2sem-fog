package domain.carport;

public class ConstructionMaterial {

    private int id;
    private String name;
    private String materialType;
    private int material_types_id;
    private String description;

    public ConstructionMaterial(int id, String name, String materialType,int material_types_id, String description) {
        this.id = id;
        this.name = name;
        this.materialType = materialType;
        this.material_types_id = material_types_id;
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

    public int getMaterial_types_id() {
        return material_types_id;
    }

    @Override
    public String toString() {
        return "ConstructionMaterial{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", materialType='" + materialType + '\'' +
                ", material_types_id=" + material_types_id +
                ", description='" + description + '\'' +
                '}';
    }
}
