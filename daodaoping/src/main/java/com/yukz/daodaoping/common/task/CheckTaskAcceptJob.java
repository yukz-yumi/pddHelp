package com.yukz.daodaoping.common.task;

import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.app.task.enums.TaskVerifyStatusEnum;
import com.yukz.daodaoping.common.task.service.CheckTaskAcceptJobService;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckTaskAcceptJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(CheckTaskAcceptJob.class);
	@Autowired
	private TaskAcceptInfoService taskAcceptInfoService;
	@Autowired
    private CheckTaskAcceptJobService checkTaskAcceptJobService;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	logger.info("开始审核任务认领记录");
        try {
            Map<String, Object> query = new HashMap<String, Object>();
            query.put("taskStatus", TaskAcceptionStatusEunm.END.getStatus());
            query.put("verifyStatus", TaskVerifyStatusEnum.UNVERIFIED.getStatus());
            List<TaskAcceptInfoDO> taskAcceptInfoList = taskAcceptInfoService.list(query);
            if (null != taskAcceptInfoList && taskAcceptInfoList.size()>0) {
                for (TaskAcceptInfoDO taskAccept : taskAcceptInfoList) {
                    checkTaskAcceptJobService.checkTaskAccept(taskAccept);
                }
                logger.info("成功审核任务认领记录");
            }
        } catch (Exception e) {
            logger.error("定时审核任务认领出错"+e.getMessage());
            e.printStackTrace();
        }
    }


}