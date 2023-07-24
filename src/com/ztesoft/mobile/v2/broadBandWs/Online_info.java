/**
 * Online_info.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class Online_info  implements java.io.Serializable {
    private java.lang.String user_name;

    private java.lang.String userip;

    private java.lang.String isnip;

    private java.lang.String nasportid;

    private java.lang.String serstarttime;

    private java.lang.String accutime;

    private java.lang.String acctsessionid;

    private java.lang.String inputvolumn;

    private java.lang.String outputvolumn;

    public Online_info() {
    }

    public Online_info(
           java.lang.String user_name,
           java.lang.String userip,
           java.lang.String isnip,
           java.lang.String nasportid,
           java.lang.String serstarttime,
           java.lang.String accutime,
           java.lang.String acctsessionid,
           java.lang.String inputvolumn,
           java.lang.String outputvolumn) {
           this.user_name = user_name;
           this.userip = userip;
           this.isnip = isnip;
           this.nasportid = nasportid;
           this.serstarttime = serstarttime;
           this.accutime = accutime;
           this.acctsessionid = acctsessionid;
           this.inputvolumn = inputvolumn;
           this.outputvolumn = outputvolumn;
    }


    /**
     * Gets the user_name value for this Online_info.
     * 
     * @return user_name
     */
    public java.lang.String getUser_name() {
        return user_name;
    }


    /**
     * Sets the user_name value for this Online_info.
     * 
     * @param user_name
     */
    public void setUser_name(java.lang.String user_name) {
        this.user_name = user_name;
    }


    /**
     * Gets the userip value for this Online_info.
     * 
     * @return userip
     */
    public java.lang.String getUserip() {
        return userip;
    }


    /**
     * Sets the userip value for this Online_info.
     * 
     * @param userip
     */
    public void setUserip(java.lang.String userip) {
        this.userip = userip;
    }


    /**
     * Gets the isnip value for this Online_info.
     * 
     * @return isnip
     */
    public java.lang.String getIsnip() {
        return isnip;
    }


    /**
     * Sets the isnip value for this Online_info.
     * 
     * @param isnip
     */
    public void setIsnip(java.lang.String isnip) {
        this.isnip = isnip;
    }


    /**
     * Gets the nasportid value for this Online_info.
     * 
     * @return nasportid
     */
    public java.lang.String getNasportid() {
        return nasportid;
    }


    /**
     * Sets the nasportid value for this Online_info.
     * 
     * @param nasportid
     */
    public void setNasportid(java.lang.String nasportid) {
        this.nasportid = nasportid;
    }


    /**
     * Gets the serstarttime value for this Online_info.
     * 
     * @return serstarttime
     */
    public java.lang.String getSerstarttime() {
        return serstarttime;
    }


    /**
     * Sets the serstarttime value for this Online_info.
     * 
     * @param serstarttime
     */
    public void setSerstarttime(java.lang.String serstarttime) {
        this.serstarttime = serstarttime;
    }


    /**
     * Gets the accutime value for this Online_info.
     * 
     * @return accutime
     */
    public java.lang.String getAccutime() {
        return accutime;
    }


    /**
     * Sets the accutime value for this Online_info.
     * 
     * @param accutime
     */
    public void setAccutime(java.lang.String accutime) {
        this.accutime = accutime;
    }


    /**
     * Gets the acctsessionid value for this Online_info.
     * 
     * @return acctsessionid
     */
    public java.lang.String getAcctsessionid() {
        return acctsessionid;
    }


    /**
     * Sets the acctsessionid value for this Online_info.
     * 
     * @param acctsessionid
     */
    public void setAcctsessionid(java.lang.String acctsessionid) {
        this.acctsessionid = acctsessionid;
    }


    /**
     * Gets the inputvolumn value for this Online_info.
     * 
     * @return inputvolumn
     */
    public java.lang.String getInputvolumn() {
        return inputvolumn;
    }


    /**
     * Sets the inputvolumn value for this Online_info.
     * 
     * @param inputvolumn
     */
    public void setInputvolumn(java.lang.String inputvolumn) {
        this.inputvolumn = inputvolumn;
    }


    /**
     * Gets the outputvolumn value for this Online_info.
     * 
     * @return outputvolumn
     */
    public java.lang.String getOutputvolumn() {
        return outputvolumn;
    }


    /**
     * Sets the outputvolumn value for this Online_info.
     * 
     * @param outputvolumn
     */
    public void setOutputvolumn(java.lang.String outputvolumn) {
        this.outputvolumn = outputvolumn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Online_info)) return false;
        Online_info other = (Online_info) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.user_name==null && other.getUser_name()==null) || 
             (this.user_name!=null &&
              this.user_name.equals(other.getUser_name()))) &&
            ((this.userip==null && other.getUserip()==null) || 
             (this.userip!=null &&
              this.userip.equals(other.getUserip()))) &&
            ((this.isnip==null && other.getIsnip()==null) || 
             (this.isnip!=null &&
              this.isnip.equals(other.getIsnip()))) &&
            ((this.nasportid==null && other.getNasportid()==null) || 
             (this.nasportid!=null &&
              this.nasportid.equals(other.getNasportid()))) &&
            ((this.serstarttime==null && other.getSerstarttime()==null) || 
             (this.serstarttime!=null &&
              this.serstarttime.equals(other.getSerstarttime()))) &&
            ((this.accutime==null && other.getAccutime()==null) || 
             (this.accutime!=null &&
              this.accutime.equals(other.getAccutime()))) &&
            ((this.acctsessionid==null && other.getAcctsessionid()==null) || 
             (this.acctsessionid!=null &&
              this.acctsessionid.equals(other.getAcctsessionid()))) &&
            ((this.inputvolumn==null && other.getInputvolumn()==null) || 
             (this.inputvolumn!=null &&
              this.inputvolumn.equals(other.getInputvolumn()))) &&
            ((this.outputvolumn==null && other.getOutputvolumn()==null) || 
             (this.outputvolumn!=null &&
              this.outputvolumn.equals(other.getOutputvolumn())));
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
        if (getUser_name() != null) {
            _hashCode += getUser_name().hashCode();
        }
        if (getUserip() != null) {
            _hashCode += getUserip().hashCode();
        }
        if (getIsnip() != null) {
            _hashCode += getIsnip().hashCode();
        }
        if (getNasportid() != null) {
            _hashCode += getNasportid().hashCode();
        }
        if (getSerstarttime() != null) {
            _hashCode += getSerstarttime().hashCode();
        }
        if (getAccutime() != null) {
            _hashCode += getAccutime().hashCode();
        }
        if (getAcctsessionid() != null) {
            _hashCode += getAcctsessionid().hashCode();
        }
        if (getInputvolumn() != null) {
            _hashCode += getInputvolumn().hashCode();
        }
        if (getOutputvolumn() != null) {
            _hashCode += getOutputvolumn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Online_info.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://broad.bean.soap.aaa.huawei.com/xsd", "online_info"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isnip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isnip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nasportid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nasportid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serstarttime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serstarttime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accutime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accutime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctsessionid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acctsessionid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inputvolumn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inputvolumn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputvolumn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outputvolumn"));
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
