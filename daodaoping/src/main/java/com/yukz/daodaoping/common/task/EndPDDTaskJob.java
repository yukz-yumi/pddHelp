package com.yukz.daodaoping.common.task;

import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.common.config.ConfigKey;
import com.yukz.daodaoping.common.task.threads.EndPDDTaskThread;
import com.yukz.daodaoping.system.service.SysSetService;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.enums.PlatformEnum;
import com.yukz.daodaoping.task.enums.TaskResultEnum;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.FutureTask;

@Component
public class EndPDDTaskJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(EndPDDTaskJob.class);
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	@Autowired
    private SysSetService sysSetService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	logger.info("开始处理PDD任务到期");
        try {
            Map<String, Object> query = new HashMap<String, Object>();
            query.put("taskStatus", TaskAcceptionStatusEunm.END.getStatus());
            query.put("taskResult", TaskResultEnum.UNCOMPLETED.getCode());
            //获取系统配置项
            List<Object> sysSetKeys = new ArrayList<>();
            sysSetKeys.add("sysSet::sysSet-ttl_completed-"+ PlatformEnum.PDD.getCode()+"-null-"+ ConfigKey.agentId);
            sysSetKeys.add("sysSet::sysSet-proportion_commission_level1-"+ "null-null-"+ ConfigKey.agentId);
            sysSetKeys.add("sysSet::sysSet-proportion_commission_level1-"+ "null-null-"+ ConfigKey.agentId);
            sysSetKeys.add("sysSet::sysSet-proportion_task-"+ PlatformEnum.PDD.getCode()+"-null-"+ ConfigKey.agentId);
            Map<String, Object> sysSetListMap = sysSetService.mapFromRedisHandle(sysSetKeys);

            //  任务完成时间缓存值
            String taskCompleted = (String)sysSetListMap.get("ttl_completed");
            Integer completedSecond = 0;
            if (StringUtils.isNotBlank(taskCompleted)) {
                completedSecond = Integer.parseInt(taskCompleted);
            }
            //任务超时时间 + 接单人完成时间 <= 当前时间
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(new Date());
            cal.add(GregorianCalendar.SECOND, 0-completedSecond);
            query.put("expireTimeEnd", completedSecond);

            List<TaskApplyInfoDO> taskApplayList = taskApplyInfoService.listWithOrder(query);
            if (null != taskApplayList && taskApplayList.size()>0) {
                for (TaskApplyInfoDO taskApply : taskApplayList) {
                    EndPDDTaskThread endPDDTaskThread = new EndPDDTaskThread(
                            "结束任务线程："+taskApply.getId(), taskApply, sysSetListMap);
                    FutureTask<Boolean> future = (FutureTask<Boolean>) taskExecutor.submit(endPDDTaskThread);
                }
            }

            /*List<TaskAcceptInfoDO> taskAcceptInfoList = taskAcceptInfoService.list(query);
            if (null != taskAcceptInfoList && taskAcceptInfoList.size()>0) {
                for (TaskAcceptInfoDO taskAccept : taskAcceptInfoList) {
                    checkTaskAcceptJobService.checkTaskAccept(taskAccept);
                }
                logger.info("成功处理任务到期");
            }*/
        } catch (Exception e) {
            logger.error("定时处理任务到期出错"+e.getMessage());
            e.printStackTrace();
        }
    }


}