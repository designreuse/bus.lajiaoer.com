package com.demo.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static String randomNumberString(int strLength) {
		StringBuilder sb = new StringBuilder(strLength);
		for (int i = 0; i < strLength; i++) {
			double r = Math.random();
			int n = (int) (r * 10.0);
			sb.append(n);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String p = randomNumberString(0);
		System.out.println(p);
	}
}
