package org.tang.wechat.api.exception;

public class IllegalFormatException extends Exception {
    private static final long serialVersionUID = 8529592923808370980L;

    public IllegalFormatException() {
    }

    public IllegalFormatException(String message) {
        super(message);
    }

    public IllegalFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalFormatException(Throwable cause) {
        super(cause);
    }
}
