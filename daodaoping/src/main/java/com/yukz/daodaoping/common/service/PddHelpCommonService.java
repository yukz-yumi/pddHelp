package com.yukz.daodaoping.common.service;

import com.yukz.daodaoping.pddhelp.utils.PageData;

/**
 * author：ykz time:2019/7/6 20:44
 */
public interface PddHelpCommonService {
    /**保存导入数据
     * @param pd
     * @throws Exception
     */
    public int saveDevData(PageData pd)throws Exception;
}
