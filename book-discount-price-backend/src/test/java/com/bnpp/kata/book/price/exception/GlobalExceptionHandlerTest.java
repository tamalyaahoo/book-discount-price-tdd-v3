package com.bnpp.kata.book.price.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("handleInvalidBasketException → should return BAD_REQUEST when status is null")
    void testInvalidBookException_defaultStatus() {
        InvalidBookException ex = new InvalidBookException("Invalid book error", null);

        ResponseEntity<Map<String, Object>> response =
                handler.handleInvalidBasketException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid book error", response.getBody().get("error"));
    }

    @Test
    @DisplayName("handleInvalidBasketException → should use provided status")
    void testInvalidBookException_customStatus() {
        InvalidBookException ex = new InvalidBookException("Custom", HttpStatus.CONFLICT);

        ResponseEntity<Map<String, Object>> response =
                handler.handleInvalidBasketException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Custom", response.getBody().get("error"));
    }

    @Test
    @DisplayName("handleGenericException → should return INTERNAL_SERVER_ERROR")
    void testGenericException() {
        Exception ex = new Exception("Unexpected");

        ResponseEntity<Map<String, Object>> response =
                handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().get("error"));
    }


    @Test
    @DisplayName("handleIllegalArgument → should return BAD_REQUEST")
    void testIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Bad arg");

        ResponseEntity<Map<String, String>> response =
                handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad arg", response.getBody().get("error"));
    }
}