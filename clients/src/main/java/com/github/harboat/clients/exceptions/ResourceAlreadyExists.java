package com.github.harboat.clients.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExists extends RuntimeException {

    public ResourceAlreadyExists() {
        super("Resource already exists!");
    }

    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
