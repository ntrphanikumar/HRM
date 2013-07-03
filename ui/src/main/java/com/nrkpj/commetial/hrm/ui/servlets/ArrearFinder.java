package com.nrkpj.commetial.hrm.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nrkpj.commetial.hrm.core.dos.CurrentMeter;
import com.nrkpj.commetial.hrm.core.dos.House;
import com.nrkpj.commetial.hrm.core.dos.WaterMeter;
import com.nrkpj.commetial.hrm.core.services.APCPDCLArrearService;
import com.nrkpj.commetial.hrm.core.services.HMWSSBArrearService;
import com.nrkpj.commetial.hrm.utils.ServiceFactory;

public class ArrearFinder extends HttpServlet {

    public static final String CURRENT_ARREARS = "current_arrears";
    public static final String WATER_ARREARS = "water_arrears";

    private static final long serialVersionUID = -7432124979510620486L;

    private APCPDCLArrearService apcpdclArrearService;
    private HMWSSBArrearService waterArrearService;

    public void init() throws ServletException {
        super.init();
        apcpdclArrearService = ServiceFactory.getService(APCPDCLArrearService.class);
        waterArrearService = ServiceFactory.getService(HMWSSBArrearService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemType = request.getParameter("itemType");
        if ("power".equals(itemType)) {
            String circle = request.getParameter("circle");
            String ero = request.getParameter("ero");
            String serviceNum = request.getParameter("scno");
            CurrentMeter currentMeter = new CurrentMeter().setScno(serviceNum).setHouse(new House().setCircle(circle).setEro(ero));
            double amount = apcpdclArrearService.getArrears(currentMeter);
            request.getSession().setAttribute(CURRENT_ARREARS, amount);
        } else if ("water".equals(itemType)) {
            String canNum = request.getParameter("can");
            double amount = waterArrearService.getArrears(new WaterMeter().setCanNumber(canNum));
            request.getSession().setAttribute(WATER_ARREARS, amount);

        }
        response.sendRedirect("/hrm/jsp/arrears.jsp");
    }

}
