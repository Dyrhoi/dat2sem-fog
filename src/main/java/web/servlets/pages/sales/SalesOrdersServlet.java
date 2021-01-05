package web.servlets.pages.sales;

import domain.bill.Bill;
import domain.carport.Carport;
import domain.carport.Shed;
import domain.order.Order;
import domain.order.exceptions.OfferNotFoundException;
import domain.order.exceptions.OrderNotFoundException;
import domain.order.ticket.Ticket;
import domain.user.sales_representative.SalesRepresentative;
import web.plugins.Notifier;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet({"/sales/orders", "/sales/orders/*"})
public class SalesOrdersServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String slug;
        /*
         * UUID IS SET IN URL
         * */
        req.setAttribute("salesrep", null);
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            String path = req.getPathInfo().substring(1);

            slug = path.split("/")[0];

            String subPath = "";
            try {
                subPath = path.split("/")[1];
            } catch (IndexOutOfBoundsException ignored) {}

            if(subPath.equals("")) {
                try {
                    UUID uuid = setOrderFromUUID(req, slug);
                    req.setAttribute("page", 2);
                    super.render("order - " + uuid, "sales/salesOrders", req, resp);
                } catch (IllegalArgumentException | OrderNotFoundException e) { //Illegal Argument from UUID.fromString (Maybe just pass a string to DAO?)
                    e.printStackTrace();
                }
            }
            else if (subPath.equals("edit")) {
                try {
                    UUID uuid = setOrderFromUUID(req, slug);
                    req.setAttribute("roofMaterials", api.getRoofMaterials());
                    super.render("Changing order - " + uuid, "sales/changeOrder", req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (subPath.equals("ticket")) {
                try {
                    UUID uuid = setOrderFromUUID(req, slug);
                    Ticket ticket = api.getTicket(api.getOrder(uuid).getToken());
                    req.setAttribute("isStaff", true);
                    req.setAttribute("ticket", ticket);
                    super.render("Support Samtale - " + uuid, "sales/ticket", req, resp);
                } catch (OrderNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (subPath.equals("material-list")) {
                try {
                    UUID uuid = setOrderFromUUID(req, slug);
                    Order order = api.getOrder(uuid);
                    Bill bill = new Bill();
                    resp.setContentType("application/pdf");
                    bill.generatePDF(order, resp);
                } catch (OrderNotFoundException e ){
                    e.printStackTrace();
                }
            }
            else {
                resp.sendError(404);
            }

            /*
             * SHOW ALL ORDERS IF NO UUID IN URL
             * */
        }
        else {
            List<Order> orders = api.getOrders();
            req.setAttribute("orders", orders);
            super.render("Alle ordre - Fog", "sales/allOrders", req, resp);
        }
    }

    private UUID setOrderFromUUID(HttpServletRequest req, String slug) throws OrderNotFoundException {
        UUID uuid = UUID.fromString(slug);
        Order order = api.getOrder(uuid);

        req.setAttribute("order", order);
        req.setAttribute("roof_material", api.getRoofMaterial(order.getCarport().getRoof_material()));
        return uuid;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UUID uuid = null;

        if (action.equals("update-offer")) {
            Order.Offer offer = new Order.Offer(-1, null, Integer.parseInt(req.getParameter("offer")), false);
            uuid = UUID.fromString(req.getParameter("uuid"));
            SalesRepresentative salesRepresentative = (SalesRepresentative) req.getSession().getAttribute("user");
            try {

                api.updateOffer(uuid, offer, salesRepresentative);
                super.addNotifcation(req, new Notifier(Notifier.Type.SUCCESS, "Tilbud opdateret"));
            } catch (OfferNotFoundException | OrderNotFoundException e) {
                resp.sendError(404, "Vi kunne ikke finde ordren eller tilbudet, du prøvede at oprette.");
                return;
            }
        }
        else if (action.equals("update-salesrep")) {
            try {
                uuid = UUID.fromString(req.getParameter("uuid"));
                Order order = null;
                order = api.getOrder(uuid);
                SalesRepresentative salesRepresentative = (SalesRepresentative) req.getSession().getAttribute("user");
                int ret = api.updateSalesRep(order, salesRepresentative);
                super.addNotifcation(req, new Notifier(Notifier.Type.SUCCESS, "Ordre taget"));
                resp.sendRedirect(req.getContextPath() + "/sales/orders");
                return;
            } catch (OrderNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        else if (action.equals("update-order")) {
            try {
                uuid = UUID.fromString(req.getParameter("uuid"));
                Order order = api.getOrder(uuid);
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
                } else {
                    roofType = Carport.roofTypes.FLAT;
                    roof_material = Integer.parseInt(req.getParameter("roof_flat_material"));
                    roofAngle = -1;
                }

                Carport carport = new Carport(id, null, carportWidth, carportLength, roofType, roofAngle, roof_material);

                //create shed
                Shed shed = null;
                if (req.getParameter("shed-length") != null) {
                    shedWidth = Integer.parseInt(req.getParameter("shed-width"));
                    shedLength = Integer.parseInt(req.getParameter("shed-length"));
                    shed = new Shed(id, shedWidth, shedLength);
                }

                SalesRepresentative salesRepresentative = (SalesRepresentative) req.getSession().getAttribute("user");

                //create order
                int carportId = api.getCarportIdFromUuid(uuid);
                api.updateOrder(carportId, carport, shed, order, salesRepresentative);
                super.addNotifcation(req, new Notifier(Notifier.Type.SUCCESS, "Ordre opdateret"));
            }
            catch (OrderNotFoundException e) {
                resp.sendError(404, "Kunne ikke opdatere den her ordre, da den ikke kunne findes i systemet.");
                return;
            }

        }
        else if (action.equals("mark-as-paid")) {
            try {
                uuid = UUID.fromString(req.getParameter("uuid"));
                Order order = api.getOrder(uuid);
                boolean isPaid = req.getParameter("is-paid") != null && req.getParameter("is-paid").equals("on");
                if(order.hasAcceptedOffer() != null) {
                    api.markOrderAsPaid(order, isPaid, (SalesRepresentative) req.getSession().getAttribute("user"));
                    super.addNotifcation(req, new Notifier(Notifier.Type.SUCCESS, "Ordre opdateret, ordre status er blevet sat som " + (isPaid ? "betalt" : "ikke betalt")));
                }
                else throw new OrderNotFoundException();
            } catch (OrderNotFoundException e) {
                resp.sendError(404, "Kunne ikke opdatere den her ordre, da den ikke kunne findes i systemet.");
                return;
            }
        }
        else {
            resp.sendError(500, "NO ACTION FOUND!");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/sales/orders/" + uuid.toString());
    }
}