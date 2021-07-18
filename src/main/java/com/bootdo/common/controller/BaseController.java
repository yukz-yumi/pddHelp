package com.bootdo.common.controller;

import com.bootdo.common.service.GotooCommonService;
import com.bootdo.common.utils.R;
import com.bootdo.gotoo.utils.ObjectExcelRead;
import com.bootdo.gotoo.utils.PageData;
import com.bootdo.gotoo.utils.UuidUtil;
import com.bootdo.gotoo.utils.XmlParser;
import com.bootdo.system.domain.UserToken;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}

	public String getName() {
		return getUser().getName();
	}

	/**得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}

	public String getSegmentCode() {
		return getUser().getSegmentCode();
	}

	public R commonImport(String filePath, String fileName, String path, Logger logger, GotooCommonService gotooCommonService) {
		Element element = XmlParser.getRootNode(path);
		String desc = element.attributeValue("desc");
		List<Element> listE = XmlParser.getChildList(element);
		logger.info("导入" + desc + "到数据库");

		String tablename = element.attributeValue("tableName");
		String startRow = element.attributeValue("startRow");
		String startColumn = element.attributeValue("startColumn");
		String notNull = element.attributeValue("notNull");//渠道编码
		String sheets = element.attributeValue("sheets");
		String[] sheet = sheets.split(",");

		// 该集合用于存储读取到的所有excel的数据
		Map<String, List<PageData>> mapData = new HashMap<String, List<PageData>>();
		for (int i = 0; i < sheet.length; i++) {
			String key = sheet[i];
			List<PageData> listPdT = (List) ObjectExcelRead.readExcel(filePath,
					fileName, Integer.parseInt(startRow),
					Integer.parseInt(startColumn), Integer.parseInt(key));
			mapData.put(key, listPdT);
		}

		// 默认循环主表信息
		List<PageData> listPd = mapData.get("0");

		if (listPd != null && listPd.size() > 0) {

			for (int i = 0; i < listPd.size(); i++) {

				PageData pageDataT = listPd.get(i);
				// 这个限制条件一定要加
				if (listPd.get(i).getString(notNull) == null
						|| "".equalsIgnoreCase(listPd.get(i).getString(notNull))) {

					continue;
				}

				StringBuffer fieldsb = new StringBuffer();
				StringBuffer fieldValues = new StringBuffer();
				// 保存的对象
				PageData pd0 = new PageData();
				pd0.put("tableName", tablename);
				// 主键
				String uuid = this.get32UUID();
				fieldsb.append("FID");
				fieldValues.append("null");

				for (int j = 0; j < listE.size(); j++) {
					String isRead = XmlParser.getAttribute(listE.get(j),
							"isRead").getValue();
					// 如果不需要读取，则直接跳出本次循环
					if ("0".equals(isRead)) {
						continue;
					}

					fieldsb.append(",");
					fieldValues.append(",");

					String rname = XmlParser
							.getAttribute(listE.get(j), "rname").getValue();
					String sname = XmlParser
							.getAttribute(listE.get(j), "sname").getValue();
					String sheetItem = XmlParser.getAttribute(listE.get(j),
							"sheet").getValue();
					String defuleValue = XmlParser.getAttribute(listE.get(j),
							"defuleValue").getValue();
					Attribute dateFormatAttr = XmlParser.getAttribute(listE.get(j),
							"dateFormat");
					String dateFormat = null;
					if (null != dateFormatAttr) {
						dateFormat = dateFormatAttr.getValue();
					}
					boolean flag = XmlParser.hasChild(listE.get(j));
					String values = "";
					// 有公式
//					if (flag) {
//						values = calcFormula(listE.get(j), pageDataT, mapData,
//								notNull);
//
//					} else {
					// 如果不是默认主表中的数据，则需要取出对应的数据
					if (!"0".equals(sheetItem)) {
						List<PageData> t1 = mapData.get(sheetItem);
						if (t1 != null && t1.size() > 0) {
							String notNullValue = pageDataT
									.getString(notNull);
							for (int k = 0; k < t1.size(); k++) {
								PageData t2 = t1.get(k);
								String notNullValue1 = t2
										.getString(notNull);
								if (notNullValue
										.equalsIgnoreCase(notNullValue1)) {
									values = t2.getString(rname);
									break;
								}
							}
						}
					} else {
						values = pageDataT.getString(rname);
					}
					//判断如果是日期格式不为空，则需要进行格式化
					try {
						if (com.bootdo.common.utils.StringUtils.isNotEmpty(dateFormat)) {
							// 把作业日期格式统一成yyyy-mm-dd
							if (!values.contains("-")) {
								Date workDate = new SimpleDateFormat("yyyy.MM.dd").parse(values);
								values = new SimpleDateFormat("yyyy-MM-dd").format(workDate);
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
						logger.error("导入数据出错：", e);
					}
//					}
					// 如果值为空，则给设置的默认值
					if (values == null || "".equals(values)) {
						values = defuleValue;
					}
					fieldsb.append(sname);
					fieldValues.append("'");
					fieldValues.append(values);
					fieldValues.append("'");
				}
				// 生成公共字段
				importCommonField(fieldsb, fieldValues);
				pd0.put("fields", fieldsb.toString());
				pd0.put("fieldValues", fieldValues.toString());
				try {
					if (gotooCommonService.saveDevData(pd0) <= 0) {
						return R.error();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("导入数据出错：", e);
				}
			}

		}
		return R.ok();
	}

	public void importCommonField(StringBuffer fieldsb, StringBuffer fieldValues) {
		Date now = new Date();
		String nowDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
		fieldsb.append(",operator");
		fieldValues.append(",'");
		fieldValues.append(getName());
		fieldValues.append("'");
		fieldsb.append(",gmt_create");
		fieldValues.append(",'");
		fieldValues.append(nowDateStr);
		fieldValues.append("'");
		fieldsb.append(",gmt_modified");
		fieldValues.append(",'");
		fieldValues.append(nowDateStr);
		fieldValues.append("'");
		fieldsb.append(",SEGMENT_CODE");
		fieldValues.append(",'");
		fieldValues.append(getSegmentCode());
		fieldValues.append("'");
	}
}