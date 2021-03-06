package com.petrpopov.cheatfood.model.data;

/**
 * User: petrpopov
 * Date: 16.07.13
 * Time: 20:20
 */
public class MessageResult {

    private Boolean error = false;
    private Boolean warning = false;
    private ErrorType errorType;
    private WarningType warningType;
    private String message;
    private Object result;

    public MessageResult() {
    }

    public MessageResult(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public Boolean getWarning() {
        return warning;
    }

    public void setWarning(Boolean warning) {
        this.warning = warning;
    }

    public WarningType getWarningType() {
        return warningType;
    }

    public void setWarningType(WarningType warningType) {
        this.warningType = warningType;
    }
}
