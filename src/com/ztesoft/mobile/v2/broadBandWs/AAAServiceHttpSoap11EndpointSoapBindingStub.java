/**
 * AAAServiceHttpSoap11EndpointSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public class AAAServiceHttpSoap11EndpointSoapBindingStub extends org.apache.axis.client.Stub implements com.ztesoft.mobile.v2.broadBandWs.AAAServiceSoap11BindingImpl {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[18];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("actAccount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "actBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ActAccountBean"), com.ztesoft.mobile.v2.broadBandWs.ActAccountBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "actAccountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setRoam");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "roambean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "RoamBean"), com.ztesoft.mobile.v2.broadBandWs.RoamBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "setRoamReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "delUserBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DelUserBean"), com.ztesoft.mobile.v2.broadBandWs.DelUserBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "delUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("modPassword");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "modbean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModBean"), com.ztesoft.mobile.v2.broadBandWs.ModBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "BackBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.BackBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "modPasswordReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "addBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AddUserBean"), com.ztesoft.mobile.v2.broadBandWs.AddUserBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "addUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deActAccount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "deActBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DeActAccountBean"), com.ztesoft.mobile.v2.broadBandWs.DeActAccountBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "deActAccountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ipAccountQuery");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ipAccountBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "IPAccountQueryBean"), com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "IPAccountQueryResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ipAccountQueryReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("modUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "modUserBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModUserBean"), com.ztesoft.mobile.v2.broadBandWs.ModUserBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "modUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("authUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "authUserBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AuthUserBean"), com.ztesoft.mobile.v2.broadBandWs.AuthUserBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AuthUserResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.AuthUserResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "authUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deductFee");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "deductFeeBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DeductFeeBean"), com.ztesoft.mobile.v2.broadBandWs.DeductFeeBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DeductFeeResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.DeductFeeResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "deductFeeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryUserByUNorIP");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryUserByUNorIPBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserByUNorIPBean"), com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserByUNorIPResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryUserByUNorIPReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("modOrder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "modOrderBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModOrderBean"), com.ztesoft.mobile.v2.broadBandWs.ModOrderBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "modOrderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryOrder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryOrderBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryOrderBean"), com.ztesoft.mobile.v2.broadBandWs.QueryOrderBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryOrderResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.QueryOrderResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryOrderReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("resetAccount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resetAccountBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResetAccountBean"), com.ztesoft.mobile.v2.broadBandWs.ResetAccountBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "resetAccountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unsetCombinedBind");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "unsetCombinedBindBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "UnsetCombinedBindBean"), com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "UnsetCombinedBindResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "unsetCombinedBindReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryUserBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserBean"), com.ztesoft.mobile.v2.broadBandWs.QueryUserBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAccessFail");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryAccessFailBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryAccessFailBean"), com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryAccessFailResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAccessFailReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryUserSession");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryUserSessionBean"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserSessionBean"), com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionBean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserSessionResponseBean"));
        oper.setReturnClass(com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryUserSessionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[17] = oper;

    }

    public AAAServiceHttpSoap11EndpointSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public AAAServiceHttpSoap11EndpointSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public AAAServiceHttpSoap11EndpointSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ActAccountBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.ActAccountBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AddUserBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.AddUserBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AddUserBeanCombined_bind_info");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.AddUserBeanCombined_bind_info.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AuthUserBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.AuthUserBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "AuthUserResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.AuthUserResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "BackBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.BackBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DeActAccountBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.DeActAccountBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DeductFeeBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.DeductFeeBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DeductFeeResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.DeductFeeResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "DelUserBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.DelUserBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "HeadBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.HeadBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "IPAccountQueryBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "IPAccountQueryResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.ModBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModOrderBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.ModOrderBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModUserBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.ModUserBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ModUserBeanCombined_bind_info");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.ModUserBeanCombined_bind_info.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryAccessFailBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryAccessFailResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryOrderBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryOrderBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryOrderResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryOrderResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryUserBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserByUNorIPBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserByUNorIPResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserResponseBeanCombined_bind_info");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBeanCombined_bind_info.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserSessionBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "QueryUserSessionResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResetAccountBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.ResetAccountBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "ResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "RoamBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.RoamBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "UnsetCombinedBindBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://gkweb1:9020/FLOWBUS_INTERFACE/services/AAAServiceHttpSoap11Endpoint", "UnsetCombinedBindResponseBean");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://broad.bean.soap.aaa.huawei.com/xsd", "online_info");
            cachedSerQNames.add(qName);
            cls = com.ztesoft.mobile.v2.broadBandWs.Online_info.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean actAccount(com.ztesoft.mobile.v2.broadBandWs.ActAccountBean actBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "actAccount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {actBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean setRoam(com.ztesoft.mobile.v2.broadBandWs.RoamBean roambean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "setRoam"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {roambean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean delUser(com.ztesoft.mobile.v2.broadBandWs.DelUserBean delUserBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "delUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {delUserBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.BackBean modPassword(com.ztesoft.mobile.v2.broadBandWs.ModBean modbean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "modPassword"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {modbean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.BackBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.BackBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.BackBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean addUser(com.ztesoft.mobile.v2.broadBandWs.AddUserBean addBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "addUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {addBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean deActAccount(com.ztesoft.mobile.v2.broadBandWs.DeActAccountBean deActBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "deActAccount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {deActBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryResponseBean ipAccountQuery(com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryBean ipAccountBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "ipAccountQuery"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ipAccountBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean modUser(com.ztesoft.mobile.v2.broadBandWs.ModUserBean modUserBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "modUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {modUserBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.AuthUserResponseBean authUser(com.ztesoft.mobile.v2.broadBandWs.AuthUserBean authUserBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "authUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {authUserBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.AuthUserResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.AuthUserResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.AuthUserResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.DeductFeeResponseBean deductFee(com.ztesoft.mobile.v2.broadBandWs.DeductFeeBean deductFeeBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "deductFee"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {deductFeeBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.DeductFeeResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.DeductFeeResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.DeductFeeResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPResponseBean queryUserByUNorIP(com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPBean queryUserByUNorIPBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "queryUserByUNorIP"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryUserByUNorIPBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean modOrder(com.ztesoft.mobile.v2.broadBandWs.ModOrderBean modOrderBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "modOrder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {modOrderBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.QueryOrderResponseBean queryOrder(com.ztesoft.mobile.v2.broadBandWs.QueryOrderBean queryOrderBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "queryOrder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryOrderBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryOrderResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryOrderResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.QueryOrderResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean resetAccount(com.ztesoft.mobile.v2.broadBandWs.ResetAccountBean resetAccountBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "resetAccount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {resetAccountBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.ResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.ResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindResponseBean unsetCombinedBind(com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindBean unsetCombinedBindBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "unsetCombinedBind"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {unsetCombinedBindBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBean queryUser(com.ztesoft.mobile.v2.broadBandWs.QueryUserBean queryUserBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "queryUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryUserBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailResponseBean queryAccessFail(com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailBean queryAccessFailBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "queryAccessFail"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryAccessFailBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean queryUserSession(com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionBean queryUserSessionBean) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service3A01.aaa.hnlt.server.pretreatment.sqm.ztesoft.com", "queryUserSession"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryUserSessionBean});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean) org.apache.axis.utils.JavaUtils.convert(_resp, com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
