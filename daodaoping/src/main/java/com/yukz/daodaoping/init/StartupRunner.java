package com.yukz.daodaoping.init;

import com.alibaba.fastjson.JSON;
import com.yukz.daodaoping.common.config.DataRedisKey;
import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 初始化类-可在此类中初始化，如redis缓存等
 *
 * @author yukz
 * @version V1.0
 */
@Order(1)
public class StartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private RedisHandler redisHandler;
    @Autowired
    private UserInfoService userInfoService;


    @Override
    public void run(String... args) throws Exception {
        logger.info("启动初始化数据-开始");
        //同步数据到redis
        this.saveCache(100001l);
    }

    private void saveCache(Long agentId) {
        logger.info("开始获取用户信息缓存数据");
        Map<String,Object> userInfoMapQuery = new HashMap<>();
        userInfoMapQuery.put("agentId",agentId);
        List<UserInfoDO> userInfoList = userInfoService.list(userInfoMapQuery);
        String userInfoDataJson = JSON.toJSONString(userInfoList);
        this.redisHandler.set(DataRedisKey.REDIS_USER_INFO_DATA+agentId,userInfoDataJson,30L, TimeUnit.DAYS);
        logger.info("-----------> 测试获取用户信息缓存数据：{}",this.redisHandler.get(DataRedisKey.REDIS_USER_INFO_DATA+agentId));

        logger.info("启动初始化数据--结束");
    }
}

