package com.cabtrip.client.utils;

import lombok.Getter;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private final String name;

    @Getter
    private final String context;

    /**
     * Create an application exception object.
     *
     * @param name    a name identifying the exception
     * @param cause   the Throwable causing the error
     * @param context a message describing the context in which the error occurred
     */
    public ApplicationException(String name, Throwable cause, String context) {
        super(name, cause);

        this.name = name;
        this.context = context;
    }

    /**
     * Create an application exception object.
     *
     * @param name    a name identifying the exception
     * @param context a message describing the context in which the error occurred
     */
    public ApplicationException(String name, String context) {
        super(name);

        this.name = name;
        this.context = context;
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
                "name='" + name + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
