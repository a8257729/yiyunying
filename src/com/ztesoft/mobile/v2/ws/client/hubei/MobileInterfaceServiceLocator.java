/**
 * MobileInterfaceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.ws.client.hubei;

public class MobileInterfaceServiceLocator extends
		org.apache.axis.client.Service implements MobileInterfaceService {

	public MobileInterfaceServiceLocator() {
	}

	public MobileInterfaceServiceLocator(
			org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public MobileInterfaceServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for MobileInterface
	private java.lang.String MobileInterface_address = "http://58.19.117.122:7003/IOMPROJ/services/MobileInterface";

	public java.lang.String getMobileInterfaceAddress() {
		return MobileInterface_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String MobileInterfaceWSDDServiceName = "MobileInterface";

	public java.lang.String getMobileInterfaceWSDDServiceName() {
		return MobileInterfaceWSDDServiceName;
	}

	public void setMobileInterfaceWSDDServiceName(java.lang.String name) {
		MobileInterfaceWSDDServiceName = name;
	}

	public MobileInterfacePortType getMobileInterface()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(MobileInterface_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getMobileInterface(endpoint);
	}

	public MobileInterfacePortType getMobileInterface(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			MobileInterfaceSoapBindingStub _stub = new MobileInterfaceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getMobileInterfaceWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setMobileInterfaceEndpointAddress(java.lang.String address) {
		MobileInterface_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (MobileInterfacePortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				MobileInterfaceSoapBindingStub _stub = new MobileInterfaceSoapBindingStub(
						new java.net.URL(MobileInterface_address), this);
				_stub.setPortName(getMobileInterfaceWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("MobileInterface".equals(inputPortName)) {
			return getMobileInterface();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"http://58.19.117.122:7003/IOMPROJ/services/MobileInterface",
				"MobileInterfaceService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://58.19.117.122:7003/IOMPROJ/services/MobileInterface",
					"MobileInterface"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("MobileInterface".equals(portName)) {
			setMobileInterfaceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
