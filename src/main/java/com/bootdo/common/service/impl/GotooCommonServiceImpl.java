package com.bootdo.common.service.impl;

import com.bootdo.common.dao.GotooCommonDao;
import com.bootdo.common.service.GotooCommonService;
import com.bootdo.gotoo.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author：ykz time:2019/7/6 20:48
 */
@Service
public class GotooCommonServiceImpl implements GotooCommonService {
    @Autowired
    private GotooCommonDao gotooCommonDao;
    /**
     * 保存导入数据
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public int saveDevData(PageData pd) throws Exception {
        return gotooCommonDao.save(pd);
    }
}
