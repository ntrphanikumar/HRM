package com.nrkpj.commetial.hrm.ui.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.nrkpj.commetial.hrm.utils.ServiceFactory;

@SuppressWarnings("serial")
public class StartupServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ServiceFactory.initializeSpringContext();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
