/**
 * UnsetCombinedBindResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class UnsetCombinedBindResponseBean  extends com.ztesoft.mobile.v2.broadBandWs.ResponseBean  implements java.io.Serializable {
    private java.lang.String logfile;

    public UnsetCombinedBindResponseBean() {
    }

    public UnsetCombinedBindResponseBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String ret_code,
           java.lang.String ret_msg,
           java.lang.String logfile) {
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
        this.logfile = logfile;
    }


    /**
     * Gets the logfile value for this UnsetCombinedBindResponseBean.
     * 
     * @return logfile
     */
    public java.lang.String getLogfile() {
        return logfile;
    }


    /**
     * Sets the logfile value for this UnsetCombinedBindResponseBean.
     * 
     * @param logfile
     */
    public void setLogfile(java.lang.String logfile) {
        this.logfile = logfile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UnsetCombinedBindResponseBean)) return false;
        UnsetCombinedBindResponseBean other = (UnsetCombinedBindResponseBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.logfile==null && other.getLogfile()==null) || 
             (this.logfile!=null &&
              this.logfile.equals(other.getLogfile())));
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
        if (getLogfile() != null) {
            _hashCode += getLogfile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UnsetCombinedBindResponseBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "UnsetCombinedBindResponseBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "logfile"));
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
