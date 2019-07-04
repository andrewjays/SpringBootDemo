package com.example.demo.tool.utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class ExcelExportUtil {

  /**
   * @param headers 列头名
   * @param keys 列名 keys 为null表示至于一列
   * @param dataSet 结果集
   * @param sheetName sheet名称
   * @return Workbook 2015-7-8
   * @author
   * @methodName createWorkBook
   * @description
   */
  public static Workbook createWorkBook(String[] headers, String[] keys, List<Object> dataSet,
      String sheetName) throws Exception {
    //创建Excel工作薄
    Workbook wb = new XSSFWorkbook();//支持2007以上版本的
//        Workbook wb = new HSSFWorkbook();//支持2007以下版本的
    //创建sheet，并命名
    Sheet sheet = wb.createSheet(sheetName);
    //设置默认列宽
    sheet.setDefaultColumnWidth(20);

    CellStyle styleHeader = wb.createCellStyle();//表头样式
    CellStyle styleContent = wb.createCellStyle();//内容样式

    //文本格式
    CellStyle textStyle = wb.createCellStyle();
    DataFormat format = wb.createDataFormat();
    textStyle.setDataFormat(format.getFormat("@")); //设置单元格格式，其他类型参考最下方

    Font fHeader = wb.createFont();//表头字体
    Font fContent = wb.createFont();//内容字体
    //设置表头字体
    fHeader.setFontHeightInPoints((short) 10);
    fHeader.setBold(true);
    fHeader.setColor(IndexedColors.BLACK.getIndex());
    //设置内容字体
    fContent.setFontHeightInPoints((short) 10);
    fContent.setColor(IndexedColors.BLACK.getIndex());

    //设置表头单元格的样式
    styleHeader.setFont(fHeader);
//        BorderStyle borderStyle = new BorderStyle(BorderStyle.THIN);
    styleHeader.setBorderBottom(BorderStyle.THIN);
    styleHeader.setBorderLeft(BorderStyle.THIN);
    styleHeader.setBorderRight(BorderStyle.THIN);
    styleHeader.setBorderTop(BorderStyle.THIN);
    styleHeader.setAlignment(HorizontalAlignment.CENTER);


    //设置表内容单元格的样式
    styleContent.setFont(fContent);
    styleContent.setBorderBottom(BorderStyle.THIN);
    styleContent.setBorderLeft(BorderStyle.THIN);
    styleContent.setBorderRight(BorderStyle.THIN);
    styleContent.setBorderTop(BorderStyle.THIN);


    //设置字符串格式
    textStyle.setFont(fContent);
    textStyle.setBorderBottom(BorderStyle.THIN);
    textStyle.setBorderLeft(BorderStyle.THIN);
    textStyle.setBorderRight(BorderStyle.THIN);
    textStyle.setBorderTop(BorderStyle.THIN);

    //创建表头
    Row row = sheet.createRow(0);
    if (headers != null && headers.length > 0) {
      for (int i = 0; i < headers.length; i++) {
        Cell cell = row.createCell(i);
        cell.setCellValue(headers[i]);
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        cell.setCellStyle(styleHeader);

      }
    }
    //表数据

    for (int i = 0; i < dataSet.size(); i++) {
      row = sheet.createRow(i + 1);
      Object obj = dataSet.get(i);
      if (null == keys) { // keys 为null表示至于一列
        Cell cell = row.createCell(0);
        Object value = obj;
        if (value instanceof Double) {
          cell.setCellStyle(styleContent);
          cell.setCellValue(Double.parseDouble(value.toString()));
          continue;
        } else if (value instanceof Integer) {
          cell.setCellStyle(styleContent);
          cell.setCellValue(Integer.parseInt(value.toString()));
          continue;
        } else {
          cell.setCellStyle(textStyle);
          cell.setCellValue(value == null ? "" : value.toString());
        }
      } else {
        for (int j = 0; j < keys.length; j++) {
          Cell cell = row.createCell(j);
          Method method = ReflectionUtils.getDeclaredMethod(obj, "get" + keys[j]) ;
          Object value = method.invoke(obj);
          if (value instanceof Double) {
            cell.setCellStyle(styleContent);
            cell.setCellValue(Double.parseDouble(value.toString()));
            continue;
          } else if (value instanceof Integer) {
            cell.setCellStyle(styleContent);
            cell.setCellValue(Integer.parseInt(value.toString()));
            continue;
          } else {
            cell.setCellStyle(textStyle);
            cell.setCellValue(value == null ? "" : value.toString());
          }
        }
      }

    }
    return wb;
  }

  /**
   * @param fileName 文件名
   * @param sheetName sheet名
   * @param headers 列名
   * @param dataSet 数据集
   * @param response 响应
   * @return void 2015-7-9
   * @author
   * @methodName download
   * @description 弹出下载框
   */
  public static void download(String fileName, String sheetName, String[] headers, String[] keys,
      List<Object> dataSet, HttpServletResponse response) throws Exception {
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-Disposition",
        "attachment;filename=" + new String((fileName + ".xlsx").getBytes("GBK"), "iso-8859-1"));
    Workbook wb = createWorkBook(headers, keys, dataSet, sheetName);
    wb.write(response.getOutputStream());
  }


}
