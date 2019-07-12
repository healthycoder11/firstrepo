package com.demo.controller;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.model.Employee;

public class ExcelGenerator {

	

	public static ByteArrayInputStream studentToExcel(List<Employee> list) throws IOException 
	  {
		System.out.println(list);
			String[] columns = {"ID", "Name", "Username", "Password"};
			
			Workbook workbook = new XSSFWorkbook();
			 ByteArrayOutputStream stream = new ByteArrayOutputStream();
			
			
			Sheet sheet=workbook.createSheet("Employee");
			// CreationHelper createHelper = workbook.getCreationHelper();
			
			Font font=workbook.createFont();
			 font.setItalic(true);
		      font.setColor(IndexedColors.RED.getIndex());
		      
		      CellStyle headercellstyle = workbook.createCellStyle();
		      headercellstyle.setFont(font);
		      
		      Row headerRow = sheet.createRow(0);
		      
		      for (int i = 0; i < columns.length; i++) {
		          Cell cell = headerRow.createCell(i);
		          cell.setCellValue(columns[i]);
		          cell.setCellStyle(headercellstyle);
		        }
		      
		      CreationHelper createHelper = workbook.getCreationHelper();
		    // HSSFDataFormat format=workbook.createDataFormat();
		     CellStyle dateStyle=workbook.createCellStyle();
		     dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy:mm:dd"));
		    
		     
		     int roww = 1;
		      for (Employee employee : list) {
		        Row row = sheet.createRow(roww++);
		   System.out.println(list);
		        row.createCell(0).setCellValue(employee.getId());
		        row.createCell(1).setCellValue(employee.getName());
		        row.createCell(2).setCellValue(employee.getUname());
		        row.createCell(3).setCellValue(employee.getPass());
		        
		 }
		      workbook.write(stream);
		      return new ByteArrayInputStream(stream.toByteArray());
	}
}
