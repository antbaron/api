package fr.epsi.api.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoginFailException extends Exception {

	private static final long serialVersionUID = 1L;

	public LoginFailException() {
		super();
	}

	public LoginFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginFailException(String message) {
		super(message);
	}

	public LoginFailException(Throwable cause) {
		super(cause);
	}

}
