package com.chorokchorok.saeroksaerok.global.error.exception;

public abstract class SaerokSaerokRuntimeException extends RuntimeException {

	private final String messageKey;
	private final Object[] params;

	public SaerokSaerokRuntimeException(String messageKey, Object[] params) {
		this.messageKey = messageKey;
		this.params = params;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getParams() {
		return params;
	}
}
