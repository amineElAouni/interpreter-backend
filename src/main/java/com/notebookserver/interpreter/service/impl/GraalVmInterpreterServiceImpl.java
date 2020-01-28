package com.notebookserver.interpreter.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notebookserver.interpreter.exception.InterpreterException;
import com.notebookserver.interpreter.exception.InterpreterRequestParsingException;
import com.notebookserver.interpreter.exception.LanguageNotSupportedException;
import com.notebookserver.interpreter.exception.TimeOutException;
import com.notebookserver.interpreter.model.ExecutionContext;
import com.notebookserver.interpreter.model.ExecutionRequest;
import com.notebookserver.interpreter.model.ExecutionResponse;
import com.notebookserver.interpreter.model.InterpreterRequest;
import com.notebookserver.interpreter.service.GraalVmInterpreterService;

@Service
public class GraalVmInterpreterServiceImpl implements GraalVmInterpreterService {

	@Value("${interpreter.execution.timeout}")
	private Long executionTimeOut;

	private static final String WHITE_SPACE = " ";

	private static final String INTERPRETER_LANGUAGE = "python";

	private Map<String, ExecutionContext> sessionContexts = new ConcurrentHashMap<>();

	@Override
	public ExecutionRequest parseInterpreterRequest(InterpreterRequest request) throws InterpreterRequestParsingException {
		final String code = request.getCode().substring(request.getCode().indexOf(WHITE_SPACE) + 1);
		final String language = request.getCode().substring(1, request.getCode().indexOf(WHITE_SPACE));
		ExecutionRequest executionRequest = ExecutionRequest.builder()
				.code(code)
				.language(language)
				.build();
		return executionRequest;
	}

	@Override
	public ExecutionResponse execute(ExecutionRequest request) throws InterpreterException {
		if (unsupportedLanguage()) {
			throw new LanguageNotSupportedException();
		}

		ExecutionContext executionContext = getContext(request.getSessionId());
		final Context context = executionContext.getContext();

		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					context.close(true);
					executionContext.getOutputStream().close();
					executionContext.getErrorsStream().close();
					executionContext.setTimedOut(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, executionTimeOut);

		try {
			context.eval(INTERPRETER_LANGUAGE, request.getCode());
			timer.cancel();
			timer.purge();
			return new ExecutionResponse(executionContext.getOutput(), executionContext.getErrors());
		} catch(PolyglotException e) {
			timer.cancel();
			timer.purge();
			if (e.isCancelled()) {
				// remove context
				sessionContexts.remove(request.getSessionId());
				throw new TimeOutException();
			}

			return new ExecutionResponse("" , e.getMessage());
		}
	}

	private boolean unsupportedLanguage() {
		try (Context tmpContext = Context.create()) {
			return !tmpContext.getEngine().getLanguages().containsKey(INTERPRETER_LANGUAGE);
		}
	}

	private ExecutionContext getContext(String sessionId) {
		return sessionContexts.computeIfAbsent(sessionId, key -> buildContext());
	}

	private ExecutionContext buildContext() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayOutputStream errorsStream = new ByteArrayOutputStream();
		Context context = Context.newBuilder(INTERPRETER_LANGUAGE).out(outputStream).err(errorsStream)
				.build();

		return new ExecutionContext(outputStream, errorsStream, context);
	}
}
