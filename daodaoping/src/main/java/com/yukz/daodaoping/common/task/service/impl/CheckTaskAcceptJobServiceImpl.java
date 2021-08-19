package com.yukz.daodaoping.common.task.service.impl;

import com.yukz.daodaoping.app.task.enums.TaskVerifyStatusEnum;
import com.yukz.daodaoping.common.task.service.CheckTaskAcceptJobService;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CheckTaskAcceptJobServiceImpl implements CheckTaskAcceptJobService {
    @Autowired
    private TaskApplyInfoService taskApplyInfoService;
    @Autowired
    private TaskAcceptInfoService taskAcceptInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkTaskAccept(TaskAcceptInfoDO taskAccept) throws Exception{
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
        upTaskAccept.setWorkerId(0l);
        taskAcceptInfoService.update(upTaskAccept);

        //查询任务申请记录，锁定任务申请，防止更新数量时并发
        TaskApplyInfoDO taskApplyInfo = taskApplyInfoService.getByIdForupdate(taskAccept.getTaskId());
        Integer completeNum = taskApplyInfo.getCompletedNumber();
        if (null == completeNum)
            completeNum = 0;
        completeNum += 1;
        TaskApplyInfoDO upTaskApply = new TaskApplyInfoDO();
        upTaskApply.setId(taskApplyInfo.getId());
        upTaskApply.setCompletedNumber(completeNum);
        upTaskApply.setGmtModify(new Date());
        taskApplyInfoService.update(upTaskApply);
    }

    private boolean verifyCertification(TaskAcceptInfoDO taskAccept) {
        return true;
    }
}
