package com.satori.rpc.register;

import java.net.InetSocketAddress;

/**
 * 实现两个功能：服务注册和服务发现，目前用zk实现，未来可以拓展redis的实现
 */
public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress address);
    InetSocketAddress serviceDiscovery(String serviceName);
}
