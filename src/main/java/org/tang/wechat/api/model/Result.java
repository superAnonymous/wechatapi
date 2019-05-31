package org.tang.wechat.api.model;

import java.io.Serializable;

public class Result implements Serializable {
    private static final long serialVersionUID = 8241012103955662769L;
    public static final int ERROR = -1;
    public static final int WARN = 0;
    public static final int SUCCESS = 1;
    private int code;
    private String message;
    private Object value;

    public Result() {
        this.code = -1;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[CODE:").append(this.code).append("]");
        if (this.message != null) {
            sb.append("[MESSAGE:").append(this.message).append("]");
        }

        if (this.value != null) {
            sb.append("[OBJECT:").append(this.value.toString()).append("]");
        }

        return sb.toString();
    }
}
