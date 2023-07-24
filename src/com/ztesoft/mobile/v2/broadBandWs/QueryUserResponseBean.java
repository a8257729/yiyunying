/**
 * QueryUserResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class QueryUserResponseBean  extends com.ztesoft.mobile.v2.broadBandWs.ResponseBean  implements java.io.Serializable {
    private java.lang.String user_name;

    private java.lang.String service_level;

    private java.lang.String master_session;

    private java.lang.String area_id;

    private java.lang.String limit_flag;

    private java.lang.String limit_port;

    private java.lang.String access_right;

    private java.lang.String template_id;

    private java.lang.String bind_flag;

    private com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBeanCombined_bind_info combined_bind_info;

    public QueryUserResponseBean() {
    }

    public QueryUserResponseBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String ret_code,
           java.lang.String ret_msg,
           java.lang.String user_name,
           java.lang.String service_level,
           java.lang.String master_session,
           java.lang.String area_id,
           java.lang.String limit_flag,
           java.lang.String limit_port,
           java.lang.String access_right,
           java.lang.String template_id,
           java.lang.String bind_flag,
           com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBeanCombined_bind_info combined_bind_info) {
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
        this.user_name = user_name;
        this.service_level = service_level;
        this.master_session = master_session;
        this.area_id = area_id;
        this.limit_flag = limit_flag;
        this.limit_port = limit_port;
        this.access_right = access_right;
        this.template_id = template_id;
        this.bind_flag = bind_flag;
        this.combined_bind_info = combined_bind_info;
    }


    /**
     * Gets the user_name value for this QueryUserResponseBean.
     * 
     * @return user_name
     */
    public java.lang.String getUser_name() {
        return user_name;
    }


    /**
     * Sets the user_name value for this QueryUserResponseBean.
     * 
     * @param user_name
     */
    public void setUser_name(java.lang.String user_name) {
        this.user_name = user_name;
    }


    /**
     * Gets the service_level value for this QueryUserResponseBean.
     * 
     * @return service_level
     */
    public java.lang.String getService_level() {
        return service_level;
    }


    /**
     * Sets the service_level value for this QueryUserResponseBean.
     * 
     * @param service_level
     */
    public void setService_level(java.lang.String service_level) {
        this.service_level = service_level;
    }


    /**
     * Gets the master_session value for this QueryUserResponseBean.
     * 
     * @return master_session
     */
    public java.lang.String getMaster_session() {
        return master_session;
    }


    /**
     * Sets the master_session value for this QueryUserResponseBean.
     * 
     * @param master_session
     */
    public void setMaster_session(java.lang.String master_session) {
        this.master_session = master_session;
    }


    /**
     * Gets the area_id value for this QueryUserResponseBean.
     * 
     * @return area_id
     */
    public java.lang.String getArea_id() {
        return area_id;
    }


    /**
     * Sets the area_id value for this QueryUserResponseBean.
     * 
     * @param area_id
     */
    public void setArea_id(java.lang.String area_id) {
        this.area_id = area_id;
    }


    /**
     * Gets the limit_flag value for this QueryUserResponseBean.
     * 
     * @return limit_flag
     */
    public java.lang.String getLimit_flag() {
        return limit_flag;
    }


    /**
     * Sets the limit_flag value for this QueryUserResponseBean.
     * 
     * @param limit_flag
     */
    public void setLimit_flag(java.lang.String limit_flag) {
        this.limit_flag = limit_flag;
    }


    /**
     * Gets the limit_port value for this QueryUserResponseBean.
     * 
     * @return limit_port
     */
    public java.lang.String getLimit_port() {
        return limit_port;
    }


    /**
     * Sets the limit_port value for this QueryUserResponseBean.
     * 
     * @param limit_port
     */
    public void setLimit_port(java.lang.String limit_port) {
        this.limit_port = limit_port;
    }


    /**
     * Gets the access_right value for this QueryUserResponseBean.
     * 
     * @return access_right
     */
    public java.lang.String getAccess_right() {
        return access_right;
    }


    /**
     * Sets the access_right value for this QueryUserResponseBean.
     * 
     * @param access_right
     */
    public void setAccess_right(java.lang.String access_right) {
        this.access_right = access_right;
    }


    /**
     * Gets the template_id value for this QueryUserResponseBean.
     * 
     * @return template_id
     */
    public java.lang.String getTemplate_id() {
        return template_id;
    }


    /**
     * Sets the template_id value for this QueryUserResponseBean.
     * 
     * @param template_id
     */
    public void setTemplate_id(java.lang.String template_id) {
        this.template_id = template_id;
    }


    /**
     * Gets the bind_flag value for this QueryUserResponseBean.
     * 
     * @return bind_flag
     */
    public java.lang.String getBind_flag() {
        return bind_flag;
    }


    /**
     * Sets the bind_flag value for this QueryUserResponseBean.
     * 
     * @param bind_flag
     */
    public void setBind_flag(java.lang.String bind_flag) {
        this.bind_flag = bind_flag;
    }


    /**
     * Gets the combined_bind_info value for this QueryUserResponseBean.
     * 
     * @return combined_bind_info
     */
    public com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBeanCombined_bind_info getCombined_bind_info() {
        return combined_bind_info;
    }


    /**
     * Sets the combined_bind_info value for this QueryUserResponseBean.
     * 
     * @param combined_bind_info
     */
    public void setCombined_bind_info(com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBeanCombined_bind_info combined_bind_info) {
        this.combined_bind_info = combined_bind_info;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryUserResponseBean)) return false;
        QueryUserResponseBean other = (QueryUserResponseBean) obj;
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
            ((this.service_level==null && other.getService_level()==null) || 
             (this.service_level!=null &&
              this.service_level.equals(other.getService_level()))) &&
            ((this.master_session==null && other.getMaster_session()==null) || 
             (this.master_session!=null &&
              this.master_session.equals(other.getMaster_session()))) &&
            ((this.area_id==null && other.getArea_id()==null) || 
             (this.area_id!=null &&
              this.area_id.equals(other.getArea_id()))) &&
            ((this.limit_flag==null && other.getLimit_flag()==null) || 
             (this.limit_flag!=null &&
              this.limit_flag.equals(other.getLimit_flag()))) &&
            ((this.limit_port==null && other.getLimit_port()==null) || 
             (this.limit_port!=null &&
              this.limit_port.equals(other.getLimit_port()))) &&
            ((this.access_right==null && other.getAccess_right()==null) || 
             (this.access_right!=null &&
              this.access_right.equals(other.getAccess_right()))) &&
            ((this.template_id==null && other.getTemplate_id()==null) || 
             (this.template_id!=null &&
              this.template_id.equals(other.getTemplate_id()))) &&
            ((this.bind_flag==null && other.getBind_flag()==null) || 
             (this.bind_flag!=null &&
              this.bind_flag.equals(other.getBind_flag()))) &&
            ((this.combined_bind_info==null && other.getCombined_bind_info()==null) || 
             (this.combined_bind_info!=null &&
              this.combined_bind_info.equals(other.getCombined_bind_info())));
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
        if (getService_level() != null) {
            _hashCode += getService_level().hashCode();
        }
        if (getMaster_session() != null) {
            _hashCode += getMaster_session().hashCode();
        }
        if (getArea_id() != null) {
            _hashCode += getArea_id().hashCode();
        }
        if (getLimit_flag() != null) {
            _hashCode += getLimit_flag().hashCode();
        }
        if (getLimit_port() != null) {
            _hashCode += getLimit_port().hashCode();
        }
        if (getAccess_right() != null) {
            _hashCode += getAccess_right().hashCode();
        }
        if (getTemplate_id() != null) {
            _hashCode += getTemplate_id().hashCode();
        }
        if (getBind_flag() != null) {
            _hashCode += getBind_flag().hashCode();
        }
        if (getCombined_bind_info() != null) {
            _hashCode += getCombined_bind_info().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryUserResponseBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserResponseBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("service_level");
        elemField.setXmlName(new javax.xml.namespace.QName("", "service_level"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("master_session");
        elemField.setXmlName(new javax.xml.namespace.QName("", "master_session"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "area_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limit_flag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limit_flag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limit_port");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limit_port"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("access_right");
        elemField.setXmlName(new javax.xml.namespace.QName("", "access_right"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("template_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "template_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bind_flag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bind_flag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("combined_bind_info");
        elemField.setXmlName(new javax.xml.namespace.QName("", "combined_bind_info"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserResponseBeanCombined_bind_info"));
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
