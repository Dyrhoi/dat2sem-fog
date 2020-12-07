package web.servlets.pages.salesman;

import domain.carport.CarportNotFoundException;
import domain.customer.CustomerNotFoundException;
import domain.order.Order;
import domain.shed.ShedNotFoundException;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet({"/salesman/orders", "/salesman/orders/*"})
public class OrdersServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*String[] paths = req.getPathInfo().split("/");
        String slug = paths[paths.length - 1];*/
        String slug = null;
        if (false) {
            try {
                UUID uuid = UUID.fromString(slug);
                Order order = api.getOrder(uuid);

                req.setAttribute("order", order);

                //super.render("Se ordre - #" + slug, "salesman/order", req, resp);
                resp.getWriter().println("<h1>#" + slug + "</h1>");
                resp.getWriter().println(order.toString());
            } catch (SQLException | CustomerNotFoundException | ShedNotFoundException | CarportNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                List<Order> orders = api.getOrders();
                req.setAttribute("orders", orders);
                super.render("Alle ordre - Fog", "salesman", req, resp);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
