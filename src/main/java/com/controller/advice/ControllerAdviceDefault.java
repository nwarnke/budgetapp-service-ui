package com.controller.advice;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerAdviceDefault
{
	private static final Logger	LOG	= LoggerFactory.getLogger(ControllerAdviceDefault.class);

	@Autowired
	private MessageSource	    messageSource;

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ResponseEntity<ClientError> handleSystemException(final RuntimeException ex)
	{
		LOG.error(ex.getMessage(), ex);

		final ClientError error = new ClientError(getI18n("error.systemTitle"), getI18n("error.system"));

		return new ResponseEntity<ClientError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private String getI18n(final String message)
	{
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(message, null, locale);
	}
}
