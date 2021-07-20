package com.yukz.daodaoping.pddhelp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 从EXCEL导入到数据库
 * 创建人：FH Q313596790
 * 创建时间：2014年12月23日
 * @version
 */
public class ObjectExcelRead {

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @param qdbm //渠道编码，校验是否是7位  不是直接返回空
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum,String qdbm) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			Workbook wb = null;
			if(isExcel2003(filename))
			{
				wb = new HSSFWorkbook(fi);
			}
			else if(isExcel2007(filename))
			{
				wb = new XSSFWorkbook(fi);
			}
			Sheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始

				PageData varpd = new PageData();
				Row row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始
					Cell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;

					if (null != cell) {

						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
							case 0://数字和日期
								if(HSSFDateUtil.isCellDateFormatted(cell))
								{
									//  如果是date类型则 ，获取该cell的date值
									cellValue = DateUtil.getDays(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								}
								else
								{
									cell.setCellType(HSSFCell.CELL_TYPE_STRING);
									cellValue = cell.getStringCellValue();
//								BigDecimal big=new BigDecimal(cell.getNumericCellValue());
//								cellValue = big.toString();
//								//解决1234.0  去掉后面的.0
//				                if(null!=cellValue&&!"".equals(cellValue.trim())){
//				                     String[] item = cellValue.split("[.]");
//				                     if(1<item.length&&"0".equals(item[1])){
//				                    	 cellValue=item[0];
//				                     }else if(1<item.length&&item[1].length()>5)
//				                     {
//				                    	 cellValue = item[0]+"."+item[1].substring(0, 4);
//				                     }
//				                }
								}

								//cellValue = String.valueOf((int) cell.getNumericCellValue());
								break;
							case 1://字符串
								cellValue = cell.getStringCellValue();
								break;
							case 2://公式
								//读公式计算值
								cellValue = String.valueOf(cell.getNumericCellValue());
								if (cellValue.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
									cellValue = cell.getStringCellValue().toString();
								}
								break;
							case 3://空
								cellValue = "";
								break;
							case 4://boolean
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case 5://错误
								cellValue ="";
								break;
						}
					} else {
						cellValue = "";
					}
					String indexs = qdbm.substring(3);
					//校验渠道编码是7位
					if(Integer.parseInt(indexs) == j)
					{
						if(cellValue.length()!=7)
						{
							return null;
						}
					}
					varpd.put("var"+j, cellValue);

				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return varList;
	}
	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			Workbook wb = null;
			if(isExcel2003(filename))
			{
				wb = new HSSFWorkbook(fi);
			}
			else if(isExcel2007(filename))
			{
				wb = new XSSFWorkbook(fi);
			}
			Sheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始

				PageData varpd = new PageData();
				Row row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始

					Cell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {

						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
							case 0://数字和日期
								if(HSSFDateUtil.isCellDateFormatted(cell))
								{
									//  如果是date类型则 ，获取该cell的date值
									cellValue = DateUtil.getDays(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								}
								else
								{
									BigDecimal big=new BigDecimal(cell.getNumericCellValue());
									cellValue = big.toString();
									//解决1234.0  去掉后面的.0
									if(null!=cellValue&&!"".equals(cellValue.trim())){
										String[] item = cellValue.split("[.]");
										if(1<item.length&&"0".equals(item[1])){
											cellValue=item[0];
										}else if(1<item.length&&item[1].length()>3)
										{
											cellValue = item[0]+"."+item[1].substring(0, 2);
										}
									}
								}

								//cellValue = String.valueOf((int) cell.getNumericCellValue());
								break;
							case 1://字符串
								cellValue = cell.getStringCellValue();
								break;
							case 2://公式
								//读公式计算值
								cellValue = String.valueOf(cell.getNumericCellValue());
								if (cellValue.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
									cellValue = cell.getStringCellValue().toString();
								}
								break;
							case 3://空
								cellValue = "";
								break;
							case 4://boolean
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case 5://错误
								cellValue ="";
								break;
						}
					} else {
						cellValue = "";
					}

					varpd.put("var"+j, cellValue);

				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return varList;
	}
	/**
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheet //sheet
	 * @return list
	 */
	public static List<Object> readExcel(int startrow, int startcol, int endRow,Sheet sheet) {
		List<Object> varList = new ArrayList<Object>();

		try {

			for (int i = startrow; i < endRow; i++) {					//行循环开始

				PageData varpd = new PageData();
				Row row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始

					Cell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {

						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
							case 0://数字和日期
								if(HSSFDateUtil.isCellDateFormatted(cell))
								{
									//  如果是date类型则 ，获取该cell的date值
									cellValue = DateUtil.getDays(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								}
								else
								{
									BigDecimal big=new BigDecimal(cell.getNumericCellValue());
									cellValue = big.toString();
									//解决1234.0  去掉后面的.0
									if(null!=cellValue&&!"".equals(cellValue.trim())){
										String[] item = cellValue.split("[.]");
										if(1<item.length&&"0".equals(item[1])){
											cellValue=item[0];
										}else if(1<item.length&&item[1].length()>3)
										{
											cellValue = item[0]+"."+item[1].substring(0, 2);
										}
									}
								}

								//cellValue = String.valueOf((int) cell.getNumericCellValue());
								break;
							case 1://字符串
								cellValue = cell.getStringCellValue();
								break;
							case 2://公式
								//读公式计算值
								cellValue = String.valueOf(cell.getNumericCellValue());
								if (cellValue.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
									cellValue = cell.getStringCellValue().toString();
								}
								break;
							case 3://空
								cellValue = "";
								break;
							case 4://boolean
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case 5://错误
								cellValue ="";
								break;
						}
					} else {
						cellValue = "";
					}

					varpd.put("var"+j, cellValue);

				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return varList;
	}
	/**
	 *
	 * @描述：是否是2003的excel，返回true是2003
	 *
	 * @返回值：boolean
	 */
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	/**
	 *
	 * @描述：是否是2007的excel，返回true是2007
	 *
	 * @返回值：boolean
	 */

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("0", null);

		System.out.println(map.get("0"));
	}
}
