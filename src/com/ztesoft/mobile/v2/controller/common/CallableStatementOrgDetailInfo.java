package com.ztesoft.mobile.v2.controller.common;

import oracle.jdbc.internal.OracleTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CallableStatementOrgDetailInfo implements CallableStatementCallback<Map<String, String>> {

    private String in_date;
    private String in_date_type;
    private Integer orgId;
    private Integer staffId;
    public CallableStatementOrgDetailInfo(String in_date, String in_date_type, Integer orgId,Integer staffId) {
        this.in_date = in_date;
        this.in_date_type = in_date_type;
        this.orgId = orgId;
        this.staffId = staffId;
    }

    public Map<String, String> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
        callableStatement.registerOutParameter(1,OracleTypes.VARCHAR);
        callableStatement.setString(2, in_date);
        callableStatement.setString(3, in_date_type);
        callableStatement.setInt(4, orgId);
        callableStatement.setInt(5, staffId);

        callableStatement.execute();
        Map<String, String> map = new HashMap();
        map.put("return", callableStatement.getString(1).replaceAll("\\\\",""));
        return map;
    }
}
