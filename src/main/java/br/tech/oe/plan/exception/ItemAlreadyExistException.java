package br.tech.oe.plan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ItemAlreadyExistException extends RuntimeException {

    public ItemAlreadyExistException() {
    }

    public ItemAlreadyExistException(String message) {
        super(message);
    }

    public ItemAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}