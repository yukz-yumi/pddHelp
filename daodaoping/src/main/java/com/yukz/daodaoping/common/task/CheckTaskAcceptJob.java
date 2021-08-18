package com.yukz.daodaoping.common.task;

import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.app.task.enums.TaskVerifyStatusEnum;
import com.yukz.daodaoping.app.webConfig.UserSessionInterceptor;
import com.yukz.daodaoping.common.utils.Query;
import com.yukz.daodaoping.oa.domain.Response;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckTaskAcceptJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(CheckTaskAcceptJob.class);
	@Autowired
	private TaskAcceptInfoService taskAcceptInfoService;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	logger.info("百事可乐-树莓口味：劲爽口感 无糖配方");
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("taskStatus", TaskAcceptionStatusEunm.END.getStatus());
        query.put("verifyStatusIsnull", "1");
        List<TaskAcceptInfoDO> taskAcceptInfoList = taskAcceptInfoService.list(query);
        if (null != taskAcceptInfoList && taskAcceptInfoList.size()>0) {
            List<TaskAcceptInfoDO> upList = new ArrayList<>();
            for (TaskAcceptInfoDO taskAccept : taskAcceptInfoList) {
                //校验接单人提交的凭证是否有效
                boolean verify = verifyCertification(taskAccept);
                // 通过则更新审核状态为已审核
                TaskAcceptInfoDO upTaskAccept = new TaskAcceptInfoDO();
                upTaskAccept.setId(taskAccept.getId());
                if (verify) {
                    upTaskAccept.setVerifyStatus(TaskVerifyStatusEnum.VERIFIED.getStatus());
                } else {
                    upTaskAccept.setVerifyStatus(TaskVerifyStatusEnum.REJECTED.getStatus());
                }
                upList.add(upTaskAccept);
            }
            //批量更新任务认领的审核状态
            int num = taskAcceptInfoService.batchUpdate(upList);
            logger.info("成功审核任务认领记录"+num+"条");
        }
    }

    private boolean verifyCertification(TaskAcceptInfoDO taskAccept) {
        return true;
    }
}