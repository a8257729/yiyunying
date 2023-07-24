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

final public class ConvertXMLToObject {

  public static ActionModel getActionModelFromInputStream(
      InputStream inputStream,
      String characterEncoding) throws ParseException, DocumentException {

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
          System.out.println(_element);
          _parameter = convetToObject(_element);
          //System.out.println(_parameter);
          parameterMap.put("parameter_" + (i + 1), _parameter);
        }
        _element = null;
        _parameter = null;
      }
      actionModel.setParameterMap(parameterMap);
      actionModel.setActionSpace(XMLParse.getAttribute(root, "actionSpace"));
      actionModel.setActionName(XMLParse.getAttribute(root, "actionName"));
      actionModel.setActionPath(XMLParse.getAttribute(root, "actionPath"));
    }
    return actionModel;
  }

  public static void flushOutputStream(Object obj, OutputStream outputS) throws
      IOException {
    Document doc = XMLParse.createDocument();
    Element root = XMLParse.createElement("Output");
    doc.add(root);
    objectToXml(root, null, obj);
    XMLParse.toXML(doc, outputS, "GBK");
  }

  public static void sendException(AjaxVisibleException e,
                                   OutputStream outputStream) throws
      IOException {
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

  public static Object convetToObject(Element ele) throws ParseException {
    String type = XMLParse.getAttribute(ele, "type");
    //String temp = XMLParse.getAttribute(ele, "value");
    String temp = ele.getTextTrim();
    Object value = null;
    if (type.equalsIgnoreCase("i")) {
      value = temp == "" ? null : new Integer(temp);
    }
    if (type.equalsIgnoreCase("l")) {
        value = temp == "" ? null : new Long(temp);
      }
    else if (type.equalsIgnoreCase("f")) {
      value = temp == "" ? null : new Float(temp);
    }
    else if (type.equalsIgnoreCase("t")) {
      value = temp == "" ? null : parseDateTime(temp);
    }
    else if (type.equalsIgnoreCase("t2")) {
      if (!ValidateHelper.validateNotEmpty(temp)) {
        value = null;
      }
      else {
        Date d = parseDateTime(temp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        value = calendar;
      }
      // value = temp == "" ? null : calendar.setTime();
    }
    else if (type.equalsIgnoreCase("b")) {
      value = temp == "" ? null : new Boolean(temp);
    }
    else if (type.equalsIgnoreCase("s")) {
      value = temp;
    }
    else if (type.equalsIgnoreCase("m")) {
      Map params = new HashMap();
      List list = XMLParse.getChildElements(ele);
      for (int i = 0; i < list.size(); i++) {
        Element childE = (Element) list.get(i);
        params.put(XMLParse.getAttribute(childE, "name"),
                   convetToObject(childE));
      }
      value = params;
    }
    else if (type.equalsIgnoreCase("c")) {
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
    }
    else if (obj instanceof Float || obj instanceof Double ||
             obj instanceof BigDecimal) {
      return "f";
    }
    else if (obj instanceof Long) {
      return "l";
    }
    else if (obj instanceof Boolean) {
      return "b";
    }
    else if (obj instanceof Date) {
      return "t";
    }
    else if (obj instanceof Calendar) {
      return "t2";
    }
    else if (obj instanceof String) {
      return "s";
    }
    else if (obj instanceof Collection) {
      return "c";
    }
    else if (obj instanceof Map) {
      return "m";
    }
    else if (obj.getClass().isArray()) {
      return "a";
    }
    else if (obj instanceof Element) {
      return "ele";
    }
    else {
      return "o";
    }
  }

  private static void setEleFromM(Element ele, Map map) {
    XMLParse.setAttribute(ele, "type", "m");
    Iterator ir = map.keySet().iterator();
    while (ir.hasNext()) {
      Element childE = XMLParse.createElement("param");
      ele.add(childE);
      Object key = ir.next();
      Object obj = map.get(key);
      objectToXml(childE, (String) key, obj);
    }
  }

  private static void setEleFromC(Element ele, Collection col) {
    XMLParse.setAttribute(ele, "type", "c");
    Iterator ir = col.iterator();
    while (ir.hasNext()) {
      Element childE = XMLParse.createElement("param");
      ele.add(childE);
      objectToXml(childE, null, ir.next());
    }
  }

  private static void setEleFromVE(Element ele, AjaxVisibleException ve) {
    XMLParse.setAttribute(ele, "type", "ve");
    Element childE = XMLParse.createElement("exception");

    XMLParse.setAttribute(childE, "level", ve.getLevel());
    XMLParse.setAttribute(childE, "code", ve.getExceptionCode());
    childE.addCDATA(ve.getExceptionString());

    ele.add(childE);
  }

  private static void setEleFromA(Element ele, Object[] obj) {
    XMLParse.setAttribute(ele, "type", "c");
    for (int i = 0; i < obj.length; i++) {
      Element childE = XMLParse.createElement("param");
      ele.add(childE);
      objectToXml(childE, null, obj[i]);
    }
  }

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

  final private static void objectToXml(Element ele, String name,
                                        Object value) {
    String type = getObjectType(value);
    if (name != null && name.trim().length() > 0) {
      XMLParse.setAttribute(ele, "name", name);
    }
    if (type.equalsIgnoreCase("n")) {
      return;
    }
    if (type.equalsIgnoreCase("ele")) {
      XMLParse.setAttribute(ele, "type", type);
      ele.add( (Element) value);
    }
    else if (type.equalsIgnoreCase("b") || type.equalsIgnoreCase("i")
             || type.equalsIgnoreCase("l") || type.equalsIgnoreCase("f")
             || type.equalsIgnoreCase("s")) {
      XMLParse.setAttribute(ele, "type", type);
      ele.addCDATA(value.toString());
    }
    else if (type.equalsIgnoreCase("ve")) {
      AjaxVisibleException ex = (AjaxVisibleException) value;
      setEleFromVE(ele, ex);
      // ele.addCDATA(strB.toString());
    }
    else if (type.equalsIgnoreCase("e")) {
      Throwable ex = (Throwable) value;
      StringBuffer strB = new StringBuffer();
      exceptionToStr(ex, strB);
      XMLParse.setAttribute(ele, "type", type);
      ele.addCDATA(strB.toString());
    }
    else if (type.equalsIgnoreCase("t")) {
      XMLParse.setAttribute(ele, "type", type);
      ele.addCDATA(value.toString());
    }
    else if (type.equalsIgnoreCase("t2")) {
      Calendar calendar = (Calendar) value;
      XMLParse.setAttribute(ele, "type", type);
      ele.addCDATA(calendar.getTime().toString());
    }
    else if (type.equalsIgnoreCase("c")) {
      setEleFromC(ele, (Collection) value);
    }
    else if (type.equalsIgnoreCase("m")) {
      setEleFromM(ele, (Map) value);
    }
    else if (type.equalsIgnoreCase("a")) {
      setEleFromA(ele, (Object[]) value);
    }
    else if (type.equalsIgnoreCase("o")) {
      Map map = BeanMap.create(value);
      setEleFromM(ele, map);
    }
  }

  private static Date parseDateTime(String str) throws ParseException {
    SimpleDateFormat dateFormat = null;
    if (str.length() == 10) {
      dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    else {
      dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    return dateFormat.parse(str);
  }

  public static String toXmlString(Element ele) throws XMLDocException {
    Document doc = XMLParse.createDocument();
    doc.add(ele);
    return Dom4jUtils.toXML(doc, "GBK");
  }
}
