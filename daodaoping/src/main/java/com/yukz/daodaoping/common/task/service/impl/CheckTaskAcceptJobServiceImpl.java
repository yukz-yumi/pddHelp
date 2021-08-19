package com.yukz.daodaoping.common.task.service.impl;

import com.yukz.daodaoping.app.task.enums.TaskVerifyStatusEnum;
import com.yukz.daodaoping.approve.domain.VerifyRecordInfoDO;
import com.yukz.daodaoping.approve.service.VerifyRecordInfoService;
import com.yukz.daodaoping.common.task.service.CheckTaskAcceptJobService;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private VerifyRecordInfoService verifyRecordInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkTaskAccept(TaskAcceptInfoDO taskAccept) throws Exception{
        Date now = new Date();
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
        upTaskAccept.setGmtModify(now);
        taskAcceptInfoService.update(upTaskAccept);

        //产生审核记录
        VerifyRecordInfoDO verifyRecord = new VerifyRecordInfoDO();
        verifyRecord.setAgentId(taskAccept.getAgentId());
        verifyRecord.setExpireTime(now);
        verifyRecord.setGmtCreate(now);
        verifyRecord.setGmtModify(now);
        verifyRecord.setTaskAcceptId(taskAccept.getId());
        verifyRecord.setTaskId(taskAccept.getTaskId());
        verifyRecord.setWorkerId(0l);
        verifyRecord.setOperation(upTaskAccept.getVerifyStatus());
        if (!verify) {
            verifyRecord.setInvalidateReason("系统自动审核未通过");
        }
        verifyRecordInfoService.save(verifyRecord);

        //查询任务申请记录，锁定任务申请，防止更新数量时并发
        TaskApplyInfoDO taskApplyInfo = taskApplyInfoService.getByIdForupdate(taskAccept.getTaskId());
        Integer completeNum = taskApplyInfo.getCompletedNumber();
        if (null == completeNum)
            completeNum = 0;
        completeNum += 1;
        TaskApplyInfoDO upTaskApply = new TaskApplyInfoDO();
        upTaskApply.setId(taskApplyInfo.getId());
        upTaskApply.setCompletedNumber(completeNum);
        upTaskApply.setGmtModify(now);
        taskApplyInfoService.update(upTaskApply);
    }

    private boolean verifyCertification(TaskAcceptInfoDO taskAccept) {
        Date now = new Date();
        boolean rtn = false;
        if (StringUtils.isNotBlank(taskAccept.getCertificationUrl())) {
            rtn = true;
        }
        return true;
    }
}
