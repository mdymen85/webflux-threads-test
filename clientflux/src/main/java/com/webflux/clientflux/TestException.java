package com.webflux.clientflux;

import org.springframework.util.Assert;

import java.util.Arrays;

public class TestException extends RuntimeException {

    private final String code;

    private final String field;

    private final String action;

    private final Object[] args;

    public TestException(Throwable cause, String code, String field, String action, Object... args) {
        super(code + " - " + Arrays.toString(args), cause);
        Assert.hasLength(code, "N�o se pode criar uma BusinessException com um code vazio ou n�o informado");
        this.code = code;
        this.field = field;
        this.action = action;
        this.args = args;
    }

}
