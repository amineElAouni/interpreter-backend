package com.notebookserver.interpreter.service;

import com.notebookserver.interpreter.exception.InterpreterException;
import com.notebookserver.interpreter.exception.InterpreterRequestParsingException;
import com.notebookserver.interpreter.model.ExecutionRequest;
import com.notebookserver.interpreter.model.ExecutionResponse;
import com.notebookserver.interpreter.model.InterpreterRequest;

public interface GraalVmInterpreterService {

	ExecutionRequest parseInterpreterRequest(InterpreterRequest request) throws InterpreterRequestParsingException;

	ExecutionResponse execute(ExecutionRequest request) throws InterpreterException;

}
