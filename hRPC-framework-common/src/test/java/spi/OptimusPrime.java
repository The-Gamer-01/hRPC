package spi;

import com.hyx.extension.SPI;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className OptimusPrime
 * @description TODO
 * @date 2022/4/2 0:24
 **/

public class OptimusPrime implements Robot {

    @Override
    public void sayHello() {
        System.out.println("OptimusPrime");
    }
}
