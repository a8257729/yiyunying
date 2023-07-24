package com.ztesoft.mobile.v2.dao.menu;

import com.zterc.uos.oaas.exception.OAASException;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MobilePostPrivDAO extends BaseDAO{
	public void insert(Map paramMap) throws DataAccessException;

    public void update(Map paramMap) throws DataAccessException;

    public void delete(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap) throws DataAccessException;

    public List selAll(Map paramMap) throws DataAccessException;
    public List selByPostId(Map paramMap) throws DataAccessException;
    public Map getAllPostPrivData(int postId)throws Exception ;
    public Map getAllSubPostPrivData(int postId)throws Exception ;
    public List getPrivCode(String strpriv) throws DataAccessException;
    public void removeJobPrivs(int postId);
    public void addJobPrivsBath(String[] roleIds, int postId, String[] grantPrivs, String[] selectedPrivs);
    public void updateJobPrivs(String[] roleIds, int postId,String[] grantPrivs, String[] selectedPrivs) throws OAASException;
}
