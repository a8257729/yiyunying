package com.ztesoft.eoms.common.idproduct;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 *
 */
public class TransactionManager {

  private UserTransaction tx = null;

  /**
   * 是否自己管理事务（true：自己提交、回滚事务；false：当前事务是嵌套事务，自己不进行事务管理)
   */
  private boolean transactionBySelf = true;

  private TransactionManager(){}

  public static TransactionManager getInstance() throws Exception {
    Context ic = new InitialContext();
    UserTransaction _tx = (UserTransaction) ic.lookup("java:comp/UserTransaction"); //resin服务器中取得UserTransaction

    TransactionManager tm = new TransactionManager();
    tm.tx = _tx;
    if(_tx.getStatus() != Status.STATUS_NO_TRANSACTION)
    tm.transactionBySelf = false;

    return tm;
  }

  public void beginTransaction() throws Exception {
    if (tx == null)
    return;
    if(tx.getStatus() != Status.STATUS_NO_TRANSACTION)
      transactionBySelf = false;

    if (transactionBySelf) {
      tx.begin();
      //System.out.println("TransactionManager:打开事务！");
    }//else {
      //System.out.println("TransactionManager:已经处于事务中了！所以没有再次打开事务！");
    //}
  }

  public void commitTransaction() throws Exception {
    if (tx == null)
      return;
    if (transactionBySelf) {
      tx.commit();
      //System.out.println("TransactionManager:提交事务！");
    }// else {
      //System.out.println("TransactionManager:已经处于事务中了！所以没有提前提交事务！");
    //}
  }

  public void rollbackTransaction() throws Exception {
    if (tx == null)
      return;
    if (transactionBySelf) {
      tx.rollback();
      //System.out.println("TransactionManager:回滚事务！");
    }// else {
      //System.out.println("TransactionManager:已经处于事务中了！所以没有提前回滚事务！");
    //}
  }
}
