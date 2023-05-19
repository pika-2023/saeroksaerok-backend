package com.chorokchorok.saeroksaerok.global.util;

import java.util.regex.Pattern;

import com.chorokchorok.saeroksaerok.global.error.exception.InputValidationException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Preconditions {

	public static void checkNotNull(Object value, String message) {
		if (value == null) {
			throw new NullPointerException(message);
		}
	}

	public static void checkPatternMatches(Pattern pattern, String value) {
		if (!pattern.matcher(value).find()) {
			throw new InputValidationException("pattern", "email");
		}
	}

	public static void checkLength(int minLength, int maxLength, String value, String type) {
		if (value.length() < minLength || value.length() > maxLength) {
			throw new InputValidationException("length", type, minLength, maxLength);
		}
	}
}
