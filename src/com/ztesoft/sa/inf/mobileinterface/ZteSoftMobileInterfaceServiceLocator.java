/**
 * ZteSoftMobileInterfaceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.sa.inf.mobileinterface;

public class ZteSoftMobileInterfaceServiceLocator extends org.apache.axis.client.Service implements com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterfaceService {

    public ZteSoftMobileInterfaceServiceLocator() {
    }


    public ZteSoftMobileInterfaceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZteSoftMobileInterfaceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZteSoftMobileInterface
    private java.lang.String ZteSoftMobileInterface_address = "http://10.19.100.172:7004/IOMPROJ/services/ZteSoftMobileInterface";

    public java.lang.String getZteSoftMobileInterfaceAddress() {
        return ZteSoftMobileInterface_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZteSoftMobileInterfaceWSDDServiceName = "ZteSoftMobileInterface";

    public java.lang.String getZteSoftMobileInterfaceWSDDServiceName() {
        return ZteSoftMobileInterfaceWSDDServiceName;
    }

    public void setZteSoftMobileInterfaceWSDDServiceName(java.lang.String name) {
        ZteSoftMobileInterfaceWSDDServiceName = name;
    }

    public com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterface_PortType getZteSoftMobileInterface() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ZteSoftMobileInterface_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getZteSoftMobileInterface(endpoint);
    }

    public com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterface_PortType getZteSoftMobileInterface(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterfaceSoapBindingStub _stub = new com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterfaceSoapBindingStub(portAddress, this);
            _stub.setPortName(getZteSoftMobileInterfaceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZteSoftMobileInterfaceEndpointAddress(java.lang.String address) {
        ZteSoftMobileInterface_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterface_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterfaceSoapBindingStub _stub = new com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterfaceSoapBindingStub(new java.net.URL(ZteSoftMobileInterface_address), this);
                _stub.setPortName(getZteSoftMobileInterfaceWSDDServiceName());
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
        if ("ZteSoftMobileInterface".equals(inputPortName)) {
            return getZteSoftMobileInterface();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.19.100.172:7004/IOMPROJ/services/ZteSoftMobileInterface", "ZteSoftMobileInterfaceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.19.100.172:7004/IOMPROJ/services/ZteSoftMobileInterface", "ZteSoftMobileInterface"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZteSoftMobileInterface".equals(portName)) {
            setZteSoftMobileInterfaceEndpointAddress(address);
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
