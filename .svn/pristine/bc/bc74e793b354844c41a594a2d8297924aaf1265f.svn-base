/**
 * 
 */
package com.ztesoft.mobile.common.db.sql;

import com.ztesoft.mobile.common.helper.StringHelper;
import com.ztesoft.mobile.common.helper.ValidateHelper;

/**
 * @author dawn
 * 
 */
public class UpdateSqlBuilder implements ISqlBuilder {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildHeaderSql(java.lang.String)
	 */
	public String buildHeaderSql(String tableName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE ").append(tableName).append(" SET ");
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildColumnSqlForUpdate(java.lang.String[])
	 */
	public String buildColumnSql(String[] columnPatternStrArr) {
		
		
		StringBuffer buffer = new StringBuffer(100);
		String _tempPatternStr = "";
		for (int i = 0; i < (columnPatternStrArr.length); i++) {

			_tempPatternStr = columnPatternStrArr[i].toUpperCase();

			if (_tempPatternStr.indexOf(":") == -1) {

				throw new IllegalArgumentException(
						"PATTERN STR FORMAT  FAILED! PLEASE CHECK.. COLUMN PATTERN = "
								+ _tempPatternStr);

			}
			buffer.append(_tempPatternStr.substring(0, _tempPatternStr
					.indexOf(":")));

			buffer.append(" = ?").append(",");

		}
		if (buffer.charAt(buffer.length() - 1) == ',') {

			buffer = buffer.deleteCharAt(buffer.length() - 1);

		}
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildWhereColumnSql(java.lang.String[])
	 */
	public String buildWhereColumnSql(String[] wherePatternStrArr) {
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
