package com.satori.rpc.server;

import com.satori.rpc.codec.JSONSerializer;
import com.satori.rpc.codec.MyDecode;
import com.satori.rpc.codec.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //这里可以自己选择序列化器，先选用json序列化
        pipeline.addLast(new MyEncode(new JSONSerializer()));
        pipeline.addLast(new MyDecode());
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));

    }
}
