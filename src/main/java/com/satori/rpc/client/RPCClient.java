package com.satori.rpc.client;

import com.satori.rpc.common.RPCRequest;
import com.satori.rpc.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
