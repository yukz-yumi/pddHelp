package com.bootdo.common.service.impl;

import com.bootdo.common.dao.PddHelpCommonDao;
import com.bootdo.common.service.PddHelpCommonService;
import com.bootdo.pddhelp.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author：ykz time:2019/7/6 20:48
 */
@Service
public class PddHelpCommonServiceImpl implements PddHelpCommonService {
    @Autowired
    private PddHelpCommonDao pddHelpCommonDao;
    /**
     * 保存导入数据
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public int saveDevData(PageData pd) throws Exception {
        return pddHelpCommonDao.save(pd);
    }
}
