package com.hyx.protocol;

import com.hyx.extension.SPI;

/**
 * 通信协议SPI.
 *
 * @author hyx
 **/

@SPI
public interface Protocol {
    
    /**
     * 获取默认协议端口号.
     * 应用层一定会需要有一个默认端口号,比如HTTP协议端口号为80.
     * @return 默认端口号.
     */
    int getDefaultPort();
    
    
}
