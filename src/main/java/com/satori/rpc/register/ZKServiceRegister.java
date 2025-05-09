package com.satori.rpc.register;

import com.satori.rpc.loadbalance.LoadBalance;
import com.satori.rpc.loadbalance.impl.RandomLoadBalance;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

public class ZKServiceRegister implements ServiceRegister{

    private CuratorFramework client;
    private static final String ROOT_PATH = "MyRPC";

    private LoadBalance loadBalance=new RandomLoadBalance();

    public ZKServiceRegister() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(retryPolicy).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("ZKServiceRegister started");
    }

    @Override
    public void register(String serviceName, InetSocketAddress address) {
        try{
            if(client.checkExists().forPath("/"+serviceName)==null){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
            }
            String path = "/"+serviceName+"/"+getServiceAddress(address);
            System.out.println("ZKServiceRegister path:"+path);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        }catch (Exception e){
            System.out.println("此服务已存在");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        System.out.println(serviceName);
        try{
            List<String> strings =client.getChildren().forPath("/"+serviceName);
            System.out.println(strings.size());
            String string =loadBalance.balance(strings);

            return parseAddress(string);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getServiceAddress(InetSocketAddress address){
        return address.getHostString()+":"+address.getPort();
    }

    private InetSocketAddress parseAddress(String address){
        String[] split = address.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }
}
