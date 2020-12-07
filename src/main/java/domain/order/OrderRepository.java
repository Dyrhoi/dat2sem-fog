package domain.order;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getOrders() throws SQLException;
    Order getOrder(UUID uuid) throws SQLException;

    OrderFactory createOrder();

}
