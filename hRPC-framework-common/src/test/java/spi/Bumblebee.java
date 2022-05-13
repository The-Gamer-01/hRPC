package spi;

import com.hyx.extension.SPI;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className Bumblebee
 * @description TODO
 * @date 2022/4/2 0:24
 **/

public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Bumblebee");
    }
}
