package web.servlets.pages.sales;

import domain.order.Order;
import domain.order.exceptions.OrderNotFoundException;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet({"/sales/orders", "/sales/orders/*"})
public class SalesOrdersServlet extends BaseServlet {

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
                req.setAttribute("prePrice", Math.round(Math.random() * (40000-20000) + 20000));
                req.setAttribute("roof_material", api.getRoofMaterial(order.getCarport().getRoof_material() + 1));

                //super.render("Se ordre - #" + slug, "salesman/order", req, resp);
                /*resp.getWriter().println("<h1>#" + slug + "</h1>");
                resp.getWriter().println(order.toString());*/
                super.render("order - " + uuid, "salesmanOrders", req, resp);
            } catch (IllegalArgumentException | OrderNotFoundException e) { //Illegal Argument from UUID.fromString (Maybe just pass a string to DAO?)
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
