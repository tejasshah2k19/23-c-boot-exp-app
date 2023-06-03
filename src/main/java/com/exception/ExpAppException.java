package com.exception;

import com.enums.ExpAppError;

public class ExpAppException extends Exception {

	// errorCode
	// msg
	public ExpAppException(ExpAppError erroCode) {
		super();
	}
}
