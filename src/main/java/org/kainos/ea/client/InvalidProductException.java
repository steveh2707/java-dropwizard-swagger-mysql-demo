package org.kainos.ea.client;

public class InvalidProductException extends Throwable {
    public InvalidProductException(String validationMessage) {
        super(validationMessage);
    }
}
