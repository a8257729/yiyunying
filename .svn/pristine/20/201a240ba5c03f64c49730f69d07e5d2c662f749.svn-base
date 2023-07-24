/**
 * QueryOrderResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class QueryOrderResponseBean  extends com.ztesoft.mobile.v2.broadBandWs.ResponseBean  implements java.io.Serializable {
    private java.lang.String service_id;

    private java.lang.String service_name;

    public QueryOrderResponseBean() {
    }

    public QueryOrderResponseBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String ret_code,
           java.lang.String ret_msg,
           java.lang.String service_id,
           java.lang.String service_name) {
        super(
            version,
            servtype,
            reqtype,
            sequence,
            priority,
            reqtime,
            continued,
            ret_code,
            ret_msg);
        this.service_id = service_id;
        this.service_name = service_name;
    }


    /**
     * Gets the service_id value for this QueryOrderResponseBean.
     * 
     * @return service_id
     */
    public java.lang.String getService_id() {
        return service_id;
    }


    /**
     * Sets the service_id value for this QueryOrderResponseBean.
     * 
     * @param service_id
     */
    public void setService_id(java.lang.String service_id) {
        this.service_id = service_id;
    }


    /**
     * Gets the service_name value for this QueryOrderResponseBean.
     * 
     * @return service_name
     */
    public java.lang.String getService_name() {
        return service_name;
    }


    /**
     * Sets the service_name value for this QueryOrderResponseBean.
     * 
     * @param service_name
     */
    public void setService_name(java.lang.String service_name) {
        this.service_name = service_name;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryOrderResponseBean)) return false;
        QueryOrderResponseBean other = (QueryOrderResponseBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.service_id==null && other.getService_id()==null) || 
             (this.service_id!=null &&
              this.service_id.equals(other.getService_id()))) &&
            ((this.service_name==null && other.getService_name()==null) || 
             (this.service_name!=null &&
              this.service_name.equals(other.getService_name())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getService_id() != null) {
            _hashCode += getService_id().hashCode();
        }
        if (getService_name() != null) {
            _hashCode += getService_name().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryOrderResponseBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryOrderResponseBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("service_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "service_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("service_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "service_name"));
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
