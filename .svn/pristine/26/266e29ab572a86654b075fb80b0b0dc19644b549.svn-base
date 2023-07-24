package com.ztesoft.eoms.im.dao;
import java.util.Map;
import java.util.List;
import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.exception.DataAccessException;
public interface ImChatLogDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selUnReadMsg(Map paramMap)throws DataAccessException;
	public void updateReadState(Map paramMap) throws DataAccessException;
	public Map selChatLog(Map paramMap)throws DataAccessException;
	public void insertGroupLog(int senderId,int receiveGroupId,String message);
	public void updateMsgReadState(Map paramMap) throws DataAccessException ;
	
}

