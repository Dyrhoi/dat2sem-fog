package web.servlets.pages;

import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//URL: /
@WebServlet("")
public class OrderServlet extends BaseServlet {
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

        boolean carportHasShed;
        //TODO: Roof type ?

        int carportWidth;
        int carportLength;


    }
}
