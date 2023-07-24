package com.ztesoft.mobile.common.xwork.execution;

import org.dom4j.Element;
import java.util.Calendar;
import net.sf.cglib.beans.BeanMap;
import com.ztesoft.mobile.common.exception.AjaxVisibleException;
import java.util.Collection;
import java.util.Map;
import java.util.Date;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Iterator;
import org.dom4j.Document;
import java.io.OutputStream;
import java.io.IOException;

import com.ztesoft.mobile.common.helper.DateHelper;


final public class ConvertObjectToXML {
    public ConvertObjectToXML(){}

 /**
 * 把object转换成XML格式
 * @param obj
 * @param actionSpace
 * @param actionName
 * @return
 */
    public static String ObjectToXML(Object obj, String actionSpace, String actionName) throws IOException {
        String output = "<?xml version=\"1.0\" encoding=\"gb2312\"?>";
       output = output + " <input actionSpace=\"" + actionSpace + "\" ";
       output = output + " actionName=\"" + actionName + "\" ";
       output = output + " actionPath=\"" + actionSpace +"/" + actionName + "\">";
       output = output + ObjectToString(obj);
       output = output + "</input>";
       System.out.println(output);
       return output;
    }

    public static String ObjectToString(Object obj) throws IOException {
        String retString = obj.toString();
        Document doc = XMLParse.createDocument();
        OutputStream outputS = null;

        Element root = XMLParse.createElement("param");
        objectToXml(root, null, obj,0);

         retString = root.asXML();

         System.out.println(retString);
        return retString;
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


    final private static void objectToXml(Element ele, String name, Object value, int _deep) {
   String type = getObjectType(value);
   //int _deep =0;

   if (name != null && name.trim().length() > 0) {
     XMLParse.setAttribute(ele, "name", name);
   }
   if (type.equalsIgnoreCase("n")) {
    XMLParse.setAttribute(ele, "type", type);
    XMLParse.setAttribute(ele, "value", "n");
   }else if (type.equalsIgnoreCase("ele")) {
     XMLParse.setAttribute(ele, "type", type);
     ele.add((Element) value);
   } else if (type.equalsIgnoreCase("b") || type.equalsIgnoreCase("i")
       || type.equalsIgnoreCase("l") || type.equalsIgnoreCase("f")
       || type.equalsIgnoreCase("s")) {
     XMLParse.setAttribute(ele, "type", type);
      XMLParse.setAttribute(ele, "value", value.toString());
     //ele.addCDATA(value.toString());
     //System.out.println(ele.getData().toString());
   } else if (type.equalsIgnoreCase("ve")) {
     AjaxVisibleException ex = (AjaxVisibleException) value;
     setEleFromVE(ele, ex);
     // ele.addCDATA(strB.toString());
   } else if (type.equalsIgnoreCase("e")) {
     Throwable ex = (Throwable) value;
     StringBuffer strB = new StringBuffer();
     exceptionToStr(ex, strB);
     XMLParse.setAttribute(ele, "type", type);
     XMLParse.setAttribute(ele, "value", value.toString());
     //ele.addCDATA(strB.toString());
   } else if (type.equalsIgnoreCase("t")) {
     XMLParse.setAttribute(ele, "type", type);
     XMLParse.setAttribute(ele, "value", DateHelper.parse((Date)value));
     //ele.addCDATA(value.toString());
   } else if (type.equalsIgnoreCase("t2")) {
     Calendar calendar = (Calendar) value;
     XMLParse.setAttribute(ele, "type", type);
     XMLParse.setAttribute(ele, "value", value.toString());
     //ele.addCDATA(calendar.getTime().toString());
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

}
