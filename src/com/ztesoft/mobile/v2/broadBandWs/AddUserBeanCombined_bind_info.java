/**
 * AddUserBeanCombined_bind_info.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class AddUserBeanCombined_bind_info  implements java.io.Serializable {
    private java.lang.String bind_type;

    private java.lang.String nas_node_id;

    private java.lang.String nas_ip_address;

    private java.lang.String nas_identify;

    private java.lang.String nas_port_id;

    private java.lang.String vlan_id;

    public AddUserBeanCombined_bind_info() {
    }

    public AddUserBeanCombined_bind_info(
           java.lang.String bind_type,
           java.lang.String nas_node_id,
           java.lang.String nas_ip_address,
           java.lang.String nas_identify,
           java.lang.String nas_port_id,
           java.lang.String vlan_id) {
           this.bind_type = bind_type;
           this.nas_node_id = nas_node_id;
           this.nas_ip_address = nas_ip_address;
           this.nas_identify = nas_identify;
           this.nas_port_id = nas_port_id;
           this.vlan_id = vlan_id;
    }


    /**
     * Gets the bind_type value for this AddUserBeanCombined_bind_info.
     * 
     * @return bind_type
     */
    public java.lang.String getBind_type() {
        return bind_type;
    }


    /**
     * Sets the bind_type value for this AddUserBeanCombined_bind_info.
     * 
     * @param bind_type
     */
    public void setBind_type(java.lang.String bind_type) {
        this.bind_type = bind_type;
    }


    /**
     * Gets the nas_node_id value for this AddUserBeanCombined_bind_info.
     * 
     * @return nas_node_id
     */
    public java.lang.String getNas_node_id() {
        return nas_node_id;
    }


    /**
     * Sets the nas_node_id value for this AddUserBeanCombined_bind_info.
     * 
     * @param nas_node_id
     */
    public void setNas_node_id(java.lang.String nas_node_id) {
        this.nas_node_id = nas_node_id;
    }


    /**
     * Gets the nas_ip_address value for this AddUserBeanCombined_bind_info.
     * 
     * @return nas_ip_address
     */
    public java.lang.String getNas_ip_address() {
        return nas_ip_address;
    }


    /**
     * Sets the nas_ip_address value for this AddUserBeanCombined_bind_info.
     * 
     * @param nas_ip_address
     */
    public void setNas_ip_address(java.lang.String nas_ip_address) {
        this.nas_ip_address = nas_ip_address;
    }


    /**
     * Gets the nas_identify value for this AddUserBeanCombined_bind_info.
     * 
     * @return nas_identify
     */
    public java.lang.String getNas_identify() {
        return nas_identify;
    }


    /**
     * Sets the nas_identify value for this AddUserBeanCombined_bind_info.
     * 
     * @param nas_identify
     */
    public void setNas_identify(java.lang.String nas_identify) {
        this.nas_identify = nas_identify;
    }


    /**
     * Gets the nas_port_id value for this AddUserBeanCombined_bind_info.
     * 
     * @return nas_port_id
     */
    public java.lang.String getNas_port_id() {
        return nas_port_id;
    }


    /**
     * Sets the nas_port_id value for this AddUserBeanCombined_bind_info.
     * 
     * @param nas_port_id
     */
    public void setNas_port_id(java.lang.String nas_port_id) {
        this.nas_port_id = nas_port_id;
    }


    /**
     * Gets the vlan_id value for this AddUserBeanCombined_bind_info.
     * 
     * @return vlan_id
     */
    public java.lang.String getVlan_id() {
        return vlan_id;
    }


    /**
     * Sets the vlan_id value for this AddUserBeanCombined_bind_info.
     * 
     * @param vlan_id
     */
    public void setVlan_id(java.lang.String vlan_id) {
        this.vlan_id = vlan_id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddUserBeanCombined_bind_info)) return false;
        AddUserBeanCombined_bind_info other = (AddUserBeanCombined_bind_info) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bind_type==null && other.getBind_type()==null) || 
             (this.bind_type!=null &&
              this.bind_type.equals(other.getBind_type()))) &&
            ((this.nas_node_id==null && other.getNas_node_id()==null) || 
             (this.nas_node_id!=null &&
              this.nas_node_id.equals(other.getNas_node_id()))) &&
            ((this.nas_ip_address==null && other.getNas_ip_address()==null) || 
             (this.nas_ip_address!=null &&
              this.nas_ip_address.equals(other.getNas_ip_address()))) &&
            ((this.nas_identify==null && other.getNas_identify()==null) || 
             (this.nas_identify!=null &&
              this.nas_identify.equals(other.getNas_identify()))) &&
            ((this.nas_port_id==null && other.getNas_port_id()==null) || 
             (this.nas_port_id!=null &&
              this.nas_port_id.equals(other.getNas_port_id()))) &&
            ((this.vlan_id==null && other.getVlan_id()==null) || 
             (this.vlan_id!=null &&
              this.vlan_id.equals(other.getVlan_id())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getBind_type() != null) {
            _hashCode += getBind_type().hashCode();
        }
        if (getNas_node_id() != null) {
            _hashCode += getNas_node_id().hashCode();
        }
        if (getNas_ip_address() != null) {
            _hashCode += getNas_ip_address().hashCode();
        }
        if (getNas_identify() != null) {
            _hashCode += getNas_identify().hashCode();
        }
        if (getNas_port_id() != null) {
            _hashCode += getNas_port_id().hashCode();
        }
        if (getVlan_id() != null) {
            _hashCode += getVlan_id().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddUserBeanCombined_bind_info.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AddUserBeanCombined_bind_info"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bind_type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bind_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nas_node_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nas_node_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nas_ip_address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nas_ip_address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nas_identify");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nas_identify"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nas_port_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nas_port_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlan_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlan_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
