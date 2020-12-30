package web.servlets.pages.authentication;

import domain.user.sales_representative.SalesRepresentative;
import domain.user.sales_representative.SalesRepresentativeNonMatchingPasswordException;
import domain.user.sales_representative.SalesRepresentativeNotFoundException;
import web.plugins.Notifier;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authenticate")
public class Authenticate extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.render("Log ind - Fog", "authenticate", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            if(email == null || password == null)
                throw new SalesRepresentativeNotFoundException();

            SalesRepresentative salesRepresentative = api.authorizeSalesRepresentative(email, password);
            req.getSession().setAttribute("user", salesRepresentative);
            resp.sendRedirect(req.getContextPath() + "/sales");
        } catch (SalesRepresentativeNotFoundException e) {
            super.addNotifcation(req, new Notifier(Notifier.Type.DANGER, "Der fandtes ingen Salgsassistenter med denne e-mail."));
            resp.sendRedirect(req.getContextPath() + "/authenticate");
        } catch (SalesRepresentativeNonMatchingPasswordException e) {
            super.addNotifcation(req, new Notifier(Notifier.Type.DANGER, "Kodeordet var forkert."));
            resp.sendRedirect(req.getContextPath() + "/authenticate");
        }
    }
}
