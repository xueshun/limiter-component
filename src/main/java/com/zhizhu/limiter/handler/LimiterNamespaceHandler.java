package com.zhizhu.limiter.handler;

import com.zhizhu.limiter.paraser.ClusterLimiterBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by zhizhu on 16/11/8.
 */
public class LimiterNamespaceHandler extends NamespaceHandlerSupport {


    @Override
    public void init() {
        registerBeanDefinitionParser("clusterLimiter", new ClusterLimiterBeanDefinitionParser());
    }
}
