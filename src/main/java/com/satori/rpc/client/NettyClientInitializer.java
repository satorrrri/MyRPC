package com.satori.rpc.client;

import com.satori.rpc.codec.JSONSerializer;
import com.satori.rpc.codec.MyDecode;
import com.satori.rpc.codec.MyEncode;
import com.satori.rpc.server.NettyRPCServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode(new JSONSerializer()));
        pipeline.addLast(new NettyClientHandler());
    }
}
