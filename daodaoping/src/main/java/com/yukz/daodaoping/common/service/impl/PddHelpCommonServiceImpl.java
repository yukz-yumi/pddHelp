package com.yukz.daodaoping.common.service.impl;

import com.yukz.daodaoping.common.dao.PddHelpCommonDao;
import com.yukz.daodaoping.common.service.PddHelpCommonService;
import com.yukz.daodaoping.system.utils.PageData;
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
