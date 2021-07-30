package com.yukz.daodaoping.common.aspect;

import com.yukz.daodaoping.common.config.DataRedisKey;
import com.yukz.daodaoping.system.config.RedisHandler;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yukz
 * @varsion V1.0
 * @date: 2021/7/30 12:53
 */
@Aspect
@Component
public class CleanDataAspect extends DataRedisKey {
    private static final Logger logger = LoggerFactory.getLogger(CleanDataAspect.class);
    @Autowired
    protected RedisHandler redisHandler;

    @Pointcut("@annotation(com.yukz.daodaoping.common.aspect.CleanData)")
    public void cleanDataPointCut() {
    }

    @Around("cleanDataPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 执行方法
        Object result = point.proceed();
        //执行完后，清空对应的缓存逻辑
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        CleanData cleanData = method.getAnnotation(CleanData.class);
        String value = cleanData.value();
        Long agentId = 100001l;
        if (StringUtils.isNotBlank(value)) {
            logger.info("清除[{}]缓存。。",value+agentId);
            this.redisHandler.remove(value+agentId);//清除缓存
        }
        return result;
    }
}
