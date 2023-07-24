package com.ztesoft.mobile.v2.dao.common;

/**
 * User: heisonyee
 * Date: 13-3-13
 * Time: ÉÏÎç10:35
 */

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.Map;
import java.util.List;


public interface MobileFeedbackDAO extends BaseDAO {

    public void insert(Map paramMap) throws DataAccessException;

    //public void insertBatch(List paramMapList) throws DataAccessException;

    public void update(Map paramMap) throws DataAccessException;

    //public void updateBatch(List paramMapList) throws DataAccessException;

    public void delete(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap) throws DataAccessException;

    public List selAll(Map paramMap) throws DataAccessException;
}