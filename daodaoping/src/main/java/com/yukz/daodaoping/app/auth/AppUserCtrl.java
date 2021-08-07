package com.yukz.daodaoping.app.auth;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.annotations.Delete;
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

import com.yukz.daodaoping.app.auth.request.UserExAccountRequest;
import com.yukz.daodaoping.app.auth.vo.LoginParamVo;
import com.yukz.daodaoping.app.auth.vo.UserAgent;
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
@RequestMapping("appInt/user/")
public class AppUserCtrl {
	
	private static final Logger logger = LoggerFactory.getLogger(AppUserCtrl.class);
	
	@Autowired
	private AppUserBiz userBiz;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserVsExAccountService userVsExAccountService;
	
	/**
	 * 通过openId查询用户是否存在
	 * @param openId
	 * @return
	 */
	@GetMapping("login/{agentId}/{openId}")
	public UserAgent getUser(@PathVariable("agentId") String agentId,@PathVariable("openId") String openId, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("open_id", openId);
		param.put("agent_id", Long.parseLong(agentId));
		List<UserInfoDO> userList = userInfoService.list(param);
		if (userList.isEmpty()) {
			return null;
		}
		if (userList.size() > 1) {
			 throw new BDException("more than one userinfo record at same agent");
		}
		UserInfoDO UserInfo = userList.get(0);
		
		UserAgent userAgent = convertor(UserInfo);
		// 向session中传入值
		request.getSession().setAttribute(Constants.USER_AGENT, userAgent);
		
		return userAgent;
	}

	/**
	 * 绑定手机号
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
		String nickName = loginParam.getNickName();
		try {
			userBiz.volidateCodeChecked(validateCode);
			userInfoDO.setMobile(mobile);
			userInfoDO.setOpenId(openId);
			userInfoDO.setAgentId(agentId);
			userInfoDO.setNickName(nickName);
			userBiz.initUser(userInfoDO);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return R.error("用户绑定失败");
		}
		UserAgent userAgent = convertor(userInfoDO);
		// 向session中传入值
		request.getSession().setAttribute(Constants.USER_AGENT, userAgent);
		
		return R.ok().put("userAgent", userAgent);
	}
	
	/**
	 * 绑定外部账户
	 * @param userVsAccountDO
	 * @return
	 */
	@PostMapping("bindExAccount")
	public R bindExAccount(UserAgent userAgent ,@RequestBody UserExAccountRequest userExAccountRequest, UserVsExAccountDO userVsAccountDO) {
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
		userBiz.addExAccountRecord(userInfo, userExAccountRequest);
		return R.ok();
	}
	
	@GetMapping()
	public UserInfoDO getUserInfoDO(UserAgent userAgent) {
		return userInfoService.get(userAgent.getUserId());
	}
	
	@PutMapping("exAccount/turnOff/{id}")
	public R turnOffExAccount(@PathVariable("id") Long id) {
		UserVsExAccountDO currentRecord = userVsExAccountService.get(id);
		currentRecord.setAllowed(IsAllowEnum.NO.getStatus());
		return R.ok();
	}

	@PutMapping("exAccount/turnOn/{id}")
	public R turnOnExAccount(@PathVariable("id") Long id) {
		UserVsExAccountDO currentRecord = userVsExAccountService.get(id);
		currentRecord.setAllowed(IsAllowEnum.YES.getStatus());
		return R.ok();
	}
	
	
	@DeleteMapping("exAccount/{id}")
	public R deleteExAccount(@PathVariable("id") Long id) {
		userVsExAccountService.remove(id);
		return R.ok();
	}
	
	@GetMapping("exAccount")
	public List<UserVsExAccountDO> getExAccount(UserAgent userAgent) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userAgent.getUserId());
		return userVsExAccountService.list(map);
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
		} catch (Exception e) {
			logger.error("对象转换失败");
		}
		return userAgent;
	}
	
}
