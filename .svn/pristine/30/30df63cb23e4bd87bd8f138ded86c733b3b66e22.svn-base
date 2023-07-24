/**
 * WfmToHandTerminalImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Aug 08, 2005 (11:49:10 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.ws.client.shanghai;

public class WfmToHandTerminalImplServiceLocator extends org.apache.axis.client.Service implements com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplService {

    public WfmToHandTerminalImplServiceLocator() {
    }


    public WfmToHandTerminalImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WfmToHandTerminalImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WfmToHandTerminalImpl
    private java.lang.String WfmToHandTerminalImpl_address = "http://134.74.1.82:7010/IOMPROJ/services/WfmToHandTerminalImpl";

    public java.lang.String getWfmToHandTerminalImplAddress() {
        return WfmToHandTerminalImpl_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WfmToHandTerminalImplWSDDServiceName = "WfmToHandTerminalImpl";

    public java.lang.String getWfmToHandTerminalImplWSDDServiceName() {
        return WfmToHandTerminalImplWSDDServiceName;
    }

    public void setWfmToHandTerminalImplWSDDServiceName(java.lang.String name) {
        WfmToHandTerminalImplWSDDServiceName = name;
    }

    public com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImpl_PortType getWfmToHandTerminalImpl() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WfmToHandTerminalImpl_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWfmToHandTerminalImpl(endpoint);
    }

    public com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImpl_PortType getWfmToHandTerminalImpl(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplSoapBindingStub _stub = new com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplSoapBindingStub(portAddress, this);
            _stub.setPortName(getWfmToHandTerminalImplWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWfmToHandTerminalImplEndpointAddress(java.lang.String address) {
        WfmToHandTerminalImpl_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImpl_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplSoapBindingStub _stub = new com.ztesoft.mobile.v2.ws.client.shanghai.WfmToHandTerminalImplSoapBindingStub(new java.net.URL(WfmToHandTerminalImpl_address), this);
                _stub.setPortName(getWfmToHandTerminalImplWSDDServiceName());
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
        if ("WfmToHandTerminalImpl".equals(inputPortName)) {
            return getWfmToHandTerminalImpl();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://WfmToHandTerminalImpl.service.handterminal.wfm.ztesoft.com", "WfmToHandTerminalImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://WfmToHandTerminalImpl.service.handterminal.wfm.ztesoft.com", "WfmToHandTerminalImpl"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WfmToHandTerminalImpl".equals(portName)) {
            setWfmToHandTerminalImplEndpointAddress(address);
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
