package com.chorokchorok.saeroksaerok.global.util;

import java.util.regex.Pattern;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Preconditions {

	public static void checkNotNull(Object value, String message) {
		if (value == null) {
			//TODO 예외처리
		}
	}

	public static void checkNotNullForSerializer(Object target, String name) {
		if (target == null) {
			//TODO 예외처리
		}
	}

	public static void checkPatternMatches(Pattern pattern, String value) {
		if (!pattern.matcher(value).find()) {
			//TODO 예외처리
		}
	}

	public static void checkLength(int minLength, int maxLength, String value, String type) {
		if (value.length() < minLength || value.length() > maxLength) {
			//TODO 예외처리
		}
	}
}
