package com.zhizhu.limiter.paraser;

import com.zhizhu.limiter.domain.LimiterBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Created by zhizhu on 16/11/8.
 */
public class ClusterLimiterBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    private static final Logger logger = LoggerFactory.getLogger(ClusterLimiterBeanDefinitionParser.class);


    protected Class<LimiterBean> getBeanClass(Element element) {
        return LimiterBean.class;
    }


    @Override
    protected void doParse(Element element, ParserContext parserContext,
                                       BeanDefinitionBuilder builder) {

        parseMethodElements(element,parserContext,builder);


        String interfaceName = element.getAttribute("interface");

        builder.addPropertyValue("interfaze", interfaceName);

//        builder.addPropertyValue("ref", new RuntimeBeanReference(ref));
    }

    private void parseMethodElements(Element refElement,
                                    ParserContext parserContext,
                                    BeanDefinitionBuilder builder){
        String limiterConfigElementName = "limiterConfig";
        Element methodSpecialElement = DomUtils.getChildElementByTagName(refElement,
                limiterConfigElementName);

        if (methodSpecialElement != null){
            String warmupConfigElementName = "warmupConfig";
            String maxQps = "maxQps";
            String warmupSwitch = "warmupSwitch";
            String warmupTime = "warmupTime";


            List<Element> methodElements = DomUtils.getChildElementsByTagName(
                    methodSpecialElement, warmupConfigElementName);

            ManagedMap map = new ManagedMap(methodElements.size());
            map.setMergeEnabled(true);
            map.setSource(parserContext.getReaderContext().extractSource(methodSpecialElement));

            for (Element element : methodElements){

                String  maxQpsValueStr = element.getAttribute(maxQps);
                if(StringUtils.isEmpty(maxQpsValueStr)) {
                    throw new RuntimeException("maxQpsValue must be positive");
                }

                try {
                    long maxQpsValue = Long.parseLong(maxQpsValueStr);
                    builder.addPropertyValue("maxQps",maxQpsValue);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("maxQpsValue must be number");
                }


                String warmupSwitchValueStr = element.getAttribute(warmupSwitch);
                if(StringUtils.isNotEmpty(warmupSwitchValueStr)) {

                    boolean parseBoolean = false;
                    try {
                        parseBoolean = Boolean.parseBoolean(warmupSwitchValueStr);
                    } catch (Exception e) {
                        logger.error("预热开关参数错误！！不启动预热");
                    }

                    builder.addPropertyValue("warmupSwitch",parseBoolean);

                    if(parseBoolean) {
                        String warmupTimeValueStr = element.getAttribute(warmupTime);
                        if(StringUtils.isEmpty(warmupTimeValueStr)) {
                            logger.error("预热时间参数错误！！不启动预热");
                        }

                        try {
                            long warmupTimeValue = Long.parseLong(warmupTimeValueStr);
                            builder.addPropertyValue("warmupTime",warmupTimeValue);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("warmupTime value must be number");
                        }

                    }



                }
                break;

            }

        }
    }
}
