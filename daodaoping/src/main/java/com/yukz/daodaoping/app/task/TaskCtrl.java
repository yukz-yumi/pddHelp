package com.yukz.daodaoping.app.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.app.task.request.TaskApplyRequest;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

@RequestMapping("appInt/task/")
@RestController
public class TaskCtrl {
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
		
	@GetMapping("list/{agentId}/{platform}/{pageNum}/{pageSize}")
	public PageInfo<TaskTypeInfoDO> getAllTaskType(
			@PathVariable("agentId") Long agentId,@PathVariable("platform") String platform,
			@PathVariable("pageNum") int pageNum , @PathVariable("pageSize") int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("agent_id", agentId);
		map.put("platform", agentId);
		map.put("allowed",IsAllowEnum.YES.getStatus());
		List<TaskTypeInfoDO> taskTypeInfoList = taskTypeInfoService.list(map);
		PageInfo<TaskTypeInfoDO> pageResult = new PageInfo<TaskTypeInfoDO>(taskTypeInfoList);
		return pageResult;
	}
	
	/**
	 * TODO 助力金额计算
	 * @param assistantNum
	 * @return
	 */
	@PutMapping("amountCalculate")
	public String taskAmoutCalculate(int assistantNum) {
		
		return "";
	}
	
	/**
	 * 任务申请
	 * @param taskAppleRequest
	 * @return
	 */
	@PostMapping("apply")
	public R taskApply(@RequestBody TaskApplyRequest taskAppleRequest,TaskApplyInfoDO taskApplyInfoDO) {
		try {
			PropertyUtils.copyProperties(taskApplyInfoDO, taskAppleRequest);			
		} catch (Exception e) {
			R.error("对象复制出现异常");
		}
		taskApplyInfoDO.setTaskStatus(TaskStatusEnum.SUSPEND.getStatus()); //未支付时的任务为挂起
		taskApplyInfoService.save(taskApplyInfoDO);
		return R.ok();
	}
	
	/**
	 * 任务提交：生成订单并更新任务状态，并计算任务过期时间
	 * @param tasksubmit
	 * @return
	 */
	@PostMapping("submit")
	public R taskSubmit(@RequestBody TaskApplyRequest taskAppleRequest,TaskApplyInfoDO taskApplyInfoDO) {
		
		return R.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
