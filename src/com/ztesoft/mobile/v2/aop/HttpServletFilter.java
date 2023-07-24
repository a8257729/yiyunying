package com.ztesoft.mobile.v2.aop;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * request请求封装过滤器，重写了HttpServletRequestWrapper中的读取数据流方法
 * 过滤器用于使请求用封装后的request请求进行后续处理，一便可以进行多次读取数据流
 * add by yangjihui 2020/04/22
 */
public class HttpServletFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
//        System.out.println("我是过滤器");
        if(request instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) request);
        }
        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }
    public void destroy() {

    }
}
