package com.yukz.daodaoping.app.task;

import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.task.request.TaskApplyRequest;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

/**
 * 发单/接单 业务处理类
 * @author micezhao
 *
 */
@Service
public class TaskExecuteBiz {
	
	// 申请任务 时间-> 决定任务是否立即开始
	// 计算配置的任务过期时间
	// 定时任务扫描
	// 接受延迟任务的处理
	// 取消任务：是否申请退款
	
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	/**
	 * 初始化任务记录
	 * @return
	 */
	public boolean initTaskApplyInfo(TaskApplyInfoDO taskApplyInfoDO,TaskApplyRequest taskAppleRequest ) {
		try {
			PropertyUtils.copyProperties(taskApplyInfoDO, taskAppleRequest);			
		} catch (Exception e) {
			R.error("对象复制出现异常");
		}
		
		taskApplyInfoDO.setTaskStatus(TaskStatusEnum.SUSPEND.getStatus()); //未支付时的任务为挂起
		taskApplyInfoService.save(taskApplyInfoDO);
		return true;
	}
	
	public Date getTaskExpireTime(TaskApplyInfoDO taskApplyInfoDO) {
		TaskTypeInfoDO taskInfoDo = taskTypeInfoService.get(taskApplyInfoDO.getTaskId());
		int interval = taskInfoDo.getExpirtTime();
		
		return 
	}
	
}
