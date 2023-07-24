/**
 * QueryAccessFailBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class QueryAccessFailBean  extends com.ztesoft.mobile.v2.broadBandWs.HeadBean  implements java.io.Serializable {
    private java.lang.String user_name;

    private java.lang.String start_time;

    private java.lang.String end_time;

    public QueryAccessFailBean() {
    }

    public QueryAccessFailBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String user_name,
           java.lang.String start_time,
           java.lang.String end_time) {
        super(
            version,
            servtype,
            reqtype,
            sequence,
            priority,
            reqtime,
            continued);
        this.user_name = user_name;
        this.start_time = start_time;
        this.end_time = end_time;
    }


    /**
     * Gets the user_name value for this QueryAccessFailBean.
     * 
     * @return user_name
     */
    public java.lang.String getUser_name() {
        return user_name;
    }


    /**
     * Sets the user_name value for this QueryAccessFailBean.
     * 
     * @param user_name
     */
    public void setUser_name(java.lang.String user_name) {
        this.user_name = user_name;
    }


    /**
     * Gets the start_time value for this QueryAccessFailBean.
     * 
     * @return start_time
     */
    public java.lang.String getStart_time() {
        return start_time;
    }


    /**
     * Sets the start_time value for this QueryAccessFailBean.
     * 
     * @param start_time
     */
    public void setStart_time(java.lang.String start_time) {
        this.start_time = start_time;
    }


    /**
     * Gets the end_time value for this QueryAccessFailBean.
     * 
     * @return end_time
     */
    public java.lang.String getEnd_time() {
        return end_time;
    }


    /**
     * Sets the end_time value for this QueryAccessFailBean.
     * 
     * @param end_time
     */
    public void setEnd_time(java.lang.String end_time) {
        this.end_time = end_time;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryAccessFailBean)) return false;
        QueryAccessFailBean other = (QueryAccessFailBean) obj;
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
            ((this.start_time==null && other.getStart_time()==null) || 
             (this.start_time!=null &&
              this.start_time.equals(other.getStart_time()))) &&
            ((this.end_time==null && other.getEnd_time()==null) || 
             (this.end_time!=null &&
              this.end_time.equals(other.getEnd_time())));
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
        if (getStart_time() != null) {
            _hashCode += getStart_time().hashCode();
        }
        if (getEnd_time() != null) {
            _hashCode += getEnd_time().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryAccessFailBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryAccessFailBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("start_time");
        elemField.setXmlName(new javax.xml.namespace.QName("", "start_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end_time");
        elemField.setXmlName(new javax.xml.namespace.QName("", "end_time"));
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
