package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final String mailSend = "admin@overcatch.co.uk";


    public String sendMailHtml(String mailTo, String pass, String userName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(mailTemplate(pass, userName), true);
            messageHelper.setSubject("OverCatch Login Password ");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String forgotPasswordGetCodeSend(String mailTo, String confirmationCode,String resetLink) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(forgotPasswordTemplate(confirmationCode,resetLink), true);
            messageHelper.setSubject("OverCatch Forgot Password");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }

        return "Mail Sent!";
    }

    public String sendInvoiceToBuyer(String mailToBuyer, String clientName,String invoiceCode, File invoiceFile) throws MessagingException {
        invoiceFile=new File("C:\\Users\\Tein\\test\\statement\\test.pdf");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);

        try {
            messageHelper.setTo(mailToBuyer);
            messageHelper.setFrom(mailSend);
            messageHelper.addAttachment(invoiceCode+".pdf",invoiceFile);
            messageHelper.setText(invoiceMailTemplate(clientName), true);
            messageHelper.setSubject("OverCatch Login Password ");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }

        return "Mail Sent!";
    }

    public String sendInvoiceBuyer() throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        try {
            messageHelper.setSubject("OverCatch Login Password ");
            messageHelper.setFrom(mailSend);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }

        return "Mail Sent";
    }

    public String sendMailHtmlChangedPassword(String mailTo, String userName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(mailTemplate2(userName), true);
            messageHelper.setSubject("OverCatch Login Password ");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
          
            return "Error...";
        }

        return "Mail Sent!";
    }

    public String sendMailHtmlTaskConfirm(String mailTo, String taskType, String taskConfirm, String confirmMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(TaskDetailTemplate(taskType, taskConfirm, confirmMessage), true);
            messageHelper.setSubject("Your request about");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }

        return "Mail Sent!";
    }

    public String sendMailHtmlAddTask(String mailTo, String ccTo,  String taskType) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            if(ccTo != null){
                String[] ccList = ccTo.split(";");
                messageHelper.setCc(ccList);
            }
            messageHelper.setText(AddTaskTemplate(taskType), true);
            messageHelper.setSubject("About Your Request");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String sendMailHtmlAddTaskTR(String mailTo, String ccTo,  String taskType) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            if(ccTo != null){
                String[] ccList = ccTo.split(";");
                messageHelper.setCc(ccList);
            }

            if (taskType.equals("Letter")){
                taskType = "Mektup";
            }
            else if(taskType.equals("Company Application")){
                taskType = "Şirket Başvurusu";
            }
            else if (taskType.equals("Expenses Delete")){
                taskType = "Masraf Sil";
            }
            else if (taskType.equals("Support")){
                taskType = "Destek";
            }
            else if(taskType.equals("Invoice Update")){
                taskType = "Fatura Güncelleme";
            }
            else if(taskType.equals("Invoice Delete")){
                taskType = "Fatura Silme";
            }

            messageHelper.setText(AddTaskTemplateTR(taskType), true);
            messageHelper.setSubject("Destek Talebiniz Hakkında");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }

        return "Mail Sent!";
    }

    public String sendMail(String mailTo, String text, String subject) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(SupportMailEmployee(mailTo , subject , text),true);
            //messageHelper.setText(text, true);
            messageHelper.setSubject(subject);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }

        return "Mail Sent!";
    }

    public String sendLetterFromManager(String mailTo, String ccMail, File attachment, File pdf, String userName, String msg, String from, String date) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        try{
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setFrom(mailSend);
            mimeMessageHelper.setSubject("Letter Request");

            mimeMessageHelper.setText(sentLetterFromManagerTemplate(!userName.isBlank() || !userName.isEmpty() ? userName : "",
                    msg != null ? msg : "",
                    !from.isBlank() || !from.isEmpty() ? from : "",
                    date), true);
            mimeMessageHelper.setCc(ccMail);
            if (attachment != null)
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);
            if (pdf != null)
                mimeMessageHelper.addAttachment(pdf.getName(), pdf);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            return "Error";
        }
        return "Mail sent";
    }

    public String sendLetterFromManagerTR(String mailTo, String ccMail, File attachment, File pdf, String userName, String msg, String from, String date) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        try{
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setFrom(mailSend);
            mimeMessageHelper.setSubject("Mektup Talebi");

            mimeMessageHelper.setText(sentLetterFromManagerTemplateTR(!userName.isBlank() || !userName.isEmpty() ? userName : "",
                    msg != null ? msg : "",
                    !from.isBlank() || !from.isEmpty() ? from : "",
                    date), true);
            mimeMessageHelper.setCc(ccMail);
            if (attachment != null)
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);
            if (pdf != null)
                mimeMessageHelper.addAttachment(pdf.getName(), pdf);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            return "Error";
        }
        return "Mail sent";
    }

    public String deleteCashInvoiceRequest(String mailTo, String ccMail, String userName, String msg, String from, String date, TaskConfirmEnum taskConfirm) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        try{
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setFrom(mailSend);
            mimeMessageHelper.setSubject("Expense Delete Request");

            mimeMessageHelper.setText(cashInvoiceRequest(
                    !userName.isBlank() || !userName.isEmpty() ? userName : "",
                    msg != null ? msg : "",
                    !from.isBlank() || !from.isEmpty() ? from : "",
                    date, taskConfirm.toString()
            ), true);
            mimeMessageHelper.setCc(ccMail);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            return "Error";
        }
        return "Mail sent";
    }

    public String deleteCashInvoiceRequestTR(String mailTo, String ccMail, String userName, String msg, String from, String date, TaskConfirmEnum taskConfirm) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        String confirmValue = taskConfirm.getDescriptionTR(taskConfirm.toString());
        try{
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setFrom(mailSend);
            mimeMessageHelper.setSubject("Masraf Silme Talebi");

            mimeMessageHelper.setText(cashInvoiceRequestTR(
                    !userName.isBlank() || !userName.isEmpty() ? userName : "",
                    msg != null ? msg : "",
                    !from.isBlank() || !from.isEmpty() ? from : "",
                    date, confirmValue), true);
            mimeMessageHelper.setCc(ccMail);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            return "Error";
        }
        return "Mail sent";
    }

    //@Async
    public String sendLetterRequest(String mailTo, String ccMail, File attachment, File pdf, String userName, String msg, String from, String date, TaskConfirmEnum taskConfirm, String letterTypeName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        try{
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setFrom(mailSend);
            mimeMessageHelper.setSubject("Letter Request");

            mimeMessageHelper.setText(sentLetterRequestTemplate(!userName.isBlank() || !userName.isEmpty() ? userName : "",
                    msg != null ? msg : "",
                    !from.isBlank() || !from.isEmpty() ? from : "",
                    date, taskConfirm.toString(), letterTypeName), true);
            mimeMessageHelper.setCc(ccMail);
            if (attachment != null)
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);
            if (pdf != null)
                mimeMessageHelper.addAttachment(pdf.getName(), pdf);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            return "Error";
        }
        return "Mail sent";
    }

    //@Async
    public String sendLetterRequestTR(String mailTo, String ccMail, File attachment, File pdf, String userName, String msg, String from, String date, TaskConfirmEnum taskConfirm, String letterTypeName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        //E-postayı Türkçe olarak göndermem gerektiği için DONE-INPROGRESS-REJECTED seçeneklerini türkçe olarak gönderiyorum .
        String confirmValue = taskConfirm.getDescriptionTR(taskConfirm.toString());

        try{
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setFrom(mailSend);
            mimeMessageHelper.setSubject("Mektup Talebi");

            if(letterTypeName.equals("LetterforVisaApplication")){
                letterTypeName = "Vize Başvuru Mektubu";
            }
            else if(letterTypeName.equals("MilitaryLetter")){
                letterTypeName = "Askeri Mektup";
            }
            else if(letterTypeName.equals("HMRCCoverLetter(LimitedCompany)")){
                letterTypeName = "HMRC Ön Mektubu (Limited Şirket)";
            }
            else if(letterTypeName.equals("LetterforBank")){
                letterTypeName = "Banka mektubu";
            }
            else if(letterTypeName.equals("LetterforNIApplication")){
                letterTypeName = "NI Başvuru Mektubu";
            }
            else if(letterTypeName.equals("RentReferenceLetter(Standard)")){
                letterTypeName = "Kira Referans Mektubu(Standart)";
            }
            else if (letterTypeName.equals("Accountant Reference Letter Standard")){
                letterTypeName = "Muhasebeci Referans Mektubu Standardı";
            }


            mimeMessageHelper.setText(sentLetterRequestTemplateTR(!userName.isBlank() || !userName.isEmpty() ? userName : "",
                                                                    msg != null ? msg : "",
                                                                    !from.isBlank() || !from.isEmpty() ? from : "",
                                                                    date, confirmValue , letterTypeName), true);
            mimeMessageHelper.setCc(ccMail);
            if (attachment != null)
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);
            if (pdf != null)
                mimeMessageHelper.addAttachment(pdf.getName(), pdf);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println(e.getMessage());
            return "Error";
        }
        return "Mail sent";
    }

    //@Async
    public String sendSupportTicketWithAttachment(String mailTo, String topicOfRequest ,String ticketText, MultipartFile ticketFile) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper supportTicket = new MimeMessageHelper(mimeMessage,true);

        try {
            supportTicket.setTo(mailTo);
            supportTicket.setFrom(mailSend);
            supportTicket.setSubject("Overcatch New Support Ticket "+ topicOfRequest); //newSupportTemplate
            supportTicket.setText(newSupportTemplate(topicOfRequest , mailTo ,ticketText),true);
            supportTicket.addAttachment(ticketFile.getOriginalFilename() , ticketFile);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return "Error...";
        }

        return "Mail Sent!";
    }

    public String sendNoticeMailHtmlWithAttachment(String mailTo, String subject, String msg, String userName , MultipartFile ticketFile) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper notification = new MimeMessageHelper(mimeMessage,true);
        try {
            notification.setTo(mailTo);
            notification.setFrom(mailSend);
            notification.setText(sendNoticeTemplate(msg,userName), true);
            notification.setSubject(subject);
            notification.addAttachment(ticketFile.getOriginalFilename(),ticketFile);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String sendNoticeMailHtmlWithAttachmentTR(String mailTo, String subject, String msg, String userName , MultipartFile ticketFile) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper notification = new MimeMessageHelper(mimeMessage,true);
        try {
            notification.setTo(mailTo);
            notification.setFrom(mailSend);
            notification.setText(sendNoticeTemplateTR(msg,userName), true);
            notification.setSubject(subject);
            notification.addAttachment(ticketFile.getOriginalFilename(),ticketFile);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    //Resend yapıldığında çalışacak olan metot . Dosyalı bir şekilde ilgili kişiye gönderiyor .
    public String resendNoticeWithAttachment(String mailTo, String subject, String msg, String userName , File ticketFile) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper notification = new MimeMessageHelper(mimeMessage,true);
        try {
            notification.setTo(mailTo);
            notification.setFrom(mailSend);
            notification.setText(sendNoticeTemplate(msg,userName), true);
            notification.setSubject(subject);
            notification.addAttachment(ticketFile.getName(),ticketFile);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    //Resend yapıldığında çalışacak olan metot TR Seklinde . Dosyalı bir şekilde ilgili kişiye gönderiyor .
    public String resendNoticeWithAttachmentTR(String mailTo, String subject, String msg, String userName , File ticketFile) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper notification = new MimeMessageHelper(mimeMessage,true);
        try {
            notification.setTo(mailTo);
            notification.setFrom(mailSend);
            notification.setText(sendNoticeTemplateTR(msg,userName), true);
            notification.setSubject(subject);
            notification.addAttachment(ticketFile.getName(),ticketFile);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    //@Async
    public String sendSupportTicket(String mailTo, String ticketText, String topicOfRequest) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper supportTicket = new MimeMessageHelper(mimeMessage);
        try {
            supportTicket.setTo(mailTo);
            supportTicket.setText(newSupportTemplate(topicOfRequest , mailTo ,ticketText),true);
            supportTicket.setSubject("Overcatch New Support Ticket "+ topicOfRequest);
            supportTicket.setFrom(mailSend);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }
    //@Async
    public String sendAnswerTicket(String mailTo, String senderMail ,String answerText, String topicOfRequest ) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper supportTicket = new MimeMessageHelper(mimeMessage);
        try {
            supportTicket.setTo(mailTo);
            supportTicket.setFrom(mailSend);
            supportTicket.setText(anserSupportTemplate(topicOfRequest , senderMail ,answerText),true);
            supportTicket.setSubject("Overcatch New Support Ticket "+ topicOfRequest);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    //@Async
    public String sendAnswerTicketTR(String mailTo, String senderMail ,String answerText, String topicOfRequest ) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper supportTicket = new MimeMessageHelper(mimeMessage);
        try {
            supportTicket.setTo(mailTo);
            supportTicket.setFrom(mailSend);
            supportTicket.setText(anserSupportTemplateTR(topicOfRequest , senderMail ,answerText),true);
            supportTicket.setSubject("Overcatch Yeni Destek Bileti "+ topicOfRequest);
            supportTicket.setFrom(mailSend);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String sendMailEmployee(String mailTo, String user, String subject, String question) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(SupportMailEmployee(user, subject, question), true);
            messageHelper.setSubject("OverCatch Employee Notification");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String sendMessageWithAttachment(String to,String cc, String subject, String clientName, Resource pathToAttachment) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try{
            //helper.setFrom("noreply@baeldung.com");
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);
            helper.setFrom(mailSend);
            helper.setText(invoiceMailTemplate(clientName),true);
//
//        FileSystemResource file
//                = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice.pdf", pathToAttachment);
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "mail sent!";
    }

    //@Async
    public String sendMessageWithAttachmentTR(String to,String cc, String subject, String clientName, Resource pathToAttachment) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            //helper.setFrom("noreply@baeldung.com");
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);
            helper.setFrom(mailSend);
            helper.setText(invoiceMailTemplateTR(clientName),true);
            //FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice.pdf", pathToAttachment);
            mailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "Mail Sent!";
    }

    public String sendReminderMailHtml(String mailTo, String msg, String userName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(reminderTemplate(msg,userName), true);
            messageHelper.setSubject("OverCatch Reminder Service");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String sendReminderMailHtmlTR(String mailTo, String msg, String userName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(reminderTemplateTR(msg,userName), true);
            messageHelper.setSubject("OverCatch Hatırlatma Hizmeti");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String sendNoticeMailHtml(String mailTo, String subject, String msg, String userName) {
        //MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(sendNoticeTemplate(msg,userName), true);
            messageHelper.setSubject(subject);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String sendNoticeMailHtmlTR(String mailTo, String subject, String msg, String userName) {
        //MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(mailTo);
            messageHelper.setFrom(mailSend);
            messageHelper.setText(sendNoticeTemplateTR(msg,userName), true);
            messageHelper.setSubject(subject);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        return "Mail Sent!";
    }

    public String cashInvoiceRequest(String userName, String msg, String from, String date, String confirm){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src='https://overcatch.co.uk/assets/images/logos/overmavi350.png' width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi " + userName + " !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"> <strong>Expense</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi " + userName + ". Your expense delete request is " + confirm + "</strong></p>\n" +
                "                                                                             <div style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + msg + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + from + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + date + "</strong></p>" +
                "                                                                              </div>" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Do you have any question?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String cashInvoiceRequestTR(String userName, String msg, String from, String date, String confirm){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src='https://overcatch.co.uk/assets/images/logos/overmavi350.png' width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Merhaba " + userName + " !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Masraf</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Merhaba " + userName + ". Masraf silme talebiniz : " + confirm + "</strong></p>\n" +
                "                                                                             <div style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + msg + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + from + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + date + "</strong></p>" +
                "                                                                              </div>" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Herhangi bir sorun var mı?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir. <br>Lütfen bu e-postayı yanıtlamayın.</p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String newSupportTemplate(String helpType , String mailTo , String supportText){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>    \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif font-size:0px;margin:0;padding:0;background-color:#f3f0f5\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">\n" +
                "                                                              <img src=\"https://overcatch.co.uk/assets/images/logos/overmavi350.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-image:url('https://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                  <strong>Overcatch New Support Ticket \" "+ helpType + "\"</strong>\n" +
                "                                                                </p>                                                                \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"> "+ mailTo +" created support ticket :&nbsp; " + supportText + "</p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>                                                                      \n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"https://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                            <span>\n" +
                "                                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                                <tr>\n" +
                "                                                                                  <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                              </table>\n" +
                "                                                                            </span>\n" +
                "                                                                          </a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>       \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String anserSupportTemplate(String helpType , String mailTo , String supportText){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>    \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif font-size:0px;margin:0;padding:0;background-color:#f3f0f5\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">\n" +
                "                                                              <img src=\"https://overcatch.co.uk/assets/images/logos/overmavi350.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-image:url('https://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                  <strong>Overcatch New Support Ticket \" "+ helpType + "\".</strong>\n" +
                "                                                                </p>                                                                \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"> "+ mailTo +" 's answere support ticket :&nbsp; " + supportText + "</p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>                                                                      \n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"https://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                            <span>\n" +
                "                                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                                <tr>\n" +
                "                                                                                  <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                              </table>\n" +
                "                                                                            </span>\n" +
                "                                                                          </a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>       \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String anserSupportTemplateTR(String helpType , String mailTo , String supportText){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>    \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif font-size:0px;margin:0;padding:0;background-color:#f3f0f5\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">\n" +
                "                                                              <img src=\"https://overcatch.co.uk/assets/images/logos/overmavi350.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-image:url('https://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                  <strong>Overcatch Yeni Destek Bileti \" "+ helpType + "\".</strong>\n" +
                "                                                                </p>                                                                \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">Destek biletinizi yanıtlayan : " + mailTo + " Mesaj içeriği : &nbsp; " + supportText + "</p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>                                                                      \n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"https://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                            <span>\n" +
                "                                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                                <tr>\n" +
                "                                                                                  <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>G i r i ş</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                              </table>\n" +
                "                                                                            </span>\n" +
                "                                                                          </a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>G i r i ş</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>       \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir. <br>Lütfen bu e-postayı yanıtlamayın. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String forgotPasswordTemplate(String confirmationCode,String resetLink) {
        return "<!DOCTYPE html><html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"x-apple-disable-message-reformatting\"><meta name=\"format-detection\" content=\"telephone=no\"><title>Başlık</title><link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\"><style>html,body,table,tbody,tr,td,div,p,ul,ol,li,h1,h2,h3,h4,h5,h6{margin: 0;padding: 0;}body{-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}table{border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;}table td{border-collapse: collapse;}h1,h2,h3,h4,h5,h6{font-family: Arial;}.ExternalClass{width: 100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div{line-height: 100%;}/* Outermost container in Outlook.com */.ReadMsgBody{width: 100%;}img{-ms-interpolation-mode: bicubic;}</style><style>a[x-apple-data-detectors=true]{color: inherit !important;text-decoration: inherit !important;}u + #body a{color: inherit;text-decoration: inherit !important;font-size: inherit;font-family: inherit;font-weight: inherit;line-height: inherit;}a, a:link, .no-detect-local a, .appleLinks a{color: inherit !important;text-decoration: inherit;}</style><style>.width600{width: 600px;max-width: 100%;}@media all and (max-width: 599px){.width600{width: 100% !important;}}@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px),only screen and (max-device-width: 599px){.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}}</style><!--[if gte mso 9]><style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style><![endif]--></head><body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\"><style>@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px){.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}}</style><div style=\"background-color:#f3f0f5;\"><table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td valign=\"top\" align=\"left\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\"><tr><td valign=\"top\" align=\"center\" style=\"padding:25px;\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\"/></a></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><!--[if gte mso 9]><div style=\"background-color:#ffffff;\"><v:rect fill=\"true\" stroke=\"false\" style=\"width:600px;\"><v:fill type=\"tile\" " +
                "src=\"http://overcatch.co.uk/assets/images/temp/bg_saas_06.png\" color=\"#ffffff\"/><v:textbox style=\"mso-fit-shape-to-text:true;\" inset=\"0,0,0,0\"><![endif]--><div><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" " +
                "style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-positioOverCatch System</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">" +
                "Your Password Reset Code: &nbsp;" +confirmationCode+ "</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\"><a  target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" href=\"http://"+resetLink+"\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>Reset Password</strong></font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/iconfinder_Help_383993.png\" width=\"60\" height=\"60\" alt=\"designed by Iconfinder\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width60\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Do you have any question?</strong></p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\"><br>www.overcatch.co.uk</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"images/1605870645197_Over_Catch_Main_Logo.png\" width=\"200\" height=\"118\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width200\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">Head Office<br>17 Green Lanes, Newington Green, </p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Get Directions</font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service.<br>Please do not reply to this email.</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></div><!--[if gte mso 9]></v:textbox></v:rect></div><![endif]--></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></div></body></html>";
    }

    public String invoiceMailTemplate(String clientName) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">    \n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"https://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                             \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi You have been billed by " + clientName + " !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">You can examine the attached file to examine the details. </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">                                                                          \n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\"></font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Do you have any question?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String invoiceMailTemplateTR(String clientName) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>  \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                               \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong> Merhaba " + clientName + " tarafından faturalandırıldınız !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">Detayları incelemek için ekteki dosyayı inceleyebilirsiniz. </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"http://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"></a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\"></font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\">                                                                          \n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Herhangi bir sorun var mı?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir. <br>Lütfen bu e-postayı yanıtlamayın.</p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String mailTemplate(String pass, String userName) {
        return "<!DOCTYPE html><html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"x-apple-disable-message-reformatting\"><meta name=\"format-detection\" content=\"telephone=no\"><title>Başlık</title><link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\"><style>html,body,table,tbody,tr,td,div,p,ul,ol,li,h1,h2,h3,h4,h5,h6{margin: 0;padding: 0;}body{-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}table{border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;}table td{border-collapse: collapse;}h1,h2,h3,h4,h5,h6{font-family: Arial;}.ExternalClass{width: 100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div{line-height: 100%;}/* Outermost container in Outlook.com */.ReadMsgBody{width: 100%;}img{-ms-interpolation-mode: bicubic;}</style><style>a[x-apple-data-detectors=true]{color: inherit !important;text-decoration: inherit !important;}u + #body a{color: inherit;text-decoration: inherit !important;font-size: inherit;font-family: inherit;font-weight: inherit;line-height: inherit;}a, a:link, .no-detect-local a, .appleLinks a{color: inherit !important;text-decoration: inherit;}</style><style>.width600{width: 600px;max-width: 100%;}@media all and (max-width: 599px){.width600{width: 100% !important;}}@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px),only screen and (max-device-width: 599px){.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}}</style><!--[if gte mso 9]><style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style><![endif]--></head><body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\"><style>@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px){.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}}</style><div style=\"background-color:#f3f0f5;\"><table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td valign=\"top\" align=\"left\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\"><tr><td valign=\"top\" align=\"center\" style=\"padding:25px;\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\"/></a></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><!--[if gte mso 9]><div style=\"background-color:#ffffff;\"><v:rect fill=\"true\" stroke=\"false\" style=\"width:600px;\"><v:fill type=\"tile\" " +
                "src=\"http://overcatch.co.uk/assets/images/temp/bg_saas_06.png\" color=\"#ffffff\"/><v:textbox style=\"mso-fit-shape-to-text:true;\" inset=\"0,0,0,0\"><![endif]--><div><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" " +
                "style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\"><tr><td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td width=\"60\" style=\"line-height:1px;\">&nbsp;</td><td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\"><tr><td valign=\"top\" style=\"padding-top:30px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">" +
                "<strong>Hi " + userName + " !</strong></p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">Welcome to Overcatch</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">" +
                "Your Overcatch password is :&nbsp;" + pass + "</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\"><a href=\"http://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/iconfinder_Help_383993.png\" width=\"60\" height=\"60\" alt=\"designed by Iconfinder\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width60\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Do you have any question?</strong></p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\"><br>www.overcatch.co.uk</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"images/1605870645197_Over_Catch_Main_Logo.png\" width=\"200\" height=\"118\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width200\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">Head Office<br>17 Green Lanes, Newington Green, </p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Get Directions</font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service.<br>Please do not reply to this email.</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></div><!--[if gte mso 9]></v:textbox></v:rect></div><![endif]--></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></div></body></html>";
    }

    public String mailTemplate2(String userName) {
        return "<!DOCTYPE html><html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"x-apple-disable-message-reformatting\"><meta name=\"format-detection\" content=\"telephone=no\"><title>Başlık</title><link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\"><style>html,body,table,tbody,tr,td,div,p,ul,ol,li,h1,h2,h3,h4,h5,h6{margin: 0;padding: 0;}body{-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}table{border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;}table td{border-collapse: collapse;}h1,h2,h3,h4,h5,h6{font-family: Arial;}.ExternalClass{width: 100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div{line-height: 100%;}/* Outermost container in Outlook.com */.ReadMsgBody{width: 100%;}img{-ms-interpolation-mode: bicubic;}</style><style>a[x-apple-data-detectors=true]{color: inherit !important;text-decoration: inherit !important;}u + #body a{color: inherit;text-decoration: inherit !important;font-size: inherit;font-family: inherit;font-weight: inherit;line-height: inherit;}a, a:link, .no-detect-local a, .appleLinks a{color: inherit !important;text-decoration: inherit;}</style><style>.width600{width: 600px;max-width: 100%;}@media all and (max-width: 599px){.width600{width: 100% !important;}}@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px),only screen and (max-device-width: 599px){.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}}</style><!--[if gte mso 9]><style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style><![endif]--></head><body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\"><style>@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px){.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}}</style><div style=\"background-color:#f3f0f5;\"><table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td valign=\"top\" align=\"left\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\"><tr><td valign=\"top\" align=\"center\" style=\"padding:25px;\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\"/></a></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><!--[if gte mso 9]><div style=\"background-color:#ffffff;\"><v:rect fill=\"true\" stroke=\"false\" style=\"width:600px;\"><v:fill type=\"tile\" " +
                "src=\"http://overcatch.co.uk/assets/images/temp/bg_saas_06.png\" color=\"#ffffff\"/><v:textbox style=\"mso-fit-shape-to-text:true;\" inset=\"0,0,0,0\"><![endif]--><div><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" " +
                "style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-positioOverCatch System</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">" +
                "Your Password Has Been Changed &nbsp;" + "</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\"><a href=\"http://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/iconfinder_Help_383993.png\" width=\"60\" height=\"60\" alt=\"designed by Iconfinder\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width60\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Do you have any question?</strong></p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\"><br>www.overcatch.co.uk</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"images/1605870645197_Over_Catch_Main_Logo.png\" width=\"200\" height=\"118\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width200\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">Head Office<br>17 Green Lanes, Newington Green, </p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Get Directions</font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service.<br>Please do not reply to this email.</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></div><!--[if gte mso 9]></v:textbox></v:rect></div><![endif]--></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></div></body></html>";
    }

    public String TaskDetailTemplate(String taskType, String confirmStatus, String confirmMessage) {
        return "<!DOCTYPE html><html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"x-apple-disable-message-reformatting\"><meta name=\"format-detection\" content=\"telephone=no\"><title>Başlık</title><link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\"><style>html,body,table,tbody,tr,td,div,p,ul,ol,li,h1,h2,h3,h4,h5,h6{margin: 0;padding: 0;}body{-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}table{border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;}table td{border-collapse: collapse;}h1,h2,h3,h4,h5,h6{font-family: Arial;}.ExternalClass{width: 100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div{line-height: 100%;}/* Outermost container in Outlook.com */.ReadMsgBody{width: 100%;}img{-ms-interpolation-mode: bicubic;}</style><style>a[x-apple-data-detectors=true]{color: inherit !important;text-decoration: inherit !important;}u + #body a{color: inherit;text-decoration: inherit !important;font-size: inherit;font-family: inherit;font-weight: inherit;line-height: inherit;}a, a:link, .no-detect-local a, .appleLinks a{color: inherit !important;text-decoration: inherit;}</style><style>.width600{width: 600px;max-width: 100%;}@media all and (max-width: 599px){.width600{width: 100% !important;}}@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px),only screen and (max-device-width: 599px){.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}}</style><!--[if gte mso 9]><style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style><![endif]--></head><body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\"><style>@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px){.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}}</style><div style=\"background-color:#f3f0f5;\"><table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td valign=\"top\" align=\"left\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\"><tr><td valign=\"top\" align=\"center\" style=\"padding:25px;\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\"/></a></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><!--[if gte mso 9]><div style=\"background-color:#ffffff;\"><v:rect fill=\"true\" stroke=\"false\" style=\"width:600px;\"><v:fill type=\"tile\" " +
                "src=\"http://overcatch.co.uk/assets/images/temp/bg_saas_06.png\" color=\"#ffffff\"/><v:textbox style=\"mso-fit-shape-to-text:true;\" inset=\"0,0,0,0\"><![endif]--><div><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" " +
                "style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-positioOverCatch System</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">" +
                "Your " + taskType + " task status " + confirmStatus + ". <br> Message: " + confirmMessage + "&nbsp;" + "</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\"><a href=\"http://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/iconfinder_Help_383993.png\" width=\"60\" height=\"60\" alt=\"designed by Iconfinder\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width60\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Do you have any question?</strong></p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\"><br>www.overcatch.co.uk</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"images/1605870645197_Over_Catch_Main_Logo.png\" width=\"200\" height=\"118\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width200\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">Head Office<br>17 Green Lanes, Newington Green, </p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Get Directions</font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service.<br>Please do not reply to this email.</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></div><!--[if gte mso 9]></v:textbox></v:rect></div><![endif]--></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></div></body></html>";
    }

    public String SupportMailEmployee(String user, String subject, String question) {
        return "<!DOCTYPE html><html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"x-apple-disable-message-reformatting\"><meta name=\"format-detection\" content=\"telephone=no\"><title>Başlık</title><link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\"><style>html,body,table,tbody,tr,td,div,p,ul,ol,li,h1,h2,h3,h4,h5,h6{margin: 0;padding: 0;}body{-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}table{border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;}table td{border-collapse: collapse;}h1,h2,h3,h4,h5,h6{font-family: Arial;}.ExternalClass{width: 100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div{line-height: 100%;}/* Outermost container in Outlook.com */.ReadMsgBody{width: 100%;}img{-ms-interpolation-mode: bicubic;}</style><style>a[x-apple-data-detectors=true]{color: inherit !important;text-decoration: inherit !important;}u + #body a{color: inherit;text-decoration: inherit !important;font-size: inherit;font-family: inherit;font-weight: inherit;line-height: inherit;}a, a:link, .no-detect-local a, .appleLinks a{color: inherit !important;text-decoration: inherit;}</style><style>.width600{width: 600px;max-width: 100%;}@media all and (max-width: 599px){.width600{width: 100% !important;}}@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px),only screen and (max-device-width: 599px){.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}}</style><!--[if gte mso 9]><style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style><![endif]--></head><body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\"><style>@media screen and (min-width: 600px){.hide-on-desktop{display: none;}}@media all and (max-width: 599px){.hide-on-mobile{display:none !important; width:0px !important;height:0px !important; overflow:hidden;}.main-container{width: 100% !important;}.col{width: 100%;}.fluid-on-mobile{width: 100% !important;height: auto !important; text-align:center;}.fluid-on-mobile img{width: 100% !important;}}</style><div style=\"background-color:#f3f0f5;\"><table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td valign=\"top\" align=\"left\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\"><tr><td valign=\"top\" align=\"center\" style=\"padding:25px;\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\"/></a></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td align=\"center\" width=\"100%\"><table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\"><tr><td width=\"100%\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><!--[if gte mso 9]><div style=\"background-color:#ffffff;\"><v:rect fill=\"true\" stroke=\"false\" style=\"width:600px;\"><v:fill type=\"tile\" " +
                "src=\"http://overcatch.co.uk/assets/images/temp/bg_saas_06.png\" color=\"#ffffff\"/><v:textbox style=\"mso-fit-shape-to-text:true;\" inset=\"0,0,0,0\"><![endif]--><div><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" " +
                "style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-positioOverCatch System</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">" +
                "New Support Request <br>Customer Name: " + user + " <br>Subject: " + subject + ".<br>Request: " + question + "&nbsp;" + "</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\"><a href=\"http://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\"><a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\"><font style=\"color:#ffffff;\" class=\"button\"><strong>L o g i n</strong></font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">" +
                "<img src=\"http://overcatch.co.uk/assets/images/temp/iconfinder_Help_383993.png\" width=\"60\" height=\"60\" alt=\"designed by Iconfinder\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width60\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Do you have any question?</strong></p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-bottom:15px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\"><br>www.overcatch.co.uk</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\"><tr><td></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\"><tr><td valign=\"top\" align=\"center\"><a href=\"www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"images/1605870645197_Over_Catch_Main_Logo.png\" width=\"200\" height=\"118\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width200\"/></a></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">Head Office<br>17 Green Lanes, Newington Green, </p><span class=\"mso-font-fix-arial\"></span><p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" align=\"center\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\"><span><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Get Directions</font></span></td></tr></table></span></a><div style=\"display:none; mso-hide: none;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\"><tr><td align=\"center\" style=\"padding:1px;\"><a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\"><span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\"><font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font></span></a></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\"><tr><td></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\"><div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\"><p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service.<br>Please do not reply to this email.</p><span class=\"mso-font-fix-arial\"></span></div></td></tr></table></td></tr></table></div><!--[if gte mso 9]></v:textbox></v:rect></div><![endif]--></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></div></body></html>";
    }

    public String sentLetterFromManagerTemplate(String userName, String msg, String from, String date){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src='https://overcatch.co.uk/assets/images/logos/overmavi350.png' width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi " + userName + " !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"> <strong>Letter</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi " + userName + ". You have a letter</strong></p>\n" +
                "                                                                             <div style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + msg + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + from + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + date + "</strong></p>" +
                "                                                                              </div>" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Do you have any question?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String sentLetterFromManagerTemplateTR(String userName, String msg, String from, String date){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src='https://overcatch.co.uk/assets/images/logos/overmavi350.png' width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Merhaba " + userName + " !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"> <strong>Mektup</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Merhaba " + userName + ". Bir mektubun var .</strong></p>\n" +
                "                                                                             <div style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + msg + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + from + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + date + "</strong></p>" +
                "                                                                              </div>" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Herhangi bir sorun var mı?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir. <br>Lütfen bu e-postayı yanıtlamayın. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String sentLetterRequestTemplate(String userName, String msg, String from, String date, String confirm, String letterTypeName){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src='https://overcatch.co.uk/assets/images/logos/overmavi350.png' width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi " + userName + " !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"> <strong>Letter</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi " + userName + ". Your " + letterTypeName + " letter request is " + confirm + "</strong></p>\n" +
                "                                                                             <div style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + msg + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + from + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + date + "</strong></p>" +
                "                                                                              </div>" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Do you have any question?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String sentLetterRequestTemplateTR(String userName, String msg, String from, String date, String confirm, String letterTypeName){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src='https://overcatch.co.uk/assets/images/logos/overmavi350.png' width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Merhaba " + userName + " !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"> <strong>Mektup</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Merhaba " + userName + " . " + letterTypeName + " isteğiniz " + confirm + "</strong></p>\n" +
                "                                                                             <div style=\"padding-top:20px;padding-right:10px;padding-left:10px;\">" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + msg + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + from + "</strong></p>\n" +
                "                                                                                    <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>" + date + "</strong></p>" +
                "                                                                              </div>" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Herhangi bir sorun var mı?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir. <br>Lütfen bu e-postayı yanıtlamayın.</p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String AddTaskTemplate(String taskType) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>    \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif font-size:0px;margin:0;padding:0;background-color:#f3f0f5\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">\n" +
                "                                                              <img src=\"https://overcatch.co.uk/assets/images/logos/overmavi350.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-image:url('https://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">   \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                                                                                             "<p style=\"padding: 0; margin: 0;text-align: center;\">Your " + taskType + " request has been created successfully .</p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>                                                                      \n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"https://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                            <span>\n" +
                "                                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                                <tr>\n" +
                "                                                                                  <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                              </table>\n" +
                "                                                                            </span>\n" +
                "                                                                          </a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>       \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String AddTaskTemplateTR(String taskType) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>    \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif font-size:0px;margin:0;padding:0;background-color:#f3f0f5\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">\n" +
                "                                                              <img src=\"https://overcatch.co.uk/assets/images/logos/overmavi350.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-image:url('https://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">   \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                               <p style=\"padding: 0; margin: 0;text-align: center;\"> " + taskType + " talebiniz başarıyla oluşturuldu .</p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>                                                                      \n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"https://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                            <span>\n" +
                "                                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                                <tr>\n" +
                "                                                                                  <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>G i r i ş</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                              </table>\n" +
                "                                                                            </span>\n" +
                "                                                                          </a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>G i r i ş</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>       \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir. <br>Lütfen bu e-postayı yanıtlamayın.</p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String sendNoticeTemplate(String msg , String userName){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"https://overcatch.co.uk/assets/images/logos/overmavi350.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">\" + \" <strong>Hi \" + userName + \" !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Notification</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Hi " + userName + ". You have a notification :</strong></p>\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">" + msg + "</p>\n" +
                "                                                                              \n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>                                                                            \n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>  \n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Do you have any question?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String sendNoticeTemplateTR(String msg , String userName){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t\t\t\t\t\t\t\t<style>.col{width: 100% !important;}.width600{width: 600px;}.width281{width: 281px;height: auto;}.width60{width: 60px;height: auto;}.width200{width: 200px;height: auto;}.hide-on-desktop{display: none;}.hide-on-desktop table{mso-hide: all;}.nounderline{text-decoration: none !important;}.mso-font-fix-arial{font-family: Arial, sans-serif;}.mso-font-fix-georgia{font-family: Georgia, sans-serif;}.mso-font-fix-tahoma{font-family: Tahoma, sans-serif;}.mso-font-fix-times_new_roman{font-family: 'Times New Roman', sans-serif;}.mso-font-fix-trebuchet_ms{font-family: 'Trebuchet MS', sans-serif;}.mso-font-fix-verdana{font-family: Verdana, sans-serif;}</style>\n" +
                "\t\t\t\t\t\t\t\t<![endif]-->\n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"https://overcatch.co.uk/assets/images/logos/overmavi350.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" \" +\n" +
                "                                        \" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <!--\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">\"<strong>Merhaba \" + userName + \" !</strong>\n" +
                "                                                                </p>\n" +
                "                                                                -->                                                                  \n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Bildirim</strong>\n" +
                "                                                                </p>  \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\"><strong>Merhaba " + userName + " bir bildiriminiz var :</strong></p>\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">" + msg + "</p>\n" +
                "                                                                              \n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>                                                                            \n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>  \n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>                                                        \n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                    \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Herhangi bir sorun var mı?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir. <br>Lütfen bu e-postayı yanıtlamayın.</p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String reminderTemplate(String msg, String userName) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>    \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong> Hi " + userName + "</strong>\n" +
                "                                                                </p>                                                               \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">&nbsp;" + msg + "</p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"http://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                            <span>\n" +
                "                                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                                <tr>\n" +
                "                                                                                  <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                              </table>\n" +
                "                                                                            </span>\n" +
                "                                                                          </a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>L o g i n</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                   \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Do you have any question?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">If you require any further information,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">feel free to contact us.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" align=\"center\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"img-wrap\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" align=\"center\">\n" +
                "                                                        <a href=\"www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\">\n" +
                "                                                          <img src=\"images/1605870645197_Over_Catch_Main_Logo.png\" width=\"200\" height=\"118\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width200\" />\n" +
                "                                                        </a>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Get Directions</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Click here to edit me</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">This e-mail is send from Overcatch auto-reply service. <br>Please do not reply to this email. </p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public String reminderTemplateTR(String msg, String userName) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "    <title>Başlık</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style>\n" +
                "      html,\n" +
                "      body,\n" +
                "      table,\n" +
                "      tbody,\n" +
                "      tr,\n" +
                "      td,\n" +
                "      div,\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      li,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-spacing: 0;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "      }\n" +
                "\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: Arial;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      .ExternalClass,\n" +
                "      .ExternalClass p,\n" +
                "      .ExternalClass span,\n" +
                "      .ExternalClass font,\n" +
                "      .ExternalClass td,\n" +
                "      .ExternalClass div {\n" +
                "        line-height: 100%;\n" +
                "      }\n" +
                "\n" +
                "      /* Outermost container in Outlook.com */\n" +
                "      .ReadMsgBody {\n" +
                "        width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      a[x-apple-data-detectors=true] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      u+#body a {\n" +
                "        color: inherit;\n" +
                "        text-decoration: inherit !important;\n" +
                "        font-size: inherit;\n" +
                "        font-family: inherit;\n" +
                "        font-weight: inherit;\n" +
                "        line-height: inherit;\n" +
                "      }\n" +
                "\n" +
                "      a,\n" +
                "      a:link,\n" +
                "      .no-detect-local a,\n" +
                "      .appleLinks a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: inherit;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <style>\n" +
                "      .width600 {\n" +
                "        width: 600px;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .width600 {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px),\n" +
                "      only screen and (max-device-width: 599px) {\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>    \n" +
                "  </head>\n" +
                "  <body id=\"body\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"font-family:Arial, sans-serif; font-size:0px;margin:0;padding:0;background-color:#f3f0f5;\">\n" +
                "    <style>\n" +
                "      @media screen and (min-width: 600px) {\n" +
                "        .hide-on-desktop {\n" +
                "          display: none;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      @media all and (max-width: 599px) {\n" +
                "        .hide-on-mobile {\n" +
                "          display: none !important;\n" +
                "          width: 0px !important;\n" +
                "          height: 0px !important;\n" +
                "          overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .main-container {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .col {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "          text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .fluid-on-mobile img {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "    <div style=\"background-color:#f3f0f5;\">\n" +
                "      <table height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" align=\"left\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "              <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ff2121\" style=\"background-color:#ff2121;\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#def1f1\" style=\"border-radius:2px;border-collapse:separate !important;background-color:#def1f1;\">\n" +
                "                                      <tr>\n" +
                "                                        <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                              <td valign=\"top\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                  <tr>\n" +
                "                                                    <td valign=\"top\" align=\"center\">\n" +
                "                                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"max-width:100%;\" class=\"fluid-on-mobile img-wrap\">\n" +
                "                                                        <tr>\n" +
                "                                                          <td valign=\"top\" align=\"center\" style=\"padding:25px;\">\n" +
                "                                                            <a href=\"https://www.overcatch.co.uk\" class=\"imglink\" target=\"_blank\"><img src=\"http://overcatch.co.uk/assets/images/temp/1605874604167_overcath_2.png\" width=\"281\" height=\"115\" alt=\"\" border=\"0\" style=\"display:block;font-size:14px;max-width:100%;height:auto;\" class=\"width281\" />\n" +
                "                                                            </a>\n" +
                "                                                          </td>\n" +
                "                                                        </tr>\n" +
                "                                                      </table>\n" +
                "                                                    </td>\n" +
                "                                                  </tr>\n" +
                "                                                </table>\n" +
                "                                              </td>\n" +
                "                                            </tr>\n" +
                "                                          </table>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" width=\"100%\">\n" +
                "                        <table class=\"width600 main-container\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                          <tr>\n" +
                "                            <td width=\"100%\">\n" +
                "                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                  <td valign=\"top\">                                    \n" +
                "                                    <div>\n" +
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background-image:url('http://overcatch.co.uk/assets/images/temp/bg_saas_06.png');background-repeat:no-repeat;background-position:right top;\">\n" +
                "                                        <tr>\n" +
                "                                          <td valign=\"top\" style=\"padding-top:30px;padding-bottom:30px;\">\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:40px;color:#6d6e71;line-height:45px;text-align:left;\"></div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td width=\"60\" style=\"line-height:1px;\">&nbsp;</td>\n" +
                "                                                <td valign=\"top\" style=\"padding-right:60px;padding-bottom:20px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border:2px double #34a6bd;border-radius:25px;border-collapse:separate !important;background-color:#ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\" style=\"padding-top:30px;\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:25px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:35px;color:#6d6e71;line-height:45px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\"><strong> Merhaba " + userName + " </strong>\n" +
                "                                                                </p>                                                               \n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #7ac5cc;\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td></td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">&nbsp;" + msg + "</p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:20px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" align=\"center\" style=\"padding-top:5px;padding-bottom:5px;\">\n" +
                "                                                                          <a href=\"http://overcatch.co.uk/login\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                            <span>\n" +
                "                                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                                <tr>\n" +
                "                                                                                  <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>G i r i ş</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                              </table>\n" +
                "                                                                            </span>\n" +
                "                                                                          </a>\n" +
                "                                                                          <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#6bb8e6\" style=\"border-radius:50px;border-collapse:separate !important;background-color:#6bb8e6;\" class=\"fluid-on-mobile\">\n" +
                "                                                                              <tr>\n" +
                "                                                                                <td align=\"center\" style=\"padding-top:10px;padding-right:35px;padding-bottom:10px;padding-left:35px;\">\n" +
                "                                                                                  <a href=\"https://www.overcatch.co.uk/login\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;text-decoration:none;text-align:center;\">\n" +
                "                                                                                    <span style=\"color:#ffffff !important;font-family:Verdana, Geneva, sans-serif;font-size:14px;mso-line-height:exactly;line-height:22px;mso-text-raise:4px;\">\n" +
                "                                                                                      <font style=\"color:#ffffff;\" class=\"button\">\n" +
                "                                                                                        <strong>G i r i ş</strong>\n" +
                "                                                                                      </font>\n" +
                "                                                                                    </span>\n" +
                "                                                                                  </a>\n" +
                "                                                                                </td>\n" +
                "                                                                              </tr>\n" +
                "                                                                            </table>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                              <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                <tr>\n" +
                "                                                                  <td valign=\"top\" style=\"padding-top:20px;\">\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td style=\"padding:10px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td></td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>                                                                   \n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-top:15px;padding-right:10px;padding-left:10px;\">\n" +
                "                                                                          <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:25px;color:#6d6e71;line-height:39px;text-align:left;\">\n" +
                "                                                                            <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                              <strong>Herhangi bir sorun var mı?</strong>\n" +
                "                                                                            </p>\n" +
                "                                                                            <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                          </div>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                      <tr>\n" +
                "                                                                        <td valign=\"top\" style=\"padding-bottom:15px;\">\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td valign=\"top\" style=\"padding-top:20px;padding-right:10px;padding-bottom:20px;padding-left:10px;\">\n" +
                "                                                                                <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:16px;color:#000000;line-height:25px;text-align:left;\">\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">Daha fazla bilgiye ihtiyacınız varsa,</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">bizimle iletişime geçmekten çekinmeyin.</p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                  <p style=\"padding: 0; margin: 0;text-align: center;\">\n" +
                "                                                                                    <br>www.overcatch.co.uk\n" +
                "                                                                                  </p>\n" +
                "                                                                                  <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                                </div>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                                            <tr>\n" +
                "                                                                              <td style=\"padding:10px;\">\n" +
                "                                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:2px dashed #a9a9a9;\">\n" +
                "                                                                                  <tr>\n" +
                "                                                                                    <td></td>\n" +
                "                                                                                  </tr>\n" +
                "                                                                                </table>\n" +
                "                                                                              </td>\n" +
                "                                                                            </tr>\n" +
                "                                                                          </table>\n" +
                "                                                                        </td>\n" +
                "                                                                      </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                  </td>\n" +
                "                                                                </tr>\n" +
                "                                                              </table>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>                                            \n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td valign=\"top\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                              <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:15px;color:#6d6e71;line-height:16px;text-align:left;\">\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">Head Office <br>17 Green Lanes, Newington Green, </p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                                <p style=\"padding: 0; margin: 0;text-align: center;\">London N16 9BS, UK</p>\n" +
                "                                                                <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                          <tr>\n" +
                "                                                            <td valign=\"top\" align=\"center\">\n" +
                "                                                              <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"display:inline-block; text-decoration:none;\" class=\"fluid-on-mobile\">\n" +
                "                                                                <span>\n" +
                "                                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                    <tr>\n" +
                "                                                                      <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Yol Tarifi</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </td>\n" +
                "                                                                    </tr>\n" +
                "                                                                  </table>\n" +
                "                                                                </span>\n" +
                "                                                              </a>\n" +
                "                                                              <div style=\"display:none; mso-hide: none;\">\n" +
                "                                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#55c0e2\" style=\"border-radius:5px;border-collapse:separate !important;background-color:#55c0e2;\" class=\"fluid-on-mobile\">\n" +
                "                                                                  <tr>\n" +
                "                                                                    <td align=\"center\" style=\"padding:1px;\">\n" +
                "                                                                      <a href=\"https://goo.gl/maps/T2SBkD3zTh8Jkmxn6\" target=\"_blank\" style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;text-decoration:none;text-align:center;\">\n" +
                "                                                                        <span style=\"color:#ffffff !important;font-family:Arial;font-size:18px;mso-line-height:exactly;line-height:25px;mso-text-raise:3px;\">\n" +
                "                                                                          <font style=\"color:#ffffff;\" class=\"button\">Beni düzenlemek için buraya tıklayın.</font>\n" +
                "                                                                        </span>\n" +
                "                                                                      </a>\n" +
                "                                                                    </td>\n" +
                "                                                                  </tr>\n" +
                "                                                                </table>\n" +
                "                                                              </div>\n" +
                "                                                            </td>\n" +
                "                                                          </tr>\n" +
                "                                                        </table>\n" +
                "                                                      </td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td style=\"padding-top:10px;padding-right:50px;padding-bottom:10px;padding-left:50px;\">\n" +
                "                                                  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border-top:1px solid #ffffff;\">\n" +
                "                                                    <tr>\n" +
                "                                                      <td></td>\n" +
                "                                                    </tr>\n" +
                "                                                  </table>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                              <tr>\n" +
                "                                                <td valign=\"top\" style=\"padding-top:10px;padding-right:10px;padding-bottom:15px;padding-left:10px;\">\n" +
                "                                                  <div style=\"font-family:Raleway, Trebuchet MS, Segoe UI, sans-serif;font-size:13px;color:#6d6e71;line-height:22px;text-align:left;\">\n" +
                "                                                    <p style=\"padding: 0; margin: 0;text-align: center;\">Bu e-posta, Overcatch otomatik yanıtlama hizmetinden gönderilir.<br>lütfen bu emaile cevap vermeyiniz.</p>\n" +
                "                                                    <span class=\"mso-font-fix-arial\"></span>\n" +
                "                                                  </div>\n" +
                "                                                </td>\n" +
                "                                              </tr>\n" +
                "                                            </table>\n" +
                "                                          </td>\n" +
                "                                        </tr>\n" +
                "                                      </table>\n" +
                "                                    </div>                                    \n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }
}
