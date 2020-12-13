package domain.order;

import domain.order.exceptions.OrderNotFoundException;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getOrders();
    Order getOrder(UUID uuid) throws OrderNotFoundException;

    OrderFactory createOrder();

    void updateOrder(int id);

    int getCarportIdFromUuid(UUID uuid);
}
