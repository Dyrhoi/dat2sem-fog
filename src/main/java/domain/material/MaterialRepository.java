package domain.material;

import domain.carport.RoofMaterial;

import java.util.List;

public interface MaterialRepository {
    List<RoofMaterial> getRoofMaterials();
    String getRoofMaterial(int id);
}
