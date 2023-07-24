/**
 * ModBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class ModBean  extends com.ztesoft.mobile.v2.broadBandWs.HeadBean  implements java.io.Serializable {
    private java.lang.String user_name;

    private java.lang.String old_password;

    private java.lang.String new_password;

    public ModBean() {
    }

    public ModBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String user_name,
           java.lang.String old_password,
           java.lang.String new_password) {
        super(
            version,
            servtype,
            reqtype,
            sequence,
            priority,
            reqtime,
            continued);
        this.user_name = user_name;
        this.old_password = old_password;
        this.new_password = new_password;
    }


    /**
     * Gets the user_name value for this ModBean.
     * 
     * @return user_name
     */
    public java.lang.String getUser_name() {
        return user_name;
    }


    /**
     * Sets the user_name value for this ModBean.
     * 
     * @param user_name
     */
    public void setUser_name(java.lang.String user_name) {
        this.user_name = user_name;
    }


    /**
     * Gets the old_password value for this ModBean.
     * 
     * @return old_password
     */
    public java.lang.String getOld_password() {
        return old_password;
    }


    /**
     * Sets the old_password value for this ModBean.
     * 
     * @param old_password
     */
    public void setOld_password(java.lang.String old_password) {
        this.old_password = old_password;
    }


    /**
     * Gets the new_password value for this ModBean.
     * 
     * @return new_password
     */
    public java.lang.String getNew_password() {
        return new_password;
    }


    /**
     * Sets the new_password value for this ModBean.
     * 
     * @param new_password
     */
    public void setNew_password(java.lang.String new_password) {
        this.new_password = new_password;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModBean)) return false;
        ModBean other = (ModBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.user_name==null && other.getUser_name()==null) || 
             (this.user_name!=null &&
              this.user_name.equals(other.getUser_name()))) &&
            ((this.old_password==null && other.getOld_password()==null) || 
             (this.old_password!=null &&
              this.old_password.equals(other.getOld_password()))) &&
            ((this.new_password==null && other.getNew_password()==null) || 
             (this.new_password!=null &&
              this.new_password.equals(other.getNew_password())));
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
        if (getUser_name() != null) {
            _hashCode += getUser_name().hashCode();
        }
        if (getOld_password() != null) {
            _hashCode += getOld_password().hashCode();
        }
        if (getNew_password() != null) {
            _hashCode += getNew_password().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ModBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("old_password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "old_password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("new_password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "new_password"));
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
