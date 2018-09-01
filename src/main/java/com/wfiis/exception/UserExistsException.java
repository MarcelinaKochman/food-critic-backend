package com.wfiis.exception;

public class UserExistsException extends Exception {

    private static final String USER_ALREADY_EXIST = "User already exist!";

    public UserExistsException() {
        super(USER_ALREADY_EXIST);
    }
}
