package web.servlets.pages.sales;

import domain.order.Order;
import domain.user.sales_representative.SalesRepresentative;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/sales")
public class SalesServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = api.getOrders();
        req.setAttribute("orders", orders);
        SalesRepresentative salesRepresentative = (SalesRepresentative) req.getSession().getAttribute("user");
        req.setAttribute("salesRepId", salesRepresentative.getId());
        super.render("Sælger side - Fog", "sales/allOrders", req, resp);
    }
}
