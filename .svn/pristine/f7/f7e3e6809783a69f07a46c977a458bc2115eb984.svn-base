/**
 * QueryUserSessionResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class QueryUserSessionResponseBean  extends com.ztesoft.mobile.v2.broadBandWs.ResponseBean  implements java.io.Serializable {
    private com.ztesoft.mobile.v2.broadBandWs.Online_info[] online_infos;

    public QueryUserSessionResponseBean() {
    }

    public QueryUserSessionResponseBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String ret_code,
           java.lang.String ret_msg,
           com.ztesoft.mobile.v2.broadBandWs.Online_info[] online_infos) {
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
        this.online_infos = online_infos;
    }


    /**
     * Gets the online_infos value for this QueryUserSessionResponseBean.
     * 
     * @return online_infos
     */
    public com.ztesoft.mobile.v2.broadBandWs.Online_info[] getOnline_infos() {
        return online_infos;
    }


    /**
     * Sets the online_infos value for this QueryUserSessionResponseBean.
     * 
     * @param online_infos
     */
    public void setOnline_infos(com.ztesoft.mobile.v2.broadBandWs.Online_info[] online_infos) {
        this.online_infos = online_infos;
    }

    public com.ztesoft.mobile.v2.broadBandWs.Online_info getOnline_infos(int i) {
        return this.online_infos[i];
    }

    public void setOnline_infos(int i, com.ztesoft.mobile.v2.broadBandWs.Online_info _value) {
        this.online_infos[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryUserSessionResponseBean)) return false;
        QueryUserSessionResponseBean other = (QueryUserSessionResponseBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.online_infos==null && other.getOnline_infos()==null) || 
             (this.online_infos!=null &&
              java.util.Arrays.equals(this.online_infos, other.getOnline_infos())));
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
        if (getOnline_infos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOnline_infos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOnline_infos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryUserSessionResponseBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserSessionResponseBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("online_infos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "online_infos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://broad.bean.soap.aaa.huawei.com/xsd", "online_info"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
