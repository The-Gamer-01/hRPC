package com.hyx;

import lombok.Data;

/**
 * URL,此版本用作注册到注册中心的URL.
 *
 * @author hyx
 **/

@Data
public class URL {
    private final String protocol;
    
    private final String host;
    
    private final int port;
    
    private final String serviceName;
    
    public URL() {
        this.protocol = null;
        this.host = null;
        this.port = 0;
        this.serviceName = null;
    }
    
    public URL(String protocol, String host, int port, String serviceName) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.serviceName = serviceName;
    }
}
