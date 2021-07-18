package com.bootdo.common.aspect;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.domain.LogDO;
import com.bootdo.common.service.LogService;
import com.bootdo.common.utils.HttpContextUtils;
import com.bootdo.common.utils.IPUtils;
import com.bootdo.common.utils.JSONUtils;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.gotoo.utils.AesUtil;
import com.bootdo.gotoo.utils.Const;
import com.bootdo.system.domain.UserDO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class AesAppIntDecodeAspect {
    private static final Logger logger = LoggerFactory.getLogger(AesAppIntDecodeAspect.class);


    @Pointcut("execution(* com.bootdo.gotooappint.controller.*.*(..))")
    public void decodePointCut() {
    }

    @Around("decodePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        logger.info("执行API方法前 解密参数");
        //访问目标方法的参数：
        Object[] args = point.getArgs();
        if (args != null && args.length > 0 && args[0].getClass() == String.class) {
            args[0] = AesUtil.deCode((String)args[0], Const.AES_KEY);
            logger.info("解密后的参数："+ String.valueOf(args[0]));
        }
        //用改变后的参数执行目标方法
        Object returnValue = point.proceed(args);
        return returnValue;
    }

}
