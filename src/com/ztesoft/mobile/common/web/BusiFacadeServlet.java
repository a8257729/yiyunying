package com.ztesoft.mobile.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zterc.uos.UOSException;
import com.zterc.uos.helpers.ClientKeys;
import com.ztesoft.mobile.common.helper.StringHelper;
import org.apache.log4j.Logger;
import com.zterc.uos.helpers.ClientJNDINames;
import com.zterc.uos.runtime.ExecutionDelegate;
import com.zterc.uos.runtime.ExecutionFactory;
import com.zterc.uos.util.xml.XMLDom4jUtils;

/**
 *
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 * @author eoms
 * @Modify dawn
 * @version 1.0
 */
public class BusiFacadeServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(BusiFacadeServlet.class);
    //字符集设置
    private static final String LOCAL_CHARSET = "GBK";
    //JNDI文件路径
    private static String jndiFilePath = null;


    /** Initialize global variables */
    public void init() throws ServletException {
        super.init();
        
        String path = getServletContext().getRealPath("/");
        if (path == null) {
            path = "";
        }

        String jndipropertiesfile = path +
                                    getInitParameter("jndi.properties.file");
        String publickeyfile = path + getInitParameter("public.key.file");
        String licensefile = path + getInitParameter("license.file");
        String usertransactionFlag = getInitParameter("usertransaction.flag");
        String runtimeTier = getInitParameter("runtime.tier");
        String remoteServerHost = getInitParameter("remoteServerHost");

        try {
            System.setProperty(ClientKeys.WEB_INF_PATH,
                               path);
            System.setProperty(ClientKeys.JNDIPROPERTIES_FILE,
                               jndipropertiesfile);
            //重新初始化UOSHelper.ClientJNDINames类 zhouzhk 2006-02-20
            ClientJNDINames.loadConfig();
            //设置runtime是EJB还是JavaBean。
            System.setProperty("RUNTIME_TIER", runtimeTier);
            //当ejb和web服务器分离部署的时候，部分web页面需要直接调用服务器端的servlet，且后台组装url，需获得此变量
            System.setProperty("remoteServerHost", remoteServerHost);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 统一服务方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void service(HttpServletRequest request,
                        HttpServletResponse response) throws
            ServletException, IOException {
        request.setCharacterEncoding(LOCAL_CHARSET);
        response.setContentType("text/html; charset=" + LOCAL_CHARSET);
        String returnValue = null;

        try {
            returnValue = ExecutionFactory.execute(request, response);
        } catch (UOSException e) {
            e.printStackTrace(System.out);
            e.printStackTrace(System.err);
            //returnValue = e.getMessage();
            returnValue = e.toXML();
        }

        finally {
            /*
            try {
                //强制关闭threadLocal中connection，线程缓存connection要被关闭。不管dao有没有关闭。这里都检验，强制关闭。以便支持一次对话一个connection(dawn)
                ConnectionAdapters.closeConnection();

            } catch (SQLException e) {
                throw new ServletException("Connection closed Error" +
                                           e.getMessage());
            }
            //重新设置threadLocal
            ConnectionAdapters.resetThreadLocal();
*/
        }

        PrintWriter out = response.getWriter();
        out.print(returnValue);
        out.close();
    }

    private String getJndiFilePath() {

        return getServletContext().getRealPath("/")
                +
                StringHelper.replaceNull(getInitParameter(
                        "jndi.properties.file"));

    }


    /** Clean up resources */
    public void destroy() {
        super.destroy();
    }
}
