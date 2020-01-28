package com.notebookserver.interpreter.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.notebookserver.interpreter.InterpreterApplication;
import com.notebookserver.interpreter.exception.TimeOutException;
import com.notebookserver.interpreter.model.ExecutionRequest;
import com.notebookserver.interpreter.model.ExecutionResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterpreterApplication.class)
public class GraalVmInterpreterServiceImplTest {

	@Autowired
	private GraalVmInterpreterServiceImpl graalVmInterpreterService;

	@Test
	public void should_return_hello_world() {
		String helloWorld = "Hello World";
		ExecutionRequest request = ExecutionRequest.builder().build();
		request.setLanguage("python");
		request.setCode("console.log('"+ helloWorld + "');");
		request.setSessionId("mySessionId");

		ExecutionResponse response = graalVmInterpreterService.execute(request);
		assertTrue(response.getErrors().isEmpty());
		assertEquals(helloWorld + "\n", response.getOutput());
	}

	@Test
	public void should_return_error_when_undefined_variable() {
		ExecutionRequest request = ExecutionRequest.builder().build();
		request.setLanguage("js");
		request.setCode("console.log(a)");
		request.setSessionId("mySessionId");

		ExecutionResponse response = graalVmInterpreterService.execute(request);
		assertTrue(response.getOutput().isEmpty());
		assertEquals("ReferenceError: a is not defined", response.getErrors());
	}

	@Test(expected = TimeOutException.class)
	public void should_return_infinite_loop() {
		ExecutionRequest request = ExecutionRequest.builder().build();
		request.setLanguage("python");
		request.setCode("while True;");
		request.setSessionId("mySessionId");

		graalVmInterpreterService.execute(request);
	}

}
