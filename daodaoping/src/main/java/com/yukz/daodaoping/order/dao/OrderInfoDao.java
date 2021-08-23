package com.yukz.daodaoping.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yukz.daodaoping.app.order.vo.OrderDetailVO;
import com.yukz.daodaoping.order.domain.OrderInfoDO;

/**
 * 订单表
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:03:58
 */
@Mapper
public interface OrderInfoDao {

	OrderInfoDO get(Long id);
	
	List<OrderInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(OrderInfoDO orderInfo);
	
	int update(OrderInfoDO orderInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	OrderDetailVO getOrderDetailById(Long id);
	
	
	List<OrderDetailVO> getOrderDetail(Map<String,Object> map);
}
