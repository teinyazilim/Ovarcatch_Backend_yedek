package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.BankStatement;
import com.tein.overcatchbackend.domain.model.SheetDescription;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class BankStatementConvertService {

    public String ExcelConvert(File file) {
        List<SheetDescription> test= StatementAnalizFunc(file);
        String filePath="C:/Users/Tein/test/statement/";
        Integer randomNumber;
        String excelFilePath="";
        Random random = new Random();
        randomNumber = random.nextInt(899999) + 100000;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Statement");
            XSSFWorkbook myWorkBook = new XSSFWorkbook(new FileInputStream(file));
            int rowCount = 0;
            for (int i = 0; i < test.size(); i++) {
                XSSFSheet firstSheet = myWorkBook.getSheetAt(i);
                for (int k = test.get(i).getSayfaBasi(); k <= test.get(i).getSayfaSonu(); k++) {
                    Row row = firstSheet.getRow(k);
                    if (row != null) {
                        BankStatement resourceContent = new BankStatement();
                        int t = 0;
                        Row row1 = sheet.createRow(rowCount++);
                        Cell cell1 = row1.createCell(0);
                        Cell cell2 = row1.createCell(1);
                        Cell cell3 = row1.createCell(2);
                        Cell cell4 = row1.createCell(3);
                        Cell cell5 = row1.createCell(4);
                        try {
                            if (row.getCell(t).getRichStringCellValue() != null || row.getCell(t).getRichStringCellValue().toString().length() > 0) {
                                String date = String.valueOf(row.getCell(t).getRichStringCellValue());
                                resourceContent.setStatementDate(date);

                            }else {
                                resourceContent.setStatementDate("");
                            }
                        } catch (Exception e) {
                            System.out.println("sheet:"+i+"satır:"+k+".Statement hatası");
                            e.printStackTrace();
                        }
                        try {
                            if (row.getCell(t + 1).getRichStringCellValue() != null || row.getCell(t+1).getRichStringCellValue().toString().length() > 0) {
                                String description = String.valueOf(row.getCell(t + 1).getRichStringCellValue());
                                resourceContent.setStatementDescription(description);
                            }else {
                                resourceContent.setStatementDescription("");
                            }
                        } catch (Exception e) {
                            System.out.println("sheet:"+i+"satır:"+k+".Statement hatası 2");
                            e.printStackTrace();
                        }
                        if(test.get(i).getIsBosluk()){
                            t++;
                        }
                        try {
                            if (row.getCell(t + 2).getRichStringCellValue() != null || row.getCell(t+2).getRichStringCellValue().toString().length() > 0) {
                                if(row.getCell(t + 2).getRichStringCellValue().length() >0){
                                    String moneyOut = String.valueOf(row.getCell(t + 2).getRichStringCellValue());
                                    resourceContent.setMoneyOut(moneyOut);
                                }else {
                                    resourceContent.setMoneyOut("0");
                                }

                            }else {
                                resourceContent.setMoneyOut("0");
                            }
                        } catch (Exception e) {
                            System.out.println("sheet:"+i+"satır:"+k+".Statement hatası 3");
                            e.printStackTrace();
                        }

                        try {
                            if (row.getCell(t + 3).getRichStringCellValue() != null) {
                                if(row.getCell(t+3).getRichStringCellValue().toString().length() >= 1){
                                    String moneyIn = String.valueOf(row.getCell(t + 3).getRichStringCellValue());
                                    resourceContent.setMoneyIn(moneyIn);
                                }else {
                                    resourceContent.setMoneyIn("0");
                                }

                            }
                            else {
                                resourceContent.setMoneyIn("0");
                            }
                        } catch (Exception e) {
                            System.out.println("sheet:"+i+"satır:"+k+".Statement hatası 4");
                            e.printStackTrace();
                        }
                        try {
                            if (row.getCell(t + 4).getRichStringCellValue() != null || row.getCell(t+4).getRichStringCellValue().toString().length() > 0) {
                                if(row.getCell(t+4).getRichStringCellValue().toString().length() >= 1){
                                    String balance = String.valueOf(row.getCell(t + 4).getRichStringCellValue());
                                    resourceContent.setBalance(balance);
                                }else {
                                    resourceContent.setBalance("0");
                                }
                            }
                            else {
                                resourceContent.setBalance("0");
                            }
                        } catch (Exception e) {
                            System.out.println("sheet:"+i+"satır:"+k+".Statement hatası 5");
                            e.printStackTrace();
                        }
                        excelFilePath = filePath+randomNumber+".xls";
                        cell1.setCellValue(resourceContent.getStatementDate());
                        cell2.setCellValue(resourceContent.getStatementDescription());
                        cell3.setCellValue(resourceContent.getMoneyOut());
                        cell4.setCellValue(resourceContent.getMoneyIn());
                        cell5.setCellValue(resourceContent.getBalance());
                        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                            workbook.write(outputStream);
                        }

                    }
                }

            }
            workbook.close();
            myWorkBook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return randomNumber+".xls";
    }
    public List<SheetDescription> StatementAnalizFunc(File file){
        List<SheetDescription> list=new ArrayList<SheetDescription>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFWorkbook myWorkBook = new XSSFWorkbook(new FileInputStream(file));

            String sayfaBaslangaci = "01 OCK";
            String sayfaSonu = "01 OCK";
            String yil="2020";
            int kaydırma=0;
            for (int i = 0; i < myWorkBook.getNumberOfSheets(); i++) {
                XSSFSheet firstSheet = myWorkBook.getSheetAt(i);

                SheetDescription sd = new SheetDescription();
                for (int k = i==1 ? 3:1; k <= firstSheet.getLastRowNum(); k++) {
//                    if(i==1){
//                        kaydırma=i;
//                    }
                    Row row = firstSheet.getRow(k);

                    if (row != null) {
                        if(row.getCell(5)!=null){
                            if(row.getCell(5).getRichStringCellValue().length()>19) {
                                if(Pattern.matches(".. ... - .. ... ....",row.getCell(5).getRichStringCellValue().toString().substring(0,20))){
                                    sayfaSonu=row.getCell(5).getRichStringCellValue().toString().substring(9,15);
                                    sayfaBaslangaci=row.getCell(5).getRichStringCellValue().toString().substring(0,6);
                                    yil=row.getCell(5).getRichStringCellValue().toString().substring(16,20);
                                }
                            }
                        }
                        if(row.getCell(4)!=null){
                            if(row.getCell(4).getRichStringCellValue().length()>19) {
                                if(Pattern.matches(".. ... - .. ... ....",row.getCell(4).getRichStringCellValue().toString().substring(0,20))){
                                    sayfaSonu=row.getCell(4).getRichStringCellValue().toString().substring(9,15);
                                    sayfaBaslangaci=row.getCell(4).getRichStringCellValue().toString().substring(0,6);
                                    yil=row.getCell(4).getRichStringCellValue().toString().substring(16,20);
                                }
                            }
                        }
                        if(row.getCell(4) !=null){
                            if(row.getCell(4).getRichStringCellValue().toString().contains("Continued")){
                                sd.setSayfaSonu(k-1);
                                k=firstSheet.getLastRowNum();
                            }

                        }
                        if(row.getCell(2) !=null){
                            if(row.getCell(2).getRichStringCellValue().toString().contains("Contactless")){
                                sd.setIsBosluk(true);
                            }

                        }
                        if(row.getCell(5) !=null){
                            if(row.getCell(5).getRichStringCellValue().toString().contains("Continued")){
                                sd.setSayfaSonu(k-1);
                                k=firstSheet.getLastRowNum();
                            }

                        }
                        if(row.getCell(0) !=null){
                            if(sayfaSonu.charAt(0) =='0'){
                                if(row.getCell(0).getRichStringCellValue().toString().equals(sayfaSonu.substring(1)) &&
                                        (row.getCell(1).getRichStringCellValue().toString().equals("Balance carried forward") ||
                                                row.getCell(1).getRichStringCellValue().toString().equals("End balance"))){
                                    sd.setSayfaSonu(k);
                                    sd.setSheetNumber(i);
                                    i=myWorkBook.getNumberOfSheets();
                                    k=firstSheet.getLastRowNum();
                                }

                            }else {
                                if (row.getCell(0).getRichStringCellValue().toString().equals(sayfaSonu) &&
                                        (row.getCell(1).getRichStringCellValue().toString().equals("Balance carried forward") ||
                                                row.getCell(1).getRichStringCellValue().toString().equals("End balance"))) {
                                    sd.setSayfaSonu(k);
                                    sd.setSheetNumber(i);
                                    i = myWorkBook.getNumberOfSheets();
                                    k = firstSheet.getLastRowNum();
                                }
                            }
                            System.out.println(row.getCell(0).getRichStringCellValue().toString());
                            System.out.println(sayfaBaslangaci);
                            if(sayfaBaslangaci.charAt(0) =='0'){
                                if(row.getCell(0).getRichStringCellValue().toString().equals(sayfaBaslangaci.substring(1))){
                                    sd.setSayfaBasi(k);
                                }
                            }else if(row.getCell(0).getRichStringCellValue().toString().equals(sayfaBaslangaci)){
                                sd.setSayfaBasi(k);
                            }
                            if(sd.getSayfaBasi()==null ){
                                sd.setSayfaBasi(4);
                            }

                        }
                    }

                }
                if(sd !=null){
                    list.add(sd);
                }
            }

        workbook.close();
        myWorkBook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
