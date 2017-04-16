package com.zhizhu.limiter;

import com.zhizhu.limiter.api.TestBiz;
import com.zhizhu.limiter.domain.LimiterBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhizhu on 16/11/8.
 *
 * <p>
 *
 *     {@link #call()}可以通过在AOP切面对接口的访问的拦截中实现， 实现限流
 *     这里主要是用怎么用spring标签实现自定义的一些功能
 * </p>
 */
public class Application {

    static ApplicationContext context;

    static volatile ConcurrentHashMap<String,Long> accessMap = new ConcurrentHashMap();

    final static Lock lock = new ReentrantLock();

    static {
        context = new ClassPathXmlApplicationContext(
                "test.xml");
    }

    public static void main(String[] args) {

        testLimiter();

    }

    public static void testLimiter() {


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    call();
                }
            });
        }

        executorService.shutdown();
    }

    /**
     *
     * <p>
     *     可以通过AOP切面中实现对接口的访问的拦截， 实现限流
     * </p>
     *
     */
    private static void call() {

        final TestBiz testBiz = (TestBiz) context.getBean("testBiz");
        final LimiterBean limiterBean = (LimiterBean) context.getBean("testBizLimiter");

        final long maxQps = limiterBean.getMaxQps();

        final String name = TestBiz.class.getName();


        Long count = accessMap.get(name);

        if (null != count && count.longValue() >= maxQps) {
            System.out.println(Thread.currentThread().getName() + " 服务器繁忙，请稍后再试");
        } else {


            try {
                lock.lock();
                count = accessMap.get(name);
                if(null == count) {
                    count = 1L;
                    accessMap.put(name, count);

                } else {
                    if(count.longValue() >= maxQps) {
                        System.out.println(Thread.currentThread().getName() + " 服务器繁忙，请稍后再试");
                        return;
                    }
                }
            } finally {
                lock.unlock();
            }

            accessMap.put(name,count++);
            String response = testBiz.test();
            System.out.println(Thread.currentThread().getName() + " : " + response);
            accessMap.put(name, count--);

        }

    }


    public void testLimiterBean() {

        final LimiterBean limiterBean = (LimiterBean) context.getBean("testBizLimiter");

        System.out.println(limiterBean.getInterfaze());

        System.out.println(limiterBean);

    }


}
