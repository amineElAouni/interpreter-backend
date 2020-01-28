package com.notebookserver.interpreter.model;

import java.io.ByteArrayOutputStream;

import org.graalvm.polyglot.Context;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExecutionContext {

	private ByteArrayOutputStream outputStream;

	private ByteArrayOutputStream errorsStream;

	private Context context;

	private boolean timedOut = false;

	public ExecutionContext(ByteArrayOutputStream outputStream, ByteArrayOutputStream errorsStream, Context context) {
		this.outputStream = outputStream;
		this.errorsStream = errorsStream;
		this.context = context;
	}

	public String getOutput() {
		String out = this.outputStream.toString();
		this.outputStream.reset();
		return out;
	}

	public String getErrors() {
		String out = this.errorsStream.toString();
		this.errorsStream.reset();
		return out;
	}

}
