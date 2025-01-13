package com.fullstack.projet.controllers;

import com.fullstack.projet.exceptions.ErrorBody;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorHandlingUtils {

    public static ResponseEntity<Object> handleNotFound(Logger logger, Exception e) {
        logger.warn(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    public static ResponseEntity<Object> handleBadRequest(Logger logger, Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorBody(e.getMessage()));
    }

    public static ResponseEntity<Object> handleBadRequestWithCustomMessage(Logger logger, String message) {
        logger.error(message);
        return ResponseEntity.badRequest().body(new ErrorBody(message));
    }

    public static ResponseEntity<Object> handleForbiddenRequest(Logger logger, Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorBody(e.getMessage()));
    }

    public static ResponseEntity<Object> handleInternalServerError(Logger logger, String message, Exception e) {
        logger.error(message, e);
        return ResponseEntity.internalServerError().body(new ErrorBody(message));
    }


}