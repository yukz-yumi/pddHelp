package com.yukz.daodaoping.app.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class SessionCofig implements WebMvcConfigurer {
	
	
	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new RedisSessionInterceptor())
	                //排除拦截
//	                .excludePathPatterns("/appInt/user/login/**")
	                .excludePathPatterns("/appInt/user/bindMobile")
	                //拦截路径
	                .addPathPatterns("/**");
	    }
}
