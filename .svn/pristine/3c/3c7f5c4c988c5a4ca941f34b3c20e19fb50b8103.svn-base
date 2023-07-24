package com.ztesoft.mobile.common.helper;

import java.util.regex.*;

public class RegularHelper {
	/**
	 * 正则表达式匹配
	 * 
	 * @param match
	 *            要匹配的字符串
	 * @param regex
	 *            正则表达式
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
