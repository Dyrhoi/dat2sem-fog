package domain.order;

import domain.carport.Carport;
import domain.order.exceptions.OrderNotFoundException;
import domain.shed.Shed;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getOrders();
    Order getOrder(UUID uuid) throws OrderNotFoundException;

    OrderFactory createOrder();

    int updateOrder(int id, Carport carport, Shed shed);

    int getCarportIdFromUuid(UUID uuid);
}
