package com.ztesoft.mobile.common.helper;

import java.util.regex.*;

public class RegularHelper {
	/**
	 * ������ʽƥ��
	 * 
	 * @param match
	 *            Ҫƥ����ַ���
	 * @param regex
	 *            ������ʽ
	 * @return
	 */
	public static final boolean Match(String match, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(match);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
