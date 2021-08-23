package com.yukz.daodaoping.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.app.order.vo.OrderDetailVO;
import com.yukz.daodaoping.order.dao.OrderInfoDao;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;



@Service
public class OrderInfoServiceImpl implements OrderInfoService {
	@Autowired
	private OrderInfoDao orderInfoDao;
	
	@Override
	public OrderInfoDO get(Long id){
		return orderInfoDao.get(id);
	}
	
	@Override
	public List<OrderInfoDO> list(Map<String, Object> map){
		return orderInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return orderInfoDao.count(map);
	}
	
	@Override
	public int save(OrderInfoDO orderInfo){
		return orderInfoDao.save(orderInfo);
	}
	
	@Override
	public int update(OrderInfoDO orderInfo){
		return orderInfoDao.update(orderInfo);
	}
	
	@Override
	public int remove(Long id){
		return orderInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return orderInfoDao.batchRemove(ids);
	}

	@Override
	public OrderDetailVO getOrderDetailById(Long id) {
		
		return orderInfoDao.getOrderDetailById(id);
	}

	@Override
	public List<OrderDetailVO> getOrderDetailList(Map<String, Object> map) {
		
		return orderInfoDao.getOrderDetail(map);
	}
	
	
}
