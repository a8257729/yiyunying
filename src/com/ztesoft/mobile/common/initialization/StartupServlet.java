package com.ztesoft.mobile.common.initialization;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class StartupServlet extends HttpServlet {
    //Initialize global variables
    public void init() throws ServletException {
        StartupOpe ope = new StartupOpe(getServletContext(), getServletConfig());
        //各有各的try-catch,以免导致失败的后面的不会执行
        try {
            ope.initEnVariable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ope.initXWorkActionMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
  
                
    }

    //Clean up resources
    public void destroy() {
        try {
             StartupOpe.cleanDAOMap();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

}
