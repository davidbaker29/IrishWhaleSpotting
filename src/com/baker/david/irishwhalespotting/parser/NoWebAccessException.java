package com.baker.david.irishwhalespotting.parser;

import java.io.IOException;

public class NoWebAccessException extends RuntimeException {

	public NoWebAccessException(IOException e) {
		super(e);
	}

	public NoWebAccessException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
