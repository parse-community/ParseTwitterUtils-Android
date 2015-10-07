package com.parse.internal.signpost.exception;

@SuppressWarnings("all")
public abstract class OAuthException extends Exception {

    public OAuthException(String message) {
        super(message);
    }

    public OAuthException(Throwable cause) {
        super(cause);
    }

    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
