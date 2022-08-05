package com.tein.overcatchbackend.service;

import com.aspose.pdf.internal.imaging.system.collections.Generic.List;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.tein.overcatchbackend.domain.model.Address;
import com.tein.overcatchbackend.repository.AddressRepository;
import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.eadge.extractpdfexcel.PdfConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

@Service
public class StatementExcelExportService {

    public StatementExcelExportService(){

    }
    public String asposePdftoExcelExport(String fileName,String bankType) throws IOException {
        Random random = new Random();
        Integer outputFileName = random.nextInt(899999) + 100000;
        String outputfile="";
        String outputFilePath = "C:/Users/Tein/test/statement/";
        try {
            outputfile=bankType+"-"+outputFileName.toString()+".xls";
            outputFilePath=outputFilePath+outputfile;
            Document document = new Document(fileName);
            document.save(outputFilePath, SaveFormat.Excel);
            document.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            eadgePdftoExcelExport(fileName,bankType);
        }finally {
            return outputfile;
        }
    }

    public String eadgePdftoExcelExport(String fileName,String bankType) throws IOException {
        Random random = new Random();
        Integer outputFileName = random.nextInt(899999) + 100000;
        String outputfile="";
        String outputFilePath = "C:/Users/Tein/test/statement/";
        try {
            outputfile=bankType+"-"+outputFileName.toString()+".xls";
            outputFilePath=outputFilePath+outputfile;
            PdfConverter.createExcelFile(fileName, outputFilePath);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            iceBluePdftoExcelExport(fileName,bankType);
        }finally {
            return outputfile;
        }
    }
    public String iceBluePdftoExcelExport(String fileName,String bankType) throws IOException {
        Random random = new Random();
        Integer outputFileName = random.nextInt(899999) + 100000;
        String outputfile="";
        String outputFilePath = "C:/Users/Tein/test/statement/";
        try {
            outputfile=bankType+"-"+outputFileName.toString()+".xlsx";
            outputFilePath=outputFilePath+outputfile;
            PdfDocument pdf = new PdfDocument();
            pdf.loadFromFile(fileName);
            pdf.saveToFile(outputFilePath, FileFormat.XLSX);
            pdf.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            return outputfile.toString();
        }
    }

}

