/**
 * 
 */
package com.ztesoft.eoms.common.db.sql;

import com.ztesoft.eoms.common.helper.StringHelper;
import com.ztesoft.eoms.common.helper.ValidateHelper;

/**
 * @author dawn
 * 
 */
public class DeleteSqlBuilder implements ISqlBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildColumnSql(java.lang.String[])
	 */
	public String buildColumnSql(String[] columnPatternStrArr) {
		// TODO Auto-generated method stub

		return "";
	}

	/*
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildHeaderSql(java.lang.String)
	 */
	public String buildHeaderSql(String tableName) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer(16);
		buffer.append("DELETE  FROM ").append(tableName).append(" ");
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildWhereColumnSql(java.lang.String[])
	 */
	public String buildWhereColumnSql(String[] wherePatternStrArr) {
		// TODO Auto-generated method stub

		StringBuffer buffer = new StringBuffer(100);

		String _tempPatternStr = "";

		if (ValidateHelper.validateNotEmpty(wherePatternStrArr)) {

			buffer.append(" WHERE ");
			// &&:ID:id ||:ID:id
			String[] strArr = null;
			for (int i = 0; i < wherePatternStrArr.length; i++) {

				_tempPatternStr = wherePatternStrArr[i].toUpperCase();

				strArr = StringHelper.split(_tempPatternStr, ":");

				if (strArr.length == 3) {
					// 表示有关系运算符
					if (strArr[0].trim().equals("&&")) {
						buffer.append(" AND ");
					}
					if (strArr[0].trim().equals("||")) {
						buffer.append(" OR ");
					}

					buffer.append(strArr[1].trim());

				} else if (strArr.length == 2) {
					buffer.append(strArr[0]);
				} else {
					throw new IllegalArgumentException(
							"PATTERN STR FORMAT  FAILED! PLEASE CHECK.. WHERE COLUMN PATTERN  = "
									+ _tempPatternStr);
				}

				buffer.append(" ").append("= ?");

			}
		}
		return buffer.toString();
	}

}
