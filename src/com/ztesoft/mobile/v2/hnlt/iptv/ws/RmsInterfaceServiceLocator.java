/**
 * RmsInterfaceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.hnlt.iptv.ws;

public class RmsInterfaceServiceLocator extends org.apache.axis.client.Service implements com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterfaceService {

    public RmsInterfaceServiceLocator() {
    }


    public RmsInterfaceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RmsInterfaceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // 正式环境Use to get a proxy class for RMSInterface
     private java.lang.String RMSInterface_address = "http://gkweb1:9020/FLOWBUS_INTERFACE/services/RMSInterface";
    //本地
    //private java.lang.String RMSInterface_address = "http://localhost:7001/WebRoot/services/rmsInterface?wsdl";
    public java.lang.String getRMSInterfaceAddress() {
        return RMSInterface_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RMSInterfaceWSDDServiceName = "RMSInterface";

    public java.lang.String getRMSInterfaceWSDDServiceName() {
        return RMSInterfaceWSDDServiceName;
    }

    public void setRMSInterfaceWSDDServiceName(java.lang.String name) {
        RMSInterfaceWSDDServiceName = name;
    }

    public com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterface getRMSInterface() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RMSInterface_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRMSInterface(endpoint);
    }

    public com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterface getRMSInterface(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ztesoft.mobile.v2.hnlt.iptv.ws.RMSInterfaceSoapBindingStub _stub = new com.ztesoft.mobile.v2.hnlt.iptv.ws.RMSInterfaceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRMSInterfaceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRMSInterfaceEndpointAddress(java.lang.String address) {
        RMSInterface_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ztesoft.mobile.v2.hnlt.iptv.ws.RMSInterfaceSoapBindingStub _stub = new com.ztesoft.mobile.v2.hnlt.iptv.ws.RMSInterfaceSoapBindingStub(new java.net.URL(RMSInterface_address), this);
                _stub.setPortName(getRMSInterfaceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RMSInterface".equals(inputPortName)) {
            return getRMSInterface();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://weixin.operate.rms.ztesoft.com/", "rmsInterfaceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://weixin.operate.rms.ztesoft.com/", "RMSInterface"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RMSInterface".equals(portName)) {
            setRMSInterfaceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
