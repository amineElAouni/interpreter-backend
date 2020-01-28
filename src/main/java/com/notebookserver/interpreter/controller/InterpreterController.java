package com.notebookserver.interpreter.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notebookserver.interpreter.exception.InterpreterException;
import com.notebookserver.interpreter.model.InterpreterRequest;
import com.notebookserver.interpreter.model.InterpreterResponse;
import com.notebookserver.interpreter.service.GraalVmInterpreterProcess;

@Validated
@RestController
public class InterpreterController {

	@Autowired
	private GraalVmInterpreterProcess graalVmInterpreterProcess;

	@PostMapping("/execute")
	public ResponseEntity<InterpreterResponse> execute(@Valid @RequestBody InterpreterRequest interpreterRequest, HttpSession httpSession)
			throws InterpreterException {
		InterpreterResponse interpreterResponse = graalVmInterpreterProcess.executeInterpreterProcess(interpreterRequest, httpSession);
		return ResponseEntity.ok(interpreterResponse);
	}

}
