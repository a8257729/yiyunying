package com.ztesoft.eoms.common.idproduct;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.CommonError;

import com.ztesoft.eoms.common.idproduct.stragety.IdGetStragety;
import com.ztesoft.eoms.common.idproduct.stragety.IdGetStragetyFactory;

/**
 *
 * <p>Title: 电子运维项目</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: ZTESoft</p>
 *
 * @author dawn
 * @version 1.0
 */
public
        class SequencePool {


    private static String confFilePath =
            "D:\\PROJECT\\OSS_IOM_V2\\SOURCE\\EOMSPROJV2\\classes\\sequence.properties";

    private static IdGetStragety idGetStragety = null;
    /**独例*/
    private static SequencePool instance = null;


    /**
     * 私有构造子.加载路径
     */
    private SequencePool() throws UOSException {
        this(SequencePool.confFilePath);
    }

    /**
     * 私有构造子，加载外部路径
     * @param confFilePath String
     */

    private SequencePool(String confFilePath) throws UOSException{

        SequencePool.confFilePath = confFilePath;
        idGetStragety = IdGetStragetyFactory.getIdGetStragety(SequencePool.
                confFilePath);
    }


    /**
     * 进行刷新
     * @param confFilePath String
     * @throws IOException
     * @throws SQLException
     */
    public void reload() throws UOSException {

        idGetStragety.refresh();

    }


    /**
     * 获取实例
     * @return SequencePool
     * @throws Exception
     */
    public static SequencePool newInstance() throws UOSException {

        if (instance == null) {
            synchronized (SequencePool.class) {
                if (instance == null) {
                    instance = new SequencePool();

                }

            }

        }

        return instance;
    }

    /**
     * 获取实例
     * @param confFilePath String
     * @return SequencePool
     * @throws Exception
     */
    public static SequencePool newInstance(String confFilePath) throws
            Exception {
        //如果配置文件不存在，则返回。
        File conFile = new File(confFilePath);
        if (!conFile.exists()) {
            throw new IOException(confFilePath + " not exists!");
        }
        if (instance == null) {
            synchronized (SequencePool.class) {
                if (instance == null) {
                    instance = new SequencePool(confFilePath);

                }

            }

        }
        return instance;
    }


    /**
     * 获取下一个值
     * @param key String
     * @return long
     * @throws UOSException
     */

    public long nextId(String key, int addNum) throws UOSException {
        if (addNum < 0) {
            throw new UOSException(new CommonError(
                    "Increase quantity being able to not be a minus!"));
        }
        return idGetStragety.nextId(key, addNum);
    }


    /**
     * 测试函数
     * @param args String[]
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
    
          SequencePool sequencePool = SequencePool.newInstance();
         sequencePool.reload();
        //测试刷新函数
        for (int i = 0; i < 100; i++) {
            new Thread(new TestThread()).start();
        }

    }
}


/**
 * 测试线程
 * <p>Title: 电子运维项目</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: ZTESoft</p>
 *
 * @author not attributable
 * @version 1.0
 */
class TestThread implements Runnable {
    public void run() {
        long id = -1;
        try {

            SequencePool sequencePool = SequencePool.newInstance();

            id = sequencePool.nextId("FAULT_ORDER", 1);

        } catch (Exception e) {

        }
        //System.out.println(Thread.currentThread().getName() + "====" + id);
    }

}
