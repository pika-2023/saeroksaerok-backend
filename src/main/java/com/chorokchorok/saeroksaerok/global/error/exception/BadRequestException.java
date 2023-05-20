package com.chorokchorok.saeroksaerok.global.error.exception;

public class BadRequestException extends SaerokSaerokRuntimeException {

	protected static final String MESSAGE_KEY = "error.bad.request";

	public BadRequestException(String detailKey, Object... params) {
		super(MESSAGE_KEY + "." + detailKey, params);
	}
}
