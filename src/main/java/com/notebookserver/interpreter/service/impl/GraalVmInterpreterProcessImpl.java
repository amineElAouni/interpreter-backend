package com.notebookserver.interpreter.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notebookserver.interpreter.exception.InterpreterException;
import com.notebookserver.interpreter.model.ExecutionRequest;
import com.notebookserver.interpreter.model.ExecutionResponse;
import com.notebookserver.interpreter.model.InterpreterRequest;
import com.notebookserver.interpreter.model.InterpreterResponse;
import com.notebookserver.interpreter.service.GraalVmInterpreterProcess;
import com.notebookserver.interpreter.service.GraalVmInterpreterService;

@Service
public class GraalVmInterpreterProcessImpl implements GraalVmInterpreterProcess {

	@Autowired
	private GraalVmInterpreterService graalVmInterpreterService;

	@Override
	public InterpreterResponse executeInterpreterProcess(InterpreterRequest interpreterRequest, HttpSession httpSession) throws InterpreterException {
		ExecutionRequest executionRequest = graalVmInterpreterService.parseInterpreterRequest(interpreterRequest);
		String sessionId = interpreterRequest.getSessionId() != null ? interpreterRequest.getSessionId() : httpSession.getId();
		executionRequest.setSessionId(sessionId);
		ExecutionResponse executionResponse = graalVmInterpreterService.execute(executionRequest);
		InterpreterResponse interpreterResponse = InterpreterResponse.builder()
				.result(executionResponse.getOutput())
				.errors(executionResponse.getErrors())
				.sessionId(sessionId)
				.build();
		return interpreterResponse;
	}
}
