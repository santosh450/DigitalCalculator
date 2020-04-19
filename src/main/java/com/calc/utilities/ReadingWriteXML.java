package com.calc.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadingWriteXML {

	static Workbook calculatorBook = null;
	static File file = null;  
	static FileInputStream inputStream = null;
	static Sheet EmpDataSheet;

	public static ArrayList<Object[]> getDataFromExcel() {

		ArrayList<Object[]> myData = new ArrayList<Object[]>();

		DataFormatter formatter = new DataFormatter();
		file =   new File(System.getProperty("user.dir")+"/Testdata/TestInput.xlsx");
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			calculatorBook = WorkbookFactory.create(inputStream);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		EmpDataSheet = calculatorBook.getSheet("Sheet1");
		int Last_row = EmpDataSheet.getLastRowNum();
		int Last_column = EmpDataSheet.getRow(0).getLastCellNum();
		String s[] = new String[Last_column];

		for(int i=1;i<=Last_row;i++){
			for(int j=0;j<s.length;j++){			// read last column -1 (No data in excel)
				s[j] = formatter.formatCellValue(EmpDataSheet.getRow(i).getCell(j));
			}
			Object obj[] = {s[0], s[1], s[2], s[3]};	//dont use ob[0], obj[2] instead of s[0], s[1]
			myData.add(obj);
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myData;
	}

	public static void writeDataToExcel(int rowNum, String s) throws IOException {

		File file =    new File(System.getProperty("user.dir")+"/Testdata/TestInput.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		Row row = sheet.getRow(rowNum);
		Cell cell = row.createCell(3);
		cell.setCellValue(s);
		FileOutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);
	}
}

