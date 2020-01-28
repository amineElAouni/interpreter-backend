package com.notebookserver.interpreter.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		setterVisibility = JsonAutoDetect.Visibility.NONE)
public class InterpreterRequest {

	@ApiModelProperty(required = true, value = "The piece of code to execute")
	@NotBlank(message = "Request code is required")
	@Pattern(regexp = "%(\\w+)\\s+(.*)", message = "Invalid request code format, your code should follow the given format: %python code")
	private final String code;

	@ApiModelProperty(value = "The session id of the user")
	private final String sessionId;

}
