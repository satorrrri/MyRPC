package com.satori.rpc.client;

import com.satori.rpc.common.RPCRequest;
import com.satori.rpc.common.RPCResponse;
import com.satori.rpc.register.ServiceRegister;
import com.satori.rpc.register.ZKServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

public class NettyRPCClient implements RPCClient{
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup group;
    private String host;
    private int port;
    private ServiceRegister serviceRegister;

    public NettyRPCClient() {this.serviceRegister = new ZKServiceRegister();}

    static{
        group=new NioEventLoopGroup();
        bootstrap=new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new NettyClientInitializer());
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host=address.getHostName();
        port=address.getPort();
        try{
            ChannelFuture channelFuture=bootstrap.connect(host,port).sync();
            Channel channel=channelFuture.channel();

            channel.writeAndFlush(request);
            channel.closeFuture().sync();

            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response=channel.attr(key).get();
            System.out.println(response);
            return response;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}
