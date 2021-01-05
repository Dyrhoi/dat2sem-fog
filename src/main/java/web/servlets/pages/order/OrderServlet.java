package web.servlets.pages.order;

import api.Util;
import domain.carport.Carport;
import domain.order.Order;
import domain.user.customer.Customer;
import domain.order.OrderFactory;
import domain.carport.Shed;
import validation.ValidationErrorException;
import web.plugins.Notifier;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

//URL: /
@WebServlet("")
public class OrderServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roofMaterials", api.getRoofMaterials());
        super.render("Byg selv Carport - Fog", "order/order", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname;
        String lastname;
        String email;
        String phone;

        String address;
        String city;
        String postalCode;

        boolean carportHasShed;

        int carportWidth;
        int carportLength;
        Carport.roofTypes roofType;
        Integer roofAngle;
        int roof_material;

        int shedWidth;
        int shedLength;

        int id = -1;
        try {
            //create customer
            firstname = req.getParameter("first-name");
            lastname = req.getParameter("last-name");
            email = req.getParameter("email");
            phone = req.getParameter("phone-number");
            address = req.getParameter("address");
            city = req.getParameter("city");
            postalCode = req.getParameter("postal-code");

            Customer.Address tmpAddress = new Customer.Address(address, city, postalCode);
            Customer customer = new Customer(id, firstname, lastname, email, phone, tmpAddress);

            //create carport
            carportWidth = Integer.parseInt(req.getParameter("carport-width"));
            carportLength = Integer.parseInt(req.getParameter("carport-length"));
            roofType = Carport.roofTypes.valueOf(req.getParameter("roof-type").toUpperCase());
            carportHasShed = req.getParameter("shed-checkbox") != null;
            if (roofType.equals(Carport.roofTypes.ANGLED)){
                roofAngle = Integer.parseInt(req.getParameter("roof-angle"));
                roof_material = Integer.parseInt(req.getParameter("roof_angled_material"));
            }
            else {
                roof_material = Integer.parseInt(req.getParameter("roof_flat_material"));
                roofAngle = -1;
            }

            //create shed
            Shed shed = null;
            if (carportHasShed) {
                shedWidth = Integer.parseInt(req.getParameter("shed-width"));
                shedLength = Integer.parseInt(req.getParameter("shed-length"));
                shed = new Shed(id, shedWidth, shedLength);
            }
            Carport carport = new Carport(id, shed, carportWidth, carportLength, roofType, roofAngle, roof_material);

            String note = req.getParameter("note");


            //create order
            OrderFactory orderFactory = api.createOrder();
            UUID uuid = UUID.randomUUID();
            orderFactory.setUuid(uuid);

            orderFactory.setCustomer(customer);

            orderFactory.setCarport(carport);
            orderFactory.setShed(shed);

            orderFactory.setNote(note);

            String token = Util.generateSecureToken();
            orderFactory.setToken(token);

            Order order = orderFactory.validateAndCommit();
            resp.sendRedirect(req.getContextPath() + "/order/thank-you/" + order.getToken());
        } catch (ValidationErrorException e) {
            Notifier notifier = new Notifier(Notifier.Type.DANGER, "Vi kunne ikke oprette ordren, disse inputs va ikke udfyldt korrekt.", e);
            super.addNotifcation(req, notifier);
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
