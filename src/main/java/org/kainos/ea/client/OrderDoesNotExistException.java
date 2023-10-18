package org.kainos.ea.client;

public class OrderDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get order from the database";
    }
}
