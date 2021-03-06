package com.andrew.common.text;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.nio.charset.Charset;

/**
 * 补充String常用方法
 *
 * @author Andrew
 * @date 2020/3/20
 */
public class StringUtil {
	private StringUtil() {

	}

	/**
	 * {@linkplain String#format(String, Object...)} 需要占位符%s的数量与Object...的数量一致，否则会报错。
	 * 而{@linkplain MessageFormatter#arrayFormat(String, Object[])} 没有这个限制，且效率高于{@linkplain String#format(String, Object...)}
	 *
	 * @param messagePattern 占位符使用"{}"，而非"%s"
	 * @param args
	 * @return
	 */
	public static String format(String messagePattern, Object... args) {
		if (StringUtils.isBlank(messagePattern)) {
			return StringUtils.EMPTY;
		}

		if (null == args || args.length == 0) {
			return messagePattern;
		}

		return MessageFormatter.arrayFormat(messagePattern, args).getMessage();
	}

	/**
	 * JDK8 String缺少replace last
	 *
	 * @param s
	 * @param sub
	 * @param with
	 * @return
	 */
	public static String replaceLast(String s, char sub, char with) {
		if (s == null) {
			return null;
		}

		int index = s.lastIndexOf(sub);
		if (index == -1) {
			return s;
		}
		char[] str = s.toCharArray();
		str[index] = with;
		return new String(str);
	}

	/**
	 * 将字符串转二进制(可指定字符集)
	 * 如果字符串中存在中文，必须指定字符集，否则可能由于操作系统配置不一致得到二进制结果不一样
	 */
	public static String toBinary(String s, Charset charset, String separator) {
		byte[] bytes = charset == null ? s.getBytes() : s.getBytes(charset);
		// 分隔符可以是空格
		boolean isFill = StringUtils.isNotEmpty(separator);
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}

			if (isFill) {
				binary.append(separator);
			}
		}

		return binary.toString();
	}
}
