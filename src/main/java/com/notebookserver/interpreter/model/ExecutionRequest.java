package com.notebookserver.interpreter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionRequest {

	private String language;

	private String code;

	private String sessionId;

}
