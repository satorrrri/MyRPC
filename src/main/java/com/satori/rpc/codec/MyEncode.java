package com.satori.rpc.codec;

import com.satori.rpc.common.RPCRequest;
import com.satori.rpc.common.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(msg instanceof RPCRequest){
            out.writeShort(MessageType.REQUEST.getCode());
        }else if(msg instanceof RPCResponse){
            out.writeShort(MessageType.RESPONSE.getCode());
        }

        out.writeShort(serializer.getType());
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
