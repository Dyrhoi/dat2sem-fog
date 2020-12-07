package web.servlets.pages.salesman;

import domain.order.Order;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet({"/salesman/orders", "/salesman/orders/*"})
public class OrdersServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] paths = req.getPathInfo().split("/");
        String slug = paths[paths.length - 1];
        if (!slug.equals("orders")) {
            try {
                UUID uuid = UUID.fromString(slug);
                Order order = api.getOrder(uuid);

                req.setAttribute("order", order);

                //super.render("Se ordre - #" + slug, "salesman/order", req, resp);
                resp.getWriter().println("<h1>#" + slug + "</h1>");
                resp.getWriter().println(order.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {

        }
    }
}
