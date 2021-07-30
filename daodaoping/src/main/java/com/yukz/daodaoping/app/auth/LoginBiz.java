package com.yukz.daodaoping.app.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.common.SerialNumGenerator;
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.service.UserInfoService;

/**
 * 用于处理登录流程的业务类
 * @author micezhao
 *
 */
@Service
public class LoginBiz {
	
	private static final long INIT_SCORE = 100;
	
	private static final String INIT_USER_GRADE = "1";
	
	private static final String TIMEFORMAT = "yyyyMMddHH";
	
	@Value("${serial.prefix.user}")
	private String prefix;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SerialNumGenerator generator;
		
	/**
	 * 检查用户在当前机构是否已经存在
	 * @param openId
	 * @param agentId
	 * @return 不存在返回 true/ 存在返回 false
	 */
	public boolean isUserExisted (String openId, Long agentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("open_id", openId);
		params.put("agent_id", agentId);
		List<UserInfoDO> list = userInfoService.list(params);
		return list.isEmpty();
	}	
	
	public boolean volidateCodeChecked(String validateCode) {
		// TODO 调用外部接口检查验证码是否正确
		return true;
	}
	
	/**
	 * 初始化一条新的用户信息
	 * @param userInfoDO
	 * @return
	 */
	public boolean initUser(UserInfoDO userInfoDO){
		String serialId=generator.getSerialBizId(prefix, TIMEFORMAT, 8);
		userInfoDO.setUserId(Long.parseLong(serialId));
		userInfoDO.setScores(INIT_SCORE);
		userInfoDO.setUserGrade(INIT_USER_GRADE);
		userInfoDO.setGmtCreate(new Date());
		userInfoDO.setUserStatus(UserStatusEnum.UNBIND.getUserStatus());
		return userInfoService.save(userInfoDO) >= 1?true:false;
	}
	
	
	
}
