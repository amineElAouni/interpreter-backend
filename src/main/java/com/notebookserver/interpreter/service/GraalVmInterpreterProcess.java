package com.notebookserver.interpreter.service;

import javax.servlet.http.HttpSession;

import com.notebookserver.interpreter.exception.InterpreterException;
import com.notebookserver.interpreter.model.InterpreterRequest;
import com.notebookserver.interpreter.model.InterpreterResponse;

public interface GraalVmInterpreterProcess {

	InterpreterResponse executeInterpreterProcess(InterpreterRequest interpreterRequest, HttpSession httpSession) throws InterpreterException;

}
