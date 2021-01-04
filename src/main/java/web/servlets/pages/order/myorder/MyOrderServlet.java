package web.servlets.pages.order.myorder;

import domain.order.Order;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.ticket.Ticket;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/my-order/*")
public class MyOrderServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                Order order = api.getOrder(slug);
                Ticket ticket = api.getTicket(slug);
                String svgCarportDrawing = api.getSVGDrawing(order.getCarport());
                req.setAttribute("order", order);
                req.setAttribute("ticket", ticket);
                req.setAttribute("svgCarportDrawing", svgCarportDrawing);
                super.render("Ordre #" + order.getUuid() + " - Fog", "order/my-order", req, resp);
            } catch (OrderNotFoundException | IllegalArgumentException e) {
                resp.sendError(404, "Vi kunne ikke finde ordren.");
            }
        }
    }
}
