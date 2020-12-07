package domain.order;

import domain.carport.CarportNotFoundException;
import domain.customer.CustomerNotFoundException;
import domain.shed.ShedNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    List<Order> getOrders() throws SQLException;
    Order getOrder(UUID uuid) throws SQLException, CarportNotFoundException, ShedNotFoundException, CustomerNotFoundException;

    OrderFactory createOrder();

}
