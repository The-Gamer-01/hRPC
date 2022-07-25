package spi;

/**
 * Bumblebee Test.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/2 0:24
 **/

public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Bumblebee");
    }
}
