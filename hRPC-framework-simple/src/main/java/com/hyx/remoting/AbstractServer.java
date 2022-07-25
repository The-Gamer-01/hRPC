package com.hyx.remoting;

/**
 * 抽象Server.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/19 11:15
 **/

public abstract class AbstractServer {

    /**
     * 开启服务.
     */
    protected abstract void doOpen() throws InterruptedException;

    /**
     * 关闭服务.
     */
    protected abstract void doClose();
}
