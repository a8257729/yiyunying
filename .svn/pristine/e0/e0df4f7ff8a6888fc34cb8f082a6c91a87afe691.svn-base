package com.ztesoft.eoms.common.idproduct.stragety;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zterc.uos.UOSException;
import com.ztesoft.eoms.common.helper.ValidateHelper;
import com.ztesoft.eoms.common.idproduct.InitIdDto;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public abstract class AbstractIdGetStragety implements IdGetStragety {


    public AbstractIdGetStragety(String confFilePath) throws UOSException {

        if (ValidateHelper.validateNotNull(sequeMap)) {
            sequeMap.clear();
            sequeMap = null;
        }
        if (!ValidateHelper.validateNotNull(sequeMap)) {
            sequeMap = new HashMap(100);
        }
        this.confFilePath = confFilePath;
        refresh();
    }

    protected String confFilePath = "";

    protected Map sequeMap = new HashMap(100);

    protected Map keyMap = new HashMap(100);

    /**对象锁*/
    private static Map locks = new HashMap(100);
    /**
     * 获得对象锁，针对内存中的单个Sequence对象进行锁定。
     * @param key String
     * @return Object
     */
    protected Object getLock(String key) {
        Object lock = locks.get(key);
        if (lock == null) {
            synchronized (locks) {
                lock = locks.get(key);
                if (!ValidateHelper.validateNotNull(lock)) { //双重检查
                    lock = key;
                    locks.put(key, key);
                }
            }
        }

        return lock;
    }


    public void refresh() throws UOSException {

        initInitIdDto();

    }

    /**
     * getInitIdDto
     *
     * @return InitIdDto[]
     */
    protected abstract InitIdDto[] initInitIdDto() throws UOSException;

    protected abstract InitIdDto getInitIdDto(String tableName) throws
            UOSException;


    public InitIdDto[] qryInitIdDto() throws UOSException {
        List list = new ArrayList();

        if (ValidateHelper.validateNotNull(sequeMap) && !sequeMap.isEmpty()) {
            for (Iterator entryIter = sequeMap.entrySet().iterator();
                                      entryIter.hasNext(); ) {
                Entry entry = (Entry) entryIter.next();
                if (entry != null) {
                    list.add((InitIdDto) entry.getValue());
                }
            }

        }
        return (InitIdDto[]) list.toArray(new InitIdDto[list.size()]);
    }


}
