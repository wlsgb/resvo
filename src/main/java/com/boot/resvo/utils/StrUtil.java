package com.boot.resvo.utils;

import org.springframework.stereotype.Component;

@Component
public class StrUtil {

	/**
	 * String 값이 비어있는 경우
	 * @param str String 값
	 * @return String 값이 비어있는 경우 true 반환
	 */
	public static boolean ifNull(String str) {
		// 체크
		return str == null || str.equals("") || str.equals("null");
	}
}
