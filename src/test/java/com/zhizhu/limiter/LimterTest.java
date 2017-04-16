package com.zhizhu.limiter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhizhu on 16/11/8.
 */
public class LimterTest extends TestCase {

//    private Logger logger = LoggerFactory.getLogger(LimterTest.class);

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LimterTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LimterTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        try{

            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[] { "classpath:test.xml" });
            context.start();
            System.out.println("服务启动成功!");
            System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟

//            URL url = LimterTest.class.getClassLoader().getResource("test.xml");
//            String path = url.getPath();
//            ApplicationContext context = new FileSystemXmlApplicationContext("/" + path);
//
//            context.getBean("testBiz");



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
