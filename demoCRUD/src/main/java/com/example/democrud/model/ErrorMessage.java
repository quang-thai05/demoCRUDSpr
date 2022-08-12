package com.example.democrud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage extends RuntimeException {
    private int statusCode;
    private String message;

    @Override
    public String toString() {
        return "ErrorMessage{" + "statusCode=" + statusCode + ", message='" + message + '\'' + '}';
    }
}
