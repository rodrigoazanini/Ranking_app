package org.ranking_app.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
       super("Wrong password");
    }
}
