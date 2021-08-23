package com.yukz.daodaoping.order.service;

import com.yukz.daodaoping.app.order.vo.OrderDetailVO;
import com.yukz.daodaoping.order.domain.OrderInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:03:58
 */
public interface OrderInfoService {
	
	OrderInfoDO get(Long id);
	
	List<OrderInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderInfoDO orderInfo);
	
	int update(OrderInfoDO orderInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	OrderDetailVO getOrderDetailById(Long id);
	
	List<OrderDetailVO> getOrderDetailList(Map<String, Object> map);
}
