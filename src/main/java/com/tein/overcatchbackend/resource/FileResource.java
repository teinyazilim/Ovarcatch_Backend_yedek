package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.DocumentDTO;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.BankTransaction;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.domain.vm.ResponseFileUpload;
import com.tein.overcatchbackend.enums.DocumentType;
import com.tein.overcatchbackend.mapper.DocumentMapper;
import com.tein.overcatchbackend.repository.BankTransactionRepository;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.DocumentRepository;
import com.tein.overcatchbackend.repository.UserRepository;
import com.tein.overcatchbackend.service.*;
import com.tein.overcatchbackend.util.DateUtil;
import com.tein.overcatchbackend.util.GlobalVariable;
//import com.tein.overcatchbackend.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;


@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileResource {
    @Value("${filePath.frontEndConf}")
    public String filePathConf;

    @Value("${filePath.pdfConf}")
    public String pdfConf;

    public static String PDF_TYPE = "application/pdf";

    public static String EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String OLD_EXCEL_TYPE = "application/vnd.ms-excel";
    public static String OCTET_STREAM = "application/octet-stream";

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;
    private final DocumentRepository documentRepository;
    private final ClientRepository clientRepository;
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final BankStatementConvertService bankStatementConvertService;
    private final StatementExcelExportService statementExcelExportService;
    private final BankTransactionRepository bankTransactionRepository;
    private final TransactionDetailConverterService transactionDetailConverterService;

    public static String getSupportedFileType(MultipartFile file) {
       /* if (hasPdfFormat(file)) {

        } else if (hasImageFormat(file)) {
            return CSV_TYPE;
        } else {
            return null;
        }*/
        return "";
    }

    public static boolean hasPdfFormat(MultipartFile file) {
        return PDF_TYPE.equals(file.getContentType());  //|| PDF_TYPE1.equals(file.getContentType()) ;
    }

    @GetMapping("/downloadFile/{clientId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFileWithUserFolder(@PathVariable String fileName, @PathVariable String clientId, HttpServletRequest request) {

        Client client = clientRepository.findById(Long.parseLong(clientId)).get();
        return documentService.downloadFile(fileName, client.getClientFolder(), request);
    }
    @GetMapping("/downloadPhoto/{userid}/{fileName:.+}")
    public ResponseEntity<Resource> getProfilPhotoWithUserFolder(@PathVariable String fileName, @PathVariable String userid, HttpServletRequest request) {
        User user = userRepository.findById(Long.parseLong(userid)).get();
        return documentService.downloadFile(fileName, user.getUserFolder(), request);
    }

    @GetMapping("/downloadStatement/{fileName:.+}")
    public ResponseEntity<Resource> downloadStatement(@PathVariable String fileName, HttpServletRequest request) {

        System.out.println(fileName);

        return documentService.downloadFile(fileName, "statement", request);
    }
    //Resim Güncelleme için yapıldı

    //localhost:8081/api/file/downloadFile/solution-BRPCARD-20201121011001-.pdf örnek kullanımı
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        var user = currentUserService.getCurrentUser();
        return documentService.downloadFile(fileName, user.getUserFolder(), request);
    }

    //Resim Güncelleme için yapıldı
    @PostMapping("/profil/upload")
    public ResponseEntity<ResponseFileUpload> uploadProfilPhoto(@RequestParam("file") MultipartFile file) throws Exception {
        var user = currentUserService.getCurrentUser();

        //Aşağıdaki if koşulunda eğer user tablosunda user/folder kısımı null ise
        //Kullanıcının resimlerini yükleyebilmesi için klasör path yolu veriyorum
        //Böylelikle kullanıcı yeni bir resim yüklemek isteyip Server'da klasör dizini bulunmuyor ise hata almamasını sağlıyorum .
        if(user.getUserFolder() == null){
            user.setUserFolder("user\\"+user.getName().toUpperCase(Locale.ROOT).toString() + user.getId());
        }

        //String fileName=user.getName().replaceAll(" ","")+ "-"+ DateUtil.toString(LocalDateTime.now())+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String fileName = GlobalVariable.converSessiz(user.getName()) + "-" + DateUtil.toString(LocalDateTime.now()) + ".jpg";
        documentService.uploadProfilPhoto(file, fileName, user.getUserFolder());
        user.setPhotoURL(fileName);
        //user.setPhoto(ImageUtils.resizeImage(file));
        userRepository.save(user);

        return ResponseEntity.ok().body(ResponseFileUpload.builder().processId(UUID.randomUUID().toString()).build());

    }


    @PostMapping("/upload")
    public ResponseEntity<ResponseFileUpload> uploadFile(@RequestParam("file") MultipartFile file,
                                                         @RequestParam(required = false, name = "documentType") DocumentType documentType,
                                                         @RequestParam(required = false, name = "companyId") Long companyId,
                                                         @RequestParam(required = false, name = "description") String description) throws Exception {

        Client client = clientRepository.findByClientId(companyId).orElseThrow(() -> new AppException("Company not found"));

        var user = currentUserService.getCurrentUser();
        // String selectedUserCode = getCurrentUser(userCode);

        /*String fileType = getSupportedFileType(file);
        if (fileType == null) {
            throw new Exception("Geçersiz dosya formatı tespit edildi: " + file.getContentType());
        }*/

        String fileName = client.getId() + "-" +
                documentType.toString() + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));


        try {
            DocumentDTO documentData = DocumentDTO.builder()
                    .processId(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .clientId(client.getId())
                    .fileName(fileName)
                    .documentType(documentType)
                    .documentName(file.getOriginalFilename())
                    .filePath("downloadFile/" + user.getUserFolder() + "/" + fileName)
                    .fileDescription(description)
                    .isActive(true)
                    .build();

            Document document = documentMapper.toEntity(documentData);
            document.setClient(client);

            //Document forFrontEnd = documentService.uploadFile(file, document, fileName, filePathConf);
            //ResponseEntity.ok().body(ResponseFileUpload.builder().processId(forFrontEnd.getId().toString()).build());

            Document document1 = documentService.uploadFile(file, document, fileName, client.getClientFolder());
            return ResponseEntity.ok().body(ResponseFileUpload.builder().processId(document1.getId().toString()).build());
        } finally {
            try {
                file.getInputStream().close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }
    @PostMapping("/upload/pdf")
    public ResponseEntity<ResponseFileUpload> uploadPdf(@RequestParam("file") MultipartFile file,
                                                         @RequestParam(required = false, name = "documentType") DocumentType documentType,
                                                         @RequestParam(required = false, name = "companyId") Long companyId,
                                                         @RequestParam(required = false, name = "description") String description) throws Exception {

        Client client = clientRepository.findByClientId(companyId).orElseThrow(() -> new AppException("Company not found"));

        var user = currentUserService.getCurrentUser();
        String fileName = client.getId() + "-" +
                documentType.toString() + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        try {
            DocumentDTO documentData = DocumentDTO.builder()
                    .processId(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .fileName(fileName)
                    .documentType(documentType)
                    .documentName(file.getOriginalFilename())
                    .filePath("downloadFile/" + user.getUserFolder() + "/" + fileName)
                    .fileDescription("")
                    .build();

            Document document = documentMapper.toEntity(documentData);
            document.setClient(client);

            Document forPdf = documentService.uploadFile(file, document, fileName, pdfConf);
            return ResponseEntity.ok().body(ResponseFileUpload.builder().processId(forPdf.getId().toString()).build());
        } finally {
            try {
                file.getInputStream().close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }
    @PostMapping("/uploadstatement")
    public ResponseEntity<ResponseFileUpload> uploadStatementFile(@RequestParam("file") MultipartFile file,

        @RequestParam(required = false, name = "bankType") String bankType,
        @RequestParam(required = false, name = "clientId") String clientId,
        @RequestParam(required = false, name = "statementType") String statementType) throws Exception {
        String filePath="C:/Users/Tein/test/statement/";
        try {
            String fileName = clientId+"-"+
                    DocumentType.BANK.toString() + "-" + bankType+ "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
//            String fileName=statementExcelExportService.asposePdftoExcelExport(s);
            String s=documentService.uploadStatementFile(file,fileName,"statement\\" );
            Document document=new Document();
            document.setDocumentName(fileName);
            document.setFileDescription("Statement");
            document.setFilePath("C:/Users/Tein/test/statement/" + fileName);
            document.setUserID(currentUserService.getCurrentUser().getId());
            document.setDocumentType(DocumentType.BANK);
            Client client=clientRepository.findById(Long.parseLong(clientId)).get();
            document=documentRepository.save(document);
            BankTransaction bankTransaction=new BankTransaction();
            bankTransaction.setPdfName(file.getOriginalFilename());
            bankTransaction.setBankType(bankType);
            bankTransaction.setClient(client);
            bankTransaction.setClientType(client.getClientTypeEnum().toString());
            bankTransaction.setStatementType(statementType);
            bankTransaction.setDocument(document);

            BankTransaction b = bankTransactionRepository.save(bankTransaction);
//            clientName-Bank Statement - Date
            switch (bankType) {
                case "MONESE":
                    transactionDetailConverterService.writeMonese(s,b);
                    break;
                case "TURKISH":
                    transactionDetailConverterService.writeTurkish(s,b);
                    break;
                case "HSBC":
                    fileName=statementExcelExportService.eadgePdftoExcelExport (s,bankType);
                    break;
                case "TIDE":
                    transactionDetailConverterService.writeTide(s,b);
                    break;
                case "STARLING":
                    transactionDetailConverterService.writeStarling(s,b);
                    break;
                case "NATWEST":
                    transactionDetailConverterService.writeNatwest(s,b);
                    break;
                case "MONZO":
                    transactionDetailConverterService.writeMonzo(s,b);
                    break;
//                    transactionDetailConverterService.convertMonzo(s);
                case "METRO":
                    break;
                case "LLOYDS":
                    break;
                case "BARCLAYS":
                    fileName=statementExcelExportService.iceBluePdftoExcelExport(s,bankType);
                    File file1=new File(filePath+fileName);
                    bankStatementConvertService.ExcelConvert(file1);
                    break;
            }

            bankTransactionRepository.save(bankTransaction);


            return ResponseEntity.ok().body(ResponseFileUpload.builder().processId(fileName).build());
        } finally {
            try {
                file.getInputStream().close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }
    @PostMapping("/uploadExport")
    public ResponseEntity<ResponseFileUpload> uploadandExport(@RequestParam("file") MultipartFile file,
                                                                  @RequestParam(required = false, name = "bankType") String bankType,
                                                                  @RequestParam(required = false, name = "clientId") String clientId,
                                                                  @RequestParam(required = false, name = "statementType") String statementType) throws Exception {

        try {
            String fileName =
                    DocumentType.BANK.toString() + "-" + bankType+ "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
//            String fileName=statementExcelExportService.asposePdftoExcelExport(s);
            String s=documentService.uploadStatementFile(file,fileName,"statement\\" );

            fileName=statementExcelExportService.eadgePdftoExcelExport(s,bankType);
            Document document=new Document();
            document.setDocumentName(fileName);
            document.setFileDescription("Statement");
            document.setFilePath("C:/Users/Tein/test/statement/" + fileName);
            document.setUserID(currentUserService.getCurrentUser().getId());
            document.setDocumentType(DocumentType.OTHER);
            document=documentRepository.save(document);

//            com.aspose.pdf.Document document = new com.aspose.pdf.Document(s);
//            Integer randomNumber;
//            Random random = new Random();
//            randomNumber = random.nextInt(899999) + 100000;
//            String fileName=filePath+""+randomNumber+".xls";
//            document.save(fileName, com.aspose.pdf.SaveFormat.Excel);
//            document.close();
//            File statementFile=new File(fileName);
//
//            String name=bankStatementConvertService.ExcelConvert(statementFile);
            return ResponseEntity.ok().body(ResponseFileUpload.builder().processId(fileName).build());
        } finally {
            try {
                file.getInputStream().close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    private String getCurrentUser(String userCode) throws Exception {
        String selectedUserCode = userCode;

        try {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getId().toString();
        } catch (Exception ex) {
            //do nothing
        }
        if (selectedUserCode == null || selectedUserCode.isEmpty()) {
            throw new Exception("Kullanıcı kodu belirtilmedi");
        }
        return null;
    }

}
