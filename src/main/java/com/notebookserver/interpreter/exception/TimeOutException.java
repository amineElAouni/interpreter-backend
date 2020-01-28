package com.notebookserver.interpreter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Request take too long")
public class TimeOutException extends InterpreterException {

}
