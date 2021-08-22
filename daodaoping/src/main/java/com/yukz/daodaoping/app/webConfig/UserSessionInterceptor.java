package com.yukz.daodaoping.app.webConfig;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.enums.ExAccountEnum;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.user.domain.UserVsExAccountDO;

@Component
public class UserSessionInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(UserSessionInterceptor.class);
	
	@Autowired
	private RedisHandler redisHandler;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String sessionId = request.getHeader(Constants.USER_TOKEN);
		Object object = redisHandler.hmGet(Constants.USER_AGENT, sessionId);
		UserAgent  userAgent= JSON.parseObject(JSON.toJSONString(object), UserAgent.class);
		if (userAgent == null) {
			return false;
		}
		logger.info("请求拦截器执行...解析用户对象完成:{}", JSONObject.toJSONString(userAgent));
		boolean hasBinded = false;
//		List<UserVsExAccountDO> list = new ArrayList<UserVsExAccountDO>();
		List<UserVsExAccountDO> list = userAgent.getExAccountList();
		if (list.isEmpty()) {
//			throw new Exception("请先绑定用户外部账号");
			response401(response);
			return false;
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getAllowed().equals(IsAllowEnum.YES.getStatus())
						&& list.get(i).getAccountStatus().equals(ExAccountEnum.AVAILABLE.getExAccountStatus())) {
					hasBinded = true;
					break;
				}
			}
		}

		return hasBinded;
	}
	
	private void response401(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		try {
			response.getWriter().print(JSON.toJSONString(R.error("请绑定外部账号")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
