package com.franquicia.franquicia_api.shared.util;

import java.util.Map;

public class ResponseError {
    private String status;
    private int statusCode;
    private Map<String, Object> errors;

    public ResponseError(String status, int statusCode, Map<String, Object> errors) {
        this.status = status;
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }
}
