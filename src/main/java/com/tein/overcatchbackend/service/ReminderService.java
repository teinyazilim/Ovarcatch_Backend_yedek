package com.tein.overcatchbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tein.overcatchbackend.domain.dto.*;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.mapper.CompanyMapper;
import com.tein.overcatchbackend.mapper.ReminderMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.CompanyRepository;
import com.tein.overcatchbackend.repository.ReminderRepository;
import com.tein.overcatchbackend.repository.ReminderTypeRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class ReminderService {

    private final MailService mailService;
    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;

    private final ClientMapper clientMapper;
    private final CompanyMapper companyMapper;
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final ReminderTypeRepository reminderTypeRepository;


    public ReminderDTO getReminder() {
        return reminderMapper.toDto(reminderRepository.findReminderDates());
    }




    // Reminder - Limited , Company_Number - Company_name , Company- email verilerini aldık !
    public List<ClientDTO> getReminderCompanyList() {
        List<ClientDTO> limitedLists;
        limitedLists = clientMapper.toDto(clientRepository.getLimitedOfReminder("LIMITED" , "OTHER,ECAA,TRADING"));
        return limitedLists;
    }

    @Transactional
    @Scheduled(cron="15 05 15 * * *", zone="Europe/Istanbul")
    //Her gün saat 15-30 ' da çalışacak
    public void taskThread() {

        LocalDate todaysDate =LocalDate.now();
        List<Client> soleTraderClientLists;
        soleTraderClientLists = clientRepository.getClientOfReminder("SOLETRADER" , "OTHER,ECAA,TRADING");
        Reminder reminderDateList = reminderRepository.findReminderDates();
        System.out.println("Theread - Çalıştı :");
        // DB ' de bulunana AY-GUN Kıyaslamalı Tarih bilgisi .
        if ( (reminderDateList.getReminderFirstDate().getMonthValue() == todaysDate.getMonthValue() && reminderDateList.getReminderFirstDate().getDayOfMonth() == todaysDate.getDayOfMonth()) ||
             (reminderDateList.getReminderSecondDate().getMonthValue() == todaysDate.getMonthValue() && reminderDateList.getReminderSecondDate().getDayOfMonth() == todaysDate.getDayOfMonth()) ||
             (reminderDateList.getReminderThirdDate().getMonthValue() == todaysDate.getMonthValue()  && reminderDateList.getReminderThirdDate().getDayOfMonth() == todaysDate.getDayOfMonth()) ||
             (reminderDateList.getReminderFourthDate().getMonthValue() == todaysDate.getMonthValue() && reminderDateList.getReminderFourthDate().getDayOfMonth() == todaysDate.getDayOfMonth())
        ){
            for (Client client: soleTraderClientLists) {
                if (client.getReminderRepeat() == null && client.getStatus().equals(false)){
                    client.setReminderRepeat(0);
                }

                if (client.getStatus().equals(false) && client.getReminderRepeat() < 4){
                    System.out.println("Reminder Repeat Count : "+client.getReminderRepeat());
                    //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                    if(client.getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage() == null){
                        client.getCustomerClients().get(0).getCustomerInfo().getUser().setUserlanguage("en");
                    }

                    if (client.getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("tr")){
                        client.setReminderRepeat(client.getReminderRepeat() + 1);

                        String sentReminder = mailService.sendReminderMailHtmlTR(client.getCustomerClients().get(0).getCustomerInfo().getUser().getEmail(),
                                reminderDateList.getReminderType().getReminderTemplate(),
                                client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                        + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname());
                        System.out.println("Reminder Template Mesaj İçeriği :"+ reminderDateList.getReminderType().getReminderTemplate());
                        System.out.println(todaysDate + " Tarihinde  " + " Kullanıcı : " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname() + " Hatırlatma TR-Maili :"
                                + sentReminder +" Gönderilen Mail : " + client.getReminderRepeat());

                    }
                    if (client.getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("en")){
                        client.setReminderRepeat(client.getReminderRepeat() + 1);

                        String sentReminder = mailService.sendReminderMailHtml(client.getCustomerClients().get(0).getCustomerInfo().getUser().getEmail(),
                                reminderDateList.getReminderType().getReminderTemplate(),
                                client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                        + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname());
                        System.out.println("Reminder Template Mesaj İçeriği :"+ reminderDateList.getReminderType().getReminderTemplate());
                        System.out.println(todaysDate + " Tarihinde  " + " Kullanıcı : " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname() + " Hatırlatma EN-Maili Gönderildi :"
                                + sentReminder +" Gönderilen Mail : " + client.getReminderRepeat());

                    }
                }
            }
        }

        // 31 Ocak ... Tarihinde olabilir .
        else if (reminderDateList.getReminderResetDate().getMonthValue() == todaysDate.getMonthValue()
                    && reminderDateList.getReminderResetDate().getDayOfMonth() == todaysDate.getDayOfMonth()){
            System.out.println("Günün Tarihi : " + todaysDate + "Sole-Trader Status/Durum Kısmını Sıfırlama tarihi");
            for (Client client: soleTraderClientLists) {
                try{
                    client.setStatus(false); // Sole-Trader Kullanıcısının status değeri false çekiliyor .
                    client.setReminderRepeat(0);// Hatırlatma Mail Sayacı 0 değerine çekiliyor .
                    client.setReminderDate(null);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                System.out.println(" Kullanıcı : " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                                + " " +
                                                    client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname()
                                                + " Hatırlatma Mail Sayacı ve Durum Bilgisi Sıfırlandı .");
            }
        }
        else {
            // Belirli Tarih aralığında değil ise çalışan else
            System.out.println("Günün Tarihi : "+ todaysDate);
        }
    }


    @Transactional
    @Scheduled(cron = "50 52 10 * * * ", zone = "Europe/Istanbul")
    public void limitedThread(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try{

            //Company - House ' dan Canlı veri alınıyor
            // -> Company Number DB 'den çekilcek !
            // Canlıdan verisini çekmek istediğim şirketin company number bilgisine göre API ye istek atacağım

            List<Client> limitedLists;
            limitedLists = (clientRepository.getLimitedOfReminder("LIMITED" , "OTHER,ECAA,TRADING"));

            for (Client list : limitedLists) {

                if (list.getCompany().getCompanyNumber() == null || list.getCompany().getCompanyNumber().equals("")){
                    System.out.println(list.getCompany().getName() + ":" + "null");
                    System.out.println("-----------------------------------------");


                }
                else {
                    System.out.println(list.getCompany().getCompanyNumber());

                    //11766935
                    String companyNumber = list.getCompany().getCompanyNumber();
                    System.out.println("companyNumber : " + companyNumber);
                    Unirest.setTimeouts(0, 0);
                    com.mashape.unirest.http.HttpResponse<JsonNode> response = Unirest.get("https://api.companieshouse.gov.uk/company" + "/" + companyNumber)
                            .header("Authorization", "Basic c2hURVp4TURaRXdZNEJmZjk2M29IWkk3TV9IbVozaWFhbkdBSkVtTjo6")
                            .asJson();
                    System.out.println(response.getStatus());

                    if(!response.getStatusText().equals("Not Found")){

                        JSONObject myJSON = response.getBody().getObject();
                        ConfirmationStatementDTO confirmationStatementDTO = new ConfirmationStatementDTO();
                        AccountDTO accountDTO=new AccountDTO();
                        //LastAccountDTO lastAccountDTO=new LastAccountDTO();
                        System.out.println("Obje:"+myJSON.length());
                        //System.out.println(myJSON.get("confirmation_statement").toString());

                        if(myJSON.length()>17){

                            //koşulu buraya yaz




                                if (myJSON.has("confirmation_statement") && myJSON.has("accounts")&& myJSON.get("confirmation_statement").toString()!=null && myJSON.get("accounts").toString()!=null){
                                    ConfirmationStatementDTO deneme1=new ObjectMapper().readValue(myJSON.get("confirmation_statement").toString(), ConfirmationStatementDTO.class);
                                    AccountDTO accountDTO1=new ObjectMapper().readValue(myJSON.get("accounts").toString(), AccountDTO.class);
                                    //ConfirmationStatementDTO deneme1=new ObjectMapper().readValue(myJSON.get("confirmation_statement").toString(), ConfirmationStatementDTO.class);
                                    //AccountDTO accountDTO1=new ObjectMapper().readValue(myJSON.get("accounts").toString(), AccountDTO.class);
                                    //LastAccountDTO lastAccountDTO1=new ObjectMapper().readValue(myJSON.get("last_accounts").toString(),LastAccountDTO.class);


                                    // company=companyMapper.toEntity(list.getCompany()); //company'inin içine listeyi atacak.
                                    if(deneme1.getLast_made_up_to()==null){
                                        System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                    }else{
                                        list.getCompany().setLastStatementDate(LocalDate.parse(deneme1.getLast_made_up_to(),formatter));

                                    }
                                    if(deneme1.getNext_made_up_to()==null){
                                        System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                    }else{
                                        list.getCompany().setNextStatementDate(LocalDate.parse(deneme1.getNext_made_up_to(),formatter));

                                    }
                                    if(deneme1.getNext_due()==null){
                                        System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                    }else{
                                        list.getCompany().setStatementDueDate(LocalDate.parse(deneme1.getNext_due(),formatter));

                                    }



                                        if(accountDTO1.getNext_made_up_to()==null){
                                            System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                        }else{
                                            list.getCompany().setNextAccountsDate(LocalDate.parse(accountDTO1.getNext_made_up_to(),formatter));

                                        }

                                        if(accountDTO1.getNext_due()==null){
                                            System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                        }else{
                                            list.getCompany().setAccountsDueDate(LocalDate.parse(accountDTO1.getNext_due(),formatter));

                                        }








                                        JSONObject lastJson = response.getBody().getObject().getJSONObject("accounts");
                                        if (lastJson==null){
                                            System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                        }else{
                                            LastAccountDTO accountDTO2=new ObjectMapper().readValue(lastJson.get("last_accounts").toString(), LastAccountDTO.class);
                                            if (accountDTO2==null){
                                                System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                            }else {
                                                if(accountDTO2.getMade_up_to()==null){
                                                    System.out.println("Bu Tarih Bilgisi mevcut Değil");
                                                }else{
                                                    list.getCompany().setLastAccountsDate(LocalDate.parse(accountDTO2.getMade_up_to(),formatter));

                                                }
                                            }

                                        }







                                    // System.out.println(list.getCompany().getNextStatementDate().minusDays(-90));


                                    //company.setIsActive(true);
//                            company.setClientInfo(clientMapper.toEntity(list));
                                    Company company1=companyRepository.save(list.getCompany());
                                }else{
                                    System.out.println("Hata");
                                }






//                            list.setCompany(company1);
//                            Client client = clientRepository.save(list);


                            //System.out.println("Şirket Adı :"+ myJSON.getString("company_name"));  //bak
                            //System.out.println("Şirket Adı :"+ response.getBody().getObject());  //bak
                            //System.out.println("Şirket Adı :"+ myJSON.getJSONObject("confirmation_statement").getString("next_due"));
                        }

                    }

                    //API 'den gelen tarih bilgisini convert edip DB 'ye kaydedeceğim .
                    //LocalDate firstLocalDate = LocalDate.parse(firstDate,formatter);
                    //company.setNextStatementDate(firstLocalDate);

                    //Yukarıda ki kod yanlış ama mantık olarak bu şekilde çalışılacak !


                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    @Scheduled(cron="15 25 10 * * *", zone="Europe/Istanbul")
    //Her gün saat 15-30 ' da çalışacak
    public void  LimitedTaskThread() {

        LocalDate todaysDate =LocalDate.now();
        List<Client> soleTraderClientLists;


        Reminder reminderDateList = reminderRepository.findReminderDates();
        //Reminder reminderTemplateList =reminderRepository.limitedTemplate();
        List<ClientDTO> limitedLists;
        limitedLists = clientMapper.toDto(clientRepository.getLimitedOfReminder("LIMITED" , "OTHER,ECAA,TRADING"));

        System.out.println("Theread - Çalıştı :");
        // DB ' de bulunana AY-GUN Kıyaslamalı Tarih bilgisi .
            for (ClientDTO client: limitedLists) {
                if (client.getCompany().getNextStatementDate() == null || client.getCompany().getNextStatementDate().equals("")){

                    System.out.println("-----------------------------------------");


                }
                else{
                    if(client.getStatus().equals(true)||client.getCompany().getNextStatementDate().plusDays(90)==todaysDate){  //koşul koy
                        continue;
                    }else{
                        long days = ChronoUnit.DAYS.between(client.getCompany().getNextStatementDate(), todaysDate);
                        if(days<0){
                            days= -days;
                            System.out.println(days);
                        }
                        if (client.getCompany().getNextStatementDate().minusDays(7)==todaysDate|| days%14==0){
                            String sentReminder = mailService.sendReminderMailHtml(client.getCustomerClients().get(0).getCustomerInfo().getUser().getEmail(),
                                    reminderDateList.getReminderType().getReminderTemplate(),
                                    //reminderDateList.getReminderType().getReminderTemplate(),
                                    client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                            + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname());

                        }
                        else {
                            // Belirli Tarih aralığında değil ise çalışan else
                            System.out.println("Günün Tarihi : "+ todaysDate);
                        }
                    }

                }


            }

            for (ClientDTO list:limitedLists){
                final long days = ChronoUnit.DAYS.between(list.getCompany().getNextAccountsDate(), todaysDate);

                if (list.getCompany().getNextAccountsDate() == null || list.getCompany().getNextAccountsDate().equals("")){

                    System.out.println("-----------------------------------------");


                }
                else{
                    if (list.getCompany().getNextAccountsDate().plusDays(90)==todaysDate|| days%90==0){
                        String sentReminder = mailService.sendReminderMailHtml(list.getCustomerClients().get(0).getCustomerInfo().getUser().getEmail(),
                                // reminderTemplateList.getReminderType().getReminderTemplate(),
                                reminderDateList.getReminderType().getReminderTemplate(),
                                list.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                        + " " + list.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname());
                    }
                    else {
                        // Belirli Tarih aralığında değil ise çalışan else
                        System.out.println("Günün Tarihi : "+ todaysDate);
                    }

                }



            }
    }
    @Transactional
    @Scheduled(cron = "26 49 16 * * * ", zone = "Europe/Istanbul")
    public void LimitedVatThread(){
        LocalDate todaysDate =LocalDate.of(LocalDate.now().getYear(),1,1); //doğru format
        System.out.println(todaysDate);

        List<Client> soleTraderClientLists;


        Reminder reminderDateList = reminderRepository.findReminderDates();
        //Reminder reminderTemplateList =reminderRepository.limitedTemplate();
        List<ClientDTO> limitedLists;
        limitedLists = clientMapper.toDto(clientRepository.getLimitedOfReminder("LIMITED" , "OTHER,ECAA,TRADING"));
        System.out.println("Theread - Çalıştı :");
        Calendar calendar=Calendar.getInstance();

        for(ClientDTO client: limitedLists){
            if(client.getCompany().getVatNumber()==null|| client.getCompany().getVatNumber().equals("")||client.getStatus().equals(true)){
                System.out.println("-----------------------------------------");

            }else{
                if (client.getCompany().getVatPeriod()==null|| client.getCompany().getVatPeriod().equals("")){
                    System.out.println("-----------------------------------------");
                }
                else{
                    if (client.getCompany().getVatPeriod()=="January/April/July/October"){
                        if ((todaysDate.getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.getMonthValue()==LocalDate.now().getMonthValue()) ||(todaysDate.plusDays(7).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(7).getMonthValue()==LocalDate.now().getMonthValue()) ||(todaysDate.plusDays(14).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(14).getMonthValue()==LocalDate.now().getMonthValue())||(todaysDate.plusDays(21).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(21).getMonthValue()==LocalDate.now().getMonthValue())){
                            String sentReminder = mailService.sendReminderMailHtml(client.getCustomerClients().get(0).getCustomerInfo().getUser().getEmail(),
                                    reminderDateList.getReminderType().getReminderTemplate(),
                                    //reminderDateList.getReminderType().getReminderTemplate(),
                                    client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                            + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname());

                        }
                        else{
                            System.out.println("..");
                        }
                    }
                    else if ((client.getCompany().getVatPeriod()=="February/May/August/November")){
                        if ((todaysDate.plusDays(90).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(90).getMonthValue()==LocalDate.now().getMonthValue()) ||(todaysDate.plusDays(97).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(97).getMonthValue()==LocalDate.now().getMonthValue()) ||(todaysDate.plusDays(104).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(104).getMonthValue()==LocalDate.now().getMonthValue())||(todaysDate.plusDays(111).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(111).getMonthValue()==LocalDate.now().getMonthValue())){
                            String sentReminder = mailService.sendReminderMailHtml(client.getCustomerClients().get(0).getCustomerInfo().getUser().getEmail(),
                                    reminderDateList.getReminderType().getReminderTemplate(),
                                    //reminderDateList.getReminderType().getReminderTemplate(),
                                    client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                            + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname());

                        }
                        else{
                            System.out.println("..");
                        }

                    }
                    else if(((client.getCompany().getVatPeriod()=="March/June/September/December"))){
                      if((todaysDate.plusDays(180).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(120).getMonthValue()==LocalDate.now().getMonthValue()) ||(todaysDate.plusDays(127).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(127).getMonthValue()==LocalDate.now().getMonthValue()) ||(todaysDate.plusDays(134).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(134).getMonthValue()==LocalDate.now().getMonthValue())||(todaysDate.plusDays(141).getDayOfMonth()==LocalDate.now().getDayOfMonth() &&todaysDate.plusDays(141).getMonthValue()==LocalDate.now().getMonthValue())){
                            String sentReminder = mailService.sendReminderMailHtml(client.getCustomerClients().get(0).getCustomerInfo().getUser().getEmail(),
                                    reminderDateList.getReminderType().getReminderTemplate(),
                                    //reminderDateList.getReminderType().getReminderTemplate(),
                                    client.getCustomerClients().get(0).getCustomerInfo().getUser().getName()
                                            + " " + client.getCustomerClients().get(0).getCustomerInfo().getUser().getSurname());

                        }
                        else{
                            System.out.println("..");
                        }

                    }

                }


            }
        }

    }

    }







