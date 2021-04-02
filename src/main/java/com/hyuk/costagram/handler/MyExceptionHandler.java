package com.hyuk.costagram.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hyuk.costagram.handler.exception.NotFoundUserException;
import com.hyuk.costagram.web.dto.CommonRespDto;

@RestControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler
	public CommonRespDto<String> notFoundUserEx(NotFoundUserException e) {
		return new CommonRespDto<>(-1, e.getMessage());
	}
}
