package com.tein.overcatchbackend.service;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;

import com.tein.overcatchbackend.OvercatchBackendApplication;
import com.tein.overcatchbackend.domain.dto.DocumentDTO;
import com.tein.overcatchbackend.domain.dto.HelpViewDTO;
import com.tein.overcatchbackend.domain.dto.InvoiceDTO;
import com.tein.overcatchbackend.domain.dto.LetterDTO;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.mapper.DocumentMapper;
import com.tein.overcatchbackend.property.FileStorageProperties;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.DocumentRepository;
import com.tein.overcatchbackend.repository.UserRepository;
import com.tein.overcatchbackend.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.TypeToken;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DocumentService {



    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    private final DocumentMapper documentMapper;
    private final Path fileStorageLocation;
    private final OvercatchBackendApplication overcatchBackendApplication;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, FileStorageService fileStorageService,  DocumentMapper documentMapper, FileStorageProperties fileStorageProperties, OvercatchBackendApplication overcatchBackendApplication) {
        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
        this.documentMapper = documentMapper;
        this.fileStorageLocation =  Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.overcatchBackendApplication = overcatchBackendApplication;
    }

    public DocumentDTO saveDocument(Document document)
    {
        Document finalDocument=document;
        if(documentRepository.findById(document.getId())!=null){
            finalDocument=documentRepository.findById(document.getId()).get();
            finalDocument.setFileDescription(document.getFileDescription());
            Document d=documentRepository.save(finalDocument);
            DocumentDTO dto=documentMapper.toDto(d);
            return dto;
        }
        return documentMapper.toDto(finalDocument);

    }

    public List<Document> getDocuments(Long id){
            return documentRepository.findAllByClientIdAndIsActive(id, true);
    }

    public DocumentDTO getDocumentByID(long documentId) {
        Document document = documentRepository.findById(documentId).get();
        DocumentDTO documentDTO = documentMapper.toDto(document);
        return documentDTO;
    }

    public void uploadProfilPhoto(MultipartFile file,String newFileName,String userFolder) throws Exception {
        try {
            //fileStorageService.init();
            fileStorageService.storeFile(file,newFileName,userFolder);

        } catch (Exception ex) {
            log.info("uploadProfilPhoto for user :" + ex.getMessage());

        } finally {

        }
    }
    public Document uploadFile(MultipartFile file, Document document,String newFileName,String userFolder) throws Exception {
        try {
            String fileName = fileStorageService.storeFile(file,newFileName,userFolder);


        } catch (Exception ex) {
            log.info("uploadFile for user :" + ex.getMessage());

        } finally {
            return documentRepository.save(document);
        }
    }
    public String uploadStatementFile(MultipartFile file,String newFileName,String userFolder) throws Exception {
        String fileName="";
        try {
            fileName = fileStorageService.storeFile(file,newFileName,userFolder);

        } catch (Exception ex) {
            log.info("uploadFile for user :" + ex.getMessage());
            return fileName;

        } finally {
            return fileName;
        }
    }
    public ResponseEntity<Resource> downloadFile(String fileName,String userFolder, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName, userFolder);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



    public void deleteFileById(Long documentId) throws Exception{
        Document document = documentRepository.findById(documentId).get();

        //bilgisayardan silme işlemi
        Path targetLocation = this.fileStorageLocation.resolve(document.getClient().getClientFolder()+"\\"+document.getFileName()); //for windows
        Files.delete(targetLocation);
        //veritabında aktifliği 0 yapma
        documentRepository.deleteFileById(documentId);

    }
    public String createpdf(LetterDTO letterDTO) throws Exception{
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        String fileName = letterDTO.getClient().getId() + "-" +
                "PDF" + "-" + DateUtil.toString(LocalDateTime.now()) + letterDTO.getLetterType().getLetterTypeName() + ".pdf";
        try{
            String FILE = overcatchBackendApplication.fileUploadDir() + "/" + letterDTO.getClient().getClientFolder().replace('\\', '/') + "/" + fileName ;

            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            Paragraph paragraph1 = new Paragraph();
            paragraph1.add("\n\n\n");
            document.add(paragraph1);

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("LetterBackground.PNG");
           // File src = new ClassPathResource("LetterBackground.PNG").getFile();
            //String imageFilePath = src.getAbsolutePath();
            com.itextpdf.text.Image jpg = com.itextpdf.text.Image.getInstance(inputStream.readAllBytes());
            jpg.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
            jpg.setAlignment( com.itextpdf.text.Image.UNDERLYING);
            jpg.setAbsolutePosition(0, 0);
            document.add(jpg);

            String str = letterDTO.getLetter();
            String decodeStr = new String( Base64.getDecoder().decode(str));
            JSONObject jsonObject = new JSONObject("{ \"letterContent\":"+decodeStr+" }");
            JSONArray jsonArray = jsonObject.getJSONArray("letterContent");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject parentObject = jsonArray.getJSONObject(i);
                JSONArray jsonChildArray = parentObject.getJSONArray("children");
                if(jsonChildArray.length() > 0) {
                    String  childText = jsonChildArray.getJSONObject(0).getString("text");
                    Paragraph paragraph = new Paragraph();
                    paragraph.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 10));
                    paragraph.add(!childText.isEmpty() ? "\t\t" + childText : "\n");
                    document.add(paragraph);
                }
            }
            document.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return fileName;
    }

    public void deleteFileFromDisk(String folder, String fileName) throws Exception{
        //bilgisayardan silme işlemi
        Path targetLocation = this.fileStorageLocation.resolve(folder+"\\"+fileName); //for windows
        Files.delete(targetLocation);

    }

    public String createInvoicePdf (InvoiceDTO invoiceDTO) throws  Exception{
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        String fileName = invoiceDTO.getClient().getId() + "-" +
                "PDF" + "-" + DateUtil.toString(LocalDateTime.now()) + invoiceDTO.getInvoiceCode() + ".pdf";

        String FILE = overcatchBackendApplication.fileUploadDir() + "/" + invoiceDTO.getClient().getClientFolder().replace('\\', '/') + "/" + fileName ;
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        String base64Image = invoiceDTO.getPdf().split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        com.itextpdf.text.Image jpg = com.itextpdf.text.Image.getInstance(imageBytes);
        jpg.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
        jpg.setAlignment( Image.ALIGN_CENTER);
        jpg.setAbsolutePosition(0, 0);
        document.add(jpg);
        document.close();

        return  fileName;
    }
}

