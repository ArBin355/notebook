package com.linglifu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 获取字符串格式的当前日期
	 * 
	 * @return yyyy-MM-dd HH:mm
	 */
	public static String currentDate2String() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
	}
}
