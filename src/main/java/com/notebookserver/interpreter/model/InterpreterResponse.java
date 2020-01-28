package com.notebookserver.interpreter.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InterpreterResponse {

	@ApiModelProperty(value = "The output of the code execution")
	private final String result;

	@ApiModelProperty(value = "The errors of execution")
	private final String errors;

	@ApiModelProperty(value = "The session id of the user")
	private final String sessionId;

}
