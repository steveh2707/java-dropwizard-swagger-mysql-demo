package org.kainos.ea.client;

public class InvalidOrderException extends Throwable {
    public InvalidOrderException(String validationMessage) {
        super(validationMessage);
    }
}
