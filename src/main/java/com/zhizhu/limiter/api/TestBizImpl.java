package com.zhizhu.limiter.api;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhizhu on 16/11/8.
 */
public class TestBizImpl implements TestBiz{

    public String test() {

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "success";

    }
}
