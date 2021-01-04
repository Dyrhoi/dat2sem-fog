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
            String path = req.getPathInfo().substring(1);
            String slug = path.split("/")[0];
            String subPath = "";
            try {
                subPath = path.split("/")[1];
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                Order order = api.getOrder(slug);
                Ticket ticket = api.getTicket(slug);
                String svgCarportDrawing = api.getSVGDrawing(order.getCarport());
                req.setAttribute("order", order);
                req.setAttribute("ticket", ticket);
                req.setAttribute("svgCarportDrawing", svgCarportDrawing);

                switch (subPath) {
                    case "" -> displayOrder(req, resp, order);
                    case "offer" -> displayOrderOffer(req, resp, order);
                    default -> resp.sendError(404);
                }
            } catch (OrderNotFoundException | IllegalArgumentException e) {
                resp.sendError(404, "Vi kunne ikke finde ordren.");
            }
        }
    }

    private void displayOrderOffer(HttpServletRequest req, HttpServletResponse resp, Order order) throws IOException, ServletException {
        super.render("Ordre #" + order.getUuid() + " - Fog", "order/my-order-offer", req, resp);
    }

    private void displayOrder(HttpServletRequest req, HttpServletResponse resp, Order order) throws ServletException, IOException {
        super.render("Ordre #" + order.getUuid() + " - Fog", "order/my-order", req, resp);
    }
}
