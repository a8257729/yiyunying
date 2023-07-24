/**
 * DeductFeeBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class DeductFeeBean  implements java.io.Serializable {
    private java.lang.String sequenceNo;

    private java.lang.String srcDeviceType;

    private java.lang.String srcDeviceId;

    private java.lang.String loginName;

    private java.lang.String deductFee;

    private java.lang.String serviceId;

    public DeductFeeBean() {
    }

    public DeductFeeBean(
           java.lang.String sequenceNo,
           java.lang.String srcDeviceType,
           java.lang.String srcDeviceId,
           java.lang.String loginName,
           java.lang.String deductFee,
           java.lang.String serviceId) {
           this.sequenceNo = sequenceNo;
           this.srcDeviceType = srcDeviceType;
           this.srcDeviceId = srcDeviceId;
           this.loginName = loginName;
           this.deductFee = deductFee;
           this.serviceId = serviceId;
    }


    /**
     * Gets the sequenceNo value for this DeductFeeBean.
     * 
     * @return sequenceNo
     */
    public java.lang.String getSequenceNo() {
        return sequenceNo;
    }


    /**
     * Sets the sequenceNo value for this DeductFeeBean.
     * 
     * @param sequenceNo
     */
    public void setSequenceNo(java.lang.String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }


    /**
     * Gets the srcDeviceType value for this DeductFeeBean.
     * 
     * @return srcDeviceType
     */
    public java.lang.String getSrcDeviceType() {
        return srcDeviceType;
    }


    /**
     * Sets the srcDeviceType value for this DeductFeeBean.
     * 
     * @param srcDeviceType
     */
    public void setSrcDeviceType(java.lang.String srcDeviceType) {
        this.srcDeviceType = srcDeviceType;
    }


    /**
     * Gets the srcDeviceId value for this DeductFeeBean.
     * 
     * @return srcDeviceId
     */
    public java.lang.String getSrcDeviceId() {
        return srcDeviceId;
    }


    /**
     * Sets the srcDeviceId value for this DeductFeeBean.
     * 
     * @param srcDeviceId
     */
    public void setSrcDeviceId(java.lang.String srcDeviceId) {
        this.srcDeviceId = srcDeviceId;
    }


    /**
     * Gets the loginName value for this DeductFeeBean.
     * 
     * @return loginName
     */
    public java.lang.String getLoginName() {
        return loginName;
    }


    /**
     * Sets the loginName value for this DeductFeeBean.
     * 
     * @param loginName
     */
    public void setLoginName(java.lang.String loginName) {
        this.loginName = loginName;
    }


    /**
     * Gets the deductFee value for this DeductFeeBean.
     * 
     * @return deductFee
     */
    public java.lang.String getDeductFee() {
        return deductFee;
    }


    /**
     * Sets the deductFee value for this DeductFeeBean.
     * 
     * @param deductFee
     */
    public void setDeductFee(java.lang.String deductFee) {
        this.deductFee = deductFee;
    }


    /**
     * Gets the serviceId value for this DeductFeeBean.
     * 
     * @return serviceId
     */
    public java.lang.String getServiceId() {
        return serviceId;
    }


    /**
     * Sets the serviceId value for this DeductFeeBean.
     * 
     * @param serviceId
     */
    public void setServiceId(java.lang.String serviceId) {
        this.serviceId = serviceId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeductFeeBean)) return false;
        DeductFeeBean other = (DeductFeeBean) obj;
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
            ((this.srcDeviceType==null && other.getSrcDeviceType()==null) || 
             (this.srcDeviceType!=null &&
              this.srcDeviceType.equals(other.getSrcDeviceType()))) &&
            ((this.srcDeviceId==null && other.getSrcDeviceId()==null) || 
             (this.srcDeviceId!=null &&
              this.srcDeviceId.equals(other.getSrcDeviceId()))) &&
            ((this.loginName==null && other.getLoginName()==null) || 
             (this.loginName!=null &&
              this.loginName.equals(other.getLoginName()))) &&
            ((this.deductFee==null && other.getDeductFee()==null) || 
             (this.deductFee!=null &&
              this.deductFee.equals(other.getDeductFee()))) &&
            ((this.serviceId==null && other.getServiceId()==null) || 
             (this.serviceId!=null &&
              this.serviceId.equals(other.getServiceId())));
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
        if (getSrcDeviceType() != null) {
            _hashCode += getSrcDeviceType().hashCode();
        }
        if (getSrcDeviceId() != null) {
            _hashCode += getSrcDeviceId().hashCode();
        }
        if (getLoginName() != null) {
            _hashCode += getLoginName().hashCode();
        }
        if (getDeductFee() != null) {
            _hashCode += getDeductFee().hashCode();
        }
        if (getServiceId() != null) {
            _hashCode += getServiceId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeductFeeBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DeductFeeBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sequenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcDeviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "srcDeviceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcDeviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "srcDeviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deductFee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deductFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceId"));
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
