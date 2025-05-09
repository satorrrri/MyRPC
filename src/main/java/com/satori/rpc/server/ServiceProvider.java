package com.satori.rpc.server;

import com.satori.rpc.register.ServiceRegister;
import com.satori.rpc.register.ZKServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    private Map<String,Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;

    public ServiceProvider(String host, int port) {
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new ZKServiceRegister();
    }

    public void provideServiceInterface(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(),service);
            serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port));
        }
    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }

}
