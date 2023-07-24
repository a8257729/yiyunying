/**
 * ResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class ResponseBean  extends com.ztesoft.mobile.v2.broadBandWs.HeadBean  implements java.io.Serializable {
    private java.lang.String ret_code;

    private java.lang.String ret_msg;

    public ResponseBean() {
    }

    public ResponseBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String ret_code,
           java.lang.String ret_msg) {
        super(
            version,
            servtype,
            reqtype,
            sequence,
            priority,
            reqtime,
            continued);
        this.ret_code = ret_code;
        this.ret_msg = ret_msg;
    }


    /**
     * Gets the ret_code value for this ResponseBean.
     * 
     * @return ret_code
     */
    public java.lang.String getRet_code() {
        return ret_code;
    }


    /**
     * Sets the ret_code value for this ResponseBean.
     * 
     * @param ret_code
     */
    public void setRet_code(java.lang.String ret_code) {
        this.ret_code = ret_code;
    }


    /**
     * Gets the ret_msg value for this ResponseBean.
     * 
     * @return ret_msg
     */
    public java.lang.String getRet_msg() {
        return ret_msg;
    }


    /**
     * Sets the ret_msg value for this ResponseBean.
     * 
     * @param ret_msg
     */
    public void setRet_msg(java.lang.String ret_msg) {
        this.ret_msg = ret_msg;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseBean)) return false;
        ResponseBean other = (ResponseBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ret_code==null && other.getRet_code()==null) || 
             (this.ret_code!=null &&
              this.ret_code.equals(other.getRet_code()))) &&
            ((this.ret_msg==null && other.getRet_msg()==null) || 
             (this.ret_msg!=null &&
              this.ret_msg.equals(other.getRet_msg())));
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
        if (getRet_code() != null) {
            _hashCode += getRet_code().hashCode();
        }
        if (getRet_msg() != null) {
            _hashCode += getRet_msg().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ret_code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ret_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ret_msg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ret_msg"));
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
