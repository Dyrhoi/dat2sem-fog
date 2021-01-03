package domain.carport;

public class OrderMaterial {
    private int quantity;
    private ConstructionMaterial constructionMaterial;

    public OrderMaterial(int quantity, ConstructionMaterial constructionMaterial) {
        this.quantity = quantity;
        this.constructionMaterial = constructionMaterial;
    }

    public int getQuantity() {
        return quantity;
    }

    public ConstructionMaterial getConstructionMaterial() {
        return constructionMaterial;
    }

    @Override
    public String toString() {
        return "OrderMaterial{" +
                "quantity=" + quantity +
                ", constructionMaterial=" + constructionMaterial +
                '}';
    }
}
