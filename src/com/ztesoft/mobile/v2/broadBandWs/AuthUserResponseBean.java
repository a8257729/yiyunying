/**
 * AuthUserResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class AuthUserResponseBean  implements java.io.Serializable {
    private java.lang.String sequenceNo;

    private java.lang.Integer resultCode;

    private java.lang.String loginName;

    private java.lang.String serviceLevel;

    private java.lang.String status;

    private java.lang.String subState;

    private java.lang.Integer accountLeft;

    public AuthUserResponseBean() {
    }

    public AuthUserResponseBean(
           java.lang.String sequenceNo,
           java.lang.Integer resultCode,
           java.lang.String loginName,
           java.lang.String serviceLevel,
           java.lang.String status,
           java.lang.String subState,
           java.lang.Integer accountLeft) {
           this.sequenceNo = sequenceNo;
           this.resultCode = resultCode;
           this.loginName = loginName;
           this.serviceLevel = serviceLevel;
           this.status = status;
           this.subState = subState;
           this.accountLeft = accountLeft;
    }


    /**
     * Gets the sequenceNo value for this AuthUserResponseBean.
     * 
     * @return sequenceNo
     */
    public java.lang.String getSequenceNo() {
        return sequenceNo;
    }


    /**
     * Sets the sequenceNo value for this AuthUserResponseBean.
     * 
     * @param sequenceNo
     */
    public void setSequenceNo(java.lang.String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }


    /**
     * Gets the resultCode value for this AuthUserResponseBean.
     * 
     * @return resultCode
     */
    public java.lang.Integer getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this AuthUserResponseBean.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.Integer resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the loginName value for this AuthUserResponseBean.
     * 
     * @return loginName
     */
    public java.lang.String getLoginName() {
        return loginName;
    }


    /**
     * Sets the loginName value for this AuthUserResponseBean.
     * 
     * @param loginName
     */
    public void setLoginName(java.lang.String loginName) {
        this.loginName = loginName;
    }


    /**
     * Gets the serviceLevel value for this AuthUserResponseBean.
     * 
     * @return serviceLevel
     */
    public java.lang.String getServiceLevel() {
        return serviceLevel;
    }


    /**
     * Sets the serviceLevel value for this AuthUserResponseBean.
     * 
     * @param serviceLevel
     */
    public void setServiceLevel(java.lang.String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }


    /**
     * Gets the status value for this AuthUserResponseBean.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this AuthUserResponseBean.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the subState value for this AuthUserResponseBean.
     * 
     * @return subState
     */
    public java.lang.String getSubState() {
        return subState;
    }


    /**
     * Sets the subState value for this AuthUserResponseBean.
     * 
     * @param subState
     */
    public void setSubState(java.lang.String subState) {
        this.subState = subState;
    }


    /**
     * Gets the accountLeft value for this AuthUserResponseBean.
     * 
     * @return accountLeft
     */
    public java.lang.Integer getAccountLeft() {
        return accountLeft;
    }


    /**
     * Sets the accountLeft value for this AuthUserResponseBean.
     * 
     * @param accountLeft
     */
    public void setAccountLeft(java.lang.Integer accountLeft) {
        this.accountLeft = accountLeft;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuthUserResponseBean)) return false;
        AuthUserResponseBean other = (AuthUserResponseBean) obj;
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
            ((this.loginName==null && other.getLoginName()==null) || 
             (this.loginName!=null &&
              this.loginName.equals(other.getLoginName()))) &&
            ((this.serviceLevel==null && other.getServiceLevel()==null) || 
             (this.serviceLevel!=null &&
              this.serviceLevel.equals(other.getServiceLevel()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.subState==null && other.getSubState()==null) || 
             (this.subState!=null &&
              this.subState.equals(other.getSubState()))) &&
            ((this.accountLeft==null && other.getAccountLeft()==null) || 
             (this.accountLeft!=null &&
              this.accountLeft.equals(other.getAccountLeft())));
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
        if (getLoginName() != null) {
            _hashCode += getLoginName().hashCode();
        }
        if (getServiceLevel() != null) {
            _hashCode += getServiceLevel().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSubState() != null) {
            _hashCode += getSubState().hashCode();
        }
        if (getAccountLeft() != null) {
            _hashCode += getAccountLeft().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthUserResponseBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AuthUserResponseBean"));
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
        elemField.setFieldName("loginName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountLeft");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountLeft"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
