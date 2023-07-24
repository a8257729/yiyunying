package com.ztesoft.mobile.v2.controller.common;

import java.util.Map;

import com.ztesoft.mobile.v2.core.StatefulController;
import com.ztesoft.mobile.v2.service.common.MobileWebConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class WebConfigController extends StatefulController
{
  private static final Logger logger = Logger.getLogger(WebConfigController.class);
  public static final boolean DEBUG=false;
  private MobileWebConfigService mobileWebConfigService;

  private MobileWebConfigService getMobileWebConfigService()
  {
    return this.mobileWebConfigService;
  }

  @Autowired(required=false)
  public void setMobileWebConfigService(MobileWebConfigService mobileWebConfigService) {
    this.mobileWebConfigService = mobileWebConfigService;
  }

  protected String getConfigValue(String configCode) {
    if (logger.isDebugEnabled())
      logger.debug("Call getConfigValue method for " + configCode);

    return getMobileWebConfigService().getConfigValue(configCode);
  }
  
  protected Map getConfigFTPPath(String configCode)
  {
	  if (logger.isDebugEnabled())
	      logger.debug("Call getConfigFTPPath method for " + configCode);
	  return getMobileWebConfigService().getConfigFTPPath(configCode);
  }
}