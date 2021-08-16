package com.yukz.daodaoping.common.config;

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
     * 文件上传地址
     */
    public static String uploadPath;
    /**
     * 机构id
     */
    public static Long agentId;

    @Value("${bootdo.uploadPath}")
    public void setUploadPath(String v) {
        uploadPath = v;
    }

    /**
     * 图片服务器url
     */
    public static String imgUrl;


    @Value("${bootdo.agentId}")
    public void setClientModel(Long v) {
        agentId = v;
    }

    @Value("${bootdo.imgUrl}")
    public void setImgUrl(String v) {
        imgUrl = v;
    }

}
