package com.yukz.daodaoping.app.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.domain.UserVsExAccountDO;
import com.yukz.daodaoping.user.service.UserInfoService;

/**
 * 用户登录处理类
 * 
 * @author micezhao
 *
 */
@RestController
@RequestMapping("appInt/user")
public class Login {

	@Autowired
	private LoginBiz loginBiz;

	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 通过openId查询用户信息
	 * @param openId
	 * @return
	 */
	@GetMapping("/{agentId}/{openId}")
	public UserInfoDO getUser(@PathVariable("agentId") String agentId,@PathVariable("openId") String openId) {
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
		return userList.get(0);
	}

	/**
	 * 绑定手机号
	 * 
	 * @param loginParam
	 * @param userInfoDO
	 * @return
	 */
	@PostMapping("bindMobile")
	public R bindPhone(@RequestParam LoginParamVo loginParam, UserInfoDO userInfoDO) {
		String validateCode = loginParam.getValidateCode();
		String mobile = loginParam.getMobile();
		String openId = loginParam.getThirdPartUserId();
		try {
			loginBiz.volidateCodeChecked(validateCode);
			userInfoDO.setMobile(mobile);
			userInfoDO.setOpenId(openId);
			loginBiz.initUser(userInfoDO);
		} catch (BDException ex) {
			return R.error(ex.getMsg());
		}
		return R.ok();
	}
	
	
	public R bindExAccount(UserVsExAccountDO userVsAccountDO) {
		return null;
	}

}
