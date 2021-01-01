package web.servlets.order;


import domain.order.Order;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.ticket.Ticket;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/order/thank-you/*")
public class OrderCompleteServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                Order order = api.getOrder(slug);
                req.setAttribute("order", order);
                super.render("Tak for din ordre! - Fog", "order/thank-you", req, resp);
            } catch (OrderNotFoundException | IllegalArgumentException e) {
                resp.sendError(404, "Der skete en fejl, prøv igen senere.");
            }
        }
    }
}
