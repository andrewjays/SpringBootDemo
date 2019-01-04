package com.example.demo.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExcelImportUtil {

	public static final int maxReadSize = 10000;

	public static <T> List<T>
	readExcelByT(Integer startRow,String filePath, String[] columns, Class<?> clazz) throws Exception {;
		File f = new File(filePath);
		InputStream in = new FileInputStream(f);
		Workbook wb =null;
		if (f.getName().indexOf(".xlsx") != -1) {
			wb = new XSSFWorkbook(in);
			return readXSSF(wb,startRow,maxReadSize,columns,clazz);
		} else {
			try {
				wb = new HSSFWorkbook(in);
			}catch (Exception e){
				in.close();
				in = new FileInputStream(f);
				wb = new XSSFWorkbook(in);
				return readXSSF(wb,startRow,maxReadSize,columns,clazz);
			}
			in.close();
			return readHSSF(wb,startRow,maxReadSize,columns,clazz);
		}
	}
	public static <T> List<T> readXSSF(Workbook wb,Integer startRow,Integer maxReadSize, String[] columns, Class<?> clazz) throws Exception{
		List<T> importData = new ArrayList<>();
		Sheet sheet = wb.getSheetAt(0);
		if(sheet.getLastRowNum()>0){
			String value = null;
			try {
				value = getCellValue(sheet.getRow(0).getCell(0));
			}catch (Exception e){
				value = "";
			}
			if(StringUtils.isBlank(value)){
				return null;
			}
		}else{
			return importData;
		}
		for(int i=startRow;i<=sheet.getLastRowNum()&&i<=maxReadSize;i++){
			Row row = sheet.getRow(i);
			int blankNum = 0;
			Object obj = clazz.newInstance();
			for(int j=0;j<columns.length;j++){
				String value = null;
				try {
					value = getCellValue(row.getCell(j));
				}catch (Exception e){
					value = "";
				}
				if(StringUtils.isBlank(value)){
					blankNum ++;
					continue;
				}
				Method method = clazz.getDeclaredMethod("set"+columns[j],String.class);
				method.invoke(obj,value);
			}
			if(blankNum==columns.length){//有一行是空行，就结束
				break;
			}
			importData.add((T) obj);
		}
		wb.close();
		return importData;
	}
	public static <T> List<T> readHSSF(Workbook wb,Integer startRow,Integer maxReadSize, String[] columns, Class<?> clazz) throws Exception{
		List<T> importData = new ArrayList<>();
		HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
		if(sheet.getLastRowNum()>0){
			String value = getCellValue(sheet.getRow(0).getCell(0));
			if(StringUtils.isBlank(value)){
				return null;
			}
		}else{
			return importData;
		}
		for(int i=startRow;i<=sheet.getLastRowNum()&&i<=maxReadSize;i++){
			HSSFRow row = sheet.getRow(i);
			int blankNum = 0;
			Object obj = clazz.newInstance();
			for(int j=0;j<columns.length;j++){
				String value = null;
				try {
					value = getCellValue(row.getCell(j));
				}catch (Exception e){
					value = "";
				}
				if(StringUtils.isBlank(value)){
					blankNum ++;
					continue;
				}
				Method method = clazz.getDeclaredMethod("set"+columns[j],String.class);
				method.invoke(obj,value);
			}
			if(blankNum==columns.length){//有一行是空行，就结束
				break;
			}
			importData.add((T) obj);
		}
		wb.close();
		return importData;
	}

	public static int readExcelSize(String filePath,int maxSize) throws Exception {
		File f = new File(filePath);
		InputStream in = new FileInputStream(f);
		Workbook wb = null;
		if (f.getName().indexOf(".xlsx") != -1) {
			wb = new XSSFWorkbook(in);
		} else {
			try {
				wb = new HSSFWorkbook(in);
			} catch (Exception e) {
				in.close();
				in = new FileInputStream(f);
				wb = new XSSFWorkbook(in);
			}
		}
		Sheet sheet = wb.getSheetAt(0);
		int result = 0;
		int start = 1;

		for (int i = start; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String value = null;
			try {
				value = getCellValue(row.getCell(0));
			} catch (Exception e) {
				value = "";
			}
			if (StringUtils.isBlank(value)) {//有一行是空行，就结束
				return result;
			}
			result++;
            if ( result > maxSize ) {
                in.close();
                wb.close();
                return result;
            }
		}
		in.close();
		wb.close();
		return result;
	}

	/**
	 * 获取ecel表格的数据
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell) {
		String cellValue = "";
		if (null != cell) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = cell.getBooleanCellValue() + "";
				break;
			case Cell.CELL_TYPE_NUMERIC:
				try {
					cellValue = getDateFormatCellValue(cell);
				} catch (Exception e) {
					double num = cell.getNumericCellValue();
					if (Math.round(num) - num == 0) {
						cellValue = String.valueOf((long) num);
					}else {
						cellValue= num+"";
					}
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			default:
				cellValue = "";
				break;
			}
		}
		return cellValue;
	}

	/**
	 * 获取excel表格的日期数据
	 * @param cell
	 * @return
	 */
	 private static String getDateFormatCellValue(Cell cell){
	  short format = cell.getCellStyle().getDataFormat();
	     SimpleDateFormat sdf = null;
	     if(format == 14 || format == 31 || format == 57 || format == 58){
	         //日期
	         sdf = new SimpleDateFormat("yyyy-MM-dd");
	     }else if (format == 20 || format == 32) {
	         //时间
	         sdf = new SimpleDateFormat("HH:mm");
	     }
	     double value = cell.getNumericCellValue();
	     Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
	     return sdf.format(date);
	 }
}
