package com.satori.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class RPCResponse implements Serializable {
    private int code;
    private String message;
    private Class<?> dataType;
    private Object data;

    public static RPCResponse success(Object data){
        return RPCResponse.builder().data(data).code(200).dataType(data.getClass()).build();
    }
    public static RPCResponse fail(){
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }
}
