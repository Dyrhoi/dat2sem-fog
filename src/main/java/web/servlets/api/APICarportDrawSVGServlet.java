package web.servlets.api;

import domain.carport.Carport;
import domain.carport.Shed;
import web.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/carport-svg-drawing")
public class APICarportDrawSVGServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int carportLength = Integer.parseInt(req.getParameter("carport-length"));
            int carportWidth = Integer.parseInt(req.getParameter("carport-width"));

            Carport.roofTypes roofType = Carport.roofTypes.valueOf(req.getParameter("roof-type").toUpperCase());

            Shed shed = null;
            int shedWidth;
            int shedLength;

            boolean carportHasShed = req.getParameter("shed-checkbox") != null;
            if (carportHasShed) {
                shedWidth = Integer.parseInt(req.getParameter("shed-width"));
                shedLength = Integer.parseInt(req.getParameter("shed-length"));
                shed = new Shed(-1, shedWidth, shedLength);
            }
            Carport carport = new Carport(-1, shed, carportWidth, carportLength, roofType, null, -1);

            String svg = api.getSVGDrawing(carport);
            resp.getWriter().println(svg);
        } catch (NumberFormatException e) {
            resp.sendError(422);
        }
    }
}
