package com.yukz.daodaoping.common.task;

import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.common.task.service.CheckTaskAcceptJobService;
import com.yukz.daodaoping.system.service.SysSetService;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.enums.TaskResultEnum;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EndTaskJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(EndTaskJob.class);
	@Autowired
	private TaskAcceptInfoService taskAcceptInfoService;
	@Autowired
    private CheckTaskAcceptJobService checkTaskAcceptJobService;
	@Autowired
    private SysSetService sysSetService;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	logger.info("开始处理任务到期");
        try {
            Map<String, Object> query = new HashMap<String, Object>();
            query.put("taskStatus", TaskAcceptionStatusEunm.END.getStatus());
            query.put("taskResult", TaskResultEnum.UNCOMPLETED.getCode());
            //获取系统配置项

            //任务超时时间 + 接单人完成时间 <= 当前时间
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(new Date());
            cal.add(GregorianCalendar.DATE, 1);

            List<TaskAcceptInfoDO> taskAcceptInfoList = taskAcceptInfoService.list(query);
            if (null != taskAcceptInfoList && taskAcceptInfoList.size()>0) {
                for (TaskAcceptInfoDO taskAccept : taskAcceptInfoList) {
                    checkTaskAcceptJobService.checkTaskAccept(taskAccept);
                }
                logger.info("成功处理任务到期");
            }
        } catch (Exception e) {
            logger.error("定时处理任务到期出错"+e.getMessage());
            e.printStackTrace();
        }
    }


}