package com.ztesoft.mobile.v2.controller.common;

import oracle.jdbc.internal.OracleTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CallableStatementA implements CallableStatementCallback<Map<String, String>> {
    private String orderId;

    public CallableStatementA(String orderId){
        this.orderId = orderId;
    }


    public Map<String, String> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
        callableStatement.setString(1, orderId);
        callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
        callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
        callableStatement.execute();
        Map<String, String> map = new HashMap();
        map.put("a", callableStatement.getString(2));
        map.put("b", callableStatement.getString(3));
        return map;
    }
}
