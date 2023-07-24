package com.ztesoft.mobile.v2.hnlt.iptv.ws;

import java.rmi.RemoteException;

public class RmsInterfaceProxy implements com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterface {
  private String _endpoint = null;
  private com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterface rmsInterface = null;
  
  public RmsInterfaceProxy() {
    _initRmsInterfaceProxy();
  }
  
  public RmsInterfaceProxy(String endpoint) {
    _endpoint = endpoint;
    _initRmsInterfaceProxy();
  }
  
  private void _initRmsInterfaceProxy() {
    try {
      rmsInterface = (new com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterfaceServiceLocator()).getRMSInterface();
      if (rmsInterface != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)rmsInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)rmsInterface)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (rmsInterface != null)
      ((javax.xml.rpc.Stub)rmsInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterface getRmsInterface() {
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface;
  }
  
  public java.lang.String rmsFeedback(java.lang.String contentXml) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.rmsFeedback(contentXml);
  }
  
  public java.lang.String call(java.lang.String contentXml) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.call(contentXml);
  }
  
  public java.lang.String urgeFaultOrder(java.lang.String contentXml) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.urgeFaultOrder(contentXml);
  }
  
  public java.lang.String bandIPTVSTB(java.lang.String contentXml) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.bandIPTVSTB(contentXml);
  }
  
  public java.lang.String unbandIPTVSTB(java.lang.String contentXml) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.unbandIPTVSTB(contentXml);
  }
  
  public java.lang.String changeIPTVSTB(java.lang.String contentXml) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.changeIPTVSTB(contentXml);
  }
  
  public java.lang.String getSerchPersion(java.lang.String url) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.getSerchPersion(url);
  }
  
  public java.lang.String queryMAC(java.lang.String mac) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.queryMAC(mac);
  }
  
  public java.lang.String queryAccount(java.lang.String account) throws java.rmi.RemoteException{
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.queryAccount(account);
  }

  public String changeIPTVStbApply(String contentXml) throws RemoteException {
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.changeIPTVStbApply(contentXml);
  }

  public String queryStbBindChgRecord(String cmdId) throws RemoteException {
    if (rmsInterface == null)
      _initRmsInterfaceProxy();
    return rmsInterface.queryStbBindChgRecord(cmdId);
  }


}