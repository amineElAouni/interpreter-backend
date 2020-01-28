package com.notebookserver.interpreter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExecutionResponse {

	private final String output;

	private final String errors;

}
