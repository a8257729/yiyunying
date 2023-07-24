package com.ztesoft.eoms.common.helper;


/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author qian.zhiliang
 * @version 1.0
 */

import org.apache.log4j.Logger;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LogHelper {
  private Logger logger;

  private LogHelper(Class cls) {
    this.logger = Logger.getLogger(cls);
  }

  public LogHelper(Logger logger) {
    this.logger = logger;
  }

  public static LogHelper getLogger(Class cls) {
    return new LogHelper(cls);
  }

  public void error(String err) {
    logger.error(formate(err));
  }

  public void info(String info) {
    if (logger.isInfoEnabled()) {
      logger.info(formate(info));
    }
  }

  public void debug(String debug) {
    if (logger.isDebugEnabled()) {
      logger.debug(formate(debug));
    }
  }

  public void fatal(String fatal) {
    logger.fatal(formate(fatal));
  }


  /* 格式化显示 */
  private String formate(String str) {
    Date date = new Date();
    DateFormat formate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return str + "  (@" + logger.getName() + " " + formate.format(date) + ")";
  }
}
