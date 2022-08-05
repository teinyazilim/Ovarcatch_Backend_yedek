package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.DocumentDTO;
import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.dto.InvoiceDTO;
import com.tein.overcatchbackend.domain.dto.LetterDTO;
import com.tein.overcatchbackend.OvercatchBackendApplication;
import com.tein.overcatchbackend.domain.dto.*;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.dto.exception.FileStorageException;
import com.tein.overcatchbackend.domain.dto.exception.MyFileNotFoundException;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.NotificationLogFilter;
import com.tein.overcatchbackend.domain.vm.ResponseFileUpload;
import com.tein.overcatchbackend.enums.DocumentType;
import com.tein.overcatchbackend.mapper.DocumentMapper;
import com.tein.overcatchbackend.property.FileStorageProperties;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.DocumentRepository;
import com.tein.overcatchbackend.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final DocumentMapper documentMapper;
    private final DocumentRepository documentRepository;
    private final ClientRepository clientRepository;
    private final CurrentUserService currentUserService;
    private final UserService userService;
    private final OvercatchBackendApplication overcatchBackendApplication;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, DocumentMapper documentMapper, DocumentRepository documentRepository, ClientRepository clientRepository, CurrentUserService currentUserService, UserService userService, OvercatchBackendApplication overcatchBackendApplication) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.documentMapper = documentMapper;
        this.documentRepository = documentRepository;
        this.clientRepository = clientRepository;
        this.currentUserService = currentUserService;
        this.userService = userService;
        this.overcatchBackendApplication = overcatchBackendApplication;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    //TODO:FOR MAC (if you uses mac, change path)
    public String storeFile(MultipartFile file,String newFileName,String userFolder) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // this.fileStorageLocation.
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Files.createDirectories(this.fileStorageLocation.resolve(userFolder));
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(userFolder+"\\"+newFileName); //for windows
            //Path targetLocation = this.fileStorageLocation.resolve(userFolder+"//"+newFileName);//for mac
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation.toString();
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public InputStream storeFileAndReturn(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return file.getInputStream();
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            System.out.println("******"+filePath.toString());
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("**********"+resource.toString());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }


    public Resource loadFileAsResource(String fileName,String userFolder) {
        try {
            //TODO:FOR MAC (if you uses mac, change path)
            Path filePath = this.fileStorageLocation.resolve(userFolder+"\\"+fileName).normalize();//for windows
            //Path filePath = this.fileStorageLocation.resolve(userFolder+"//"+fileName).normalize();//for mac
            log.debug(filePath.toString());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("Please Upload a Logo ");
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("Please Upload a Logo  " + fileName, ex);
        }
    }
    public Document loadFilefromHelp(HelpDTO helpDTO, MultipartFile file) throws Exception {
        Client client = clientRepository.findByClientId(helpDTO.getClient().getId()).orElseThrow(() -> new AppException("Company not found"));
        var user = currentUserService.getCurrentUser();
        // String selectedUserCode = getCurrentUser(userCode);

        /*String fileType = getSupportedFileType(file);
        if (fileType == null) {
            throw new Exception("Geçersiz dosya formatı tespit edildi: " + file.getContentType());
        }*/

        String fileName = client.getId() + "-" +
                "HELP" + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

        try {
            DocumentDTO documentData = DocumentDTO.builder()
                    .processId(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .fileName(fileName)
                    .documentType(DocumentType.HELP)
                    .documentName(file.getOriginalFilename())
                    .filePath("downloadFile/" + user.getUserFolder() + "/" + fileName)
                    .fileDescription("")
                    .build();

            Document document = documentMapper.toEntity(documentData);
            document.setClient(client);
            storeFile(file, fileName, client.getClientFolder());
            return  documentRepository.save(document);
        } finally {
            try {
                file.getInputStream().close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    public Document loadFilefromNotification(MultipartFile file) throws Exception {
        var user = currentUserService.getCurrentUser();
        //NotificationCreate Bulunan kişi manager dolayısıyla ben bunun dizinine oluşturduğu notification-create leri çekip tekrar göndereceğim
        String fileName = user.getId() + "-" +
                "NOTIFICATION" + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        try {
            DocumentDTO documentData = DocumentDTO.builder()
                    .processId(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .fileName(fileName)
                    .documentType(DocumentType.NOTIFICATION)
                    .documentName(fileName)
                    //Notifications Klasörüne Managerın oluşturduğu notifications ları tek bir klasöre yüklüyorum.
                    .filePath(overcatchBackendApplication.fileUploadDir() + "\\Notifications\\")
                    .fileDescription(file.getOriginalFilename())
                    .build();

            Document document = documentMapper.toEntity(documentData);
            storeFile(file, fileName, overcatchBackendApplication.fileUploadDir()+"\\Notifications\\");
            return  documentRepository.save(document);
        } finally {
            try {
                file.getInputStream().close();
            } catch (Exception e) {
                //do nothing
            }
        }

    }

    public Document loadFileforLetter(LetterDTO letterDTO, MultipartFile file) throws Exception {
        Client client = clientRepository.findByClientId(letterDTO.getClient().getId()).orElseThrow(() -> new AppException("Company not found"));
//        var user = userService.findById(letterDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getId()) ;
        var user = client.getCustomerClients().get(0).getCustomerInfo().getUser();

        /*String fileType = getSupportedFileType(file);
        if (fileType == null) {
            throw new Exception("Geçersiz dosya formatı tespit edildi: " + file.getContentType());
        }*/

        String fileName = client.getId() + "-" +
                "LETTER" + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

        try {
            DocumentDTO documentData = DocumentDTO.builder()
                    .processId(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .fileName(fileName)
                    .documentType(DocumentType.LETTER)
                    .documentName(file.getOriginalFilename())
                    .filePath("downloadFile/" + user.getUserFolder() + "/" + fileName)
                    .fileDescription("")
                    .build();

            Document document = documentMapper.toEntity(documentData);
            document.setClient(client);
            storeFile(file, fileName, client.getClientFolder());
            return  documentRepository.save(document);
        } finally {
            try {
                file.getInputStream().close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }
    public String loadFileForInvoice (InvoiceDTO invoiceDto) throws Exception{
        Client client = clientRepository.findByClientId(invoiceDto.getClient().getId()).orElseThrow(()-> new AppException("Client not found"));
        var user = client.getCustomerClients().get(0).getCustomerInfo().getUser();
        String fileName = client.getId() + "-" +
                "LETTER" + "-" + DateUtil.toString(LocalDateTime.now()) + invoiceDto.getPdf().toString().substring(invoiceDto.getPdf().toString().indexOf("."));
        try {
            DocumentDTO documentData = DocumentDTO.builder()
                    .processId(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .fileName(fileName)
                    .documentType(DocumentType.INVOICE)
                    .documentName("invoiceDto.getPdf().getOriginalFilename()")
                    .filePath("downloadFile/" + user.getUserFolder() + "/" + fileName)
                    .fileDescription("")
                    .build();

            Document document = documentMapper.toEntity(documentData);
            document.setClient(client);
//            storeFile(invoiceDto.getPdf(),fileName,client.getClientFolder());
            return "dene";
        }finally {
            try {

               // invoiceDto.getPdf().getInputStream().close();
            }catch (Exception e){
                throw new Exception(e);
            }
        }
    }
}
