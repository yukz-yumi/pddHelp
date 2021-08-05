package com.yukz.daodaoping.app.webConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSONObject;
import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.enums.ExAccountEnum;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.user.domain.UserVsExAccountDO;

public class UserSessionInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(UserSessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object object = request.getSession().getAttribute(Constants.USER_AGENT);
		UserAgent userAgent = JSONObject.parseObject(JSONObject.toJSONString(object), UserAgent.class);
		if (userAgent == null) {
			throw new RuntimeException("用户信息获取失败,请重新登录");
		}
		logger.info("请求拦截器执行...解析用户对象完成:{}", JSONObject.toJSONString(userAgent));
//		String name = ((HandlerMethod)handler).getMethod().getName();
//		if(!StringUtils.equalsAny(name, "mobileBind","logout","smscodeAttain","smscodeVaildate")) {
//			if(!userAgent.isHasbinded()) {
//				throw new RuntimeException("请先绑定手机号");
//			}
//		}
		boolean hasBinded = false;
		List<UserVsExAccountDO> list = new ArrayList<UserVsExAccountDO>();
		if (list.isEmpty()) {
			throw new BDException("请先绑定用户外部账号");
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
}
