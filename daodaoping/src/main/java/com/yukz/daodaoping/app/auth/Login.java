package com.yukz.daodaoping.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.service.UserInfoService;

/**
 * 用户登录处理类
 * 
 * @author micezhao
 *
 */
@RestController("/user/login")
public class Login {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public R bindPhone(@RequestParam LoginParamVo loginParam, UserInfoDO userInfoDO) {
		String validateCode = loginParam.getValidateCode();
		String phoneNum = loginParam.getMobile();
		String openId = loginParam.getThirdPartUserId();

		userInfoService.save(null);
		return R.ok();
	}

	@GetMapping("/redis")
	public R redisTest() {
		redisTemplate.opsForValue().set("test", "test");
		return R.ok();
	}

}
