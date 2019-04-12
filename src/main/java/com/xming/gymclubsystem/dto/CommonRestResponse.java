package com.xming.gymclubsystem.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xiaoming.
 * Created on 2019/03/11 00:15.
 * Description :
 */
@Data
public class CommonRestResponse implements Serializable {
    private static final long serialVersionUID = -8965948882721460674L;
    public static final int SUCCESS = 200;
    public static final int FAILED = 500;
    public static final int VALIDATE_FAILED = 404;
    //未认证
    public static final int UNAUTHORIZED = 401;
    //未授权
    public static final int FORBIDDEN = 403;
    private int code;
    private String message;
    private Object data;

    public CommonRestResponse success(Object data) {
        this.code = SUCCESS;
        this.message = "Success";
        this.data = data;
        return this;
    }
}
