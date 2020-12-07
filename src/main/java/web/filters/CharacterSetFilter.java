package web.filters;

import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class CharacterSetFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        /*
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        */
        filterChain.doFilter(request, response);
        /*
        LoggerFactory.getLogger(CharacterSetFilter.class).info(request.getParameter("first-name"));
        */
    }
}
