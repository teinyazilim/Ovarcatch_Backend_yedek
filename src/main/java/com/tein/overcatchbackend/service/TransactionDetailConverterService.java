package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.OvercatchBackendApplication;
import com.tein.overcatchbackend.domain.model.BankStatement;
import com.tein.overcatchbackend.domain.model.BankTransaction;
import com.tein.overcatchbackend.domain.model.BankTransactionDetail;
import com.tein.overcatchbackend.repository.BankTransactionDetailRepository;
import com.tein.overcatchbackend.repository.BankTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class TransactionDetailConverterService {


    @Autowired
    private final BankTransactionDetailRepository bankTransactionDetailRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final OvercatchBackendApplication overcatchBackendApplication;

//    String filePathConf = new String(overcatchBackendApplication.filePathConf);

    public void writeMonese(String filePath, BankTransaction bankTransaction) throws FileNotFoundException {

        Float totalMoneyIn = 0.0f;
        Float totalMoneyOut = 0.0f;
        LocalDate stDate = null;
        LocalDate endDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String fp = convertExcel(filePath, "convertmonese.py");
        Integer counter;
        Integer randomNumber;
        File file = new File(fp);
        Random random = new Random();
        randomNumber = random.nextInt(899999) + 100000;
        String p3;
        String n3;
        String f3;
        BankStatement resourceContent = new BankStatement();
//        List<BankTransactionDetail> bankTransactionDetails = new ArrayList<BankTransactionDetail>();
        try {
            XSSFWorkbook readWorkbook = new XSSFWorkbook(file);
            XSSFSheet readSheet = readWorkbook.getSheetAt(0);
            counter = readSheet.getPhysicalNumberOfRows();

            for (int i = 1; i < counter; i++) {
                BankTransactionDetail bankTransactionDetail = new BankTransactionDetail();
                Row readFirst;
                if (!readSheet.getRow(i).getCell(4).toString().isBlank()) {
                    if (readSheet.getRow(i).getCell(4).toString().contains("Closing"))
                        break;
                }
                if (!readSheet.getRow(i).getCell(1).toString().isBlank() || !readSheet.getRow(i).getCell(2).toString().isBlank() &&
                        !readSheet.getRow(i).getCell(4).toString().isBlank() && !readSheet.getRow(i).getCell(5).toString().isBlank()) {
                    readFirst = readSheet.getRow(i);

                    stDate = LocalDate.parse(readSheet.getRow(1).getCell(1).toString(), formatter);
                    if (!readSheet.getRow(i).getCell(1).toString().isBlank()) {
                        endDate = LocalDate.parse(readSheet.getRow(i).getCell(1).toString(), formatter);
                    }


                    LocalDate date1 = LocalDate.parse(readFirst.getCell(2).toString(), formatter);
                    bankTransactionDetail.setProcessDate(date1);
                    bankTransactionDetail.setBalance(readFirst.getCell(5).toString());
                    bankTransactionDetail.setBankTransaction(bankTransaction);
                    if (readSheet.getRow(i).getCell(4).toString().substring(0, 1).equals("+")) {
                        bankTransactionDetail.setMoneyIn(readFirst.getCell(4).toString().substring(2).replace(",", ""));
                        totalMoneyIn += Float.parseFloat(readFirst.getCell(4).toString().substring(2).replace(",", ""));
                    } else {
                        bankTransactionDetail.setMoneyOut(readFirst.getCell(4).toString().substring(2).replace(",", ""));
                        totalMoneyOut += Float.parseFloat(readFirst.getCell(4).toString().substring(2).replace(",", ""));
                    }
                    if (readSheet.getRow(i + 1).getCell(1).toString().isBlank() && readSheet.getRow(i + 1).getCell(2).toString().isBlank() &&
                            readSheet.getRow(i + 1).getCell(4).toString().isBlank() && readSheet.getRow(i + 1).getCell(5).toString().isBlank()) {
                        p3 = readFirst.getCell(3).toString();
                        n3 = readSheet.getRow(i + 1).getCell(3).toString();
                        f3 = p3 + " - " + n3;
                        bankTransactionDetail.setDescription(f3);
                        i++;
                    } else {

                        bankTransactionDetail.setDescription(readFirst.getCell(3).toString());

                    }

                } else {
                    i++;
                }
                bankTransaction.setStartDate(stDate);
                bankTransaction.setEndDate(endDate);
                bankTransaction.setTotalMoneyIn(totalMoneyIn);
                bankTransaction.setTotalMoneyOut(totalMoneyOut);
                bankTransactionRepository.save(bankTransaction);
//                bankTransactionDetails.add(bankTransactionDetail);
                bankTransactionDetailRepository.save(bankTransactionDetail);

            }
            readWorkbook.close();
            System.out.println("Oldu");
        } catch (Exception e) {
            System.out.println("Patladı");
        }

    }

    public void writeMonzo(String filePath, BankTransaction bankTransaction) throws FileNotFoundException {
        String fp = convertExcel(filePath, "monzo.py");
        Integer counter;
        File file = new File(fp);
        LocalDate stDate = null;
        LocalDate endDate = null;
        float totalMoneyIn = 0.00f;
        float totalMoneyOut = 0.00f;
        String p3;
        String n3;
        String f3;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        try {
            XSSFWorkbook readWorkbook = new XSSFWorkbook(file);
            XSSFSheet readSheet = readWorkbook.getSheetAt(0);
            counter = readSheet.getPhysicalNumberOfRows();
            List<BankTransactionDetail> test = new ArrayList();
            for (int i = 1; i < counter; i++) {
                BankTransactionDetail bankTransactionDetail = new BankTransactionDetail();
                Row readFirst;
                if (!readSheet.getRow(i).getCell(1).toString().isBlank()) {
                    if (readSheet.getRow(i).getCell(1).toString().contains("Monzo"))
                        break;
                    if (readSheet.getRow(i).getCell(1).toString().contains("Date"))
                        i++;
                }
//                if(i==94){
//                    System.out.println("Genius Oğuzhan");
//                }
                if (!readSheet.getRow(i).getCell(1).toString().isBlank() || !readSheet.getRow(i).getCell(2).toString().isBlank() ||
                        !readSheet.getRow(i).getCell(3).toString().isBlank() || !readSheet.getRow(i).getCell(4).toString().isBlank()) {
                    readFirst = readSheet.getRow(i);
                    if (readSheet.getRow(i).getCell(1).toString().isBlank() && !readSheet.getRow(i).getCell(2).toString().isBlank()) {
                        p3 = readFirst.getCell(2).toString();
                        if (!readSheet.getRow(i + 1).getCell(1).toString().isBlank() && readSheet.getRow(i + 1).getCell(2).toString().isBlank()) {
                            LocalDate date1 = LocalDate.parse(readSheet.getRow(i + 1).getCell(1).toString(), formatter);
                            bankTransactionDetail.setProcessDate(date1);
                            bankTransactionDetail.setBalance(readSheet.getRow(i + 1).getCell(4).toString());
                                    if (readSheet.getRow(i + 1).getCell(3).toString().substring(0, 1).equals("-")) {
                                        bankTransactionDetail.setMoneyOut(readSheet.getRow(i + 1).getCell(3).toString().substring(1));
                                        totalMoneyOut += Float.parseFloat(readSheet.getRow(i + 1).getCell(3).toString().substring(1).replace(",","")) ;
                                    } else {
                                        bankTransactionDetail.setMoneyIn(readSheet.getRow(i + 1).getCell(3).toString().substring(1));
                                        totalMoneyIn += Float.parseFloat(readSheet.getRow(i + 1).getCell(3).toString().substring(1).replace(",","")) ;
                                    }

                                    if (readSheet.getRow(i + 2).getCell(1).toString().isBlank() && !readSheet.getRow(i + 2).getCell(2).toString().isBlank()) {
                                        n3 = readSheet.getRow(i + 2).getCell(2).toString();
                                        f3 = p3 + " " + n3;
                                        i=i+2;
                                        bankTransactionDetail.setDescription(f3);
                                    }
                                }


                        }else {
                        LocalDate date1 = LocalDate.parse(readFirst.getCell(1).toString(), formatter);
                        bankTransactionDetail.setProcessDate(date1);
                        if(endDate ==null){
                            endDate=LocalDate.parse(readSheet.getRow(i).getCell(1).toString(), formatter);
                        }
                        stDate=LocalDate.parse(readSheet.getRow(i).getCell(1).toString(), formatter);
                        bankTransactionDetail.setDescription(readFirst.getCell(2).toString());
                        bankTransactionDetail.setBalance(readFirst.getCell(4).toString());
                        if (readFirst.getCell(3).toString().substring(0, 1).equals("-")) {
                            bankTransactionDetail.setMoneyOut(readFirst.getCell(3).toString().substring(1));
                            totalMoneyOut += Float.parseFloat(readFirst.getCell(3).toString().substring(1).replace(",","")) ;
                        } else {
                            bankTransactionDetail.setMoneyIn(readFirst.getCell(3).toString());
                            totalMoneyIn += Float.parseFloat(readFirst.getCell(3).toString().replace(",","")) ;
                        }


                    }
                    bankTransactionDetail.setBankTransaction(bankTransaction);
                    bankTransactionDetailRepository.save(bankTransactionDetail);
                }



            }
            bankTransaction.setStartDate(stDate);
            bankTransaction.setEndDate(endDate);
            bankTransaction.setTotalMoneyIn(totalMoneyIn);
            bankTransaction.setTotalMoneyOut(totalMoneyOut);
            bankTransactionRepository.save(bankTransaction);
            readWorkbook.close();
            System.out.println("Oldu");
        } catch (Exception e) {
            System.out.println("Patladı");
        }

    }

    public void writeTide(String filePath, BankTransaction bankTransaction) throws FileNotFoundException {
        String fp = convertExcel(filePath, "tide.py");
        Integer counter;
        LocalDate stDate = null;
        LocalDate endDate = null;
        File file = new File(fp);
        float totalMoneyIn = 0;
        float totalMoneyOut = 0;
        String p3;
        String n3;
        String f3;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH);
        try {
            XSSFWorkbook readWorkbook = new XSSFWorkbook(file);
            XSSFSheet readSheet = readWorkbook.getSheetAt(0);
            counter = readSheet.getPhysicalNumberOfRows();
            for (int i = 0; i < counter; i++) {
                BankTransactionDetail bankTransactionDetail = new BankTransactionDetail();
                Row readFirst;
                if (!readSheet.getRow(i).getCell(1).toString().isBlank() && !readSheet.getRow(i).getCell(2).toString().isBlank() &&
                        !readSheet.getRow(i).getCell(3).toString().isBlank() && !readSheet.getRow(i).getCell(6).toString().isBlank()) {
                    readFirst = readSheet.getRow(i);
                    LocalDate date1 = LocalDate.parse(readFirst.getCell(1).getStringCellValue(), readFirst.getCell(1).getStringCellValue().length() > 10 ? formatter : formatter1);
                    bankTransactionDetail.setProcessDate(date1);

                    bankTransactionDetail.setDescription(readFirst.getCell(3).toString());
                    if (!readSheet.getRow(i).getCell(4).toString().isBlank()) {
                        bankTransactionDetail.setMoneyOut(readFirst.getCell(4).toString().substring(1));
                        totalMoneyOut += Float.parseFloat(readFirst.getCell(4).toString().substring(1).replace(",", ""));
                    }
                    if (!readSheet.getRow(i).getCell(5).toString().isBlank()) {
                        bankTransactionDetail.setMoneyIn(readFirst.getCell(5).toString());
                        totalMoneyIn += Float.parseFloat(readFirst.getCell(5).toString().replace(",", ""));
                    }
                    endDate = LocalDate.parse(readSheet.getRow(0).getCell(1).toString(), readSheet.getRow(counter - 1).getCell(1).getStringCellValue().length() > 10 ? formatter : formatter1);

                    stDate = LocalDate.parse(readSheet.getRow(counter - 1).getCell(1).toString(), readSheet.getRow(counter - 1).getCell(1).getStringCellValue().length() > 10 ? formatter : formatter1);

                    bankTransactionDetail.setBalance(readFirst.getCell(6).toString());
                    bankTransactionDetail.setBankTransaction(bankTransaction);
                    bankTransactionDetailRepository.save(bankTransactionDetail);
                }

            }
            bankTransaction.setStartDate(stDate);
            bankTransaction.setEndDate(endDate);
            bankTransaction.setTotalMoneyIn(totalMoneyIn);
            bankTransaction.setTotalMoneyOut(totalMoneyOut);
            bankTransactionRepository.save(bankTransaction);
            readWorkbook.close();
            System.out.println("Oldu");


        } catch (Exception e) {
            System.out.println("Patladı");
        }

    }

    public void writeNatwest(String filePath, BankTransaction bankTransaction) throws FileNotFoundException {
        String fp = convertExcel(filePath, "natwest.py");
        LocalDate stDate = null;
        LocalDate endDate = null;
        Integer counter;
        File file = new File(fp);
        LocalDate sonTarih = null;
        float totalMoneyIn = 0;
        float totalMoneyOut = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH);

        try {
            XSSFWorkbook readWorkbook = new XSSFWorkbook(file);
            XSSFSheet readSheet = readWorkbook.getSheetAt(0);
            counter = readSheet.getPhysicalNumberOfRows();
            for (int i = 1; i < counter; i++) {
                BankTransactionDetail bankTransactionDetail = new BankTransactionDetail();
                Row readFirst;
                if (readSheet.getRow(i).getCell(3).toString().isBlank()) {
                    i++;
                } else {
                    if (readSheet.getRow(i).getCell(3).toString().contains("Description")) {
                        i++;
                    } else {
                        if (readSheet.getRow(i).getCell(1).toString().isBlank() && !readSheet.getRow(i).getCell(3).toString().isBlank()) {
                            readFirst = readSheet.getRow(i);
                            bankTransactionDetail.setProcessDate(sonTarih);
                            bankTransactionDetail.setTransactionType(readFirst.getCell(2).toString());
                            bankTransactionDetail.setBalance(readFirst.getCell(6).toString());
                            bankTransactionDetail.setDescription(readFirst.getCell(3).toString());
                            if (readSheet.getRow(i).getCell(4).toString().isBlank()) {
                                bankTransactionDetail.setMoneyOut(readSheet.getRow(i).getCell(5).toString());
                                totalMoneyOut += Float.parseFloat(readFirst.getCell(5).toString().replace(",", ""));
                            } else if (readSheet.getRow(i).getCell(5).toString().isBlank()) {
                                bankTransactionDetail.setMoneyIn(readSheet.getRow(i).getCell(4).toString());
                                totalMoneyIn += Float.parseFloat(readFirst.getCell(4).toString().replace(",", ""));
                            }
                            bankTransactionDetail.setBankTransaction(bankTransaction);
                            bankTransactionDetailRepository.save(bankTransactionDetail);
                        } else if (Pattern.matches(".. ... ....", readSheet.getRow(i).getCell(1).toString()) ||
                                Pattern.matches(". ... ....", readSheet.getRow(i).getCell(1).toString())) {
                            if (stDate == null) {
                                stDate = LocalDate.parse(readSheet.getRow(i).getCell(1).toString(), readSheet.getRow(i).getCell(1).getStringCellValue().length() > 10 ? formatter : formatter1);
                            }
                            readFirst = readSheet.getRow(i);
                            sonTarih = LocalDate.parse(readFirst.getCell(1).getStringCellValue(), readFirst.getCell(1).getStringCellValue().length() > 10 ? formatter : formatter1);
                            bankTransactionDetail.setProcessDate(LocalDate.parse(readFirst.getCell(1).getStringCellValue(), readFirst.getCell(1).getStringCellValue().length() > 10 ? formatter : formatter1));
                            if (!readFirst.getCell(2).toString().isBlank()) {
                                bankTransactionDetail.setTransactionType(readFirst.getCell(2).toString());
                            }
                            bankTransactionDetail.setBalance(readFirst.getCell(6).toString());
                            bankTransactionDetail.setDescription(readFirst.getCell(3).toString());
                            if (readSheet.getRow(i).getCell(4).toString().isBlank()) {
                                bankTransactionDetail.setMoneyOut(readSheet.getRow(i).getCell(5).toString());
                                totalMoneyOut += Float.parseFloat(readFirst.getCell(5).toString().replace(",", ""));
                            } else if (readSheet.getRow(i).getCell(5).toString().isBlank()) {
                                bankTransactionDetail.setMoneyIn(readSheet.getRow(i).getCell(4).toString());
                                totalMoneyIn += Float.parseFloat(readFirst.getCell(4).toString().replace(",", ""));
                            }
                            bankTransactionDetail.setBankTransaction(bankTransaction);
                            bankTransactionDetailRepository.save(bankTransactionDetail);
                        } else {
                            i++;
                        }

                    }

                }

            }

            endDate = sonTarih;
            bankTransaction.setStartDate(stDate);
            bankTransaction.setEndDate(endDate);
            bankTransaction.setTotalMoneyIn(totalMoneyIn);
            bankTransaction.setTotalMoneyOut(totalMoneyOut);
            bankTransactionRepository.save(bankTransaction);
            readWorkbook.close();
            System.out.println("Oldu");


        } catch (Exception e) {
            System.out.println("Patladı");
        }
    }

    public void writeTurkish(String filePath, BankTransaction bankTransaction) throws FileNotFoundException {
        String fp = convertExcel(filePath, "turkish.py");
        Integer counter;
        LocalDate stDate = null;
        LocalDate endDate = null;
        File file = new File(fp);
        float totalMoneyIn = 0;
        float totalMoneyOut = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        try {
            XSSFWorkbook readWorkbook = new XSSFWorkbook(file);
            XSSFSheet readSheet = readWorkbook.getSheetAt(0);
            counter = readSheet.getPhysicalNumberOfRows();
            for (int i = 1; i < counter; i++) {
                BankTransactionDetail bankTransactionDetail = new BankTransactionDetail();
                Row readFirst;

                if (!readSheet.getRow(i).getCell(1).toString().isBlank() && !readSheet.getRow(i).getCell(2).toString().isBlank() &&
                        !readSheet.getRow(i).getCell(3).toString().isBlank() && !readSheet.getRow(i).getCell(4).toString().isBlank()) {
                    if(!readSheet.getRow(i).getCell(1).getStringCellValue().equals("Trn. Date")){
                        readFirst = readSheet.getRow(i);
                        LocalDate date1 = LocalDate.parse(readFirst.getCell(1).getStringCellValue(), formatter);
                        bankTransactionDetail.setProcessDate(date1);

                        bankTransactionDetail.setDescription(readFirst.getCell(2).toString() + " ref no: " + readFirst.getCell(3).toString());
                        if (readSheet.getRow(i).getCell(4).toString().contains("-")) {
                            bankTransactionDetail.setMoneyOut(readFirst.getCell(4).toString().substring(2));
                            totalMoneyOut += Float.parseFloat(readFirst.getCell(4).toString().substring(2).replace(",", ""));
                        } else {
                            bankTransactionDetail.setMoneyIn(readFirst.getCell(4).toString().substring(2));
                            totalMoneyIn += Float.parseFloat(readFirst.getCell(4).toString().substring(2).replace(",", ""));
                        }
                        stDate = LocalDate.parse(readSheet.getRow(1).getCell(1).toString(), formatter);

                        endDate = LocalDate.parse(readSheet.getRow(counter - 1).getCell(1).toString(), formatter);
                        bankTransactionDetail.setBalance(readFirst.getCell(5).toString());
                        bankTransactionDetail.setBankTransaction(bankTransaction);
                        bankTransactionDetailRepository.save(bankTransactionDetail);
                    }

                }

            }
            bankTransaction.setStartDate(stDate);
            bankTransaction.setEndDate(endDate);
            bankTransaction.setTotalMoneyIn(totalMoneyIn);
            bankTransaction.setTotalMoneyOut(totalMoneyOut);
            bankTransactionRepository.save(bankTransaction);
            readWorkbook.close();
            System.out.println("Oldu");


        } catch (Exception e) {
            System.out.println("Patladı");
        }
    }


    public void writeHsbc(String filePath, BankTransaction bankTransaction) throws FileNotFoundException {
        String fp = convertExcel(filePath, "natwest.py");
        LocalDate stDate = null;
        LocalDate endDate = null;
        Integer counter;
        File file = new File(fp);
        LocalDate sonTarih = null;
        float totalMoneyIn = 0;
        float totalMoneyOut = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH);
        try {
            XSSFWorkbook readWorkbook = new XSSFWorkbook(file);
            int sheetCount = readWorkbook.getNumberOfSheets();
            for (int i = 0; i <= sheetCount; i++) {
                XSSFSheet readSheet = readWorkbook.getSheetAt(i);
                counter = readSheet.getPhysicalNumberOfRows();
                for (int x = 1; x < counter; x++) {
                    BankTransactionDetail bankTransactionDetail = new BankTransactionDetail();
                    Row readFirst = null;
                    if (Pattern.matches(".. ... - .. ... ....",readSheet.getRow(i).getCell(0).getRichStringCellValue().toString())){
                        readFirst = readSheet.getRow(i);
                    }
                    else if (Pattern.matches(".. ... - .. ... ....",readSheet.getRow(i-1).getCell(0).getRichStringCellValue().toString())){
                        String temp = readFirst.getCell(2).toString()+readSheet.getRow(i).getCell(3).toString();
                        readFirst.getCell(2).setCellValue(temp);
                    }
                }
            }
            endDate = sonTarih;
            bankTransaction.setStartDate(stDate);
            bankTransaction.setEndDate(endDate);
            bankTransaction.setTotalMoneyIn(totalMoneyIn);
            bankTransaction.setTotalMoneyOut(totalMoneyOut);
            bankTransactionRepository.save(bankTransaction);
            readWorkbook.close();
            System.out.println("Oldu");


        } catch (Exception e) {
            System.out.println("Patladı");
        }
    }
    public void writeStarling(String filePath, BankTransaction bankTransaction) throws FileNotFoundException {
        String fp = convertExcel(filePath, "starling.py");
        Integer counter;
        File file = new File(fp);
        LocalDate stDate = null;
        LocalDate endDate = null;
        float totalMoneyIn = 0.00f;
        float totalMoneyOut = 0.00f;
        String p3;
        String n3;
        String f3;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        try {
            XSSFWorkbook readWorkbook = new XSSFWorkbook(file);
            XSSFSheet readSheet = readWorkbook.getSheetAt(0);
            counter = readSheet.getPhysicalNumberOfRows();
            List<BankTransactionDetail> test = new ArrayList();
            for (int i = 3; i < counter; i++) {
                BankTransactionDetail bankTransactionDetail = new BankTransactionDetail();
                Row readFirst;
                if (!readSheet.getRow(i).getCell(1).toString().isBlank()) {
                    if (readSheet.getRow(i).getCell(1).toString().contains("DATE"))
                        i=i+2;
                }
                if (!readSheet.getRow(i).getCell(1).toString().isBlank() && !readSheet.getRow(i).getCell(2).toString().isBlank()) {
                    readFirst = readSheet.getRow(i);
                    LocalDate date1 = LocalDate.parse(readFirst.getCell(1).toString(), formatter);
                    bankTransactionDetail.setProcessDate(date1);
                    if(stDate ==null){
                        stDate=LocalDate.parse(readSheet.getRow(i).getCell(1).toString(), formatter);
                    }
                    endDate=LocalDate.parse(readSheet.getRow(i).getCell(1).toString(), formatter);
                    if (readSheet.getRow(i).getCell(3).toString().isBlank()) {
                        bankTransactionDetail.setDescription(readFirst.getCell(4).toString());
                        if(readFirst.getCell(6).toString().isBlank()){
                            bankTransactionDetail.setMoneyIn(readFirst.getCell(5).toString().substring(1));
                            totalMoneyIn += Float.parseFloat(readFirst.getCell(5).toString().substring(1).replace(",","")) ;
                        }else {
                            bankTransactionDetail.setMoneyOut(readFirst.getCell(6).toString().substring(1));
                            totalMoneyOut += Float.parseFloat(readFirst.getCell(6).toString().substring(1).replace(",","")) ;
                        }
                        if(!readFirst.getCell(7).toString().isBlank()){
                            bankTransactionDetail.setBalance(readFirst.getCell(7).toString().substring(1).replace(",",""));
                        }

                    }else {
                        bankTransactionDetail.setDescription(readFirst.getCell(3).toString());
                        if(readFirst.getCell(5).toString().isBlank()){
                            bankTransactionDetail.setMoneyIn(readFirst.getCell(4).toString().substring(1));
                            totalMoneyIn += Float.parseFloat(readFirst.getCell(4).toString().substring(1).replace(",","")) ;
                        }else {
                            bankTransactionDetail.setMoneyOut(readFirst.getCell(5).toString().substring(1));
                            totalMoneyOut += Float.parseFloat(readFirst.getCell(5).toString().substring(1).replace(",","")) ;
                        }
                        if(!readFirst.getCell(6).toString().isBlank()){
                            bankTransactionDetail.setBalance(readFirst.getCell(6).toString().substring(1).replace(",",""));
                        }
                        bankTransactionDetail.setDescription(readFirst.getCell(3).toString());
                    }

                    bankTransactionDetail.setBankTransaction(bankTransaction);
                    bankTransactionDetailRepository.save(bankTransactionDetail);
                }
            }
            bankTransaction.setStartDate(stDate);
            bankTransaction.setEndDate(endDate);
            bankTransaction.setTotalMoneyIn(totalMoneyIn);
            bankTransaction.setTotalMoneyOut(totalMoneyOut);
            bankTransactionRepository.save(bankTransaction);
            readWorkbook.close();
            System.out.println("Oldu");
        } catch (Exception e) {
            System.out.println("Patladı");
        }
    }

    @SneakyThrows
    public String convertExcel(String filePath, String convertType) throws FileNotFoundException {
        Process process;

        String command = "cmd /c python " + overcatchBackendApplication.filePathConf() + convertType + " " + filePath + "";
//        String command = "cmd /c python C:/Users/Administrator/Desktop/OVERCATCH/" + convertType + " " + filePath + "";
//        String command = "cmd /c python src/main/java/com/tein/overcatchbackend/service/scripts/" + convertType + " " + filePath + "";

        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line;
        String exportedPath = "";
        while ((line = bri.readLine()) != null) {
            System.out.println(line);
            exportedPath = line;
        }
        bri.close();
        while ((line = bre.readLine()) != null) {
            System.out.println(line);
        }
        bre.close();
        p.waitFor();
        System.out.println("Done.");

        p.destroy();

        return exportedPath;
    }
}
