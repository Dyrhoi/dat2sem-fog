package web.servlets.pages.ticket;

import domain.order.exceptions.OrderNotFoundException;
import domain.order.ticket.Ticket;
import domain.order.ticket.TicketMessage;
import domain.user.User;
import domain.user.sales_representative.SalesRepresentative;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/ticket", "/ticket/*"})
public class TicketServlet extends BaseServlet {
    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                Ticket ticket = api.getTicket(slug);
                req.setAttribute("ticket", ticket);
                super.render("Sag #UUID - Fog", "ticket/_slug", req, resp);
            } catch (OrderNotFoundException e) {
                resp.sendError(404, "Vi kunne ikke finde ordren, prøv igen.");
            }
        }
    }*/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                //TODO: Make factory.

                boolean isStaff = req.getParameter("is-staff") != null && req.getParameter("is-staff").equals("true");

                //System.out.println(req.getParameter("is-staff"));
                //System.out.println(isStaff);

                Ticket ticket = api.getTicket(slug);
                String content = req.getParameter("content");
                User user = ticket.getOrder().getCustomer();

                if(req.getSession().getAttribute("user") instanceof SalesRepresentative && isStaff) {
                    user = (SalesRepresentative) req.getSession().getAttribute("user");
                }

                PolicyFactory policy = new HtmlPolicyBuilder()
                        .allowElements("p")
                        .allowElements("strong")
                        .allowElements("br")
                        .allowElements("ol")
                        .allowElements("ul")
                        .allowElements("li")
                        .allowElements("em")
                        .allowElements("u")
                        .toFactory();
                String safeHTMLContent = policy.sanitize(content);

                TicketMessage ticketMessage = new TicketMessage(safeHTMLContent, user, null);

                api.updateTicket(slug, ticketMessage);
                String url =
                        isStaff ? "/sales/orders/" + ticket.getOrder().getUuid() + "/ticket"
                        : "/order/my-order/" + slug + "/ticket";
                resp.sendRedirect(req.getContextPath() + url);
            } catch (OrderNotFoundException e) {
                resp.sendError(500, "Ugyldig kundesamtale.");
            }
        }
    }
}
