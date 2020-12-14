package web.servlets.pages.ticket;

import domain.order.exceptions.TicketNotFoundException;
import domain.order.ticket.Ticket;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/ticket", "/ticket/*"})
public class TicketServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                Ticket ticket = api.getTicket(slug);
                req.setAttribute("ticket", ticket);
                super.render("Sag #UUID - Fog", "ticket/_slug", req, resp);
            } catch (TicketNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
