package com.yukz.daodaoping.app.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.velocity.runtime.directive.Foreach;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.task.threads.SetTaskExecutionThread;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;

/**
 * 日扫描计划任务
 * 
 * @author micezhao
 *
 */

@Service
public class DailyTaskSchedule {

	@Autowired
	private TaskApplyInfoService taskApplyInfoService;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private RabbitMqHandler mqHandler;

	public void dailyTaskScanner() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("task_status", TaskStatusEnum.WAIT.getStatus());
		paramMap.put("to_days(start_time)", "to_days(now())"); // 只查询当天的任务
		List<TaskApplyInfoDO> list = taskApplyInfoService.list(paramMap);

		int fragment = 0; // 任务计数器
		int threadNum = 1;
		List<TaskApplyInfoDO> tempList = new ArrayList<TaskApplyInfoDO>();
		List<Future<Boolean>> futures = new ArrayList<>();
		for (TaskApplyInfoDO item : list) {
			tempList.add(item);
			fragment++;
			if (fragment % 50 == 0) {
				SetTaskExecutionThread setTaskExecutionThread = new SetTaskExecutionThread("同步发单任务线程:" + threadNum,
						tempList, redissonClient, mqHandler);
				FutureTask<Boolean> future = (FutureTask<Boolean>) taskExecutor.submit(setTaskExecutionThread);
				futures.add(future);
				tempList = new ArrayList<TaskApplyInfoDO>();
				threadNum++;
				// 计数回归
				fragment = 0;
			}
		}
		if (fragment > 0) {
			SetTaskExecutionThread setTaskExecutionThread = new SetTaskExecutionThread("同步发单任务线程:" + threadNum, tempList,
					redissonClient, mqHandler);
			FutureTask<Boolean> future = (FutureTask<Boolean>) taskExecutor.submit(setTaskExecutionThread);
			futures.add(future);
		}
		for (Future<Boolean> future : futures) {
			try {
				future.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
