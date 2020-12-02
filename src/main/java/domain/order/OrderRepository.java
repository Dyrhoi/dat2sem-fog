package domain.order;

import java.util.List;

public interface OrderRepository {
    List<Order> getOrders();
    Order getOrder(int id);

    OrderFactory createOrder();

}
