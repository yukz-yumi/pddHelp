package com.yukz.daodaoping.app.auth;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.auth.request.UserExAccountRequest;
import com.yukz.daodaoping.app.auth.vo.UserExAccountVo;
import com.yukz.daodaoping.app.enums.ExAccountEnum;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.app.enums.UserStatusEnum;
import com.yukz.daodaoping.common.SerialNumGenerator;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.domain.UserVsExAccountDO;
import com.yukz.daodaoping.user.service.UserInfoService;
import com.yukz.daodaoping.user.service.UserVsExAccountService;

/**
 * 用于处理登录流程的业务类
 * @author micezhao
 *
 */
@Service
public class AppUserBiz {
	
	private static final long INIT_SCORE = 100;
	
	private static final String INIT_USER_GRADE = "1";
	
	private static final String TIMEFORMAT = "yyyyMMddHH";
	
	@Value("${serial.prefix.user}")
	private String prefix;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SerialNumGenerator generator;
	
	@Autowired
	private UserVsExAccountService userVsExAccountService;
		
	/**
	 * 检查用户在当前机构是否已经存在
	 * @param openId
	 * @param agentId
	 * @return 不存在返回 true/ 存在返回 false
	 */
	public boolean isUserExisted (String openId, Long agentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openId", openId);
		params.put("agentId", agentId);
		List<UserInfoDO> list = userInfoService.list(params);
		return list.isEmpty();
	}	
	
	public UserInfoDO hasMobilebind (String openId, Long agentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openId", openId);
		params.put("agentId", agentId);
		params.put("userStatus", UserStatusEnum.NORMAL.getUserStatus());
		List<UserInfoDO> list = userInfoService.list(params);
		if(!list.isEmpty()) {
			return list.get(0);
		}else {
			return null;
		}
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
		String serialId=generator.getSerialBizId(prefix, TIMEFORMAT, 4);
		userInfoDO.setUserId(Long.parseLong(serialId));
		userInfoDO.setScores(INIT_SCORE);
		userInfoDO.setUserGrade(INIT_USER_GRADE);
		userInfoDO.setGmtCreate(new Date());
//		userInfoDO.setUserStatus(UserStatusEnum.UNBIND.getUserStatus());
		return userInfoService.save(userInfoDO) >= 1?true:false;
	}
	
	public UserInfoDO getUserInfoByUserId(Long userId, Long agentId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId",userId);
		map.put("agentId",agentId);
		List<UserInfoDO> list = userInfoService.list(map);
		if(list.isEmpty()) {
			return null;
		}
		if(list.size()>1) {
			throw new BDException("存在重复用户信息");
		}
		return list.get(0);
	}
	
	/**
	 * 新增外部账号的初始状态是 待审核
	 * @param userInfo
	 * @param exAccoutRequest
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public UserExAccountVo addExAccountRecord(UserInfoDO userInfo, UserExAccountRequest exAccoutRequest) {
		boolean validated = exAccountValidate(exAccoutRequest.getAccountType(), exAccoutRequest.getAccount());
		if(!validated) {
			throw new BDException("外部账号不合法");
		}
		UserVsExAccountDO userExAccountDO = new UserVsExAccountDO();
		userExAccountDO.setUserId(userInfo.getUserId());
		userExAccountDO.setAgentId(userInfo.getAgentId());
		userExAccountDO.setAccountType(exAccoutRequest.getAccountType());
		userExAccountDO.setAccount(exAccoutRequest.getAccount());
		userExAccountDO.setAccountImg(exAccoutRequest.getAccountImg());
		userExAccountDO.setAccountStatus(ExAccountEnum.AVAILABLE.getExAccountStatus()); 
		userExAccountDO.setAllowed(IsAllowEnum.YES.getStatus());
		userExAccountDO.setGmtCreate(new Date());
		int i = userVsExAccountService.save(userExAccountDO);
		if(i<1) {
			throw new BDException("外部账号记录新增失败");
		}
		UserExAccountVo vo = new UserExAccountVo();
		try {
			PropertyUtils.copyProperties(vo,userInfo);			
		}catch(Exception ex) {
			throw new BDException("对象转换失败");
		}
		
		return vo;

	}
	
	/**
	 * TODO 调用外部接口验证待绑定的账号合法性
	 * @param accountType
	 * @param account
	 * @return
	 */
	public boolean exAccountValidate(String accountType, String account) {
		
		return true;
	}
}
