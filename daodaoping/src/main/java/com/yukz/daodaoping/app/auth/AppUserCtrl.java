package com.yukz.daodaoping.app.auth;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yukz.daodaoping.app.auth.request.UserExAccountRequest;
import com.yukz.daodaoping.app.auth.vo.LoginParamVo;
import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.auth.vo.UserExAccountVo;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.app.enums.UserStatusEnum;
import com.yukz.daodaoping.app.webConfig.Constants;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.domain.UserVsExAccountDO;
import com.yukz.daodaoping.user.service.UserInfoService;
import com.yukz.daodaoping.user.service.UserVsExAccountService;

/**
 * 用户登录处理类
 * 
 * @author micezhao
 *
 */

@RestController
@RequestMapping("/appInt/user/")
public class AppUserCtrl {
	
	private static final Logger logger = LoggerFactory.getLogger(AppUserCtrl.class);
	
	@Autowired
	private AppUserBiz userBiz;
	
	@Autowired
	private RedisHandler redisHandler;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserVsExAccountService userVsExAccountService;
	
	/**
	 * TODO 异常抛出有问题
	 * 通过openId查询用户是否存在
	 * @param openId
	 * @return
	 */
	@PostMapping("login")
	public R getUser(@RequestBody LoginParamVo loginParam, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openId", loginParam.getOpenId());
		param.put("agentId", loginParam.getAgentId());
		List<UserInfoDO> userList = userInfoService.list(param);
		if (userList.isEmpty()) {
			return R.ok().put("data", UserStatusEnum.UNBIND.getUserStatus());
		}
		if (userList.size() > 1) {
			return R.error("存在重复的openId");
		}
		UserInfoDO UserInfo = userList.get(0);
	
		String sessionId = request.getSession().getId();
		UserAgent userAgent = convertor(UserInfo);
		userAgent.setSessionId(sessionId);
		// 向 redis 中存入
		redisHandler.hmSet(Constants.USER_AGENT, sessionId, JSON.toJSON(userAgent));
		return R.ok().put("data", userAgent);
	}

	/**
	 * 绑定手机号（注册用户）
	 * 
	 * @param loginParam
	 * @param userInfoDO
	 * @return
	 */
	@PostMapping("bindMobile")
	public R bindPhone(@RequestBody LoginParamVo loginParam, UserInfoDO userInfoDO, HttpServletRequest request) {
		String validateCode = loginParam.getValidateCode();
		String mobile = loginParam.getMobile();
		String openId = loginParam.getOpenId();
		Long agentId = loginParam.getAgentId();
		String headImgUrl = loginParam.getHeadImgUrl();
		String nickName = loginParam.getNickName();
		if(userBiz.hasMobilebind(openId, agentId) != null ) {
			userInfoDO = userBiz.hasMobilebind(openId, agentId);
		}else {			
			try {
				userBiz.volidateCodeChecked(validateCode);
				userInfoDO.setMobile(mobile);
				userInfoDO.setOpenId(openId);
				userInfoDO.setHeadImgUrl(headImgUrl);
				userInfoDO.setAgentId(agentId);
				userInfoDO.setNickName(nickName);
				userBiz.initUser(userInfoDO);
				userInfoDO.setUserStatus(UserStatusEnum.NORMAL.getUserStatus());
				userInfoService.update(userInfoDO);
				
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return R.error("用户绑定失败");
			}
		}
		UserAgent userAgent = convertor(userInfoDO);
		// 向session中传入值
		request.getSession().setAttribute(Constants.USER_AGENT, userAgent);
		
		return R.ok().put("data", userAgent);
	}
	
	/**
	 * 绑定外部账户
	 * @param userVsAccountDO
	 * @return
	 */
	@PostMapping("bindExAccount")
	public R bindExAccount(UserAgent userAgent ,@RequestBody UserExAccountRequest userExAccountRequest, UserVsExAccountDO userVsAccountDO
			, HttpServletRequest request) {
		Long userId = userAgent.getUserId();
		Long agentId = userAgent.getAgentId();
		UserInfoDO userInfo = null;
		try {
			 userInfo = userBiz.getUserInfoByUserId(userId, agentId);
			 if(userInfo == null) {
				 throw new BDException("当前用户不存在");
			 }
			 if( UserStatusEnum.getEnumByStatus(userInfo.getUserStatus()) == UserStatusEnum.FORBIDDEN) {
				 throw new BDException("当前用户为:"+UserStatusEnum.FORBIDDEN.getDesc());
			 }
		}catch (BDException  ex) {
			return R.error(ex.getMessage());
		}
		UserExAccountVo vo = userBiz.addExAccountRecord(userInfo, userExAccountRequest);
		userAgent.setExAccountList(vo.getExAccountList());
		request.getSession().setAttribute(Constants.USER_AGENT, userAgent);
		return R.ok();
	}
	
	@GetMapping
	public R getUserInfoDO(UserAgent userAgent) {
		logger.debug("userId:"+userAgent.getUserId());
		Long userId = userAgent.getUserId();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<UserInfoDO> list = userInfoService.list(map);
		return R.ok().put("data",  list.get(0));
	}
	
	@PutMapping("exAccount/turnOff/{id}")
	public R turnOffExAccount(@PathVariable("id") Long id) {
		UserVsExAccountDO currentRecord = userVsExAccountService.get(id);
		currentRecord.setAllowed(IsAllowEnum.NO.getStatus());
		currentRecord.setGmtModify(new Date());
		userVsExAccountService.update(currentRecord);
		return R.ok();
	}

	@PutMapping("exAccount/turnOn/{id}")
	public R turnOnExAccount(@PathVariable("id") Long id) {
		UserVsExAccountDO currentRecord = userVsExAccountService.get(id);
		currentRecord.setAllowed(IsAllowEnum.YES.getStatus());
		currentRecord.setGmtModify(new Date());
		userVsExAccountService.update(currentRecord);
		return R.ok();
	}
	
	
	@DeleteMapping("exAccount/{id}")
	public R deleteExAccount(@PathVariable("id") Long id) {
		userVsExAccountService.remove(id);
		return R.ok();
	}
	
	@GetMapping("exAccount")
	public R getExAccount(UserAgent userAgent) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userAgent.getUserId());
		return R.ok().put("data", userVsExAccountService.list(map));
	}
	
	@PutMapping("exAccount/{id}")
	public UserVsExAccountDO updateExAccount(@RequestBody UserExAccountRequest userExAccountRequest) {
		UserVsExAccountDO userVsExAccountDO = new UserVsExAccountDO();
		try {
			PropertyUtils.copyProperties(userVsExAccountDO,userExAccountRequest);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new BDException("对象属性复制出现异常");
		}
		userVsExAccountService.update(userVsExAccountDO);
		return userVsExAccountDO;
	}
	
	
	public UserAgent convertor(UserInfoDO userInfo) {
		UserAgent userAgent = new UserAgent(); 
		try {
			PropertyUtils.copyProperties(userAgent, userInfo);
			Long agentId = userInfo.getAgentId();
			Long userId = userInfo.getUserId();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("agentId", agentId);
			List<UserVsExAccountDO> exAccountList = userVsExAccountService.list(map);
			if(!exAccountList.isEmpty()) {
				userAgent.setExAccountList(exAccountList);
			}
		} catch (Exception e) {
			logger.error("对象转换失败");
		}
		return userAgent;
	}
	
}
