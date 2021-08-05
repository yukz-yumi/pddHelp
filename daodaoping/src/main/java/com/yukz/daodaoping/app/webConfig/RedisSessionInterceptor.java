package com.yukz.daodaoping.app.webConfig;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.system.config.RedisHandler;

/**
 * TODO 后期在使用这个功能
 * 检查redis中是否存在用户信息的拦截器，以判断用户的登录状态
 * @author micezhao
 *
 */
//@Configuration
public class RedisSessionInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisSessionInterceptor.class);
	
	@Autowired
	private RedisHandler redisHandler;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 无论访问的地址是不是正确的，都进行登录验证，登录成功后的访问再进行分发，404的访问自然会进入到错误控制器中
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUserId") != null) {
			try {
				// 验证当前请求的session是否是已登录的session
				
				String loginSessionId = String.valueOf(redisHandler.get("loginUser:" + session.getAttribute("loginUserId")));
				if (loginSessionId != null && loginSessionId.equals(session.getId())) {
					return true;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		response401(response);
		return false;
	}

	private void response401(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		try {
			response.getWriter().print(JSON.toJSONString(R.error("用户不存在，请先绑定手机号")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
