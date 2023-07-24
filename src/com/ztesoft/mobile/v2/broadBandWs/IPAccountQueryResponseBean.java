/**
 * IPAccountQueryResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class IPAccountQueryResponseBean  implements java.io.Serializable {
    private java.lang.String sequenceNo;

    private java.lang.Integer resultCode;

    private java.lang.String serialNum;

    private java.lang.String loginName;

    public IPAccountQueryResponseBean() {
    }

    public IPAccountQueryResponseBean(
           java.lang.String sequenceNo,
           java.lang.Integer resultCode,
           java.lang.String serialNum,
           java.lang.String loginName) {
           this.sequenceNo = sequenceNo;
           this.resultCode = resultCode;
           this.serialNum = serialNum;
           this.loginName = loginName;
    }


    /**
     * Gets the sequenceNo value for this IPAccountQueryResponseBean.
     * 
     * @return sequenceNo
     */
    public java.lang.String getSequenceNo() {
        return sequenceNo;
    }


    /**
     * Sets the sequenceNo value for this IPAccountQueryResponseBean.
     * 
     * @param sequenceNo
     */
    public void setSequenceNo(java.lang.String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }


    /**
     * Gets the resultCode value for this IPAccountQueryResponseBean.
     * 
     * @return resultCode
     */
    public java.lang.Integer getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this IPAccountQueryResponseBean.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.Integer resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the serialNum value for this IPAccountQueryResponseBean.
     * 
     * @return serialNum
     */
    public java.lang.String getSerialNum() {
        return serialNum;
    }


    /**
     * Sets the serialNum value for this IPAccountQueryResponseBean.
     * 
     * @param serialNum
     */
    public void setSerialNum(java.lang.String serialNum) {
        this.serialNum = serialNum;
    }


    /**
     * Gets the loginName value for this IPAccountQueryResponseBean.
     * 
     * @return loginName
     */
    public java.lang.String getLoginName() {
        return loginName;
    }


    /**
     * Sets the loginName value for this IPAccountQueryResponseBean.
     * 
     * @param loginName
     */
    public void setLoginName(java.lang.String loginName) {
        this.loginName = loginName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IPAccountQueryResponseBean)) return false;
        IPAccountQueryResponseBean other = (IPAccountQueryResponseBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sequenceNo==null && other.getSequenceNo()==null) || 
             (this.sequenceNo!=null &&
              this.sequenceNo.equals(other.getSequenceNo()))) &&
            ((this.resultCode==null && other.getResultCode()==null) || 
             (this.resultCode!=null &&
              this.resultCode.equals(other.getResultCode()))) &&
            ((this.serialNum==null && other.getSerialNum()==null) || 
             (this.serialNum!=null &&
              this.serialNum.equals(other.getSerialNum()))) &&
            ((this.loginName==null && other.getLoginName()==null) || 
             (this.loginName!=null &&
              this.loginName.equals(other.getLoginName())));
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
        if (getSequenceNo() != null) {
            _hashCode += getSequenceNo().hashCode();
        }
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        if (getSerialNum() != null) {
            _hashCode += getSerialNum().hashCode();
        }
        if (getLoginName() != null) {
            _hashCode += getLoginName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IPAccountQueryResponseBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "IPAccountQueryResponseBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sequenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
