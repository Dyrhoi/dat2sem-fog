package web.servlets.pages;

import domain.carport.Carport;
import domain.customer.Customer;
import domain.shed.Shed;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//URL: /
@WebServlet("")
public class OrderServlet extends BaseServlet {

    //Temp ID creator
    private int id = 1;
    //When DBDAO is up this is to be removed

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roofTypes", api.getRoofTypes());
        super.render("Byg selv Carport - Fog", "order", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: Dyrhoi - Include variables from frontend
        String firstname;
        String lastname;
        String email;
        String phone;

        String address;
        String city;
        String postalCode;


        boolean carportHasShed;
        //TODO: Roof type ?

        int carportWidth;
        int carportLength;
        String roofType;
        int roofAngle;

        int shedWidth;
        int shedLength;

        try {
            firstname = req.getParameter("first-name");
            lastname = req.getParameter("last-name");
            email = req.getParameter("email");
            phone = req.getParameter("phone-number");

            address = req.getParameter("address");
            city = req.getParameter("city");
            postalCode = req.getParameter("postal-code");

            Customer.Address tmpAddress = new Customer.Address(address, city, postalCode);
            Customer customer = new Customer(id, firstname, lastname, email, phone, tmpAddress);

            id = id + 1;

            carportWidth = Integer.parseInt(req.getParameter("carport-width"));
            carportLength = Integer.parseInt(req.getParameter("carport-length"));
            roofType = req.getParameter("roof-type");
            //carportHasShed = req.getParameter("shed-checkbox").equals("on");
            carportHasShed = req.getParameter("shed-checkbox") != null;

            Carport carport = new Carport(id, carportWidth, carportLength, roofType);

            if (roofType.equals("angled")){
                roofAngle = Integer.parseInt(req.getParameter("roof-angle"));
                carport.setRoofAngle(roofAngle);
            }

            Shed shed = null;
            if (carportHasShed) {
                shedWidth = Integer.parseInt(req.getParameter("shed-width"));
                shedLength = Integer.parseInt(req.getParameter("shed-length"));
                shed = new Shed(id, shedWidth, shedLength);
            }

            System.out.println(customer.toString());
            System.out.println(carport.toString());
            if (shed != null) {
                System.out.println(shed.toString());
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
