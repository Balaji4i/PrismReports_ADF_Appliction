package com.fouriApps.view.backing;

import com.fouriApps.view.utils.DBUtils;

import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.adf.share.ADFContext;

import oracle.jbo.server.ApplicationModuleImpl;

import oracle.jdbc.OracleTypes;

public class CallDBQuery {
    public CallDBQuery() {
        super();
    }
    private static Object[][] dobProcArgs = null;

    /**
     * Method to get XML data for receipt report.
     * @return XML data as String
     */
    public static String getReceiptsXML(String receiptNO) throws SQLException {
        ApplicationModuleImpl am =
            (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl");
        String xml = "";
        String sqlQry = "{? = call XXPRISM_REPORT_PKG.XXPRISM_EMAIL_RECEIPT_REPORT(('"+receiptNO+"'))}";
        CallableStatement proc = null;
        try {
            proc = am.getDBTransaction().createCallableStatement(sqlQry, 0);
            proc.registerOutParameter(1, OracleTypes.VARCHAR);
            proc.executeQuery();
            xml = proc.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return xml;
    } 
    
    /**
        * Method to get XML data for receipt report.
        * @return XML data as String
        */
       public static String getReceiptsMailCC(String code) throws SQLException {
           String retStr = "";
           Context ctx;
           Connection con = null;
           try {
               ctx = new InitialContext();
               String dataSource = "jdbc/prism";
               DataSource ds = (DataSource) ctx.lookup(dataSource);
               con = ds.getConnection();
               String sqlQry =
                   "SELECT LOOKUP_VALUE_NAME_DISP AS MAIl_ADDRESS FROM XXFND_LOOKUP_V WHERE LOOKUP_TYPE_NAME = 'AUTO_MAIL_ADDRESS' and LOOKUP_VALUE_NAME = '" +code+ "'";
               PreparedStatement preparedStatement = con.prepareStatement(sqlQry);
               ResultSet rs1 = preparedStatement.executeQuery(sqlQry);
               while (rs1.next()) {
                   retStr = rs1.getString("MAIl_ADDRESS");
               }

           } catch (Exception e) {
               System.out.println(e);
           } finally {
               if (con != null) {
                   try {
                       con.close();
                   } catch (SQLException e) {
                       e.printStackTrace(); 
                   }
               }
           }
           return retStr;
       } 
}
