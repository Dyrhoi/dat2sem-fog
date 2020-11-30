package web.servlets;

import api.API;
import domain.material.dao.MaterialDAO;
import infrastructure.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseServlet extends HttpServlet {
    /*
    *
    * Initial setup for API class.
    *
    * */
    protected static final API api;

    static {
        API tmp = null;
        try {
            tmp = attachAPI();
        } catch(RuntimeException e) {
            e.printStackTrace();
        }
        api = tmp;

    }
    private static API attachAPI() {
        Database db = new Database();
        return new API(new MaterialDAO(db));
    }

    /*
    *
    * Render method.
    *
    * */
    protected void render(String title, String content, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("title", title);
        req.setAttribute("content", content);

        req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
    }
}
