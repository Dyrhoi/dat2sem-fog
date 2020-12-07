package web.servlets.pages.salesman;

import domain.order.Order;
import domain.order.exceptions.OrderNotFoundException;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet({"/salesman/orders", "/salesman/orders/*"})
public class SalesmanOrdersServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        * UUID IS SET IN URL
        * */
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                UUID uuid = UUID.fromString(slug);
                Order order = api.getOrder(uuid);

                req.setAttribute("order", order);

                //super.render("Se ordre - #" + slug, "salesman/order", req, resp);
                resp.getWriter().println("<h1>#" + slug + "</h1>");
                resp.getWriter().println(order.toString());
            } catch (OrderNotFoundException e) {
                e.printStackTrace();
            }
        }
        /*
        * SHOW ALL ORDERS IF NO UUID IN URL
        * */
        else {
            List<Order> orders = api.getOrders();
            req.setAttribute("orders", orders);
            super.render("Alle ordre - Fog", "salesman", req, resp);
        }
    }
}
