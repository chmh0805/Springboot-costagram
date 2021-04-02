package com.hyuk.costagram.handler.exception;

public class NotFoundUserException extends IllegalArgumentException{
	public NotFoundUserException(String msg) {
		super(msg);
	}
}
