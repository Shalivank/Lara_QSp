package org.vtiger.practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadDataFromExcelSheet {
	public static void main(String[] args) throws EncryptedDocumentException, IOException {

		//step 1 : Read the file using FileInputStream
		FileInputStream fis = new FileInputStream(".\\Data\\ddt.xlsx");

		//step 2 : Create workbook
		Workbook workbook = WorkbookFactory.create(fis);

		//step 3 : Load the sheet
		Sheet sheet = workbook.getSheet("Sheet1");


		//step 4 : Navigate to the Row
		Row row = sheet.getRow(1);

		//step 5 : Navigate to the cell
		Cell cell = row.getCell(2);

		//step 6 : Read the value from the cell
		System.out.println(cell.getStringCellValue());

	}
}
