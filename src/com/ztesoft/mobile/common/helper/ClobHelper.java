package com.ztesoft.mobile.common.helper;

import java.io.InputStream;
import java.lang.reflect.Method;

public class ClobHelper {

	/** 03年5月3日
	 * 把clob类型的专成string；如果不是clob则返回原型
	 * @param in
	 * @return
	 */
	public static Object clobToString(Object in) {
		try {
			if ("oracle.sql.CLOB".equals(in.getClass().getName())) {
				String rtn = "";
				oracle.sql.CLOB clob = (oracle.sql.CLOB) in;
				InputStream input = clob.getAsciiStream();
				int len = (int) clob.length();
				byte[] by = new byte[len];
				int i;
				while (-1 != (i = input.read(by, 0, by.length))) {
					input.read(by, 0, i);
				}
				rtn = new String(by);
				rtn = clob.getSubString((long) 1, (int) clob.length());

				return rtn;
			} else if ("weblogic.jdbc.wrapper.Clob_oracle_sql_CLOB".equals(in
					.getClass().getName())) {
				String rtn = "";
				Method method = in.getClass().getMethod("getVendorObj",
						new Class[] {});
				oracle.sql.CLOB clob = (oracle.sql.CLOB) method.invoke(in);
				InputStream input = clob.getAsciiStream();
				int len = (int) clob.length();
				byte[] by = new byte[len];
				int i;
				while (-1 != (i = input.read(by, 0, by.length))) {
					input.read(by, 0, i);
				}
				rtn = new String(by);
				rtn = clob.getSubString((long) 1, (int) clob.length());

				return rtn;

			} else {
				return in;
			}
		} catch (Exception e) {
			return in;
		}

	}

}
