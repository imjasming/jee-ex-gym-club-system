package com.xming.gymclubsystem.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xiaoming.
 * Created on 2019/04/11 00:15.
 * Description :
 */
@Data
public class RestResponse implements Serializable {
    private static final long serialVersionUID = -8965948882721460674L;
    public static final int SUCCESS = 200;
    public static final int FAILED = 500;

    public static final int BAD_REQUEST = 400;
    //未认证
    public static final int UNAUTHORIZED = 401;
    //未授权
    public static final int FORBIDDEN = 403;
    private int code;
    private String message;
    private Object data;

    private RestResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RestResponse ok(Object data) {
        Builder builder = new Builder(SUCCESS);
        return builder.withMessage("success").withData(data).build();
    }

    public static RestResponse failed() {
        Builder builder = new Builder(FAILED);
        return builder.withMessage("failure").build();
    }

    public static RestResponse failed(String message) {
        Builder builder = new Builder(FAILED);
        return builder.withMessage("failure").withMessage(message).build();
    }

    public static RestResponse badRequest(String message) {
        Builder builder = new Builder(BAD_REQUEST);
        return builder.withMessage(message).build();
    }

    public static RestResponse unauthorized(String message) {
        Builder builder = new Builder(UNAUTHORIZED);
        return builder.withMessage(message).build();
    }

    public static RestResponse forbidden(String message) {
        Builder builder = new Builder(FORBIDDEN);
        return builder.withMessage(message).build();
    }

    private interface IBuilder {

        IBuilder withMessage(String message);

        IBuilder withData(Object data);

        RestResponse build();
    }

    private static class Builder implements IBuilder {
        private final int code;
        private String message;
        private Object data;

        public Builder(int code) {
            this.code = code;
        }

        @Override
        public IBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        @Override
        public IBuilder withData(Object data) {
            this.data = data;
            return this;
        }

        @Override
        public RestResponse build() {
            return new RestResponse(
                    this.code,
                    this.message,
                    this.data);
        }
    }
}
