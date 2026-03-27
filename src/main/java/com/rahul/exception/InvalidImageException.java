package com.rahul.exception;

/**
 * Thrown when an image contains colored (non-grayscale) pixels,
 * indicating it is not a valid black-and-white MS Paint image.
 */
public class InvalidImageException extends Exception {

    public InvalidImageException(String message) {
        super(message);
    }
}
