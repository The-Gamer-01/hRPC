package spi;

import com.hyx.extension.ExtensionLoader;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className SPITest
 * @description SPI测试
 * @date 2022/4/2 0:20
 **/

public class SPITest {
    @Test
    public void testRobotSPI() {
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();

        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }

    @Test
    public void classLoaderTest() throws IOException {
        String dir = "META-INF/hrpc/";
        String type = "com.hyx.Robot";
        String fileName = dir + type;
        ClassLoader classLoader = SPITest.class.getClassLoader();
        System.out.println(classLoader.getResource(fileName));
        Enumeration<URL> resources = classLoader.getResources(fileName);
        while (resources.hasMoreElements()) {
            URL resourceURL = resources.nextElement();
            System.out.println("???");
        }
    }

    @Test
    public void getResourceTest() {
        ClassLoader classLoader = SPITest.class.getClassLoader();
        System.out.println(classLoader.getResource(""));
        System.out.println(classLoader.getResource("/"));
        System.out.println(classLoader.getResource("SPITest.class"));
        System.out.println(classLoader.getResource("/SPITest.class"));
        System.out.println(classLoader.getResource("META-INF/hrpc"));
    }
}
