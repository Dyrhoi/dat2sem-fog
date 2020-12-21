package web.filters;

import domain.user.sales_representative.SalesRepresentative;
import domain.user.sales_representative.SalesRepresentativeExistsException;
import domain.user.sales_representative.SalesRepresentativeNotFoundException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignedInFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            if (!(httpRequest.getSession().getAttribute("user") instanceof SalesRepresentative))
                throw new SalesRepresentativeNotFoundException();

            filterChain.doFilter(request, response);

        } catch (SalesRepresentativeNotFoundException e) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/authenticate");
        }
    }
}
