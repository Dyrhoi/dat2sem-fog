package web.servlets.pages.sales;

import domain.carport.Carport;
import domain.carport.Shed;
import domain.order.Order;
import domain.order.exceptions.OrderNotFoundException;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet({"/sales/orders", "/sales/orders/*"})
public class SalesOrdersServlet extends BaseServlet {
    String slug;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        * UUID IS SET IN URL
        * */
        /*if (req.getAttribute("slug") != null) {
            UUID uuid = null;
            try {
                uuid = setOrderFromUUID(req, req.getAttribute("slug").toString());
                System.out.println("1");
            } catch (OrderNotFoundException e) {
                e.printStackTrace();
            }
            super.render("order - " + uuid, "salesOrders", req, resp);
        }*/
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            if (req.getPathInfo().charAt(req.getPathInfo().length() - 1) == '/') {
                slug = req.getPathInfo().replaceAll("/", "");
                try {
                    UUID uuid = setOrderFromUUID(req, slug);
                    System.out.println("2");
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
                    System.out.println("3");
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
        req.setAttribute("roof_material", api.getRoofMaterial(order.getCarport().getRoof_material()));
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
        String activeRoofAngle;
        int roofAngle;
        int roof_material;

        int shedWidth;
        int shedLength;

        int id = -1;

        carportLength = Integer.parseInt(req.getParameter("carport-length"));
        carportWidth = Integer.parseInt(req.getParameter("carport-width"));
        activeRoofAngle = req.getParameter("roof-angle");
        if (activeRoofAngle != null) {
            roofType = Carport.roofTypes.ANGLED;
            roofAngle = Integer.parseInt(req.getParameter("roof-angle"));
            roof_material = Integer.parseInt(req.getParameter("roof_angled_material"));
        }
        else {
            roofType = Carport.roofTypes.FLAT;
            roof_material = Integer.parseInt(req.getParameter("roof_flat_material"));
            System.out.println();
            roofAngle = -1;
        }

        Carport carport = new Carport(id, carportWidth, carportLength, roofType, roofAngle, roof_material);

        //create shed
        Shed shed = null;
        if (req.getParameter("shed-length") != null) {
            shedWidth = Integer.parseInt(req.getParameter("shed-width"));
            shedLength = Integer.parseInt(req.getParameter("shed-length"));
            shed = new Shed(id, shedWidth, shedLength);
        }

        //create order
        System.out.println("Laver ordre");
        int carportId = api.getCarportIdFromUuid(uuid);
        api.updateOrder(carportId, carport, shed);
        System.out.println("Ordre færdig");

        resp.sendRedirect(slug);
    }
}
