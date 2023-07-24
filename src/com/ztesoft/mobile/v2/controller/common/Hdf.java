package com.ztesoft.mobile.v2.controller.common;


import com.ztesoft.eoms.common.configure.ConfigMgr;
import com.ztesoft.eoms.common.helper.Base64It;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Hdf extends BasicDataSource {

    public DataSource getDataSource() {

        DataSource ds = null;
        super.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        super.setUrl("jdbc:oracle:thin:@192.168.101.11:1521/orazte");
        super.setUsername("hnlt_gk");
        super.setPassword("ZTE$soft_gk");
        try {
            ds = super.createDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }
}
