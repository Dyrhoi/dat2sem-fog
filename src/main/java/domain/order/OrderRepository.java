package domain.order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getOrders();
    Order getOrder(UUID uuid);

    OrderFactory createOrder();

}
