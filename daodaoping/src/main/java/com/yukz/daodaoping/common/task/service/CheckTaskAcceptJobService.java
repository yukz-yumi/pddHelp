package com.yukz.daodaoping.common.task.service;

import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;

public interface CheckTaskAcceptJobService {
    void checkTaskAccept(TaskAcceptInfoDO taskAccept) throws Exception;
}
