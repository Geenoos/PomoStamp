package com.ssafy.pomostamp.user.exception;

import static com.ssafy.pomostamp.ExceptionMessage.NO_SUCH_USER_MESSAGE;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(){super(NO_SUCH_USER_MESSAGE);}
}
