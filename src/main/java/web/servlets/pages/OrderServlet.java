package web.servlets.pages;

import api.Util;
import domain.carport.Carport;
import domain.customer.Customer;
import domain.order.OrderFactory;
import domain.shed.Shed;
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
        super.render("Byg selv Carport - Fog", "order", req, resp);
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
            customer.setComment(req.getParameter("comment"));

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
            OrderFactory orderFactory = api.createOrder();
            UUID uuid = UUID.randomUUID();
            orderFactory.setUuid(uuid);

            orderFactory.setCustomer(customer);

            orderFactory.setCarport(carport);
            orderFactory.setShed(shed);

            String token = Util.generateSecureToken();
            orderFactory.setToken(token);

            orderFactory.validateAndCommit();

            //Todo:Dyrhoi - Better catch exceptions and validation.
        } catch (Exception e){
            e.printStackTrace();
        }

        doGet(req, resp);
    }
}
