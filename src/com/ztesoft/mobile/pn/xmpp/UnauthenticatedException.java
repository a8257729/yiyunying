package com.ztesoft.mobile.pn.xmpp;

/** 
 * Thrown if a user was not authenticated to do a particular operation.
 *
 */
public class UnauthenticatedException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnauthenticatedException() {
        super();
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(Throwable cause) {
        super(cause);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}