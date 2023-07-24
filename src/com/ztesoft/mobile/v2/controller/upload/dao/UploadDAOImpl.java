package com.ztesoft.mobile.v2.controller.upload.dao;

import com.ztesoft.android.dao.CeshiDataDAO;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;
import net.sf.json.JSONObject;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadDAOImpl extends BaseDAOImpl implements UploadDAO {


	public int update(String staffId, String photo) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			String sql ="update UOS_STAFF_PHOTO_PATH set photo_path = ? where staff_id=? and TRAIN_RECODE_PATH is null and EXAM_RECODE_PATH is null and COMMITMENT_RECODE_PATH is null and PERFORMANCE_PATH is null";
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,photo);
			preparedStatement.setString(2,staffId);
			preparedStatement.executeUpdate();
			return 1;
		} catch (Exception e) {

			return 0;
		} finally {
			if(null!=preparedStatement){
				preparedStatement.close();
			}
			if (null != conn) {
				conn.close();
			}
		}
	}

	public int insert(String staffId, String photo) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			String sql ="insert into UOS_STAFF_PHOTO_PATH(STAFF_ID,PHOTO_PATH) values(?,?)";
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,staffId);
			preparedStatement.setString(2,photo);
			preparedStatement.executeUpdate();
			return 1;
		} catch (Exception e) {

			return 0;
		} finally {
			if(null!=preparedStatement){
				preparedStatement.close();
			}
			if (null != conn) {
				conn.close();
			}
		}
	}
}

