package com.satori.rpc.server;

public interface RPCServer {
    void start(int port);
    void stop();
}
