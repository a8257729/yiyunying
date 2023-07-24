/**
 * AAAServiceSoap11BindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.mobile.v2.broadBandWs;

public interface AAAServiceSoap11BindingImpl extends java.rmi.Remote {
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean actAccount(com.ztesoft.mobile.v2.broadBandWs.ActAccountBean actBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean setRoam(com.ztesoft.mobile.v2.broadBandWs.RoamBean roambean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean delUser(com.ztesoft.mobile.v2.broadBandWs.DelUserBean delUserBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.BackBean modPassword(com.ztesoft.mobile.v2.broadBandWs.ModBean modbean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean addUser(com.ztesoft.mobile.v2.broadBandWs.AddUserBean addBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean deActAccount(com.ztesoft.mobile.v2.broadBandWs.DeActAccountBean deActBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryResponseBean ipAccountQuery(com.ztesoft.mobile.v2.broadBandWs.IPAccountQueryBean ipAccountBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean modUser(com.ztesoft.mobile.v2.broadBandWs.ModUserBean modUserBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.AuthUserResponseBean authUser(com.ztesoft.mobile.v2.broadBandWs.AuthUserBean authUserBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.DeductFeeResponseBean deductFee(com.ztesoft.mobile.v2.broadBandWs.DeductFeeBean deductFeeBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPResponseBean queryUserByUNorIP(com.ztesoft.mobile.v2.broadBandWs.QueryUserByUNorIPBean queryUserByUNorIPBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean modOrder(com.ztesoft.mobile.v2.broadBandWs.ModOrderBean modOrderBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.QueryOrderResponseBean queryOrder(com.ztesoft.mobile.v2.broadBandWs.QueryOrderBean queryOrderBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.ResponseBean resetAccount(com.ztesoft.mobile.v2.broadBandWs.ResetAccountBean resetAccountBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindResponseBean unsetCombinedBind(com.ztesoft.mobile.v2.broadBandWs.UnsetCombinedBindBean unsetCombinedBindBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.QueryUserResponseBean queryUser(com.ztesoft.mobile.v2.broadBandWs.QueryUserBean queryUserBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailResponseBean queryAccessFail(com.ztesoft.mobile.v2.broadBandWs.QueryAccessFailBean queryAccessFailBean) throws java.rmi.RemoteException;
    public com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean queryUserSession(com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionBean queryUserSessionBean) throws java.rmi.RemoteException;
}
