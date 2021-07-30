package com.yukz.daodaoping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统配置类参数
 * @Description TODO
 * @Author yukz
 * @Date 2021/7/30 13:21
 * @Version 1.0
 */
@Component
public class ConfigKey {
    /**
     * 机构id
     */
    public static Long agentId;


    @Value("${bootdo.agentId}")
    public void setClientModel(Long v) {
        agentId = v;
    }

}
