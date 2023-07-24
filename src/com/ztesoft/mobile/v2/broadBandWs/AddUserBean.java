/**
 * AddUserBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class AddUserBean  extends com.ztesoft.mobile.v2.broadBandWs.HeadBean  implements java.io.Serializable {
    private java.lang.String pay_type;

    private java.lang.String user_name;

    private java.lang.String password;

    private java.lang.String region;

    private java.lang.String master_session;

    private java.lang.String roaming_level;

    private java.lang.String area_id;

    private java.lang.String limit_flag;

    private java.lang.String limit_port;

    private java.lang.String prepaid_Server;

    private java.lang.String IPv6_InterfaceID;

    private java.lang.String fraIPv6_Prefix;

    private java.lang.String delIPv6_Prefix;

    private java.lang.String IPv6_Address;

    private java.lang.String accounttype;

    private java.lang.String access_right;

    private java.lang.String template_id;

    private java.lang.String bind_flag;

    private com.ztesoft.mobile.v2.broadBandWs.AddUserBeanCombined_bind_info combined_bind_info;

    public AddUserBean() {
    }

    public AddUserBean(
           java.lang.String version,
           java.lang.String servtype,
           java.lang.String reqtype,
           java.lang.String sequence,
           java.lang.String priority,
           java.lang.String reqtime,
           java.lang.String continued,
           java.lang.String pay_type,
           java.lang.String user_name,
           java.lang.String password,
           java.lang.String region,
           java.lang.String master_session,
           java.lang.String roaming_level,
           java.lang.String area_id,
           java.lang.String limit_flag,
           java.lang.String limit_port,
           java.lang.String prepaid_Server,
           java.lang.String IPv6_InterfaceID,
           java.lang.String fraIPv6_Prefix,
           java.lang.String delIPv6_Prefix,
           java.lang.String IPv6_Address,
           java.lang.String accounttype,
           java.lang.String access_right,
           java.lang.String template_id,
           java.lang.String bind_flag,
           com.ztesoft.mobile.v2.broadBandWs.AddUserBeanCombined_bind_info combined_bind_info) {
        super(
            version,
            servtype,
            reqtype,
            sequence,
            priority,
            reqtime,
            continued);
        this.pay_type = pay_type;
        this.user_name = user_name;
        this.password = password;
        this.region = region;
        this.master_session = master_session;
        this.roaming_level = roaming_level;
        this.area_id = area_id;
        this.limit_flag = limit_flag;
        this.limit_port = limit_port;
        this.prepaid_Server = prepaid_Server;
        this.IPv6_InterfaceID = IPv6_InterfaceID;
        this.fraIPv6_Prefix = fraIPv6_Prefix;
        this.delIPv6_Prefix = delIPv6_Prefix;
        this.IPv6_Address = IPv6_Address;
        this.accounttype = accounttype;
        this.access_right = access_right;
        this.template_id = template_id;
        this.bind_flag = bind_flag;
        this.combined_bind_info = combined_bind_info;
    }


    /**
     * Gets the pay_type value for this AddUserBean.
     * 
     * @return pay_type
     */
    public java.lang.String getPay_type() {
        return pay_type;
    }


    /**
     * Sets the pay_type value for this AddUserBean.
     * 
     * @param pay_type
     */
    public void setPay_type(java.lang.String pay_type) {
        this.pay_type = pay_type;
    }


    /**
     * Gets the user_name value for this AddUserBean.
     * 
     * @return user_name
     */
    public java.lang.String getUser_name() {
        return user_name;
    }


    /**
     * Sets the user_name value for this AddUserBean.
     * 
     * @param user_name
     */
    public void setUser_name(java.lang.String user_name) {
        this.user_name = user_name;
    }


    /**
     * Gets the password value for this AddUserBean.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this AddUserBean.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the region value for this AddUserBean.
     * 
     * @return region
     */
    public java.lang.String getRegion() {
        return region;
    }


    /**
     * Sets the region value for this AddUserBean.
     * 
     * @param region
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }


    /**
     * Gets the master_session value for this AddUserBean.
     * 
     * @return master_session
     */
    public java.lang.String getMaster_session() {
        return master_session;
    }


    /**
     * Sets the master_session value for this AddUserBean.
     * 
     * @param master_session
     */
    public void setMaster_session(java.lang.String master_session) {
        this.master_session = master_session;
    }


    /**
     * Gets the roaming_level value for this AddUserBean.
     * 
     * @return roaming_level
     */
    public java.lang.String getRoaming_level() {
        return roaming_level;
    }


    /**
     * Sets the roaming_level value for this AddUserBean.
     * 
     * @param roaming_level
     */
    public void setRoaming_level(java.lang.String roaming_level) {
        this.roaming_level = roaming_level;
    }


    /**
     * Gets the area_id value for this AddUserBean.
     * 
     * @return area_id
     */
    public java.lang.String getArea_id() {
        return area_id;
    }


    /**
     * Sets the area_id value for this AddUserBean.
     * 
     * @param area_id
     */
    public void setArea_id(java.lang.String area_id) {
        this.area_id = area_id;
    }


    /**
     * Gets the limit_flag value for this AddUserBean.
     * 
     * @return limit_flag
     */
    public java.lang.String getLimit_flag() {
        return limit_flag;
    }


    /**
     * Sets the limit_flag value for this AddUserBean.
     * 
     * @param limit_flag
     */
    public void setLimit_flag(java.lang.String limit_flag) {
        this.limit_flag = limit_flag;
    }


    /**
     * Gets the limit_port value for this AddUserBean.
     * 
     * @return limit_port
     */
    public java.lang.String getLimit_port() {
        return limit_port;
    }


    /**
     * Sets the limit_port value for this AddUserBean.
     * 
     * @param limit_port
     */
    public void setLimit_port(java.lang.String limit_port) {
        this.limit_port = limit_port;
    }


    /**
     * Gets the prepaid_Server value for this AddUserBean.
     * 
     * @return prepaid_Server
     */
    public java.lang.String getPrepaid_Server() {
        return prepaid_Server;
    }


    /**
     * Sets the prepaid_Server value for this AddUserBean.
     * 
     * @param prepaid_Server
     */
    public void setPrepaid_Server(java.lang.String prepaid_Server) {
        this.prepaid_Server = prepaid_Server;
    }


    /**
     * Gets the IPv6_InterfaceID value for this AddUserBean.
     * 
     * @return IPv6_InterfaceID
     */
    public java.lang.String getIPv6_InterfaceID() {
        return IPv6_InterfaceID;
    }


    /**
     * Sets the IPv6_InterfaceID value for this AddUserBean.
     * 
     * @param IPv6_InterfaceID
     */
    public void setIPv6_InterfaceID(java.lang.String IPv6_InterfaceID) {
        this.IPv6_InterfaceID = IPv6_InterfaceID;
    }


    /**
     * Gets the fraIPv6_Prefix value for this AddUserBean.
     * 
     * @return fraIPv6_Prefix
     */
    public java.lang.String getFraIPv6_Prefix() {
        return fraIPv6_Prefix;
    }


    /**
     * Sets the fraIPv6_Prefix value for this AddUserBean.
     * 
     * @param fraIPv6_Prefix
     */
    public void setFraIPv6_Prefix(java.lang.String fraIPv6_Prefix) {
        this.fraIPv6_Prefix = fraIPv6_Prefix;
    }


    /**
     * Gets the delIPv6_Prefix value for this AddUserBean.
     * 
     * @return delIPv6_Prefix
     */
    public java.lang.String getDelIPv6_Prefix() {
        return delIPv6_Prefix;
    }


    /**
     * Sets the delIPv6_Prefix value for this AddUserBean.
     * 
     * @param delIPv6_Prefix
     */
    public void setDelIPv6_Prefix(java.lang.String delIPv6_Prefix) {
        this.delIPv6_Prefix = delIPv6_Prefix;
    }


    /**
     * Gets the IPv6_Address value for this AddUserBean.
     * 
     * @return IPv6_Address
     */
    public java.lang.String getIPv6_Address() {
        return IPv6_Address;
    }


    /**
     * Sets the IPv6_Address value for this AddUserBean.
     * 
     * @param IPv6_Address
     */
    public void setIPv6_Address(java.lang.String IPv6_Address) {
        this.IPv6_Address = IPv6_Address;
    }


    /**
     * Gets the accounttype value for this AddUserBean.
     * 
     * @return accounttype
     */
    public java.lang.String getAccounttype() {
        return accounttype;
    }


    /**
     * Sets the accounttype value for this AddUserBean.
     * 
     * @param accounttype
     */
    public void setAccounttype(java.lang.String accounttype) {
        this.accounttype = accounttype;
    }


    /**
     * Gets the access_right value for this AddUserBean.
     * 
     * @return access_right
     */
    public java.lang.String getAccess_right() {
        return access_right;
    }


    /**
     * Sets the access_right value for this AddUserBean.
     * 
     * @param access_right
     */
    public void setAccess_right(java.lang.String access_right) {
        this.access_right = access_right;
    }


    /**
     * Gets the template_id value for this AddUserBean.
     * 
     * @return template_id
     */
    public java.lang.String getTemplate_id() {
        return template_id;
    }


    /**
     * Sets the template_id value for this AddUserBean.
     * 
     * @param template_id
     */
    public void setTemplate_id(java.lang.String template_id) {
        this.template_id = template_id;
    }


    /**
     * Gets the bind_flag value for this AddUserBean.
     * 
     * @return bind_flag
     */
    public java.lang.String getBind_flag() {
        return bind_flag;
    }


    /**
     * Sets the bind_flag value for this AddUserBean.
     * 
     * @param bind_flag
     */
    public void setBind_flag(java.lang.String bind_flag) {
        this.bind_flag = bind_flag;
    }


    /**
     * Gets the combined_bind_info value for this AddUserBean.
     * 
     * @return combined_bind_info
     */
    public com.ztesoft.mobile.v2.broadBandWs.AddUserBeanCombined_bind_info getCombined_bind_info() {
        return combined_bind_info;
    }


    /**
     * Sets the combined_bind_info value for this AddUserBean.
     * 
     * @param combined_bind_info
     */
    public void setCombined_bind_info(com.ztesoft.mobile.v2.broadBandWs.AddUserBeanCombined_bind_info combined_bind_info) {
        this.combined_bind_info = combined_bind_info;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddUserBean)) return false;
        AddUserBean other = (AddUserBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.pay_type==null && other.getPay_type()==null) || 
             (this.pay_type!=null &&
              this.pay_type.equals(other.getPay_type()))) &&
            ((this.user_name==null && other.getUser_name()==null) || 
             (this.user_name!=null &&
              this.user_name.equals(other.getUser_name()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.region==null && other.getRegion()==null) || 
             (this.region!=null &&
              this.region.equals(other.getRegion()))) &&
            ((this.master_session==null && other.getMaster_session()==null) || 
             (this.master_session!=null &&
              this.master_session.equals(other.getMaster_session()))) &&
            ((this.roaming_level==null && other.getRoaming_level()==null) || 
             (this.roaming_level!=null &&
              this.roaming_level.equals(other.getRoaming_level()))) &&
            ((this.area_id==null && other.getArea_id()==null) || 
             (this.area_id!=null &&
              this.area_id.equals(other.getArea_id()))) &&
            ((this.limit_flag==null && other.getLimit_flag()==null) || 
             (this.limit_flag!=null &&
              this.limit_flag.equals(other.getLimit_flag()))) &&
            ((this.limit_port==null && other.getLimit_port()==null) || 
             (this.limit_port!=null &&
              this.limit_port.equals(other.getLimit_port()))) &&
            ((this.prepaid_Server==null && other.getPrepaid_Server()==null) || 
             (this.prepaid_Server!=null &&
              this.prepaid_Server.equals(other.getPrepaid_Server()))) &&
            ((this.IPv6_InterfaceID==null && other.getIPv6_InterfaceID()==null) || 
             (this.IPv6_InterfaceID!=null &&
              this.IPv6_InterfaceID.equals(other.getIPv6_InterfaceID()))) &&
            ((this.fraIPv6_Prefix==null && other.getFraIPv6_Prefix()==null) || 
             (this.fraIPv6_Prefix!=null &&
              this.fraIPv6_Prefix.equals(other.getFraIPv6_Prefix()))) &&
            ((this.delIPv6_Prefix==null && other.getDelIPv6_Prefix()==null) || 
             (this.delIPv6_Prefix!=null &&
              this.delIPv6_Prefix.equals(other.getDelIPv6_Prefix()))) &&
            ((this.IPv6_Address==null && other.getIPv6_Address()==null) || 
             (this.IPv6_Address!=null &&
              this.IPv6_Address.equals(other.getIPv6_Address()))) &&
            ((this.accounttype==null && other.getAccounttype()==null) || 
             (this.accounttype!=null &&
              this.accounttype.equals(other.getAccounttype()))) &&
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
        if (getPay_type() != null) {
            _hashCode += getPay_type().hashCode();
        }
        if (getUser_name() != null) {
            _hashCode += getUser_name().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        if (getMaster_session() != null) {
            _hashCode += getMaster_session().hashCode();
        }
        if (getRoaming_level() != null) {
            _hashCode += getRoaming_level().hashCode();
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
        if (getPrepaid_Server() != null) {
            _hashCode += getPrepaid_Server().hashCode();
        }
        if (getIPv6_InterfaceID() != null) {
            _hashCode += getIPv6_InterfaceID().hashCode();
        }
        if (getFraIPv6_Prefix() != null) {
            _hashCode += getFraIPv6_Prefix().hashCode();
        }
        if (getDelIPv6_Prefix() != null) {
            _hashCode += getDelIPv6_Prefix().hashCode();
        }
        if (getIPv6_Address() != null) {
            _hashCode += getIPv6_Address().hashCode();
        }
        if (getAccounttype() != null) {
            _hashCode += getAccounttype().hashCode();
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
        new org.apache.axis.description.TypeDesc(AddUserBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AddUserBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pay_type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pay_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("", "region"));
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
        elemField.setFieldName("roaming_level");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roaming_level"));
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
        elemField.setFieldName("prepaid_Server");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prepaid_Server"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPv6_InterfaceID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IPv6_InterfaceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fraIPv6_Prefix");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FraIPv6_Prefix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delIPv6_Prefix");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DelIPv6_Prefix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPv6_Address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IPv6_Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accounttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accounttype"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AddUserBeanCombined_bind_info"));
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
