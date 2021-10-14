package com.yukz.daodaoping.common.task.threads;

import com.yukz.daodaoping.app.fund.FundBiz;
import com.yukz.daodaoping.app.fund.FundRequest;
import com.yukz.daodaoping.app.fund.enums.FundBizEnum;
import com.yukz.daodaoping.app.fund.enums.FundEnums;
import com.yukz.daodaoping.app.fund.enums.FundTransTypeEnum;
import com.yukz.daodaoping.app.task.enums.TaskVerifyStatusEnum;
import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;
import com.yukz.daodaoping.invitation.domain.InvitationDO;
import com.yukz.daodaoping.invitation.service.InvitationService;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class EndPDDTaskThread implements Callable<Boolean> {

	private static final Logger logger = LoggerFactory.getLogger(EndPDDTaskThread.class);

	private String threadName;

	private TaskApplyInfoDO taskApplyInfo;

	private Map<String, Object> sysSetListMap;

	@Autowired
	private TaskAcceptInfoService taskAcceptInfoService;
	@Autowired
	private FundBiz fundBiz;
	@Autowired
	private InvitationService invitationService;




	public EndPDDTaskThread(String threadName, TaskApplyInfoDO taskApplyInfo, Map<String, Object> sysSetListMap) {
		super();
		this.threadName = threadName;
		this.taskApplyInfo = taskApplyInfo;
		this.sysSetListMap = sysSetListMap;
	}

	@Override
	public Boolean call() throws Exception {
		endPDDTaskExecution(taskApplyInfo, sysSetListMap);
		return true;
	}

	/**
	 * 结束任务：放款、退款、记录流水、更新任务状态
	 * 
	 * @param taskApplyInfoDO
	 */
	@Transactional(rollbackFor = Exception.class)
	public void endPDDTaskExecution(TaskApplyInfoDO taskApplyInfoDO, Map<String, Object> sysSetListMap) {
		logger.debug(threadName + "准备执行放款操作");
		try {
			//查询该任务下的认领记录
			Map<String, Object> acceptQuery = new HashMap<>();
			acceptQuery.put("taskId", taskApplyInfoDO.getId());
			acceptQuery.put("verifyStatus", TaskVerifyStatusEnum.VERIFIED.getStatus());
			List<TaskAcceptInfoDO> acceptList = taskAcceptInfoService.list(acceptQuery);
			if (null != acceptList && acceptList.size()>0) {
				/*// 审核成功的认领记录
				List<TaskAcceptInfoDO> successList = new ArrayList<>();
				for (TaskAcceptInfoDO taskAccept : acceptList) {
					if (StringUtils.equals(taskAccept.getVerifyStatus(),
							TaskVerifyStatusEnum.VERIFIED.getStatus())) {
						successList.add(taskAccept);
					}
				}*/

				// 对审核成功的进行放款
				for (TaskAcceptInfoDO accept : acceptList) {

				}
			}
		} catch (Exception e) {
			logger.error("放款失败，线程被打断");
		}
	}

	/**
	 * 给接单人付款
	 * @param accept
	 * @param taskApplyInfoDO
	 * @param sysSetListMap
	 */
	private void fundOutOperator(TaskAcceptInfoDO accept, TaskApplyInfoDO taskApplyInfoDO, Map<String, Object> sysSetListMap) {
		// 给接单人放款
		FundRequest fundRequest = new FundRequest();
		fundRequest.setAgentId(accept.getAgentId());
		fundRequest.setOpenId(accept.getOpenId());
		fundRequest.setUserId(accept.getUserId());
		fundRequest.setBizType(FundBizEnum.TASK_TRANSFER.getBizType());
		// 根据系统设置计算奖励金额
		//   接单人奖励比例缓存值
		String proportionTask = (String)sysSetListMap.get("proportion_task");
		float proportionTaskFloat = 0;
		if (StringUtils.isNotBlank(proportionTask)) {
			proportionTaskFloat = Float.parseFloat(proportionTask);
		}
		Long totalAmount = taskApplyInfoDO.getTotalAmount();
		Integer taskNum = taskApplyInfoDO.getTaskNumber();
		// 计算每个任务接单人奖励金额
		Long unitPrice = totalAmount / taskNum;
		BigDecimal fundAmountBD = new BigDecimal(unitPrice)
				.multiply(new BigDecimal(proportionTaskFloat))
				.divide(new BigDecimal(100))
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		fundRequest.setFundAmount(fundAmountBD.longValue());
		fundRequest.setTransType(FundTransTypeEnum.FUND_OUT.getType());
		fundRequest.setOrderId(taskApplyInfoDO.getOrderId());

		try {
			FundTransferInfoDO fundTransferRecord = null;
			fundTransferRecord = (FundTransferInfoDO) fundBiz.getTransferReord(FundTransTypeEnum.FUND_OUT,
					taskApplyInfoDO.getOrderId(), FundEnums.WAIT.getStatus());
			fundBiz.fundOutProcess(fundRequest, FundBizEnum.TASK_TRANSFER);
		} catch (Exception e) {
			logger.error("支付奖励失败");
		}
	}

	/**
	 * 给推荐人付款
	 * @param accept
	 * @param taskApplyInfoDO
	 * @param sysSetListMap
	 */
	private void fundOutInvitor(TaskAcceptInfoDO accept, TaskApplyInfoDO taskApplyInfoDO, Map<String, Object> sysSetListMap) {
		Long operatorId = accept.getUserId();
		InvitationDO invitation = invitationService.get(operatorId);
		if (null != invitation) {
			// 给一级推荐人放款
			Long invitationOneUserId = invitation.getDirectUserId();
			String invitationOneOpenId = invitation.getIndirectOpenid();
			FundRequest fundRequest = new FundRequest();
			fundRequest.setAgentId(accept.getAgentId());
			fundRequest.setOpenId(invitationOneOpenId);
			fundRequest.setUserId(invitationOneUserId);
			fundRequest.setBizType(FundBizEnum.COMMISSION.getBizType());
			// 根据系统设置计算佣金金额
			//   直接推荐人佣金比例缓存值
			String proportionCommissionLevel1 = (String)sysSetListMap.get("proportion_commission_level1");
			float proportionCommissionLevel1Float = 0;
			if (StringUtils.isNotBlank(proportionCommissionLevel1)) {
				proportionCommissionLevel1Float = Float.parseFloat(proportionCommissionLevel1);
			}
			Long totalAmount = taskApplyInfoDO.getTotalAmount();
			Integer taskNum = taskApplyInfoDO.getTaskNumber();
			// 计算每个任务接单人奖励金额
			Long unitPrice = totalAmount / taskNum;
			BigDecimal fundAmountBD = new BigDecimal(unitPrice)
					.multiply(new BigDecimal(proportionCommissionLevel1Float))
					.divide(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			fundRequest.setFundAmount(fundAmountBD.longValue());
			fundRequest.setTransType(FundTransTypeEnum.FUND_OUT.getType());
			fundRequest.setOrderId(taskApplyInfoDO.getOrderId());

			try {
				FundTransferInfoDO fundTransferRecord = null;
				fundTransferRecord = (FundTransferInfoDO) fundBiz.getTransferReord(FundTransTypeEnum.FUND_OUT,
						taskApplyInfoDO.getOrderId(), FundEnums.WAIT.getStatus());
				fundBiz.fundOutProcess(fundRequest, FundBizEnum.TASK_TRANSFER);
			} catch (Exception e) {
				logger.error("支付奖励失败");
			}
		}

	}
}
