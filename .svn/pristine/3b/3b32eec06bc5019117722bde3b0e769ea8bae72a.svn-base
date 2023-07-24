package com.ztesoft.mobile.common.xwork.execution;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.cglib.beans.BeanMap;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ztesoft.mobile.common.helper.ValidateHelper;
import com.ztesoft.mobile.common.exception.AjaxVisibleException;
import com.ztesoft.mobile.common.exception.XMLDocException;
import org.dom4j.DocumentException;
import com.ztesoft.mobile.common.helper.DateHelper;

final public class ExecuteHelp {
/**
 * 从InputStream里获得ActionModel，ActionModel中包含了需要调用Action的基本信息
 * @param inputStream
 * @param characterEncoding
 * @return
 * @throws ParseException
 * @throws DocumentException
 */
  public static ActionModel getActionModelFromInputStream(
      InputStream inputStream, String characterEncoding)
      throws ParseException, DocumentException {

    ActionModel actionModel = new ActionModel();
    Document doc = XMLParse.fromXML(inputStream, characterEncoding);

    if (doc != null) {
      Map parameterMap = new HashMap();
      Element root = doc.getRootElement();
      List list = XMLParse.getChildElements(root, "param");

      if (!list.isEmpty()) {
        Object _parameter = null;
        Element _element = null;
        for (int i = 0; i < list.size(); i++) {
          _element = (Element) list.get(i);
          _parameter = convetToObject(_element);
          parameterMap.put("parameter_" + (i + 1), _parameter);
        }
        _element = null;
        _parameter = null;
      }
      actionModel.setParameterMap(parameterMap);
      actionModel.setActionSpace(XMLParse.getAttribute(root,
          "actionSpace"));
      actionModel
          .setActionName(XMLParse.getAttribute(root, "actionName"));
      actionModel
          .setActionPath(XMLParse.getAttribute(root, "actionPath"));
    }

    return actionModel;
  }
/**
 * 把返回结果刷新到输出流里去
 * @param obj
 * @param outputS
 * @throws IOException
 */
  public static void flushOutputStream(Object obj, OutputStream outputS)
      throws IOException {
    Document doc = XMLParse.createDocument();
    Element root = XMLParse.createElement("Output");
    doc.add(root);
    objectToXml(root, null, obj,1);
    XMLParse.toXML(doc, outputS, "GBK");
  }
/**
 * 如果有异常，返回异常方法
 * @param e
 * @param outputStream
 * @throws IOException
 */
  public static void sendException(AjaxVisibleException e,
      OutputStream outputStream) throws IOException {
    Document doc = XMLParse.createDocument();
    Element root = XMLParse.createElement("Output");
    doc.add(root);
    setEleFromVE(root, e);
    XMLParse.toXML(doc, outputStream, "GBK");
  }

  public static void sendException2(AjaxVisibleException e,
      OutputStream outputS) throws IOException {
    Document doc = XMLParse.createDocument();
    Element root = XMLParse.createElement("exception");
    XMLParse.setAttribute(root, "level", e.getLevel());
    XMLParse.setAttribute(root, "code", e.getExceptionCode());
    root.addCDATA(e.getExceptionString());
    doc.add(root);
    XMLParse.toXML(doc, outputS, "GBK");
  }
  /**
   * 一个Element转换成object
   * @param ele
   * @return
   * @throws ParseException
   */
  public static Object convetToObject(Element ele) throws ParseException {
    String type = XMLParse.getAttribute(ele, "type");
    String temp = XMLParse.getAttribute(ele, "value");
    Object value = null;
    if (type.equalsIgnoreCase("i")) {
      value = temp == "" ? null : new Integer(temp);
    } else if (type.equalsIgnoreCase("f")) {
      value = temp == "" ? null : new Float(temp);
    } else if (type.equalsIgnoreCase("t")) {
      value = temp == "" ? null : parseDateTime(temp);
    } else if (type.equalsIgnoreCase("t2")) {
      if (!ValidateHelper.validateNotEmpty(temp)) {
        value = null;
      } else {
        Date d = parseDateTime(temp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        value = calendar;
      }
      // value = temp == "" ? null : calendar.setTime();
    } else if (type.equalsIgnoreCase("b")) {
      value = temp == "" ? null : new Boolean(temp);
    } else if (type.equalsIgnoreCase("s")) {
      value = temp;
    } else if (type.equalsIgnoreCase("m")) {
      Map params = new HashMap();
      List list = XMLParse.getChildElements(ele);
      for (int i = 0; i < list.size(); i++) {
        Element childE = (Element) list.get(i);
        params.put(XMLParse.getAttribute(childE, "name"),
            convetToObject(childE));
      }
      value = params;
    } else if (type.equalsIgnoreCase("c")) {
      List params = new ArrayList();
      List list = XMLParse.getChildElements(ele);
      for (int i = 0; i < list.size(); i++) {
        Element childE = (Element) list.get(i);
        params.add(convetToObject(childE));
      }
      value = params;
    }
    return value;
  }
/**
 * 获得对象类型。通用方法
 * @param obj
 * @return
 */
  private static String getObjectType(Object obj) {
    // n=null,b,i,f,s,t,o,a,e=exception
    if (obj == null) {
      return "n";
    }
    if (obj instanceof Void) {
      return "n";
    }

    if (obj instanceof AjaxVisibleException) {
      return "ve";
    }

    if (obj instanceof Exception) {
      return "e";
    }

    if (obj instanceof Integer || obj instanceof BigInteger) {
      return "i";
    } else if (obj instanceof Float || obj instanceof Double
        || obj instanceof BigDecimal) {
      return "f";
    } else if (obj instanceof Long) {
      return "l";
    } else if (obj instanceof Boolean) {
      return "b";
    } else if (obj instanceof Date) {
      return "t";
    } else if (obj instanceof Calendar) {
      return "t2";
    } else if (obj instanceof String) {
      return "s";
    } else if (obj instanceof Collection) {
      return "c";
    } else if (obj instanceof Map) {
      return "m";
    } else if (obj.getClass().isArray()) {
      return "a";
    } else if (obj instanceof Element) {
      return "ele";
    } else {
      return "o";
    }
  }
/**
 * 把Map里的数据设置进element里
 * @param ele
 * @param map
 * @param _deep
 */
  private static void setEleFromM(Element ele, Map map, int _deep) {
    if (_deep > 10) {
      String _val = MonitorActionThreadLocal.getValue().toString();
      MonitorActionThreadLocal.putValue(null);
      throw new RuntimeException(_val+"层次嵌套超过十层");
      //throw new RuntimeException("层次嵌套超过十层");
    }
    XMLParse.setAttribute(ele, "type", "m");
    Iterator ir = map.keySet().iterator();
    while (ir.hasNext()) {
      Element childE = XMLParse.createElement("param");
      ele.add(childE);
      Object key = ir.next();
      Object obj = map.get(key);
      objectToXml(childE, (String) key, obj,_deep);
    }
  }
/**
 * 把collection里的数据设置进element中
 * @param ele
 * @param col
 * @param _deep
 */
  private static void setEleFromC(Element ele, Collection col, int _deep) {
    if (_deep > 10) {
      String _val = MonitorActionThreadLocal.getValue().toString();
      MonitorActionThreadLocal.putValue(null);
      throw new RuntimeException(_val+"层次嵌套超过十层");
      //throw new RuntimeException("层次嵌套超过十层");
    }
    XMLParse.setAttribute(ele, "type", "c");
    Iterator ir = col.iterator();
    while (ir.hasNext()) {
      Element childE = XMLParse.createElement("param");
      ele.add(childE);
      objectToXml(childE, null, ir.next(),_deep);
    }
  }
/**
 * 设置exception进element
 * @param ele
 * @param ve
 */
  private static void setEleFromVE(Element ele, AjaxVisibleException ve) {
    XMLParse.setAttribute(ele, "type", "ve");
    Element childE = XMLParse.createElement("exception");

    XMLParse.setAttribute(childE, "level", ve.getLevel());
    XMLParse.setAttribute(childE, "code", ve.getExceptionCode());
    childE.addCDATA(ve.getExceptionString());

    ele.add(childE);
  }
/**
 * 设置array进element
 * @param ele
 * @param obj
 * @param _deep
 */
  private static void setEleFromA(Element ele, Object[] obj,int _deep) {

    XMLParse.setAttribute(ele, "type", "c");
    for (int i = 0; i < obj.length; i++) {
      Element childE = XMLParse.createElement("param");
      ele.add(childE);
      objectToXml(childE, null, obj[i],_deep);
    }
  }
/**
 * 异常转换成String
 * @param ex
 * @param strB
 */
  private static void exceptionToStr(Throwable ex, StringBuffer strB) {
    StackTraceElement[] em = ex.getStackTrace();
    strB.append("the cause:" + ex + "\n");
    for (int i = 0; i < em.length; i++) {
      strB.append("\tat... " + em[i] + "\n");
    }
    strB.append("\t... more:" + em.length + "\n");
    Throwable nEx = ex.getCause();
    if (nEx != null) {
      exceptionToStr(nEx, strB);
    }
  }
/**
 * 对象转换成xml
 * @param ele
 * @param name
 * @param value
 * @param _deep
 */
  final private static void objectToXml(Element ele, String name, Object value, int _deep) {
    String type = getObjectType(value);
    //int _deep =0;
    if (name != null && name.trim().length() > 0) {
      XMLParse.setAttribute(ele, "name", name);
    }
    if (type.equalsIgnoreCase("n")) {
      return;
    }
    if (type.equalsIgnoreCase("ele")) {
      XMLParse.setAttribute(ele, "type", type);
      ele.add((Element) value);
    } else if (type.equalsIgnoreCase("b") || type.equalsIgnoreCase("i")
        || type.equalsIgnoreCase("l") || type.equalsIgnoreCase("f")
        || type.equalsIgnoreCase("s")) {
      XMLParse.setAttribute(ele, "type", type);
      ele.addCDATA(value.toString());
    } else if (type.equalsIgnoreCase("ve")) {
      AjaxVisibleException ex = (AjaxVisibleException) value;
      setEleFromVE(ele, ex);
      // ele.addCDATA(strB.toString());
    } else if (type.equalsIgnoreCase("e")) {
      Throwable ex = (Throwable) value;
      StringBuffer strB = new StringBuffer();
      exceptionToStr(ex, strB);
      XMLParse.setAttribute(ele, "type", type);
      ele.addCDATA(strB.toString());
    } else if (type.equalsIgnoreCase("t")) {
      XMLParse.setAttribute(ele, "type", type);
      //ele.addCDATA(value.toString());
      ele.addCDATA(DateHelper.parse((Date)value));
    } else if (type.equalsIgnoreCase("t2")) {
      Calendar calendar = (Calendar) value;
      XMLParse.setAttribute(ele, "type", type);
      //ele.addCDATA(calendar.getTime().toString());
      ele.addCDATA(DateHelper.parse((Date)calendar.getTime()));
    } else if (type.equalsIgnoreCase("c")) {
      setEleFromC(ele, (Collection) value,++_deep);
    } else if (type.equalsIgnoreCase("m")) {
      setEleFromM(ele, (Map) value,++_deep);
    } else if (type.equalsIgnoreCase("a")) {
      setEleFromA(ele, (Object[]) value,++_deep);
    } else if (type.equalsIgnoreCase("o")) {
      Map map = BeanMap.create(value);
      setEleFromM(ele, map,++_deep);
    }
  }
/**
 * 转换指定的时间格式
 * @param str
 * @return
 * @throws ParseException
 */
  private static Date parseDateTime(String str) throws ParseException {
    SimpleDateFormat dateFormat = null;
    if (str.length() == 10) {
      dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    } else {
      dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    return dateFormat.parse(str);
  }
/**
 * 把element转换成xml流
 * @param ele
 * @return
 * @throws XMLDocException
 */
  public static String toXmlString(Element ele) throws XMLDocException {
    Document doc = XMLParse.createDocument();
    doc.add(ele);
    return Dom4jUtils.toXML(doc, "GBK");
  }
}
