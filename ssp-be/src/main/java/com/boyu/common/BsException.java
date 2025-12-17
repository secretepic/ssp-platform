package com.boyu.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BsException extends Exception {

    private Integer code;

    public BsException() {
        super();
    }

    public BsException(String message) {
        super(message);
    }

    public BsException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BsException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BsException(Throwable cause) {
        super(cause);
    }

}
