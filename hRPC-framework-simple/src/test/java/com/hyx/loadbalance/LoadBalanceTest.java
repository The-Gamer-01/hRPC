package com.hyx.loadbalance;

import com.hyx.extension.ExtensionLoader;
import org.junit.Test;

/**
 * @author hyx
 **/

public class LoadBalanceTest {
    @Test
    public void test() {
        LoadBalance random = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("consistent_hash");
        System.out.println(random.name());
    }
}
