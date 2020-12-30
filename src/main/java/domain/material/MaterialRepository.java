package domain.material;

import domain.carport.OrderMaterial;
import domain.carport.RoofMaterial;
import domain.order.Order;

import java.util.List;

public interface MaterialRepository {
    List<RoofMaterial> getRoofMaterials();
    String getRoofMaterial(int id);
    List<OrderMaterial> getOrderMaterials(Order order);
}
