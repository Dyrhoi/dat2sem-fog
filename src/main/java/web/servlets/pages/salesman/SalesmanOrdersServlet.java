package web.servlets.pages.salesman;

import api.Util;
import domain.carport.Carport;
import domain.order.Order;
import domain.order.OrderFactory;
import domain.order.exceptions.OrderNotFoundException;
import domain.shed.Shed;
import validation.ValidationErrorException;
import web.servlets.BaseServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@WebServlet({"/sales/orders", "/sales/orders/*"})
public class SalesmanOrdersServlet extends BaseServlet {
    String slug;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        * UUID IS SET IN URL
        * */
        if (req.getAttribute("slug") != null) {
            UUID uuid = null;
            try {
                uuid = setOrderFromUUID(req, req.getAttribute("slug").toString());
            } catch (OrderNotFoundException e) {
                e.printStackTrace();
            }
            super.render("order - " + uuid, "salesOrders", req, resp);
        }
        else if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            if (req.getPathInfo().charAt(req.getPathInfo().length() - 1) == '/') {
                slug = req.getPathInfo().replaceAll("/", "");
                try {

                    UUID uuid = setOrderFromUUID(req, slug);
                    req.setAttribute("roofMaterials", api.getRoofMaterials());
                    super.render("Changing order - " + uuid, "changeOrder", req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                slug = req.getPathInfo().substring(1);
                try {
                    UUID uuid = setOrderFromUUID(req, slug);

                    //super.render("Se ordre - #" + slug, "salesman/order", req, resp);
                /*resp.getWriter().println("<h1>#" + slug + "</h1>");
                resp.getWriter().println(order.toString());*/
                    super.render("order - " + uuid, "salesOrders", req, resp);
                } catch (IllegalArgumentException | OrderNotFoundException e) { //Illegal Argument from UUID.fromString (Maybe just pass a string to DAO?)
                    e.printStackTrace();
                }
            }
        /*
         * SHOW ALL ORDERS IF NO UUID IN URL
         * */
        }
        else {
        List<Order> orders = api.getOrders();
        req.setAttribute("orders", orders);
        super.render("Alle ordre - Fog", "sales", req, resp);
        }
    }

    private UUID setOrderFromUUID(HttpServletRequest req, String slug) throws OrderNotFoundException {
        UUID uuid = UUID.fromString(slug);
        Order order = api.getOrder(uuid);

        req.setAttribute("order", order);
        req.setAttribute("prePrice", Math.round(Math.random() * (40000-20000) + 20000));
        if (order.getCarport().getRoof().toString().equals("FLAT")) {
            req.setAttribute("roof_material", "Plasttrapezplader");
        }
        else {
            req.setAttribute("roof_material", api.getRoofMaterial(order.getCarport().getRoof_material() + 1));
        }
        return uuid;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(slug);
        req.setAttribute("slug", slug);

        boolean carportHasShed;

        int carportWidth;
        int carportLength;
        Carport.roofTypes roofType;
        Integer roofAngle;
        int roof_material;

        int shedWidth;
        int shedLength;

        int id = -1;

        carportLength = Integer.parseInt(req.getParameter("carport-length"));
        carportWidth = Integer.parseInt(req.getParameter("carport-width"));
        roofType = Carport.roofTypes.valueOf(req.getParameter("roof-type").toUpperCase());
        carportHasShed = req.getParameter("shed-checkbox") != null;
        if (roofType.equals(Carport.roofTypes.ANGLED)){
            roofAngle = Integer.parseInt(req.getParameter("roof-angle"));
            roof_material = Integer.parseInt(req.getParameter("roof_angled_material"));
        }
        else {
            roof_material = Integer.parseInt(req.getParameter("roof_flat_material"));
            roofAngle = null;
        }

        Carport carport = new Carport(id, carportWidth, carportLength, roofType, roofAngle, roof_material);

        //create shed
        Shed shed = null;
        if (carportHasShed) {
            shedWidth = Integer.parseInt(req.getParameter("shed-width"));
            shedLength = Integer.parseInt(req.getParameter("shed-length"));
            shed = new Shed(id, shedWidth, shedLength);
        }

        //create order
        System.out.println("Laver ordre");
        int carportId = api.getCarportIdFromUuid(uuid);
        api.updateOrder(carportId);

        System.out.println("Ordre færdig");

        doGet(req, resp);
    }
}
