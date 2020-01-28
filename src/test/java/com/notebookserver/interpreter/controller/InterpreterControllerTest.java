package com.notebookserver.interpreter.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notebookserver.interpreter.InterpreterApplication;
import com.notebookserver.interpreter.model.InterpreterRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterpreterApplication.class)
@AutoConfigureMockMvc
public class InterpreterControllerTest {

	private static final String END_POINT = "/execute";

	@Autowired
	private MockMvc mvc;

	@Test
	public void should_return_bad_request_when_unsupported_language() throws Exception {
		InterpreterRequest request = InterpreterRequest.builder()
				.code("%ruby puts \"Hello World\";")
				.build();
		String message = mvc.perform(
				post(END_POINT)
						.content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getErrorMessage();

		assertEquals("Language Not Supported", message);
	}

	@Test
	public void should_return_bad_request_when_invalid_interpreter_request() throws Exception {
		InterpreterRequest request = InterpreterRequest.builder()
				.code("invalid request code")
				.build();
		mvc.perform(
				post(END_POINT)
						.content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void should_return_bad_request_when_invalid_interpreter_request2() throws Exception {
		InterpreterRequest request = InterpreterRequest.builder()
				.code("% python my code")
				.build();
		mvc.perform(
				post(END_POINT)
						.content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	public static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}

}
