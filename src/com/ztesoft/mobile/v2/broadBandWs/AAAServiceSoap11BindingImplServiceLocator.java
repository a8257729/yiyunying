/**
 * AAAServiceSoap11BindingImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class AAAServiceSoap11BindingImplServiceLocator extends org.apache.axis.client.Service implements com.ztesoft.mobile.v2.broadBandWs.AAAServiceSoap11BindingImplService {

    public AAAServiceSoap11BindingImplServiceLocator() {
    }


    public AAAServiceSoap11BindingImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AAAServiceSoap11BindingImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AAAServiceHttpSoap11Endpoint
    private java.lang.String AAAServiceHttpSoap11Endpoint_address = "http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint";
//    private java.lang.String AAAServiceHttpSoap11Endpoint_address = "http://127.0.0.1:2222/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint";

    public java.lang.String getAAAServiceHttpSoap11EndpointAddress() {
        return AAAServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AAAServiceHttpSoap11EndpointWSDDServiceName = "AAAServiceHttpSoap11Endpoint";

    public java.lang.String getAAAServiceHttpSoap11EndpointWSDDServiceName() {
        return AAAServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setAAAServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        AAAServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.ztesoft.mobile.v2.broadBandWs.AAAServiceSoap11BindingImpl getAAAServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AAAServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAAAServiceHttpSoap11Endpoint(endpoint);
    }

    public com.ztesoft.mobile.v2.broadBandWs.AAAServiceSoap11BindingImpl getAAAServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ztesoft.mobile.v2.broadBandWs.AAAServiceHttpSoap11EndpointSoapBindingStub _stub = new com.ztesoft.mobile.v2.broadBandWs.AAAServiceHttpSoap11EndpointSoapBindingStub(portAddress, this);
            _stub.setPortName(getAAAServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAAAServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        AAAServiceHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ztesoft.mobile.v2.broadBandWs.AAAServiceSoap11BindingImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ztesoft.mobile.v2.broadBandWs.AAAServiceHttpSoap11EndpointSoapBindingStub _stub = new com.ztesoft.mobile.v2.broadBandWs.AAAServiceHttpSoap11EndpointSoapBindingStub(new java.net.URL(AAAServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getAAAServiceHttpSoap11EndpointWSDDServiceName());
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
        if ("AAAServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getAAAServiceHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AAAServiceSoap11BindingImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AAAServiceHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AAAServiceHttpSoap11Endpoint".equals(portName)) {
            setAAAServiceHttpSoap11EndpointEndpointAddress(address);
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
