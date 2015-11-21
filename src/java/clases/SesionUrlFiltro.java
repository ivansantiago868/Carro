/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Juanda
 */
@WebFilter("*.xhtml")
public class SesionUrlFiltro implements Filter {

    FilterConfig configuracionFiltro;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.configuracionFiltro = filterConfig;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);

        String requestUrl = req.getRequestURI();
        String[] urlSinSesion = new String[]{
            "/faces/index.xhtml","/faces/pxpuerto.xhtml","/faces/pxetapa.xhtml","/faces/pxcarrera.xhtml"
        };

        boolean redireccion;

        if (session.getAttribute("usuario") == null) {
            
            redireccion=true;
            
            for (String item : urlSinSesion) {
                if (requestUrl.contains(item)) {
                    redireccion = false;
                    break;
                }
            }
        }
        else{
            redireccion=false;
        }
        if (redireccion) {
            res.sendRedirect(req.getContextPath() + "/faces/index.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        configuracionFiltro = null;
    }
}