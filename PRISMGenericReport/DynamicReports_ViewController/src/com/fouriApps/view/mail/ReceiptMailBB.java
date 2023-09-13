package com.fouriApps.view.mail;

import com.fouriApps.view.backing.CallDBQuery;

import com.fouriApps.view.report.RTFServices;

import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class ReceiptMailBB {
    private RichPopup mailPop; 

    public ReceiptMailBB() {
        super();
    } 

    public void onClickSend(ActionEvent actionEvent) throws FileNotFoundException,
                                                            SQLException,
                                                            InterruptedException {
        ViewObject vo =
            ADFUtils.findIterator("ReceiptsForEmailIterator").getViewObject();
        RowSetIterator rsIter = vo.createRowSetIterator(null); 
        String strMail;
        Set<String> mailList = new HashSet<String>();
        ArrayList<String> receiptList = new ArrayList<String>(); 
        while (rsIter.hasNext()) {
            Row r = rsIter.next();
            String selectVal =
                r.getAttribute("SelectTrans") != null ? r.getAttribute("SelectTrans").toString() :
                "false";
            if ("true".equals(selectVal)) {
                String receiptNo =
                    r.getAttribute("ReceiptNumber") != null ? r.getAttribute("ReceiptNumber").toString() : "0";
                receiptList.add(receiptNo);
                String mail =
                    r.getAttribute("EmailId") != null ? r.getAttribute("EmailId").toString() :
                    "0";
                if (!"0".equals(mail)) {
                    mailList.add(mail);
//                    mailList.add("ibrahim.hb@4iapps.com");
                }
            } else {

            }
        } 
        
        if(mailList.size()!=0){
            strMail = mailList.toString().replaceAll("\\[|\\]|\\s", ""); 
            Row rowOne = vo.first();
            rowOne.setAttribute("ReceiptList", receiptList.toString().replaceAll("\\[|\\]|\\s", ""));
            rowOne.setAttribute("ToAddress", strMail);
            JSFUtils.showPopup(mailPop); 
            System.err.println("receiptList -->"+receiptList); 
        }else{
            com.fouriApps.view.utils.JSFUtils.addFacesWarningMessage("Please select receipts to send !!");
        }
        
    }

    public String sendReceipt(String toAddress,ArrayList<String> receiptList) throws FileNotFoundException,
                                                       SQLException { 
        System.err.println("receiptList from sendReceipt-->"+receiptList);
        final String username = "prismalerts@omniyat.com";
        final String password = "Or@cl3alert";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        String ccAddress = "";
        String bccAdress = "";

        /**
            * If there is no data in DB it will refer below mails.
            */
        if ("".equals(toAddress)) {
            toAddress = "ibrahim.hb@4iapps.com";
        }
        if ("".equals(ccAddress)) {
            ccAddress = this.getCCAddress();
        }
        if ("".equals(bccAdress)) {
            bccAdress =  this.getBCCAddress();
        }

        String subject = "Receipts From PRISM";
        Object mailContent = MailContent.getReceiptMailBody(receiptList.toString().replaceAll("\\[|\\]|\\s", "")); 
       
        String result =
            GenericSendMail.sendWithAttachment(username, password, props,
                                               receiptList, toAddress, ccAddress,
                                               bccAdress, subject, mailContent);
        System.err.println("result--->"+result);
        JSFUtils.addFacesInformationMessage(result);
        return result;
    }

    public void setMailPop(RichPopup mailPop) {
        this.mailPop = mailPop;
    }

    public RichPopup getMailPop() {
        return mailPop;
    }

    public void onSendInPop(ActionEvent actionEvent) throws FileNotFoundException,
                                                            SQLException {
        ArrayList<String> receiptList = new ArrayList<String>(); 
        ViewObject vo =
            ADFUtils.findIterator("ReceiptsForEmailIterator").getViewObject();
        Row rowOne = vo.first();
        String receipt = rowOne.getAttribute("ReceiptList").toString();
        String mailId = rowOne.getAttribute("ToAddress")!=null?rowOne.getAttribute("ToAddress").toString():"0";
        String[] arrayList = receipt.split(",");
        receiptList = new ArrayList<String>(Arrays.asList(arrayList));
        System.err.println(receipt);
        System.err.println(mailId);
        System.err.println("receiptList from onSendInPop-->"+receiptList);
        sendReceipt(mailId,receiptList);
        unSelectAllReceipts();
        JSFUtils.hidePopup(mailPop);
    }
    public void onCancelInPop(ActionEvent actionEvent) throws InterruptedException {
        unSelectAllReceipts();  
        JSFUtils.hidePopup(mailPop); 
    }

    public String getCCAddress() throws SQLException {
        String cc = "ibrahim.hb@4iapps.com";
        cc = CallDBQuery.getReceiptsMailCC("RECEIPT_MAIL_CC");
        return cc;
    }
    public String getBCCAddress() throws SQLException {
        String bcc = "ibrahim.hb@4iapps.com";
        bcc = CallDBQuery.getReceiptsMailCC("RECEIPT_MAIL_BCC");
        return bcc;
    }

    private void unSelectAllReceipts() {
        ViewObject vo = ADFUtils.findIterator("ReceiptsForEmailIterator").getViewObject();
        RowSetIterator rsIter = vo.createRowSetIterator(null);  
        while (rsIter.hasNext()) {
            Row r = rsIter.next();
            r.setAttribute("SelectTrans", "false");
            r.setAttribute("ToAddress", "");
    } 
    } 
}
