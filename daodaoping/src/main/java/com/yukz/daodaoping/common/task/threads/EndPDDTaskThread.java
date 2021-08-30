package com.yukz.daodaoping.common.task.threads;

import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class EndPDDTaskThread implements Callable<Boolean> {

	private static final Logger logger = LoggerFactory.getLogger(EndPDDTaskThread.class);

	private String threadName;

	private TaskApplyInfoDO taskApplyInfo;




	public EndPDDTaskThread(String threadName, TaskApplyInfoDO taskApplyInfo) {
		super();
		this.threadName = threadName;
		this.taskApplyInfo = taskApplyInfo;
	}

	@Override
	public Boolean call() throws Exception {
		endPDDTaskExecution(taskApplyInfo);
		return true;
	}

	/**
	 * 结束任务：放款、退款、记录流水、更新任务状态
	 * 
	 * @param taskApplyInfoDO
	 */
	public void endPDDTaskExecution(TaskApplyInfoDO taskApplyInfoDO) {
		logger.debug(threadName + "准备执行放款操作");
		try {

		} catch (Exception e) {
			logger.error("放款失败，线程被打断");
		}
	}


}
