package com.ztesoft.mobile.common.web;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ztesoft.mobile.common.xwork.execution.*;
import com.ztesoft.mobile.common.exception.*; 

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author:ossdev
 * @version 1.0
 */
public class ActionInvocationServlet
    extends HttpServlet {

  public void init() throws ServletException {
    super.init();
  }

  public void destroy() {
    super.destroy();
  }

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
      IOException {

    response.setContentType("text/xml; charset=GBK");
    OutputStream outputStream = response.getOutputStream();
    Object returnObject = null;
    try {
    returnObject  = ActionExecution.singleton().excuteAction(
          request, response);
      ExecuteHelp.flushOutputStream(returnObject, outputStream);

    }
    catch (AjaxVisibleException ve) {
      ve.printStackTrace();
      ExecuteHelp.sendException(ve, outputStream);
    }
    catch (Exception e) {
      e.printStackTrace();
      AjaxVisibleException ve = new AjaxVisibleException("0", "0", e);
      ExecuteHelp.sendException(ve, outputStream);
    }
    finally {
      if (outputStream != null) {
        outputStream.close();
      }
      returnObject = null;
    }
  }

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
      IOException {
    doPost(request, response);
  }
}