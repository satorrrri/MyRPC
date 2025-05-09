package com.satori.rpc.loadbalance.impl;

import com.satori.rpc.loadbalance.LoadBalance;

import java.util.List;

/**
 * 轮询负载均衡
 */
public class RoundLoadBalance implements LoadBalance {
    private int choose = -1;
    @Override
    public String balance(List<String> addressList) {
        choose++;
        choose = choose%addressList.size();
        return addressList.get(choose);
    }
}
