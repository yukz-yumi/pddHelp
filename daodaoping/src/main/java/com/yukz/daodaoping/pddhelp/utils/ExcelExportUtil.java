package com.yukz.daodaoping.pddhelp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yukz.daodaoping.common.utils.JSONUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil {
    /**
     * 导出excel
     * @param model 数据集合
     * @param response  http响应对象
     */
    public static <T> void exportExcel(Map<String, Object> model, List<T> list,
                                       String fileName, Map<Integer, String> rfieldMap, HttpServletResponse response) throws Exception{
        Date date = new Date();
        HSSFSheet sheet;
        HSSFCell cell;
        OutputStream os = response.getOutputStream();
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment;filename="+new String(fileName.getBytes(),"iso-8859-1"));

        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 声明一个单子并命名
        sheet = workbook.createSheet("sheet1");

        HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont headerFont = workbook.createFont();	//标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short)11);
        headerStyle.setFont(headerFont);
        short width = 20,height=25*20;
        sheet.setDefaultColumnWidth(width);
        for(int i=0; i<len; i++){ //设置标题
            String title = titles.get(i);
            cell = getCell(sheet, 0, i);
            cell.setCellStyle(headerStyle);
            setText(cell,title);
        }
        sheet.getRow(0).setHeight(height);

        HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 向单元格里填充数据
        for (short i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            //反射获取
            Class cls = obj.getClass();
//            JSONObject jsonObject = (JSONObject)JSON.toJSON(obj);
            for (int j = 0; j < len; j++) {
                String fieldName = rfieldMap.get(j+1);
                Field f = cls.getDeclaredField(fieldName);//获取字段的类型
                f.setAccessible(true);
                Class type = f.getType();
                String varstr = "";
                // 如果是日期类型，则需要格式化
                if (type == Date.class){
                    varstr = DateUtil.fomatDateTime((Date)f.get(obj));
                } else {
                    varstr = f.get(obj) != null ? (String)f.get(obj) : "";
                }
                cell = getCell(sheet, i+1, j);
                cell.setCellStyle(contentStyle);
                setText(cell,varstr);
            }
        }

        try {
            workbook.write(os);
            System.out.println("导出成功");
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static HSSFCell getCell(HSSFSheet sheet, int row, int col) {
        HSSFRow sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }

        HSSFCell cell = sheetRow.getCell(col);
        if (cell == null) {
            cell = sheetRow.createCell(col);
        }

        return cell;
    }

    protected static void setText(HSSFCell cell, String text) {
        cell.setCellType(1);
        cell.setCellValue(text);
    }
}
