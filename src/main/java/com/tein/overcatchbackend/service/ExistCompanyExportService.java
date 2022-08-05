package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.DocumentDTO;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.*;
import com.tein.overcatchbackend.repository.*;
import com.tein.overcatchbackend.util.GlobalVariable;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.mapstruct.control.MappingControl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ExistCompanyExportService {
    private ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final CustomerClientRepository customerClientRepository;
    private final CustomerRepository customerRepository;
    private final DocumentRepository documentRepository;
    private final PersonelRepository personelRepository;

    public void companyExcelExport() throws IOException {

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        try {
            String file="C:\\teinApp\\organizasyon1.xlsx";
            XSSFWorkbook myWorkBook = new XSSFWorkbook(new FileInputStream(file));

                XSSFSheet firstSheet = myWorkBook.getSheetAt(0);
                for (int k = 1; k <= firstSheet.getPhysicalNumberOfRows(); k++) {
                    Row row = firstSheet.getRow(k);
                    if (row != null) {
                        //Model Tanımlamaları
                        Client client=new Client();
                        Address address=new Address();
                        Address addressHome=new Address();
                        Company company=new Company();
                        User user =new User();
                        Customer customer=new Customer();
                        CustomerClient customerClient=new CustomerClient();
                        user.setUserType(UserType.CUSTOMER);
                        user.setPassword(passwordEncoder.encode("12345"));
//                        user.setEmail("data[k-3]+@tein.com.tr");
                        user.setRoles(rolesFromStrings(Arrays.asList("CUSTOMER")));

                        //REF-NO Code
                        if(row.getCell(0) == null){
                            break;
                        }
                        //REF-NO Code
                        if(row.getCell(0) !=null){
                            if(row.getCell(0).getRichStringCellValue().toString().contains("AL")){
                                client.setAgreementType(AgreementType.ECAA);

                            }else if(row.getCell(0).getRichStringCellValue().toString().contains("TL")){
                                client.setAgreementType(AgreementType.TRADING);
                            }
                            client.setClientTypeEnum(ClientTypeEnum.LIMITED);
                            client.setIsExisting(true);
                            client.setState("3");
                            client.setIsMailSend(false);
                            client.setClientFileName("firstFile");
                            client.setIsExisting(true);
                            client.setIsActive(true);
                            user.setIsActive(true);
                            user.setIsDeleted(false);
                            client.setCode(row.getCell(0).getRichStringCellValue().toString());
                        }
                        //Company Name
                        if(row.getCell(1) !=null){
                            company.setName(row.getCell(1).getRichStringCellValue().toString());
                        }
                        //Title
                        if(row.getCell(2) !=null){
                            user.setTitle(row.getCell(2).getRichStringCellValue().toString());
                        }
                        //User Name
                        if(row.getCell(3) !=null){

                            String name=row.getCell(3).getRichStringCellValue().toString();
                            if(row.getCell(4)!=null){
                                name=name+" "+row.getCell(4).getRichStringCellValue().toString();
                            }
                            user.setName(name);

                        }
                        //User Surname
                        if(row.getCell(5) !=null){
                            String surname=row.getCell(5).getRichStringCellValue().toString();
                            user.setSurname(surname);
                        }
                        //Mobile phone
                        if(row.getCell(6) !=null && row.getCell(6).toString().length()>0){
                            try{
                                String s = NumberToTextConverter.toText(row.getCell(6).getNumericCellValue());
                                if(s.charAt(0)!='0'){
                                    user.setMsisdn("0"+s);
                                }else {
                                    user.setMsisdn(s);
                                }
                            }catch(Exception e){
                                String veri=row.getCell(6).toString().replaceAll(" ","");
                                if(veri.charAt(0)!='0'){
                                    user.setMsisdn("0"+veri);
                                }else {
                                    user.setMsisdn(veri);
                                }
                            }

//                            String expiryDate=row.getCell(3).getRichStringCellValue().toString();
//                            expiryDate.replace(" ", "");
//                            expiryDate.replace(".", "/");
//                            director.setVisaExpiryDate(LocalDate.parse(expiryDate, formatter));
                        }
                        //Email
                        if(row.getCell(7) !=null && row.getCell(7).toString().length()>0){
                            user.setEmail(row.getCell(7).toString());
                        }
                        if(row.getCell(8) !=null && user.getEmail() ==null && row.getCell(8).toString().length()>0){
                            user.setEmail(row.getCell(8).toString());                        }
                        if(row.getCell(10) !=null && row.getCell(10).toString().length()>0){

                            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.uuuu",new Locale("tr"));
                            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("d.MM.uuuu",new Locale("tr"));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            company.setIncorporatedDate(LocalDate.parse(dateFormat.format(row.getCell(10).getDateCellValue())));

                        }

                        // Company UTR
                        if(row.getCell(11) !=null && row.getCell(11).toString().length()>0){
                            String s = NumberToTextConverter.toText(row.getCell(11).getNumericCellValue());
                            company.setCompanyUtr(String.valueOf(s));
                        }

                        // Company House Authentication
                        if(row.getCell(12) !=null && row.getCell(12).toString().length()>0){
                            Cell cell = row.getCell(12);
                            cell.setCellType(CellType.STRING);
                            company.setAuthentication(cell.getStringCellValue());
                        }

                        String date1="";
                        //Year End Day
                        if(row.getCell(13) !=null && row.getCell(13).toString().length()>0){

                            date1=   String.valueOf((int)(row.getCell(13).getNumericCellValue()))+"/";

                        }
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.ENGLISH);
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/MMM/uuuu", Locale.ENGLISH);
                        LocalDate yearEndDay;
                        LocalDate localDate=LocalDate.now();
                        //Year End Month
                        if(row.getCell(14) !=null && row.getCell(14).toString().length()>0){
                            date1 = date1+row.getCell(14).toString()+"/";
                            date1 = date1+localDate.getYear();
                            yearEndDay = LocalDate.parse(date1, date1.length() > 10 ? formatter : formatter1);
                            client.setYearEndDate(yearEndDay);
                            company.setYearEndDate(yearEndDay);
                        }

                        //Paye Number
                        if(row.getCell(15) !=null && row.getCell(15).toString().length()>0){
                            company.setPayeNumber(row.getCell(15).getRichStringCellValue().toString());
                        }

                        //Paye Office Ref
                        if(row.getCell(16) !=null && row.getCell(16).toString().length()>0){
                            company.setPaOfficeNumber(row.getCell(16).getRichStringCellValue().toString());
                        }

                        //VAT Number
                        if(row.getCell(17) !=null && row.getCell(17).toString().length()>0){
                            try{
                                String s = NumberToTextConverter.toText(row.getCell(17).getNumericCellValue());
                                client.setVatNumber(s);
                                company.setVatNumber(s);
                            }catch(Exception e){
                                client.setVatNumber(row.getCell(17).getRichStringCellValue().toString());
                                company.setVatNumber(row.getCell(17).getRichStringCellValue().toString());
                            }
                        }

                        //VAT Number
                        if(row.getCell(18) !=null && row.getCell(18).toString().length()>0){
                            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH);
                            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("d/MM/uuuu", Locale.ENGLISH);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            client.setVatPeriodEnd(LocalDate.parse(dateFormat.format(row.getCell(18).getDateCellValue())));
//                            client.setVatPeriodEnd(LocalDate.parse(String.valueOf(row.getCell(18).getDateCellValue().getDay()) +"/"+String.valueOf(row.getCell(18).getDateCellValue().getMonth())+"/"+String.valueOf(row.getCell(18).getDateCellValue().getYear()),row.getCell(18).toString().length() > 10 ? formatter2 : formatter3));
                        }
                        //Vat Flat Rate Scheme
                        if(row.getCell(19) !=null && row.getCell(19).toString().length()>0){
                            client.setVatFlatRate(row.getCell(19).toString());
                        }

                        String date2="";
                        //Year End Day
                        if(row.getCell(20) !=null && row.getCell(20).toString().length()>0){
                            date2=  String.valueOf((int)(row.getCell(20).getNumericCellValue()))+"/";

                        }
                        //Year End Month
                        if(row.getCell(21) !=null && row.getCell(21).toString().length()>0){
                            date2 = date2+row.getCell(21).toString()+"/";
                            date2 = date2+localDate.getYear();
                            yearEndDay = LocalDate.parse(date2, date2.length() > 10 ? formatter : formatter1);
                            company.setStatementDueDate(yearEndDay);
                        }

                        // Address Line 1
                        if(row.getCell(22) !=null && row.getCell(22).toString().length()>0){
                            address.setAddressType(AddressType.OFFICE);
                              address.setStreet(row.getCell(22).toString());
//                            client.setVatFlatRate(row.getCell(22).toString());
                        }

                        // Address Line 2
                        if(row.getCell(23) !=null && row.getCell(23).toString().length()>0){
                              if(address.getStreet().length()<3){
                                  address.setStreet(address.getStreet()+ " "+ row.getCell(23).toString());
                              }else{
                                  address.setCity(row.getCell(23).toString());
                              }
//                            address.setAddressType(AddressType.OFFICE);
//                            client.setVatFlatRate(row.getCell(23).toString());
                        }

                        // Address Line 3
                        if(row.getCell(24) !=null && row.getCell(24).toString().length()>0){
                            if(address.getCity()!=null){
                                address.setCity(row.getCell(24).toString());
                            }
//                            address.setAddressType(AddressType.OFFICE);
//                            client.setVatFlatRate(row.getCell(23).toString());
                        }
                        // Address Line 4
                        if(row.getCell(25) !=null && row.getCell(25).toString().length()>0){
//                            address.setAddressType(AddressType.OFFICE);
//                            client.setVatFlatRate(row.getCell(23).toString());
                        }

                        // PostCode
                        if(row.getCell(26) !=null && row.getCell(26).toString().length()>0){
                            address.setPostcode(row.getCell(26).toString());
//                            client.setVatFlatRate(row.getCell(23).toString());
                        }
                        address.setCounty("United Kingdom");

                        // Registered Office Number
                        if(row.getCell(28) !=null  && row.getCell(28).toString().length()>0){
                            client.setNotes(row.getCell(28).toString());
                        }

                        if(row.getCell(29) !=null  && row.getCell(29).toString().length()>0){
                            try{
                                String s = NumberToTextConverter.toText(row.getCell(29).getNumericCellValue());
                                company.setRegistration(s);
                            }catch(Exception e){
                                company.setRegistration(row.getCell(29).getRichStringCellValue().toString());
                            }
                        }

                        // Home Address Line 1
                        if(row.getCell(30) !=null && row.getCell(30).toString().length()>0){
                            addressHome.setAddressType(AddressType.HOME);
                            addressHome.setStreet(row.getCell(30).toString());
//                            client.setVatFlatRate(row.getCell(22).toString());
                        }

                        // Address Line 2
                        if(row.getCell(31) !=null && row.getCell(31).toString().length()>0){
                            if(addressHome.getStreet().length()<3){
                                addressHome.setStreet(addressHome.getStreet()+ " "+ row.getCell(31).toString());
                            }else{
                                addressHome.setCity(row.getCell(31).toString());
                            }
//                            address.setAddressType(AddressType.OFFICE);
//                            client.setVatFlatRate(row.getCell(23).toString());
                        }

                        // Address Line 3
                        if(row.getCell(32) !=null && row.getCell(32).toString().length()>0){
                            if(addressHome.getCity()!=null){
                                addressHome.setCity(row.getCell(32).toString());
                            }
//                            address.setAddressType(AddressType.OFFICE);
//                            client.setVatFlatRate(row.getCell(23).toString());
                        }

                        // PostCode
                        if(row.getCell(34) !=null && row.getCell(34).toString().length()>0){
                            addressHome.setPostcode(row.getCell(34).toString());
//                            client.setVatFlatRate(row.getCell(23).toString());
                        }
                        addressHome.setCounty("United Kingdom");

                        if(row.getCell(37) != null && row.getCell(37).toString().length()>0){
                            company.setVatPeriod(row.getCell(37).toString());
                        }
                        if(row.getCell(38) != null && row.getCell(38).toString().length()>0){
                            company.setVatRegisterDate(row.getCell(38).toString());
                        }
//                        //Registared Address Line 1
//                        if(row.getCell(13) !=null){
//                            address.setAddressType(AddressType.OFFICE);
//                            String[] asd=row.getCell(13).getStringCellValue().toString().split(" ",2);
//                            address.setNumber(asd[0]);
//                            address.setStreet(asd[1]);
//                        }
//                        //Registared Address Line 2
//                        if(row.getCell(14) !=null){
//                            address.setCity(row.getCell(14).getStringCellValue().toString());
//                        }
//                        //Registared Address Line 3
//                        if(row.getCell(15) !=null){
//
//                        }
//                        //Registared Address Line 4
//                        if(row.getCell(16) !=null){
//
//                        }




//                        //Confirmation Statement Day
//                        if(row.getCell(33) !=null){
//                            date2=row.getCell(33).toString().substring(0,row.getCell(33).toString().length()-2)+"/";
//                        }
//                        //Confirmation Statement Month
//                        if(row.getCell(34) !=null){
//                            date2=date2+row.getCell(34).toString()+"/";
//                            date2=date2+localDate.getYear();
//                            company.setStatementDueDate(LocalDate.parse(date2, date2.length() > 10 ? formatter : formatter1));
//                        }
//                        //Company Auth Code
//                        if(row.getCell(35) !=null){
//                            company.setAuthentication(row.getCell(35).getRichStringCellValue().toString());
//                        }
//                        //Notes
//                        if(row.getCell(36) !=null){
//                            client.setNotes(row.getCell(36).getStringCellValue().toString());
//                        }

                        if(user.getEmail()!=null){
                            User result = userRepository.save(user);
                            result.setUserFolder("user\\"+ GlobalVariable.converSessiz(result.getName())+result.getId());
                            User result2 = userRepository.save(result);
                            client.setIsActive(true);
                            customer.setUser(result2);
                            customerRepository.save(customer);
                            ArrayList addresList = new ArrayList();
                            addresList.add(address);
                            addresList.add(addressHome);

                            client.setAddressList(addresList);
                            client.setCompany(company);
                            Client client1=clientRepository.save(client);
                            client1.setClientFolder("client\\"+client1.getClientTypeEnum()+"-"+client1.getId().toString());
                            Client client2 = clientRepository.save(client1);
                            customerClient.setClient(client2);
                            customerClient.setCustomerInfo(customer);
                            customerClient.setSharePercent(100);
                            customerClientRepository.save(customerClient);
                            importDocument(client1,client1.getCode(),"","",result2.getId());
                        }

                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        directorExcelExport();
    }
    public void importDocument(Client client,String sourcePath,String filePrefix,String filePosFix, Long userId) throws IOException {

        String directory = "C:\\teinApp\\tein\\";
        String importDirectory ="C:\\Users\\tein\\test";

        try{

            //todo bu  kısımda client doküman olmasa bile folder oluşturulmalı

            File[] files = new File(directory+sourcePath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return !file.isDirectory();
                }
            });
            if(files!=null) {
                for (File file : files) {
                    Document document=new Document();
                    document.setClient(client);
                    document.setIsActive(true);
                    DocumentType documentType = getFileType(file.getName());
                    document.setDocumentType(documentType);
                    String fileName = client.getId() + "-" + filePrefix + " " + file.getName();
                    document.setFileName(fileName);
                    document.setFilePath(importDirectory + "\\" + client.getClientFolder() + "\\" + document.getFileName());
                    document.setUserID(userId);
                    document.setDocumentName(file.getName());
                    documentRepository.save(document);
                    Files.createDirectories(Path.of(importDirectory + "\\" + client.getClientFolder()+ "\\" + filePosFix ));
                    Files.copy(Path.of(file.getAbsolutePath()), Path.of(importDirectory +"\\"+client.getClientFolder() + "\\" + filePosFix + "\\"+document.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public DocumentType getFileType(String filename){
        String prefix = filename.split("-")[0];
        if(prefix.equals("1"))
            return DocumentType.PASSPORT;
        if(prefix.equals("2"))
            return DocumentType.NINO;
        if(prefix.equals("3"))
            return DocumentType.UTR;
        if(prefix.equals("4"))
            return DocumentType.GATEWAYID;
        if(prefix.equals("5"))
            return DocumentType.DRIVINGLICENCE;
        if(prefix.equals("6"))
            return DocumentType.BANK;
        if(prefix.equals("7"))
            return DocumentType.BANKSTATEMENT;
        if(prefix.equals("8"))
            return DocumentType.OTHER;
        if(prefix.equals("9"))
            return DocumentType.VISA;
        if(prefix.equals("10"))
            return DocumentType.BRPFRONT;
        if(prefix.equals("11"))
            return DocumentType.BRPBACK;
        if(prefix.equals("12"))
            return DocumentType.POLICEREGISTRATION;
        if(prefix.equals("13"))
            return DocumentType.IDCARD;
        if(prefix.equals("14"))
            return DocumentType.PAYE;
        if(prefix.equals("15"))
            return DocumentType.PAYEREFERENCE;
        if(prefix.equals("16"))
            return DocumentType.COMPANY;
        if(prefix.equals("17"))
            return DocumentType.COMPANYUTR;
        if(prefix.equals("18"))
            return DocumentType.COMPANYAUTHENTICATIONCODE;
        if(prefix.equals("19"))
            return DocumentType.VATCERTIFICATE;
        if(prefix.equals("20"))
            return DocumentType.COMPANYSHARE;
        if(prefix.equals("21"))
            return DocumentType.COMPANYMOMERANDUM;
        if(prefix.equals("22"))
            return DocumentType.LETTER;
        if(prefix.equals("23"))
            return DocumentType.APPEALLALETTERCT211;
        if(prefix.equals("24"))
            return DocumentType.APPEALLALETTERCT600;
        if(prefix.equals("25"))
            return DocumentType.MILLITERYLETTER;
        if(prefix.equals("26"))
            return DocumentType.LIMITEDHMRCONVERTLETTER;
        if(prefix.equals("27"))
            return DocumentType.SOLOTRADERHMRCONVERTLETTER;
        if(prefix.equals("28"))
            return DocumentType.SELFASSESMENTHMRCONVERTLETTER;
        if(prefix.equals("29"))
            return DocumentType.LETTERFORBANK;
        if(prefix.equals("30"))
            return DocumentType.LETTERFOREAA2LIMITED;
        if(prefix.equals("31"))
            return DocumentType.LETTERFOREAA2SOLOTRADER;
        if(prefix.equals("32"))
            return DocumentType.LETTERFORNIAAPPLICATION;
        if(prefix.equals("33"))
            return DocumentType.LETTERFORVISAAPPLICATION;
        if(prefix.equals("34"))
            return DocumentType.RENTREFERENCE;
        if(prefix.equals("35"))
            return DocumentType.RENTREFERENCEFIGURESLIMITED;
        if(prefix.equals("36"))
            return DocumentType.RENTREFERENCEFIGURESSOLOTRADER;

        return DocumentType.OTHER;
    }
    public Set<Roles> rolesFromStrings(List<String> roles) {
        Set<Roles> rolesSet = new HashSet<>();
        roles.forEach((String item) -> {
            Roles byRoleCode = rolesRepository.findByRoleCode(item).orElseThrow(() -> new AppException("User Role not set."));
            Roles role = null;
            if (byRoleCode != null) {
                role = byRoleCode;
            } else {
                new Exception("Role not found");
            }
            rolesSet.add(role);
        });
        return rolesSet;
    }
    public void directorExcelExport() throws IOException {
        String file="C:\\teinApp\\x.xlsx";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        try {
            XSSFWorkbook myWorkBook = new XSSFWorkbook(new FileInputStream(file));

            XSSFSheet firstSheet = myWorkBook.getSheetAt(0);

            for (int k = 1; k <= firstSheet.getPhysicalNumberOfRows(); k++) {
                Row row = firstSheet.getRow(k);
                if (row != null) {
                    //Model Tanımlamaları
                    Client client1=null;
                    Address address=new Address();
                    DirectorDetail director=new DirectorDetail();

                    //REF-NO Code
                    if(row.getCell(0) == null){
                        break;
                    }
                    //REF-NO Code
                    if(row.getCell(0) !=null){
                        //if(row.getCell(0).toString().contains("AL139")){
                         //   System.out.println("test");
                        //}
                        client1=clientRepository.findByCode(row.getCell(0).toString().substring(0,5));

                        director.setCode(row.getCell(0).toString());
                    }
                    if(row.getCell(1) !=null){
                        director.setInitial(row.getCell(1).toString());
                    }
                    //Director Title
                    if(row.getCell(2) !=null && row.getCell(2).toString().length()>0){
                        director.setName(row.getCell(2).getRichStringCellValue().toString());
                    }
                    //Director SurName
                    if(row.getCell(4) !=null && row.getCell(4).toString().length()>0){
                        director.setSurname(row.getCell(4).getRichStringCellValue().toString());
                    }
                    //Director NINO
                    if(row.getCell(6) !=null && row.getCell(6).toString().length()>0){
                        director.setNino(row.getCell(6).toString());
                    }
                    //Director UTR
                    if(row.getCell(7) !=null && row.getCell(7).toString().length()>0){
                        String s = NumberToTextConverter.toText(row.getCell(7).getNumericCellValue());
                        director.setUtr(s.toString());
                    }
                    //Director Mobile
                    if(row.getCell(8) !=null && row.getCell(8).toString().length()>0){
                        try{
                            String s = NumberToTextConverter.toText(row.getCell(8).getNumericCellValue());
                            if(s.charAt(0)!='0'){
                                director.setPhoneNumber("0"+s);
                            }else {
                                director.setPhoneNumber(s);
                            }
                        }catch(Exception e){
                            String veri=row.getCell(8).toString().replaceAll(" ","");
                            if(veri.charAt(0)!='0'){
                                director.setPhoneNumber("0"+veri);
                            }else {
                                director.setPhoneNumber(veri);
                            }
                        }
                    }
                    //Director Nationality
                    if(row.getCell(9) !=null && row.getCell(9).toString().length()>0){
                        director.setNationality(row.getCell(9).getRichStringCellValue().toString());
                    }

                    String addressText="";
                    //Registared Address Line 1
                    if(row.getCell(10) !=null && row.getCell(10).toString().length()>0){
                        addressText=addressText+row.getCell(10).toString();
                    }
                    if(row.getCell(11) !=null && row.getCell(11).toString().length()>0){
                        addressText=addressText+row.getCell(11).toString();
                    }
                    if(row.getCell(12) !=null && row.getCell(12).toString().length()>0){
                        addressText=addressText+row.getCell(12).toString();
                    }
                    if(row.getCell(13) !=null && row.getCell(13).toString().length()>0){
                        addressText=addressText+row.getCell(13).toString();
                    }
                    if(row.getCell(14) !=null && row.getCell(14).toString().length()>0){
                        addressText=addressText+row.getCell(14).toString();
                    }
                    director.setResidentailAddress(addressText);

                    if(row.getCell(22) !=null && row.getCell(22).toString().length()>0){
                        try{
                            String s = NumberToTextConverter.toText(row.getCell(22).getNumericCellValue());
                            if(s.charAt(0)!='0'){
                                director.setPhoneNumber("0"+s);
                            }else {
                                director.setPhoneNumber(s);
                            }
                        }catch(Exception e){
                            String veri=row.getCell(22).toString().replaceAll(" ","");
                            if(veri.charAt(0)!='0'){
                                director.setPhoneNumber("0"+veri);
                            }else {
                                director.setPhoneNumber(veri);
                            }
                        }
                    }

                    if(row.getCell(27) !=null && row.getCell(27).toString().length()>0  && director.getPhoneNumber()==null){
                        director.setEmail(row.getCell(27).toString());
                    }

                    if(row.getCell(23) !=null && row.getCell(23).toString().length()>0  && director.getEmail()==null){
                        director.setEmail(row.getCell(23).toString());
                    }

                    //Director D.O.B Date Türkçe Geliyor
                   // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu", new Locale("tr"));
                    //DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/MM/uuuu",new Locale("tr"));
                    LocalDate date;
                    if(row.getCell(29) !=null && row.getCell(29).toString().length()>0){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        director.setDob(LocalDate.parse(dateFormat.format(row.getCell(29).getDateCellValue())));
                    }


                    //Director Active- Boş
                    if(row.getCell(30) !=null){

//                            String expiryDate=row.getCell(7).getRichStringCellValue().toString();
//                            expiryDate.replace(" ", "");
//                            expiryDate.replace(".", "/");
//                            client.setYearEndDate(LocalDate.parse(expiryDate, formatter));
                    }

                    //Nationality
                    if(row.getCell(10) !=null){

//                            String expiryDate=row.getCell(8).getRichStringCellValue().toString();
//                            expiryDate.replace(" ", "");
//                            expiryDate.replace(".", "/");
//                            company.setDueDate(LocalDate.parse(expiryDate, formatter));
                    }

                    //Mobile Phone
                    if(row.getCell(9) !=null){

                    }


                    //Registared Address Line 2
                    if(row.getCell(12) !=null){
//                        address.setCity(row.getCell(14).getStringCellValue().toString());
                    }
                    //Registared Address Line 3
                    if(row.getCell(13) !=null){

                    }
                    //Registared Address Line 4
                    if(row.getCell(16) !=null){

                    }

                    //Registared Post Code
                    if(row.getCell(14) !=null){
//                        address.setPostcode(row.getCell(17).getStringCellValue().toString());

                    }
                    //Registered Office Country
                    if(row.getCell(18) !=null){
//                        address.setCounty(row.getCell(18).getStringCellValue().toString());
                    }
                    //Registered Office Title
                    if(row.getCell(19) !=null){

                    }

                    //Registered Office Forename
                    if(row.getCell(20) !=null){

                    }
                    //Registered Office Middle Name
                    if(row.getCell(21) !=null){

                    }

                    //Registered Office Surname
                    if(row.getCell(22) !=null){

                    }
                    //Registered Office Mobile
                    if(row.getCell(23) !=null){

                    }
                    //Registered Office Email
                    if(row.getCell(24) !=null){

                    }
                    //Registered Office is Default
                    if(row.getCell(25) !=null){

                    }
                    //Registered Office Notes
                    if(row.getCell(26) !=null){

                    }

                    //Year End Day
                    if(row.getCell(27) !=null){


                    }
                    //Marital Status
                    if(row.getCell(28) !=null){
//                        director.setMaritalStatus(row.getCell(28).toString());
                    }
                    //Alternative Email
                    if(row.getCell(30) !=null ){

                    }

                    //DoB client
                    if(row.getCell(31) !=null){

                    }
                    //Date of Incorprate
                    if(row.getCell(32) !=null){
                    }
                    //Confirmation Statement Day
                    if(row.getCell(33) !=null){

                    }
                    //Confirmation Statement Month
                    if(row.getCell(34) !=null){

                    }
                    //Company Auth Code
                    if(row.getCell(35) !=null){

                    }
                    //Notes
                    if(row.getCell(36) !=null){

                    }
//                        directorDetails.add(director);
//                        company.setDirectorDetails(directorDetails);
                    if(client1!=null){
                        if (client1.getCompany().getDirectorDetails().isEmpty()){

                            client1.getCompany().getDirectorDetails().add(director);
                        }else{
                            client1.getCompany().getDirectorDetails().add(director);
                        }
                        clientRepository.save(client1);
                        Long userDirectoryDefoultId=Long.parseLong("10001");
                        importDocument(client1,client1.getCode()+"\\"+row.getCell(0).toString(),director.getName()+" "+ director.getSurname(),row.getCell(0).toString(),userDirectoryDefoultId);
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        soleTradeExcelExport();
    }
    public Set<DirectorDetail> DirectorSet(List<DirectorDetail> directors) {
        Set<DirectorDetail> rolesSet = new HashSet<>();
        directors.forEach((DirectorDetail item) -> {
            rolesSet.add(item);
        });
        return rolesSet;
    }
    public void soleTradeExcelExport() throws IOException {
        String file="C:\\teinApp\\EXPORT SOLETRADERS.xlsx";
        try {
            XSSFWorkbook myWorkBook = new XSSFWorkbook(new FileInputStream(file));

            XSSFSheet firstSheet = myWorkBook.getSheetAt(0);
            for (int k = 1; k <= firstSheet.getPhysicalNumberOfRows(); k++) {
                Row row = firstSheet.getRow(k);
                if (row != null) {
                    //Model Tanımlamaları
                    Client client=new Client();
                    Address address=new Address();
                    FounderOwner founderOwner=new FounderOwner();
                    User user =new User();
                    Customer customer=new Customer();
                    CustomerClient customerClient=new CustomerClient();
                    user.setUserType(UserType.CUSTOMER);
                    user.setPassword(passwordEncoder.encode("12345"));
                    user.setRoles(rolesFromStrings(Arrays.asList("CUSTOMER")));

                    //REF-NO Code
                    if(row.getCell(0) == null){
                        break;
                    }
                    //REF-NO Code
                    if(row.getCell(0) !=null){
                        if(row.getCell(0).getRichStringCellValue().toString().contains("SL")){
                            client.setAgreementType(AgreementType.ECAA);

                        }else if(row.getCell(0).getRichStringCellValue().toString().contains("ST")){
                            client.setAgreementType(AgreementType.TRADING);
                        }
                        client.setClientTypeEnum(ClientTypeEnum.SOLETRADE);
                        client.setIsExisting(true);
                        client.setState("3");
                        client.setIsActive(true);
                        user.setIsActive(true);
                        user.setIsDeleted(false);
                        client.setCode(row.getCell(0).getRichStringCellValue().toString());
                    }
                    //Company Name
                    if(row.getCell(1) !=null && row.getCell(1).toString().length()>0){
                        founderOwner.setInitial(row.getCell(1).getRichStringCellValue().toString());
                        user.setTitle(row.getCell(1).getRichStringCellValue().toString());
                    }
                    //Director Name
                    String isim="";
                    if(row.getCell(2) !=null && row.getCell(2).toString().length()>0){
                        isim=row.getCell(2).toString();
                    }
                    //Director SurName
                    if(row.getCell(3) !=null && row.getCell(3).toString().length()>0){
                        isim=isim+" "+row.getCell(3).toString();
                    }
                    founderOwner.setName(isim);
                    user.setName(isim);
                    //Director SurName
                    if(row.getCell(4) !=null && row.getCell(4).toString().length()>0){
                        founderOwner.setSurname(row.getCell(4).toString());
                        user.setSurname(row.getCell(4).toString());
                    }
//                    founderOwner.setTradeAsName(founderOwner.getName()+ " "+founderOwner.getSurname());
                    //Director NINO
                    if(row.getCell(6) !=null && row.getCell(6).toString().length()>0){

//                        String s = NumberToTextConverter.toText(row.getCell(6).getNumericCellValue());
                        founderOwner.setNino(row.getCell(6).toString());
                    }
                    //Director UTR
                    if(row.getCell(7) !=null && row.getCell(7).toString().length()>0){
                        String s = NumberToTextConverter.toText(row.getCell(7).getNumericCellValue());
                        founderOwner.setUtr(s.toString());
                    }
                    //Director Mobile
                    if(row.getCell(8) !=null && row.getCell(8).toString().length()>0){

                        try{
                            String s = NumberToTextConverter.toText(row.getCell(8).getNumericCellValue());
                            if(s.charAt(0)!='0'){
                                founderOwner.setPhoneNumber("0"+s);
                            }else {
                                founderOwner.setPhoneNumber(s);
                            }
                        }catch(Exception e){
                            String veri=row.getCell(8).toString().replaceAll(" ","");
                            if(veri.charAt(0)!='0'){
                                founderOwner.setPhoneNumber("0"+veri);
                            }else {
                                founderOwner.setPhoneNumber(veri);
                            }
                        }
                    }
                    //Director Nationality
                    if(row.getCell(9) !=null && row.getCell(9).toString().length()>0){
                        founderOwner.setNationality(row.getCell(9).getRichStringCellValue().toString());
                    }

                    String addressText="";
                    //Registared Address Line 1
                    if(row.getCell(10) !=null && row.getCell(10).toString().length()>0){
                        addressText=addressText+row.getCell(10).toString();
                    }
                    if(row.getCell(11) !=null && row.getCell(11).toString().length()>0){
                        addressText=addressText+row.getCell(11).toString();
                    }
                    if(row.getCell(12) !=null && row.getCell(12).toString().length()>0){
                        addressText=addressText+row.getCell(12).toString();
                    }
                    if(row.getCell(13) !=null && row.getCell(13).toString().length()>0){
                        addressText=addressText+row.getCell(13).toString();
                    }
                    if(row.getCell(14) !=null && row.getCell(14).toString().length()>0){
                        addressText=addressText+row.getCell(14).toString();
                    }
                    founderOwner.setResidentailAddress(addressText);

                    if(row.getCell(22) !=null && row.getCell(22).toString().length()>0){
                        try{
                            String s = NumberToTextConverter.toText(row.getCell(22).getNumericCellValue());
                            if(s.charAt(0)!='0'){
                                founderOwner.setPhoneNumber("0"+s);
                            }else {
                                founderOwner.setPhoneNumber(s);
                            }
                        }catch(Exception e){
                            String veri=row.getCell(22).toString().replaceAll(" ","");
                            if(veri.charAt(0)!='0'){
                                founderOwner.setPhoneNumber("0"+veri);
                            }else {
                                founderOwner.setPhoneNumber(veri);
                            }
                        }
                    }

                    if(row.getCell(26) !=null && row.getCell(26).toString().length()>0 ){
                        founderOwner.setEmail(row.getCell(26).toString());
                        user.setEmail(row.getCell(26).toString());
                    }

                    if(row.getCell(23) !=null && row.getCell(23).toString().length()>0  && user.getEmail()==null){
                        founderOwner.setEmail(row.getCell(23).toString());
                        user.setEmail(row.getCell(23).toString());
                    }

                    //Director D.O.B Date Türkçe Geliyor
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-uuuu", new Locale("tr"));
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d-MMM-uuuu",new Locale("tr"));
                    LocalDate date;
                    if(row.getCell(28) !=null && row.getCell(28).toString().length()>0){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        founderOwner.setDob(LocalDate.parse(dateFormat.format(row.getCell(28).getDateCellValue())));

                        //founderOwner.setDob(LocalDate.parse(row.getCell(28).toString(), row.getCell(28).toString().length() > 9 ? formatter : formatter1));
                    }

                    User result = userRepository.save(user);
                    result.setUserFolder("user\\"+ GlobalVariable.converSessiz(result.getName())+result.getId());
                    User result2 = userRepository.save(result);
                    client.setIsActive(true);
                    customer.setUser(result2);
                    customerRepository.save(customer);
                    client.setAddressList(Arrays.asList(address));
                    client.setFounderOwner(founderOwner);
                    Client client1=clientRepository.save(client);
                    client1.setClientFolder("client\\"+client1.getClientTypeEnum()+"-"+client1.getId().toString());
                    Client client2 = clientRepository.save(client1);
                    customerClient.setClient(client2);
                    customerClient.setCustomerInfo(customer);
                    customerClient.setSharePercent(100);
                    customerClientRepository.save(customerClient);
                    importDocument(client1,client1.getCode(),"","",result2.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PersonelExport();
    }

    public void PersonelExport () throws IOException{
        String file = "C:\\teinApp\\Personel.xlsx";
        try{
            XSSFWorkbook myWorkBook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet firstSheet = myWorkBook.getSheetAt(0);
            for (int k = 1; k <= firstSheet.getPhysicalNumberOfRows(); k++) {
                Row row = firstSheet.getRow(k);
                if(row != null){
                    Address address = new Address();
                    User user = new User();
                    user.setUserType(UserType.EMPLOYEE);
                    user.setPassword(passwordEncoder.encode("12345"));
                    user.setRoles(rolesFromStrings(Arrays.asList("EMPLOYEE")));
                    Personel personel = new Personel();

                    if(row.getCell(0)== null){
                        break;
                    }
                    if(row.getCell(0)!=null){
                        user.setIsActive(true);
                        user.setIsDeleted(false);
                        personel.setIsActive(true);
                    }
                    if(row.getCell(1)!=null){
                        user.setName(row.getCell(1).getRichStringCellValue().toString());
                        if(row.getCell(1).getRichStringCellValue().toString().equals("Gülay")){
                            user.setRoles(rolesFromStrings(Arrays.asList("MANAGER")));
                        }
                    }
                    if(row.getCell(2)!=null){
                        user.setSurname(row.getCell(2).getRichStringCellValue().toString());
                    }
                    if(row.getCell(3)!=null){
                        user.setMsisdn(row.getCell(3).getRichStringCellValue().toString());
                    }
                    if(row.getCell(4)!=null){
                        user.setAlternativeMsisdn(row.getCell(4).getRichStringCellValue().toString());
                    }
                    if(row.getCell(5) !=null && row.getCell(5).toString().length()>0){
                        user.setAlternativeEmail(row.getCell(5).toString());
                    }
                    if(row.getCell(6) !=null && row.getCell(6).toString().length()>0){
                        user.setEmail(row.getCell(6).toString());
                    }
                    if(row.getCell(7)!=null){
                        personel.setDepartment(row.getCell(7).getStringCellValue().toString());
                    }
                    if(row.getCell(8)!=null){
                        user.setTitle(row.getCell(8).getStringCellValue().toString());
                    }
                    User result = userRepository.save(user);
                    result.setUserFolder("user\\"+ GlobalVariable.converSessiz(result.getName())+result.getId());
                    User result2 = userRepository.save(result);

                    personel.setUser(result2);
                    personelRepository.save(personel);
                }
            }
            }catch (Exception e){
            e.printStackTrace();
        }
    }

}
