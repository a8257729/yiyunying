/**
 * HeadBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class HeadBean  implements java.io.Serializable {
    private java.lang.String version;

    private java.lang.String servtype;

    private java.lang.String reqtype;

    private java.lang.String sequence;

    private java.lang.String priority;

    private java.lang.String reqtime;

    private java.lang.String continued;

    public HeadBean() {
    }

    public HeadBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued) {
           this.version = version;
           this.servtype = servtype;
           this.reqtype = reqtype;
           this.sequence = sequence;
           this.priority = priority;
           this.reqtime = reqtime;
           this.continued = continued;
    }


    /**
     * Gets the version value for this HeadBean.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this HeadBean.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }


    /**
     * Gets the servtype value for this HeadBean.
     * 
     * @return servtype
     */
    public java.lang.String getServtype() {
        return servtype;
    }


    /**
     * Sets the servtype value for this HeadBean.
     * 
     * @param servtype
     */
    public void setServtype(java.lang.String servtype) {
        this.servtype = servtype;
    }


    /**
     * Gets the reqtype value for this HeadBean.
     * 
     * @return reqtype
     */
    public java.lang.String getReqtype() {
        return reqtype;
    }


    /**
     * Sets the reqtype value for this HeadBean.
     * 
     * @param reqtype
     */
    public void setReqtype(java.lang.String reqtype) {
        this.reqtype = reqtype;
    }


    /**
     * Gets the sequence value for this HeadBean.
     * 
     * @return sequence
     */
    public java.lang.String getSequence() {
        return sequence;
    }


    /**
     * Sets the sequence value for this HeadBean.
     * 
     * @param sequence
     */
    public void setSequence(java.lang.String sequence) {
        this.sequence = sequence;
    }


    /**
     * Gets the priority value for this HeadBean.
     * 
     * @return priority
     */
    public java.lang.String getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this HeadBean.
     * 
     * @param priority
     */
    public void setPriority(java.lang.String priority) {
        this.priority = priority;
    }


    /**
     * Gets the reqtime value for this HeadBean.
     * 
     * @return reqtime
     */
    public java.lang.String getReqtime() {
        return reqtime;
    }


    /**
     * Sets the reqtime value for this HeadBean.
     * 
     * @param reqtime
     */
    public void setReqtime(java.lang.String reqtime) {
        this.reqtime = reqtime;
    }


    /**
     * Gets the continued value for this HeadBean.
     * 
     * @return continued
     */
    public java.lang.String getContinued() {
        return continued;
    }


    /**
     * Sets the continued value for this HeadBean.
     * 
     * @param continued
     */
    public void setContinued(java.lang.String continued) {
        this.continued = continued;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HeadBean)) return false;
        HeadBean other = (HeadBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.servtype==null && other.getServtype()==null) || 
             (this.servtype!=null &&
              this.servtype.equals(other.getServtype()))) &&
            ((this.reqtype==null && other.getReqtype()==null) || 
             (this.reqtype!=null &&
              this.reqtype.equals(other.getReqtype()))) &&
            ((this.sequence==null && other.getSequence()==null) || 
             (this.sequence!=null &&
              this.sequence.equals(other.getSequence()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
            ((this.reqtime==null && other.getReqtime()==null) || 
             (this.reqtime!=null &&
              this.reqtime.equals(other.getReqtime()))) &&
            ((this.continued==null && other.getContinued()==null) || 
             (this.continued!=null &&
              this.continued.equals(other.getContinued())));
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
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getServtype() != null) {
            _hashCode += getServtype().hashCode();
        }
        if (getReqtype() != null) {
            _hashCode += getReqtype().hashCode();
        }
        if (getSequence() != null) {
            _hashCode += getSequence().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getReqtime() != null) {
            _hashCode += getReqtime().hashCode();
        }
        if (getContinued() != null) {
            _hashCode += getContinued().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HeadBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "HeadBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servtype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servtype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqtype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reqtype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequence");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sequence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqtime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reqtime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("continued");
        elemField.setXmlName(new javax.xml.namespace.QName("", "continued"));
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
