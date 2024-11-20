package Gym.Management.papers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import Gym.Management.people.Person;
import Gym.Management.people.Trainee;
import Gym.Management.people.Trainer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class ExcelSheets {

	public static void createSheet() 
	{
		// Create a new HSSFWorkbook (for .xls files)
		Workbook workbook = new HSSFWorkbook();

		// Create the first sheet and add data
		Sheet sheet1 = workbook.createSheet("Trainers Attendance");
		Row headerRow1 = sheet1.createRow(0);
		headerRow1.createCell(0).setCellValue("Trainer Email");
		headerRow1.createCell(1).setCellValue("Name");
		headerRow1.createCell(2).setCellValue("Asigned Hall");
		headerRow1.createCell(3).setCellValue("Date");
		for(int i = 0; i < 4; i++) {
			sheet1.autoSizeColumn(i);
		}

		//        Row dataRow1 = sheet1.createRow(1);
		//        dataRow1.createCell(0).setCellValue(101);
		//        dataRow1.createCell(1).setCellValue("John Doe");
		//        dataRow1.createCell(2).setCellValue("Engineering");

		// Create the second sheet and add data
		Sheet sheet2 = workbook.createSheet("Trainees Attendance");
		Row headerRow2 = sheet2.createRow(0);
		headerRow2.createCell(0).setCellValue("Trainee Email");
		headerRow2.createCell(1).setCellValue("Name");
		headerRow2.createCell(2).setCellValue("Date");
		for(int i = 0; i < 4; i++) {
			sheet2.autoSizeColumn(i);
		}

		//        Row dataRow2 = sheet2.createRow(1);
		//        dataRow2.createCell(0).setCellValue(201);
		//        dataRow2.createCell(1).setCellValue("Engineering");

		// Create the second sheet and add data
		Sheet sheet3 = workbook.createSheet("Trainer Card");
		Row headerRow31 = sheet3.createRow(0);
		Row headerRow32 = sheet3.createRow(1);
		Row headerRow33 = sheet3.createRow(2);
		Row headerRow34 = sheet3.createRow(3);
		Row headerRow35 = sheet3.createRow(4);
		headerRow31.createCell(0).setCellValue("Trainer ID");
		headerRow32.createCell(0).setCellValue("Name");
		headerRow33.createCell(0).setCellValue("Email");
		headerRow34.createCell(0).setCellValue("Phone");
		headerRow35.createCell(0).setCellValue("Role");
		for(int i = 0; i < 4; i++) {
			sheet3.autoSizeColumn(i);
		}


		// Create the second sheet and add data
		Sheet sheet4 = workbook.createSheet("Trainee Card");
		Row headerRow41 = sheet4.createRow(0);
		Row headerRow42 = sheet4.createRow(1);
		Row headerRow43 = sheet4.createRow(2);
		Row headerRow44 = sheet4.createRow(3);
		Row headerRow45 = sheet4.createRow(4);
		headerRow41.createCell(0).setCellValue("Trainee ID");
		headerRow42.createCell(0).setCellValue("Name");
		headerRow43.createCell(0).setCellValue("Email");
		headerRow44.createCell(0).setCellValue("Phone");
		headerRow45.createCell(0).setCellValue("Role");
		for(int i = 0; i < 4; i++) {
			sheet4.autoSizeColumn(i);
		}

		// Write the workbook to a file
		try (FileOutputStream fileOut = new FileOutputStream("GymData.xls")) {
			workbook.write(fileOut);
			System.out.println("Excel .xls file created successfully!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			// Close the workbook
			try {
				workbook.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**************************************************************************************
	 ****************************************************************************************/

	public static void takeAttendace(Person person) 
	{
		String filePath = "GymData.xls";

		if(person instanceof Trainer) {

			try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
				// Load the existing workbook
				Workbook workbook = new HSSFWorkbook(fileInputStream);

				// Get the existing sheet by name
				Sheet sheet = workbook.getSheet("Trainers Attendance");

				// Find the last row number to append new data
				int lastRowNum = sheet.getLastRowNum();
				System.out.println("Last Row Number: " + lastRowNum);


				Row row = sheet.createRow(++lastRowNum);
				row.createCell(0).setCellValue(person.getPersonEmail());
				row.createCell(1).setCellValue(person.getPersonName());
				row.createCell(2).setCellValue(((Trainer)person).getAssignedHall());
				row.createCell(3).setCellValue(LocalDate.now().toString());
				for(int i = 0; i < 4; i++) {
					sheet.autoSizeColumn(i);
				}



				// Save the updated workbook back to the file
				try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
					workbook.write(fileOutputStream);
					System.out.println("Data inserted into existing Excel sheet successfully!");
				}

				// Close the workbook
				workbook.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		else if(person instanceof Trainee) {

			try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
				// Load the existing workbook
				Workbook workbook = new HSSFWorkbook(fileInputStream);

				// Get the existing sheet by name
				Sheet sheet = workbook.getSheet("Trainees Attendance");

				// Find the last row number to append new data
				int lastRowNum = sheet.getLastRowNum();
				System.out.println("Last Row Number: " + lastRowNum);


				Row row = sheet.createRow(++lastRowNum);
				row.createCell(0).setCellValue(person.getPersonEmail());
				row.createCell(1).setCellValue(person.getPersonName());
				row.createCell(2).setCellValue(LocalDate.now().toString());
				
				for(int i = 0; i < 4; i++) {
					sheet.autoSizeColumn(i);
				}



				// Save the updated workbook back to the file
				try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
					workbook.write(fileOutputStream);
					System.out.println("Data inserted into existing Excel sheet successfully!");
				}

				// Close the workbook
				workbook.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}


	/**************************************************************************************
	 ****************************************************************************************/

	public static void createCard(Person person) 
	{
		String filePath = "GymData.xls";

		if(person instanceof Trainer) {

			

			try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
				// Load the existing workbook
				Workbook workbook = new HSSFWorkbook(fileInputStream);

				// Get the existing sheet by name
				Sheet sheet = workbook.getSheet("Trainer Card");


				Row row0 = sheet.getRow(0);
				Row row1 = sheet.getRow(1);
				Row row2 = sheet.getRow(2);
				Row row3 = sheet.getRow(3);
				Row row4 = sheet.getRow(4);
				row0.createCell(1).setCellValue(person.getPersonID());
				row1.createCell(1).setCellValue(person.getPersonName());
				row2.createCell(1).setCellValue(person.getPersonEmail());
				row3.createCell(1).setCellValue(person.getPersonPhone());
				row4.createCell(1).setCellValue("Trainer");
				
				for(int i = 0; i < 4; i++) {
					sheet.autoSizeColumn(i);
				}

				// Save the updated workbook back to the file
				try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
					workbook.write(fileOutputStream);
					System.out.println("Data inserted into existing Excel sheet successfully!");
				}

				// Close the workbook
				workbook.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		else if(person instanceof Trainee) {

			try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
				// Load the existing workbook
				Workbook workbook = new HSSFWorkbook(fileInputStream);

				// Get the existing sheet by name
				Sheet sheet = workbook.getSheet("Trainee Card");


				Row row0 = sheet.getRow(0);
				Row row1 = sheet.getRow(1);
				Row row2 = sheet.getRow(2);
				Row row3 = sheet.getRow(3);
				Row row4 = sheet.getRow(4);
				row0.createCell(1).setCellValue(person.getPersonID());
				row1.createCell(1).setCellValue(person.getPersonName());
				row2.createCell(1).setCellValue(person.getPersonEmail());
				row3.createCell(1).setCellValue(person.getPersonPhone());
				row4.createCell(1).setCellValue("Trainee");

				for(int i = 0; i < 4; i++) {
					sheet.autoSizeColumn(i);
				}

				// Save the updated workbook back to the file
				try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
					workbook.write(fileOutputStream);
					System.out.println("Data inserted into existing Excel sheet successfully!");
				}

				// Close the workbook
				workbook.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
