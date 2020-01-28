package com.notebookserver.interpreter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Can not parsing request")
public class InterpreterRequestParsingException extends InterpreterException {

}
