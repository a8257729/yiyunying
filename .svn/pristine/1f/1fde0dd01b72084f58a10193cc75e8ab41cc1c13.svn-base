package com.ztesoft.mobile.common.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ztesoft.mobile.common.helper.EqualsHelper;
import com.ztesoft.mobile.common.helper.ResourcesHelper;
import com.ztesoft.mobile.common.helper.ValidateHelper;
import com.ztesoft.mobile.common.helper.XMLHelper;
import com.ztesoft.mobile.common.exception.XMLDocException;
import com.ztesoft.mobile.common.exception.XMLParseException;

/**
 * <p>
 * Title: EomsProj
 * </p>
 *
 * <p>
 * Description: eoms系统配置实现管理类
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 *
 * <p>
 * Company: zteSoft
 * </p>
 *
 * @author dawn
 * @version 1.0
 */
public final class ConfigMgr {
  /** 日志接口 */
  private static final Log _log = LogFactory.getLog(ConfigMgr.class);

  /** 扫描标志,决定是否扫描配置文件时间戳 */
  private boolean scanFlag = false;

  /** 扫描间隔,毫秒计数 */
  private static int SCAN_INTERVAL = 1000 * 5;

  /** 配置属性缓存 */
  private Map configProp = new HashMap();

  /** 解析元素动作缓存 */
  private  Map nodeletMap = new HashMap();

  /** 文件路径,对应/WEB-INF/classes/cfg/system_config.xml */
  private String xmlFilePath = "cfg" + SPETA + "system_config.xml";

  /* xPath分割符 */
  private static final String SPETA = "/";

  /** config标签名 */
  private static final String CONFIG_TAG_NAME = "config";

  /** ItemNode标签名 */
  private static final String ITEMNODE_TAG_NAME = "itemnode";

  private static final String ITEM_TAG_NAME = "item";

  private static final String NAME_TAG_NAME = "name";

  /** 缓存上次最后修改时间 */
  private long lastFileModiedTime = -1;

  /**
   * 私有构造函数
   */
  private ConfigMgr() {
    // 先期载入xml配置文件
    loadConfigXML();
    // 如果开启了扫描标志,则用线程定时进行扫描

    if (scanFlag) {
      new Thread(new Runnable() {
        public void run() {
          while (scanFlag) {
            pause(SCAN_INTERVAL);
            _log.debug("Config Modify Thread Start......");
            /** 如果文件被修改,则进行重新载入 */
            if (isFileModified()) {
              _log.debug("Modify Properties Begin");
              loadConfigXML();
            }
          }
        }
      }).start();
    }

  }

  /**
   * 线程等待
   *
   * @param scanInterval
   *            int
   */
  private void pause(int scanInterval) {
    try {
      Thread.currentThread().sleep(scanInterval);
    }
    catch (java.lang.InterruptedException ie) {
      ; // Ignore
    }
  }

  /**
   * 比较文件是否更新
   *
   * @return boolean
   */

  private boolean isFileModified() {
    File xmlFile = null;
    try {
      xmlFile = ResourcesHelper.getResourceAsFile(ConfigMgr.class.getClassLoader(),xmlFilePath);
    }
    catch (IOException ex) {
        ex.printStackTrace();
      throw new XMLParseException("XML File:'"+xmlFilePath+"' not exists!");
    }

    if (lastFileModiedTime == -1) {
      lastFileModiedTime = xmlFile.lastModified();
    }

    boolean isModify = (lastFileModiedTime != xmlFile.lastModified());
    lastFileModiedTime = xmlFile.lastModified();
    return isModify;
  }

  /**
   * 载入xml文件进入缓存
   *
   * @throws XMLParseException
   */

  /**
   *
   */
  private void loadConfigXML() {

    Document doc = null;
    InputStream i = null;
    try {

      i = ResourcesHelper.getResourceAsStream(ConfigMgr.class.getClassLoader(),xmlFilePath);

      if (ValidateHelper.validateNotNull(i)) {
        doc = XMLHelper.fromXML(i, "GB2312");
        parseDoc(doc);
      }

    }catch (XMLDocException ex) {
     throw new XMLParseException(ex.getStackTraceAsString());
    }catch (IOException ie) {
      throw new XMLParseException(ie.getMessage());
    }
    finally {
      if (ValidateHelper.validateNotNull(i)) {
        try {
          i.close();
        }
        catch (IOException ex1) {
          ; // Ignore
        }
        i = null;
      }

    }

  }

  /**
   * 转换doc文档
   *
   * @param doc
   *            Document
   */
  private void parseDoc(Document doc) {
    if (ValidateHelper.validateNotNull(doc)) {
      ConfigEle _ele = null;

      String _xPath = SPETA + CONFIG_TAG_NAME + SPETA + ITEMNODE_TAG_NAME;

      List nodes = doc.selectNodes(_xPath);

      if (ValidateHelper.validateNotEmpty(nodes)) {
        for (int i = 0; i < nodes.size(); i++) {
          _ele = new ConfigEle();
          _ele.elementName = ITEMNODE_TAG_NAME;
          _ele.xPath = _xPath;
          Node _node = (Node) nodes.get(i);
          _ele.node = _node;
          if(nodeletMap ==null){nodeletMap=new HashMap();}
          nodeletMap.put(new Integer(i), _ele);
        }
      }
      processAll(nodeletMap);

      nodeletMap = null;

    }
    else {
      throw new XMLParseException("DOCMENT IS NULL!");
    }

  }

  /**
   * 执行所有的解析器进行转换
   *
   * @param nodeletMap
   *            Map
   */
  private void processAll(Map itemNodes) {
    if (ValidateHelper.validateNotNull(itemNodes)) {

      for (Iterator entryIter = nodeletMap.entrySet().iterator(); entryIter
           .hasNext(); ) {
        Entry entry = (Entry) entryIter.next();
        Object ele = entry.getValue();
        if (ele instanceof ConfigEle) {
          ConfigEle temp = (ConfigEle) ele;
          temp = nodeletImpl.processItemNodes(temp);
          String itemNodeName = null;
          if (ValidateHelper.validateNotNull(temp)) {
            if (ValidateHelper.validateNotNull(temp.attributes)) {
              itemNodeName = (String) temp.attributes
                  .get(NAME_TAG_NAME);
            }
            else {
              throw new IllegalArgumentException("node的Element节点中没有配置attribute");
            }

            Map children = new HashMap();
            for (int i = 0; i < temp.children.length; i++) {
              temp.children[i].node = null;
              children.put(temp.children[i].attributes.get("key"),
                           temp.children[i]);

            }

            configProp.put(itemNodeName, children);
          }
        }

      }

    }

  }

  /**
   * 打印所有配置信息
   */

  public void printAll() {

    if (_log.isDebugEnabled()) {
      _log.debug("开始打印配置信息.............................");
      _log.debug("配置信息条数=" + configProp.size());
    }
    for (Iterator entryIter = configProp.entrySet().iterator(); entryIter
         .hasNext(); ) {
      Entry entry = (Entry) entryIter.next();
      if (_log.isDebugEnabled()) {

        _log.debug(entry.getKey() + ".key1" + "="
                   + getPropertyAsString( (String) entry.getKey(), "key1"));

      }
    }
    if (_log.isDebugEnabled()) {
      _log.debug("结束打印配置信息.............................");
    }

  }

  /**
   * 单例类
   */
  private static ConfigMgr instance = null;

  public static ConfigMgr getInstance() {
    synchronized (ConfigMgr.class) {
      if (instance == null) {
        instance = new ConfigMgr();
      }

    }
    return instance;
  }

  /**
   * 得到属性值,并且转换成String
   *
   * @param key
   *            String
   * @return String
   */
  public String getPropertyAsString(String nodeName, String key) {
    Object obj = configProp.get(nodeName);
    if (ValidateHelper.validateNotNull(obj) && obj instanceof java.util.Map) {
      Map map = (Map) obj;
      obj = map.get(key);
      if (ValidateHelper.validateNotNull(obj) && obj instanceof ConfigEle) {
        ConfigEle ele = (ConfigEle) obj;
        String isRequired = (String) ele.attributes.get("is_required");
        if (ValidateHelper.validateNotEmpty(isRequired) &&
            EqualsHelper.equals(isRequired, "true")) {
          if (ValidateHelper.validateNotEmpty(ele.elementValue)) {
            return ele.elementValue;
          }
          else {

            throw new java.lang.IllegalArgumentException("NodeName=" + nodeName +
                ".key=" + key + " 被定义是必须的。请配置数据.." +
                "或者间隔" + SCAN_INTERVAL + "毫秒后再试");
          }

        }
        else {
          return ele.elementValue;
        }
      }
      else {

        return null;

      }
    }
    else {
      return null;
    }

  }

  /**
   * 得到属性值,并且转换成int
   *
   * @param key
   *            String
   * @return int
   * 如果为空，则返回最小值
   */
  public int getPropertyAsInteger(String nodeName, String key) {
    String _val = getPropertyAsString(nodeName, key);
    try {
        if(ValidateHelper.validateNotEmpty(_val)){
            return Integer.parseInt(_val);
        }else{
           return Integer.MIN_VALUE;
        }
    }
    catch (NumberFormatException ne) {
      throw new IllegalArgumentException("参数配置值非Int型数据!");
    }
  }

  /**
   * 得到属性值,并且转换成double
   *
   * @param key
   *            String
   * @return double
   */
  public double getPropertyAsDouble(String nodeName, String key) {
    String _val = getPropertyAsString(nodeName, key);
    try {
      return Double.parseDouble(_val);
    }
    catch (NumberFormatException ne) {
      throw new IllegalArgumentException("参数配置值非Int型数据!");
    }

  }

  /**
   * <p>
   * Title: EomsProj
   * </p>
   *
   * <p>
   * Description:
   * </p>
   *
   * <p>
   * Copyright: Copyright (c) 2006
   * </p>
   *
   * <p>
   * Company:
   * </p>
   *
   * @author dawn
   * @version 1.0
   */
  private class ConfigEle {

    public ConfigEle() {
    }

    private String xPath;

    private String elementName;

    private Node node;

    private Map attributes;

    private String elementValue;

    private ConfigEle[] children;
  }

  /**
   *
   * <p>
   * Title: EomsProj
   * </p>
   *
   * <p>
   * Description:
   * </p>
   *
   * <p>
   * Copyright: Copyright (c) 2006
   * </p>
   *
   * <p>
   * Company:
   * </p>
   *
   * @author not attributable
   * @version 1.0
   */
  private interface ConfigNodelet {

    public ConfigEle processItemNodes(ConfigEle ele);

    public ConfigEle processItem(ConfigEle ele);

  }

  /**
   * 解析配置元素通用解析器
   */
  private ConfigNodelet nodeletImpl = new ConfigNodelet() {

    public ConfigEle processItemNodes(ConfigEle ele) {
      Node node = ele.node;
      List children = new ArrayList();
      if (node instanceof Element) {
        List items = node.selectNodes(ITEM_TAG_NAME);

        if (ValidateHelper.validateNotEmpty(items)) {
          for (int i = 0; i < items.size(); i++) {
            ConfigEle _ele = new ConfigEle();
            _ele.node = (Node) items.get(i);
            _ele.elementName = ITEM_TAG_NAME;
            _ele = processItem(_ele);
            children.add(_ele);
          }
        }
        ele.attributes = getAttributes( (Element) node);
        ele.children = (ConfigEle[]) children.toArray(new ConfigEle[0]);
        ele.elementName = ITEMNODE_TAG_NAME;
      }
      return ele;
    }

    public ConfigEle processItem(ConfigEle ele) {
      // TODO Auto-generated method stub
      Node node = ele.node;

      if (node instanceof Element) {
        ele.attributes = getAttributes( (Element) node);
        ele.elementValue = ( (Element) node).getTextTrim();
      }
      return ele;
    }

    private Map getAttributes(Element ele) {
      Iterator iter = ele.attributeIterator();
      Map map = null;
      if (ValidateHelper.validateNotNull(iter)) {
        map = new LinkedHashMap();
        while (iter.hasNext()) {
          Attribute attr = (Attribute) iter.next();
          map.put(attr.getName(), attr.getValue());
        }

      }
      return map;
    }

  };

  public static void main(String[] args) {
    // for (int i = 0; i < 1000; i++) {
    ConfigMgr.getInstance().printAll();
    // }
  }

}
