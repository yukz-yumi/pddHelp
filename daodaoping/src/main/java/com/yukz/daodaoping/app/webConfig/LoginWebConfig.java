package com.yukz.daodaoping.app.webConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginWebConfig implements WebMvcConfigurer {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginWebConfig.class);
	
	
	/**
	 * 注入所有实现了HandlerMethodArgumentResolver 的解析器
	 */
	@Autowired
	private Map<String,HandlerMethodArgumentResolver> resolverMap = new HashMap<String,HandlerMethodArgumentResolver>();
	

	@Bean
	protected List<HandlerMethodArgumentResolver> initResolvers(){
		List<HandlerMethodArgumentResolver> list = new ArrayList<HandlerMethodArgumentResolver>();
		for (Entry<String,HandlerMethodArgumentResolver> entry : this.resolverMap.entrySet()) {
			list.add(entry.getValue());
			logger.info("----->>> 读取并加载请求参数解析器:{}",entry.getKey());
		}
		return list;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserSessionInterceptor()).addPathPatterns("/appInt/**")
			.excludePathPatterns("/appInt/user/login","/appInt/user/bindMobile","/appInt/user/bindExAccount")
			.excludePathPatterns("/appInt/pageConfig/**");
	}
	
	/**
	 * 注册自定义的对象解析器
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.addAll(initResolvers());
		logger.info("----->>> 参数转换器注入完成,注入数量:{}",initResolvers().size());
	}
}
