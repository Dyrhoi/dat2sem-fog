package domain.material;

import java.util.List;

public interface MaterialRepository {
    List<String> getRoofMaterials();
    String getRoofMaterial(int id);
}
