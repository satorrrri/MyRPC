package com.satori.rpc.codec;

import com.alibaba.fastjson.JSONObject;
import com.satori.rpc.common.RPCRequest;
import com.satori.rpc.common.RPCResponse;

public class JSONSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        switch (messageType) {
            case 0:
                RPCRequest request = JSONObject.parseObject(bytes, RPCRequest.class);
                if(request.getParams()==null) return request;

                Object[] objects = new Object[request.getParams().length];
                for(int i=0;i<objects.length;i++){
                    Class<?> paramType = request.getParamTypes()[i];
                    if(!paramType.isAssignableFrom(request.getParams()[i].getClass())){
                        objects[i]=JSONObject.toJavaObject((JSONObject)request.getParams()[i],request.getParamTypes()[i]);
                    }else{
                        objects[i]=request.getParams()[i];
                    }
                }
                request.setParams(objects);
                obj=request;
                break;
            case 1:
                RPCResponse response = JSONObject.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                if(! dataType.isAssignableFrom(response.getData().getClass())){
                    response.setData(JSONObject.toJavaObject((JSONObject)response.getData(),dataType));
                }
                obj=response;
                break;
            default:
                System.out.println("暂不支持此消息");
                throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 1;
    }
}
