/**
 * 
 */
package com.ztesoft.mobile.common.db.sql;

/**
 * @author dawn
 *
 */
public class InsertSqlBuilder implements ISqlBuilder{

	/* (non-Javadoc)
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildColumnSql(java.lang.String[])
	 */
	public String buildColumnSql(String[] columnPatternStrArr) {
		// TODO Auto-generated method stub
       StringBuffer buffer = new StringBuffer(100);
       String _tempPatternStr = "";
		for (int i = 0; i < columnPatternStrArr.length; i++) {
			_tempPatternStr = columnPatternStrArr[i].toUpperCase();
			//validate
			if (columnPatternStrArr[i].indexOf(":") == -1) {
				throw new IllegalArgumentException(
						"PATTERN STR FORMAT  FAILED! PLEASE CHECK..");
			}
			
			buffer.append(_tempPatternStr.substring(0,
					_tempPatternStr.indexOf(":")).toUpperCase());
			buffer.append(",");
		}
		if(buffer.charAt(buffer.length()-1)==','){
		buffer = buffer.deleteCharAt(buffer.length() - 1);
		}
		buffer.append(")");
		buffer.append("  VALUES (");
		for (int j = 0; j < columnPatternStrArr.length; j++) {
			buffer.append("?");
			buffer.append(",");
		}
		
		if(buffer.charAt(buffer.length()-1)==','){
			buffer = buffer.deleteCharAt(buffer.length() - 1);
			}
		
		buffer.append(")");

		return buffer.toString();

	}

	/* (non-Javadoc)
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildHeaderSql(java.lang.String)
	 */
	public String buildHeaderSql(String tableName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(16);
		sql.append("INSERT INTO ").append(tableName).append("(");
		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see com.ztesoft.eoms.common.db.sql.ISqlBuilder#buildWhereColumnSql(java.lang.String[])
	 */
	public String buildWhereColumnSql(String[] wherePatternStrArr) {
		// TODO Auto-generated method stub
		return "";
	}

}
