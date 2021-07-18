package com.bootdo.common.utils;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将查询结果集中指定的字段进行转换，通过字典或者列表进行转化
 * @Author yukz
 */
public class QueryListConvertUtils {

	/**
	 * 根据传递的列表进行转化
	 * @param source 需要转化的原列表
	 * @param className 原列表中的对象类型名称（需加上完整的包名）
	 * @param convertFeilds 需要转化的字段数组
	 * @param contrastMap 进行转化的map
	 * @return
	 */
	public static void convertFromList(List<?> source, String className, String[] convertFeilds, Map<String, Object> contrastMap) {
		try {
			Class<?> tClass = Class.forName(className);
			//遍历原列表
			for(int i=0; i<source.size(); i++) {
				Object obj = source.get(i);
				//遍历需要转化的字段
				for (int j=0; j<convertFeilds.length; j++) {
					String convertFeild = convertFeilds[j];
					//获取原列表该字段对应的值
					PropertyDescriptor pdSource = new PropertyDescriptor(convertFeild, tClass);
					Method rM = pdSource.getReadMethod();//获得读方法
					String value = (String) rM.invoke(obj);
					String convertValue = (String)contrastMap.get(value);

					PropertyDescriptor pd = new PropertyDescriptor(convertFeild, tClass);
					Method wM = pd.getWriteMethod();//获得写方法
					wM.invoke(obj, convertValue);
				}

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/*List<DeviceBaseinfoDO> sourceList = new ArrayList<>();
		DeviceBaseinfoDO deviceBaseinfoDO1 = new DeviceBaseinfoDO();
		deviceBaseinfoDO1.setF2("119");
		sourceList.add(deviceBaseinfoDO1);
		DeviceBaseinfoDO deviceBaseinfoDO2 = new DeviceBaseinfoDO();
		deviceBaseinfoDO2.setF2("121");
		sourceList.add(deviceBaseinfoDO2);

		String[] feilds = {"f2"};
		Map<String, Object> constrastMap = new HashMap<>();
		constrastMap.put("119", "轨道养护");
		constrastMap.put("121", "轨道检查");

		//打印转化后的列表
		QueryListConvertUtils.convertFromList(sourceList,
				"com.bootdo.gotoo.domain.DeviceBaseinfoDO",feilds,constrastMap);
		for(int i=0; i<sourceList.size();i++){
			DeviceBaseinfoDO temp = sourceList.get(i);
			System.out.println(temp.getF2());
		}*/
	}
}
