package com.ztesoft.mobile.v2.controller.common;

import oracle.jdbc.internal.OracleTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CallableStatementQueryUpdatePon implements CallableStatementCallback<Map<String, String>> {

    private String eqpId;

    public CallableStatementQueryUpdatePon(String eqpId) {
        this.eqpId = eqpId;

    }

    public Map<String, String> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
        callableStatement.registerOutParameter(1,OracleTypes.VARCHAR);
        callableStatement.setString(2, eqpId);


        callableStatement.execute();
        Map<String, String> map = new HashMap();
        map.put("return", callableStatement.getString(1).replaceAll("\\\\",""));
        return map;
    }
}
