package org.launchcode.productpal.models;

public class  UserNotFoundException extends Throwable {

    public UserNotFoundException(String message) {
        super(message);
    }
}
