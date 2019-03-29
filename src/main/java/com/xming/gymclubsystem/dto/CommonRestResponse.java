package com.xming.gymclubsystem.dto;

import lombok.Data;

/**
 * @author Xiaoming.
 * Created on 2019/03/11 00:15.
 * Description :
 */
@Data
public class CommonRestResponse {
    private int code;
    private String message;
    private Object data;
}
