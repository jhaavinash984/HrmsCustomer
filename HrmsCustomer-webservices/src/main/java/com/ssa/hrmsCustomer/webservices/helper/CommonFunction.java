package com.ssa.hrmsCustomer.webservices.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.dto.model.PageRequestModel;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

import org.springframework.data.domain.Sort.Direction;

@Component
public class CommonFunction {
	
	public LoggedInUser loggedInUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    LoggedInUser currentUser = (LoggedInUser)auth.getPrincipal();
	    return currentUser;
	}
	
	public PageRequest getPageRequestObject(PageRequestModel pageRequestModel){
		PageRequest pageRequest = null;
		if("ascending".equals(pageRequestModel.getDirection())){
			pageRequest =PageRequest.of(pageRequestModel.getPageNumber(), pageRequestModel.getMaxRecordsFetchPerPage(), Direction.ASC, pageRequestModel.getSortingAttribute());
		}
		else{
			pageRequest = PageRequest.of(pageRequestModel.getPageNumber(), pageRequestModel.getMaxRecordsFetchPerPage(), Direction.DESC, pageRequestModel.getSortingAttribute());
		}
		return pageRequest;
	}
	
	public CreateUpdateDetails creatorInfo(LoggedInUser loggedInUser){
		
		CreateUpdateDetails creatorInfo = new CreateUpdateDetails();
		creatorInfo.setCreatedBy(loggedInUser.getId());
		 LocalDateTime currentDateAndTime = LocalDateTime.ofInstant(new Date().toInstant(), 
                 ZoneId.systemDefault());
		creatorInfo.setCreatedDate(currentDateAndTime);
		return creatorInfo;
		
	}
	
	public String generateRandomPassword(int length){
		 //char[] splCharacter = (new String("^$*.[]{}()?-\"!@#%&/\\,><':;|_~`")).toCharArray();
		 char[] lowercaseLetter = (new String("abcdefghijklmnopqrstuvwxyz")).toCharArray();
		 char[] uppercaseLetter = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).toCharArray();
		 char[] numbers = (new String("0123456789")).toCharArray();
		 char[] allCharacter = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")).toCharArray();
		 Random rand = new SecureRandom();
        char[] password = new char[length];
        password[0] = lowercaseLetter[rand.nextInt(lowercaseLetter.length)];
		 password[1] = uppercaseLetter[rand.nextInt(uppercaseLetter.length)];
		 password[2] = numbers[rand.nextInt(numbers.length)];
		// password[3] = splCharacter[rand.nextInt(splCharacter.length)];   
        for (int i = 3; i < length; i++) {
           password[i] = allCharacter[rand.nextInt(allCharacter.length)];
        }

        for (int i = 0; i < password.length; i++) {
           int randomPosition = rand.nextInt(password.length);
           char temp = password[i];
           password[i] = password[randomPosition];
           password[randomPosition] = temp;
        }

		        return new String(password);
	}
	
	 public void excelSheetWrite(String username,String password) {
	        String excelFilePath = "C:/Users/jhaav/Desktop/credential.xlsx";
	         
	        try {
	            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	            Workbook workbook = WorkbookFactory.create(inputStream);
	 
	            Sheet sheet = workbook.getSheetAt(0);
	 
	            Object[][] bookData = {
	                    {username, password}
	            };
	 
	            int rowCount = sheet.getLastRowNum();
	 
	            for (Object[] aBook : bookData) {
	                Row row = sheet.createRow(++rowCount);
	 
	                int columnCount = 0;
	                 
	                Cell cell = row.createCell(columnCount);
	                cell.setCellValue(rowCount);
	                 
	                for (Object field : aBook) {
	                    cell = row.createCell(++columnCount);
	                    if (field instanceof String) {
	                        cell.setCellValue((String) field);
	                    } else if (field instanceof Integer) {
	                        cell.setCellValue((Integer) field);
	                    }
	                }
	 
	            }
	 
	            inputStream.close();
	            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
	            workbook.write(outputStream);
	            workbook.close();
	            outputStream.close();
	             
	        } catch (IOException | EncryptedDocumentException
	                | InvalidFormatException ex) {
	            ex.printStackTrace();
	        }
	    }

}
