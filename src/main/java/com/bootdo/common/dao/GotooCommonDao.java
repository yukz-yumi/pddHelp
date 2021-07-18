package com.bootdo.common.dao;

import com.bootdo.gotoo.utils.PageData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 国铁公共信息
 * @author ykz
 * @email 1992lcg@163.com
 * @date 2019-07-06 22:38:30
 */
@Mapper
public interface GotooCommonDao {

	int save(PageData obj);
}
