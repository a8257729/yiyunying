package com.ztesoft.mobile.v2.controller.upload.dao;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

public interface UploadDAO {

    public int update(String staffId,String photo) throws SQLException;

    public int insert(String staffId,String photo) throws SQLException;


}
