package com.aofmath.blog.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/** The Constant PATTERN_SYSDATE. */
	public static final String PATTERN_SYSDATE_YYYY = "yyyy";

	/** The Constant PATTERN_SYSDATE. */
	public static final String PATTERN_SYSDATE_MM = "MM";

	/** The Constant PATTERN_SYSDATE. */
	public static final String PATTERN_SYSDATE_DD = "dd";

	public static final String PATTERN_SYSDATE_YYYYMMDDHH24MISS = "yyyyMMddHHmmss";
	
	/**
	 * 문자열의 값이 일자값인지 검증
	 *
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @return 일자값이면 true, 아니면 false
	 */
	public static DateFormat milliFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	
	/**
	 * 기본패턴('yyyyMMdd') 날짜형 시스템일자를 구함
	 *
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 기본형('yyyyMMdd')의 시스템 일자
	 */
	public static String getToday() {
		return getToday("yyyyMMdd");
	}
	
	/**
	 * 주어진 패턴 날짜형 시스템일자를 구함
	 *
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 시스템 일자
	 */
	public static String getToday(String pattern) {
		return getDateString(new Date(), pattern);
	}
	
	/**
	 * 주어진 Date 객체를 이용하여 주어진 패턴 날짜형의 문자열을 구함.
	 *
	 * @param date
	 *            원하는 일자의 Date 객체
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
}
