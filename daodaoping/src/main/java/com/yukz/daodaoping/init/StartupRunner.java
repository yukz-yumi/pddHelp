package com.yukz.daodaoping.init;

import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.system.domain.SysSetDO;
import com.yukz.daodaoping.system.service.SysSetService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
    private SysSetService sysSetService;


    @Override
    public void run(String... args) throws Exception {
        logger.info("启动初始化数据-开始");
        //同步数据到redis
        this.saveCache(100001l);
    }

    private void saveCache(Long agentId) {
        logger.info("开始获取系统设置信息缓存数据");
        Map<String,Object> sysSetMapQuery = new HashMap<>();
        sysSetMapQuery.put("agentId",agentId);
        List<SysSetDO> sysSetList = sysSetService.list(sysSetMapQuery);
        for (SysSetDO sysSet : sysSetList) {
            int i = 0;
            i++;
            StringJoiner key = new StringJoiner("","sysSet::sysSet-","");
            key.add(sysSet.getSetKey());
            key.add("-");
            String platform = sysSet.getPlatform();
            if (StringUtils.isNotBlank(platform)) {
                key.add(platform);
            } else {
                key.add("null");
            }
            key.add("-");
            String setType = sysSet.getSetType();
            if (StringUtils.isNoneBlank(setType)) {
                key.add(setType);
            } else {
                key.add("null");
            }
            key.add("-");
            Long agentIdDB = sysSet.getAgentId();
            if (null != agentIdDB) {
                key.add(agentIdDB.toString());
            } else {
                key.add("null");
            }
            this.redisHandler.set(key.toString(), sysSet);
        }

        /*String userInfoDataJson = JSON.toJSONString(sysSetList);
        this.redisHandler.set(DataRedisKey.REDIS_USER_INFO_DATA+agentId,userInfoDataJson,30L, TimeUnit.DAYS);*/

        logger.info("启动初始化数据--结束");
    }
}

