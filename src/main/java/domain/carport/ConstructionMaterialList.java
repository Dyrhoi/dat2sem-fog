package domain.carport;

import java.util.List;

public class ConstructionMaterialList {

    private List<ConstructionMaterial> list;

    public ConstructionMaterialList(List<ConstructionMaterial> list) {
        this.list = list;
    }

    public List<ConstructionMaterial> getList() {
        return list;
    }

    public ConstructionMaterial get(int id) {
        for (ConstructionMaterial material : list) {
            if (material.getId() == id) {
                return material;
            }
        }
        return null;
    }
}
