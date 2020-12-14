package web.servlets.pages.ticket;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.exceptions.TicketNotFoundException;
import domain.order.ticket.Ticket;
import domain.order.ticket.TicketMessage;
import domain.user.User;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                Ticket ticket = api.getTicket(slug);
                req.setAttribute("ticket", ticket);
                super.render("Sag #UUID - Fog", "ticket/_slug", req, resp);
            } catch (OrderNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String slug = req.getPathInfo().substring(1);
            try {
                //TODO: Make factory.

                Ticket ticket = api.getTicket(slug);
                String content = req.getParameter("content");
                User user = ticket.getOrder().getCustomer();

                if(false) { //TODO: If is user logged in (Sales Rep)
                    //Get session information.
                }

                PolicyFactory policy = new HtmlPolicyBuilder()
                        .allowElements("p")
                        .allowElements("strong")
                        .allowElements("<br>")
                        .allowElements("ol")
                        .allowElements("ul")
                        .allowElements("li")
                        .allowElements("em")
                        .allowElements("u")
                        .toFactory();
                String safeHTMLContent = policy.sanitize(content);

                TicketMessage ticketMessage = new TicketMessage(safeHTMLContent, user, null);

                api.updateTicket(slug, ticketMessage);
                resp.sendRedirect(req.getContextPath() + "/ticket/" + slug);
            } catch (OrderNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
