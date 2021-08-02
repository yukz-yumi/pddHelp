package com.yukz.daodaoping.app.task;

import java.util.Calendar;
import java.util.Date;
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
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

@RequestMapping("appInt/task/")
@RestController
public class TaskCtrl {
	
	public static final int LIMITED_INTERVAL = 10;
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@Autowired
	private TaskExecuteBiz taskExecuteBiz;
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoDOService;
	
		
	@GetMapping("/list/{agentId}/{platform}/{pageNum}/{pageSize}")
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
	@PutMapping("/amountCalculate")
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
		boolean flag = checkInternalLimited(taskAppleRequest.getStartTime());
		if(!flag) {
			throw new BDException("任务开始时间与当前时间的间隔必须大于10分钟");
		}
		try {
			PropertyUtils.copyProperties(taskApplyInfoDO, taskAppleRequest);			
		} catch (Exception e) {
			return R.error("对象复制出现异常");
		}
		taskExecuteBiz.initTaskApplyInfo(taskApplyInfoDO);
		return R.ok();
	}
	
	@PutMapping("edit")
	public R taskEdit(@RequestBody TaskApplyRequest taskAppleRequest,TaskApplyInfoDO taskApplyInfoDO) {
		boolean flag = checkInternalLimited(taskAppleRequest.getStartTime());
		if(!flag) {
			throw new BDException("任务开始时间与当前时间的间隔必须大于10分钟");
		}
		try {
			PropertyUtils.copyProperties(taskApplyInfoDO, taskAppleRequest);			
		} catch (Exception e) {
			return R.error("对象复制出现异常");
		}
		taskExecuteBiz.editTaskApplyInfo(taskApplyInfoDO);
		return R.ok();
	}
	
	@PutMapping("cancel/{id}")
	public R taskcancel(@PathVariable("id") Long taskApplyInfoId) {
		TaskApplyInfoDO taskApplyInfoDO = taskApplyInfoDOService.get(taskApplyInfoId);
		String taskStatus = taskApplyInfoDO.getTaskStatus();
		if(TaskStatusEnum.getEnumByStatus(taskStatus) != TaskStatusEnum.WAIT 
				&& TaskStatusEnum.getEnumByStatus(taskStatus) != TaskStatusEnum.SUSPEND) {
			throw new BDException("当前任务状态不允许被取消");
		}
		taskExecuteBiz.cancel(taskApplyInfoDO);
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
	
	public boolean checkInternalLimited(Date taskStartTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(LIMITED_INTERVAL, Calendar.MINUTE);
		Calendar taskStartCal = Calendar.getInstance();
		taskStartCal.setTime(taskStartTime);
		if(!taskStartCal.before(cal)) { // 开始时间不满足时间间隔的，不允许初始化
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
}
