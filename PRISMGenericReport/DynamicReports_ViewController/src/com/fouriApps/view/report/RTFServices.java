package com.fouriApps.view.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import oracle.xdo.XDOException;
import oracle.xdo.template.FOProcessor;
import oracle.xdo.template.RTFProcessor;

public class RTFServices {
    public RTFServices() {
        super();
    }

    public static byte[] rtfReport(String xmlData,
                                   String filePath) throws FileNotFoundException {

        InputStream fiS = null;
        ByteArrayInputStream xslInStream = null;
        ByteArrayInputStream dataStream = null;
        ByteArrayOutputStream pdfOutStream = null;

        byte[] dataBytes = null;
        byte outFileTypeByte = 0;
        try {

            fiS = new FileInputStream(new File(filePath));
            outFileTypeByte = FOProcessor.FORMAT_PDF;
            RTFProcessor rtfP = new RTFProcessor(fiS);
            ByteArrayOutputStream xslOutStream = new ByteArrayOutputStream();
            rtfP.setOutput(xslOutStream);
            rtfP.process();
            xslInStream = new ByteArrayInputStream(xslOutStream.toByteArray());

            FOProcessor processor = new FOProcessor();
            processor.setConfig("/u01/data/font/xdo.cfg");
//            processor.setConfig("E:/rtf/xdo.cfg");    // local path ibrahim system
            dataStream = new ByteArrayInputStream(xmlData.getBytes());

            processor.setData(dataStream);
            processor.setTemplate(xslInStream);

            pdfOutStream = new ByteArrayOutputStream();
            processor.setOutput(pdfOutStream);

            processor.setOutputFormat(outFileTypeByte);
            processor.generate();
            dataBytes = pdfOutStream.toByteArray();
        } catch (XDOException ex) {
            System.err.println("XDOException Error - " + ex);
        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException Error - " + ex);
        }
        return dataBytes;
    }
}
