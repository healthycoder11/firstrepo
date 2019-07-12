package com.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Employee;
import com.demo.service.Service;
import com.google.common.io.Files;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@RestController
public class HomeController {

	@Autowired
	Service s;
	
	
@RequestMapping(value="/employee/pdf",method=RequestMethod.GET,produces="application/pdf")
	public ResponseEntity getStudentspdf() throws DocumentException, IOException
	{
		//ResponseEntity<InputStreamResource>
		List<Employee> emplist=(List<Employee>)s.getAllData();
		
		
		
			Document document = new Document();
			
			
			Paragraph para=new Paragraph();
			para.add("This is Table For All Student");
			
			PdfPTable table = new PdfPTable(new float[] { 1, 2 , 4 , 3});//size and no of coulumn
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			
			table.addCell("ID");
			table.addCell("Name");
			table.addCell("Username");
			table.addCell("Password");
			
			table.setHeaderRows(1);
			PdfPCell[] cells = table.getRow(0).getCells();
			for (int j = 0; j < cells.length; j++) {
				cells[j].setBackgroundColor(BaseColor.GRAY);
			}
			
			System.out.println(emplist.size());
			
			for (Employee emp: emplist) 
			{
				table.addCell(""+emp.getId());
			
				table.addCell(""+emp.getName());
				table.addCell(""+emp.getUname());
				table.addCell(""+emp.getPass());
			
			}
			PdfWriter.getInstance(document, new FileOutputStream("new1.pdf"));
			document.open();
			document.add(para);
			document.add(new Phrase(" "));
			document.add(table);
			document.close();
			System.out.println("Done");


			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=students.pdf");
	        System.out.println("ok>>>");
	        
	        
	        
	        return new ResponseEntity<byte[]>(Files.toByteArray(new File("new1.pdf")), headers, HttpStatus.OK);
	       /* ResponseEntity.ok() .headers(headers) .body();
	        
	       
	       ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
	               pdfContents, headers, HttpStatus.OK);
	       return response;*/
			
			
			
		} 
		
	
	
	@ResponseBody
	@RequestMapping(value="/emp/xlsx",method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getStudentsxml() throws IOException
	{
		
		List<Employee> list=(List<Employee>)s.getAllData();
		
		ByteArrayInputStream in=ExcelGenerator.studentToExcel(list);
	   
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Employee.xlsx");
        System.out.println("ok>>>");
        
        
        
       
        
		return ResponseEntity.ok() .headers(headers) .body(new InputStreamResource(in));
		
	}
	
	
	@RequestMapping(value="/log")
	public String firstPage()
	{
		return "index";
		
	}
	
	
}
