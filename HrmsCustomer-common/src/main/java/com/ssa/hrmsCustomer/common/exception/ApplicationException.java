package com.ssa.hrmsCustomer.common.exception;


public class ApplicationException extends RuntimeException{

    private static final long serialVersionUID = -4775352890645652914L;
    
	public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
