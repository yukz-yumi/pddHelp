package com.yukz.daodaoping.common.dao;

import com.yukz.daodaoping.system.utils.PageData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公共信息
 * @author ykz
 * @email 1992lcg@163.com
 * @date 2019-07-06 22:38:30
 */
@Mapper
public interface PddHelpCommonDao {

    int save(PageData obj);
}
