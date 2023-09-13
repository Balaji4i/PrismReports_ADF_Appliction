package com.fouriApps.view.mail;

import com.fouriApps.view.backing.CallDBQuery;
import com.fouriApps.view.report.RTFServices;

import com.view.uiutils.JSFUtils;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import javax.activation.FileDataSource;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author Ibrahim
 */
public class GenericSendMail {

    public static String sendWithAttachment(String user, String pwd,
                                            Properties props,
                                            ArrayList receipts,
                                            String toAddress, String ccAddress,
                                            String bccAddress, String subject,
                                            Object mailContent) {
        System.err.println("No of receipts-->"+receipts.size());
        System.err.println("Receipts-->"+receipts);
        final String userName = user;
        final String password = pwd;
        Session session =
            Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));

            message.setRecipients(Message.RecipientType.TO,
                                  InternetAddress.parse(toAddress));
            message.setRecipients(Message.RecipientType.CC,
                                  InternetAddress.parse(ccAddress));
            message.setRecipients(Message.RecipientType.BCC,
                                  InternetAddress.parse(bccAddress));

            message.setSubject(subject); 
             
            //        String filePath = "E:/rtf/Multiple_Receipt.rtf";
            String filePath = "/u01/data/reports/Multiple_Receipt.rtf";
            
 
            MimeMultipart mimeMultipart = new MimeMultipart();
            for (int i = 0; i < receipts.size(); i++) {
                String receiptNo = receipts.get(i).toString(); 
                String xmlData = CallDBQuery.getReceiptsXML(receiptNo); 
                byte[] bytes = RTFServices.rtfReport(xmlData, filePath);
                // 
                DataSource dataSource =
                    new ByteArrayDataSource(bytes, "application/pdf");
                MimeBodyPart pdfBodyPart = new MimeBodyPart();
                pdfBodyPart.setDataHandler(new DataHandler(dataSource));
                pdfBodyPart.setFileName(receiptNo+".pdf");   
                mimeMultipart.addBodyPart(pdfBodyPart);   
                //
            } 
            BodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(mailContent, "text/html");
            mimeMultipart.addBodyPart(textBodyPart);
            message.setContent(mimeMultipart);
            message.saveChanges();

            Transport.send(message);
            return "Mail Send Successfully";

        } catch (AddressException ex) {

            return "Error : " + ex.toString();
        } catch (MessagingException ex) {

            return "Error : " + ex.toString();
        } catch (Exception e) {
            return "Error : " + e.toString();
        }
    }

}
