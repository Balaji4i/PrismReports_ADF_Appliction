package com.fouriApps.view.backing;

import com.fouriApps.view.utils.ADFUtils;

import com.fouriApps.view.utils.JSFUtils;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class PrismReportBb {
    private RichInputDate stmtDate;
    private RichInputComboboxListOfValues custId;
    private RichInputComboboxListOfValues customerLov;
    private RichSelectOneChoice reportFormat;
    private RichInputDate fromDate;
    private RichInputDate toDate;
    private RichSelectOneChoice reportType;
    private RichSelectOneChoice propertyId;
    private RichInputDate fromRDate;
    private RichInputDate toRDate;
    private RichInputDate soaDate;
    private RichInputComboboxListOfValues soaCustName;
    private RichInputText custNumber;
    private RichSelectOneChoice cmpropertyId;
    private RichInputText saleperiod;
    private RichSelectOneChoice priceProjectId;
    private RichInputDate arDate;
    private RichInputDate suDate;
    private RichInputDate srFromDate;
    private RichInputDate srTodate;
    private RichInputDate coFromdate;
    private RichInputDate coToDate;
    private RichInputDate canDate;
    private RichSelectOneChoice priceprojectid2;
    private RichInputDate dailyfrom;
    private RichInputDate dailyto;
    private RichInputDate fcastFromDate;
    private RichInputDate fcastToDate;
    private RichInputDate salessummaryFromDate;
    private RichInputDate salessummaryToDate;
    private RichInputDate fcrSummaryFromDate;
    private RichInputDate fcrSummaryToDate;

    public PrismReportBb() {
        super();
    }

    public void okForStmtAccount(ActionEvent actionEvent) throws ParseException {
        String url = null;
        String selectedDate = null;
        String repDate = null;
        String customerName = null;
        String customerId = null;
        String path = null;
        String fileType = null;
        String _reportType=null;
        Object formatType = ADFUtils.evaluateEL("#{bindings.ReportFormat.attributeValue}");
        Object reportTypes = ADFUtils.evaluateEL("#{bindings.ReportType.attributeValue}");
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "CSOA");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);

        if (stmtDate.getValue() != null) {
            repDate = stmtDate.getValue().toString();
            selectedDate = getFormattedDate(repDate);
        }
        if (custId.getValue() != null) {
            customerName = custId.getValue().toString();
            customerId = getCustomerId(customerName);
        }
        if (formatType != null) {
            fileType = formatType.toString();
        }
        
        if(reportTypes!=null){
            _reportType=reportTypes.toString();
        }
        
//        System.err.println(fileType+"-----"+customerId+"-------"+selectedDate);
        url = path + "?P_File_Type=" + fileType + "&P_CUST_ID=" + customerId + "&P_DATE=" + selectedDate + "&P_REPORT_TYPE=" + _reportType;
//        System.err.println("URL--> "+url);
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        String taskflowURL = url;
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setStmtDate(RichInputDate stmtDate) {
        this.stmtDate = stmtDate;
    }

    public RichInputDate getStmtDate() {
        return stmtDate;
    }

    public void setCustId(RichInputComboboxListOfValues custId) {
        this.custId = custId;
    }

    public RichInputComboboxListOfValues getCustId() {
        return custId;
    }

    public void setReportFormat(RichSelectOneChoice reportFormat) {
        this.reportFormat = reportFormat;
    }

    public RichSelectOneChoice getReportFormat() {
        return reportFormat;
    }

    private String getCustomerId(String customerName) {
        String cusId = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("XXSTG_CUSTOMER_ROVO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("CustomerName", customerName);
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            cusId = r.getAttribute("CustId") == null ? "0" : r.getAttribute("CustId").toString();
        }
        funVo.applyViewCriteria(null);
        return cusId;
    }

    private String getFormattedDate(String repDate) throws ParseException { 
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        java.util.Date date = formatter.parse(repDate);  
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        return ft.format(date);
    }

    public void setFromDate(RichInputDate fromDate) {
        this.fromDate = fromDate;
    }

    public RichInputDate getFromDate() {
        return fromDate;
    }

    public void setToDate(RichInputDate toDate) {
        this.toDate = toDate;
    }

    public RichInputDate getToDate() {
        return toDate;
    }
    public void downloadSaleReport(ActionEvent actionEvent) throws ParseException {
        String fromDateS = null;
        String toDateS = null;
        String url = null;
        ViewObject vo = ADFUtils.findIterator("XXSTG_ORGANIZATIONS_ROVOIterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String orgId = r.getAttribute("DummyBu") == null ? null : r.getAttribute("DummyBu").toString();
        String unitType = r.getAttribute("UnitType") == null ? null : r.getAttribute("UnitType").toString();
        
        if (fromDate.getValue() != null) {
            String d = fromDate.getValue().toString();
            fromDateS = getFormattedDate(d);
        }
        if (toDate.getValue() != null) {
            String d = toDate.getValue().toString();
            toDateS = getFormattedDate(d);
        }
        
//        System.err.println(orgId + "-----" + unitType + "-----" + fromDateS + "-----" + toDateS);
        
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "SR");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url = path + "?P_ORG=" + orgId + "&P_UNIT_TYPE=" + unitType + "&P_FDATE=" + fromDateS + "&P_TDATE=" + toDateS;
        
//        System.err.println(url);
        
        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
    }


    public void setReportType(RichSelectOneChoice reportType) {
        this.reportType = reportType;
    }

    public RichSelectOneChoice getReportType() {
        return reportType;
    }
    
    public String getReportPathFromDB(String shortCode){
        String path = null;
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", shortCode);
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }    
        return path;
    }
    
    public void openPage(String url){
        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void receiptDownload(ActionEvent actionEvent) {
        ViewObject vo = ADFUtils.findIterator("SeperateReport_ROVOIterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String receiptNo = r.getAttribute("ReceiptNo") == null ? "0" : r.getAttribute("ReceiptNo").toString();
        String format = r.getAttribute("Format") == null ? "EXCEL" : r.getAttribute("Format").toString();
        String path = this.getReportPathFromDB("BR");
        String url = path + "?P_RECP_NUM=" + receiptNo + "&P_File_Type=" + format;
        System.err.println("URL-"+url);
        this.openPage(url);
    }

    public void downloadInventory(ActionEvent actionEvent) {
        ViewObject vo = ADFUtils.findIterator("OLD_Inventory_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String property = r.getAttribute("Property") == null ? "0" : r.getAttribute("Property").toString();
        String building = r.getAttribute("Building") == null ? "0" : r.getAttribute("Building").toString();
        String format = r.getAttribute("Format") == null ? "EXCEL" : r.getAttribute("Format").toString();
        String path = this.getReportPathFromDB("AI");
        String url = path + "?P_PROP_NAME=" + property + "&P_BUILD_NAME=" + building + "&P_File_Type=" + format;
        System.err.println("URL-"+url);
        this.openPage(url);
    }

    public void unitStatusDownload(ActionEvent actionEvent) {
        ViewObject vo = ADFUtils.findIterator("Unit_Status_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String project = r.getAttribute("Project") == null ? "0" : r.getAttribute("Project").toString();
        String property = r.getAttribute("Property") == null ? "0" : r.getAttribute("Property").toString();
        String format = r.getAttribute("Format") == null ? "EXCEL" : r.getAttribute("Format").toString();
        String path = this.getReportPathFromDB("US");
        String url = path + "?P_PROJ_NAME=" + project + "&P_PROP_NAME=" + property + "&P_File_Type=" + format;
        System.err.println("URL-"+url);
        this.openPage(url);
    }
    
    public void unitStatusNewDownload(ActionEvent actionEvent) {
        ViewObject vo = ADFUtils.findIterator("Unit_Status_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String project = r.getAttribute("Project") == null ? "0" : r.getAttribute("Project").toString();
        String property = r.getAttribute("Property") == null ? "0" : r.getAttribute("Property").toString();
        String format = r.getAttribute("Format") == null ? "EXCEL" : r.getAttribute("Format").toString();
        String path = this.getReportPathFromDB("USN");
        String url = path + "?P_PROJ_NAME=" + project + "&P_PROP_NAME=" + property + "&P_File_Type=" + format;
        System.err.println("URL-"+url);
        this.openPage(url);
    }

    public void customerMasterDownload(ActionEvent actionEvent) {
        String url = null;
        String customerName = "0";
        String path = null;
        String customerId = "0";
       //String customerLlov="0";
//        String l_propertyId="59";
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "CUST_M");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
        if (customerLov.getValue()!= null) {
        System.err.println("cust ==>"+customerLov.getValue().toString());
        customerName = customerLov.getValue().toString();
        customerId = getCustomerId(customerName);
        } else {
                System.err.println("cust ==>"+customerLov.getValue());
            System.err.println("no value");
          customerId="0";
            }
        System.err.println("cust ==>"+customerId);       
        ViewObject vo = ADFUtils.findIterator("SeperateReport_ROVOIterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String project = r.getAttribute("Project") == null ? "0" : r.getAttribute("Project").toString();
//        String property = r.getAttribute("Property") == null ? "0" : r.getAttribute("Property").toString();
         System.err.println("project ==>"+project);

        ViewObject proVO=ADFUtils.findIterator("PropertyMasterRO1Iterator").getViewObject();
       // String l_propertyId=proVO.getCurrentRow().getAttribute("Id")==null?"":proVO.getCurrentRow().getAttribute("Id").toString();
//        System.err.println("l_propertyId=>"+l_propertyId);
//        System.err.println("l_propertyId 2=>"+cmpropertyId.getValue());
//        System.err.println("property 3=>"+project);
//        System.err.println("property 4=>"+property);


        
//        if(propertyId.getValue()!=null){
//            l_propertyId=propertyId.getValue().toString();    
//        }
//        System.err.println("pro id==>"+propertyId.getValue());
       
        System.err.println(path+"-----"+customerName);
        url = path + "?P_CUST=" + customerId+"&P_PRO_ID=" + project;
        System.err.println("URL--> "+url);
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        String taskflowURL = url;
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setCustomerLov(RichInputComboboxListOfValues customerLov) {
        this.customerLov = customerLov;
    }

    public RichInputComboboxListOfValues getCustomerLov() {
        return customerLov;
    }

    public void setPropertyId(RichSelectOneChoice propertyId) {
        this.propertyId = propertyId;
    }

    public RichSelectOneChoice getPropertyId() {
        return propertyId;
    }

    public void onClickReceiptDetailXl(ActionEvent actionEvent) throws ParseException {
        String url = null;
        String path = null;
        String fromDate = null;
        String toDate = null;
        String selectedFromDate = null;
        String selectedToDate = null;
        ViewObject proVO=ADFUtils.findIterator("PropertyMasterRO1Iterator").getViewObject();
        String l_propertyId=proVO.getCurrentRow().getAttribute("Id")==null?"59":proVO.getCurrentRow().getAttribute("Id").toString();
        System.err.println("l_propertyId=>"+l_propertyId);
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "RECDEC");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
        
        
        if (fromRDate.getValue() != null) {
            fromDate = fromRDate.getValue().toString();
            selectedFromDate = getFormattedDate(fromDate);
        }
        
        if (toRDate.getValue() != null) {
            toDate = toRDate.getValue().toString();
            selectedToDate = getFormattedDate(toDate);
        }
        
        url = path + "?P_File_Type=xlsx"+"&P_PROP_NAME=" + l_propertyId+"&P_FROM_DATE="+selectedFromDate+"&P_TO_DATE="+selectedToDate;
        System.err.println("URL--> "+url);        
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        String taskflowURL = url;
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\");");
        erks.addScript(fctx, script.toString());
                
    }

    public void setFromRDate(RichInputDate fromRDate) {
        this.fromRDate = fromRDate;
    }

    public RichInputDate getFromRDate() {
        return fromRDate;
    }

    public void setToRDate(RichInputDate toRDate) {
        this.toRDate = toRDate;
    }

    public RichInputDate getToRDate() {
        return toRDate;
    }

    public void onClickReceiptDetailPdf(ActionEvent actionEvent) throws ParseException {
        String url = null;
        String path = null;
        String fromDate = null;
        String toDate = null;
        String selectedFromDate = null;
        String selectedToDate = null;
        ViewObject proVO=ADFUtils.findIterator("PropertyMasterRO1Iterator").getViewObject();
        String l_propertyId=proVO.getCurrentRow().getAttribute("Id")==null?"59":proVO.getCurrentRow().getAttribute("Id").toString();
        System.err.println("l_propertyId=>"+l_propertyId);
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "RECDEC");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
        
        
        if (fromRDate.getValue() != null) {
            fromDate = fromRDate.getValue().toString();
            selectedFromDate = getFormattedDate(fromDate);
        }
        
        if (toRDate.getValue() != null) {
            toDate = toRDate.getValue().toString();
            selectedToDate = getFormattedDate(toDate);
        }
        
        url = path + "?P_File_Type=pdf"+"&P_PROP_NAME=" + l_propertyId+"&P_FROM_DATE="+selectedFromDate+"&P_TO_DATE="+selectedToDate;
        System.err.println("URL--> "+url);        
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        String taskflowURL = url;
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setSoaDate(RichInputDate soaDate) {
        this.soaDate = soaDate;
    }

    public RichInputDate getSoaDate() {
        return soaDate;
    }

    public void soaExcel(ActionEvent actionEvent) throws ParseException {
        String url = null;
        String path = null;
        String fromDate = null;
        String toDate = null;
        String selectedFromDate = null;
        String selectedToDate = null;

        String customerName = "0";
        String l_custId = "0";
        String custNum="0";
    

        ViewObject custVO=ADFUtils.findIterator("XXSTG_CUSTOMER_ROVO1Iterator").getViewObject();

        //        String l_custId=custVO.getCurrentRow().getAttribute("CustId")==null?"59":custVO.getCurrentRow().getAttribute("CustId").toString();
        //        System.err.println("l_custId=>"+l_custId);

        if (soaCustName.getValue() != null && custNumber.getValue()!=null) {
            customerName = soaCustName.getValue().toString();
            custNum=custNumber.getValue().toString();
            l_custId = getSOACustomerId(customerName, custNum);
        }
        else {
            l_custId="59";
        }
//        ViewObject orgVo = ADFUtils.findIterator("XXSTG_ORGANIZATIONS_ROVOIterator").getViewObject(); 
//        Row or = orgVo.getCurrentRow();
//        String orgId = or.getAttribute("DummyBu") == null ? "59" : or.getAttribute("DummyBu").toString();

        //
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "CSOA");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
        
        if (soaDate.getValue() != null) {
            fromDate = soaDate.getValue().toString();
            selectedFromDate = getFormattedDate(fromDate);
        }
        
        
        
        
        url = path + "?P_File_Type=xlsx"+"&P_CUST_ID=" + l_custId+"&P_DATE="+selectedFromDate+"&P_REPORT_TYPE=CUST_STATEMENT_ACCOUNT";
       // System.err.println("URL--> "+url);        
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        String taskflowURL = url;
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\");");
        erks.addScript(fctx, script.toString());
    }
    
    
    private String getSOACustomerId(String customerName, String customerNum) {
        String cusId = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("XXSTG_CUSTOMER_ROVO1");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("CustomerName", customerName);
        vcRow.setAttribute("CustomerNumber", customerNum);
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            cusId = r.getAttribute("CustId") == null ? "0" : r.getAttribute("CustId").toString();
        }
        
        funVo.applyViewCriteria(null);
        return cusId;
    }

    public void soaExcelSummary(ActionEvent actionEvent) throws ParseException {
        String url = null;
        String path = null;
        String fromDate = null;
        String toDate = null;
        String selectedFromDate = null;
        String selectedToDate = null;

        String customerName = "0";
        String l_custId = "0";
        String custNum="0";

        ViewObject custVO=ADFUtils.findIterator("XXSTG_CUSTOMER_ROVO1Iterator").getViewObject();

        //        String l_custId=custVO.getCurrentRow().getAttribute("CustId")==null?"59":custVO.getCurrentRow().getAttribute("CustId").toString();
        //        System.err.println("l_custId=>"+l_custId);

        if (soaCustName.getValue() != null && custNumber.getValue()!=null) {
            customerName = soaCustName.getValue().toString();
            custNum=custNumber.getValue().toString();
            l_custId = getSOACustomerId(customerName, custNum);
        }
        else {
            l_custId="59";
        }
        
//        ViewObject orgVo = ADFUtils.findIterator("XXSTG_ORGANIZATIONS_ROVOIterator").getViewObject(); 
//        Row or = orgVo.getCurrentRow();
//        String orgId = or.getAttribute("DummyBu") == null ? "59" : or.getAttribute("DummyBu").toString();
        //
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "CSOA");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
        
        if (soaDate.getValue() != null) {
            fromDate = soaDate.getValue().toString();
            selectedFromDate = getFormattedDate(fromDate);
        }
        
            url = path + "?P_File_Type=xlsx"+"&P_CUST_ID=" + l_custId+"&P_DATE="+selectedFromDate+"&P_REPORT_TYPE=AR_SUMMARY";
 //System.err.println("URL--> "+url);        
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        String taskflowURL = url;
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setSoaCustName(RichInputComboboxListOfValues soaCustName) {
        this.soaCustName = soaCustName;
    }

    public RichInputComboboxListOfValues getSoaCustName() {
        return soaCustName;
    }

    public void setCustNumber(RichInputText custNumber) {
        this.custNumber = custNumber;
    }

    public RichInputText getCustNumber() {
        return custNumber;
    }


    public void setCmpropertyId(RichSelectOneChoice cmpropertyId) {
        this.cmpropertyId = cmpropertyId;
    }

    public RichSelectOneChoice getCmpropertyId() {
        return cmpropertyId;
    }

    public void otherChargesDownload(ActionEvent actionEvent) {
              String unitId="0";
                ViewObject vo = ADFUtils.findIterator("SeperateReport_ROVOIterator").getViewObject(); 
                Row r = vo.getCurrentRow();
                String propertyId = r.getAttribute("Property3") == null ? "59" : r.getAttribute("Property3").toString();
//               System.err.println("PROP-"+propertyId);
               String unitNo = r.getAttribute("UnitNumber") == null ? "0" : r.getAttribute("UnitNumber").toString();
//               System.err.println("UNITNUMBER-"+unitNo);
               if(unitNo!="0")
               {
                   unitId=getUnitId(propertyId,unitNo);
               }
               else
               {
                   unitId="59";
               }
//               System.err.println("UNITID-"+unitId);
                String path = this.getReportPathFromDB("OC");
                String url = path + "?P_File_Type=xlsx"+"&P_PROP_ID=" + propertyId + "&P_UNIT_ID=" + unitId;
                System.err.println("URL-"+url);
                this.openPage(url);
    }
    private String getUnitId(String propId, String unitNumber) {
        String unitId = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("PropertyUnitsROVO1");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("PropertyId", propId);
        vcRow.setAttribute("UnitNumber", unitNumber);
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            unitId = r.getAttribute("UnitId") == null ? "0" : r.getAttribute("UnitId").toString();
        }
        
        funVo.applyViewCriteria(null);
        return unitId;
    }

    public void onClickSalesSummary(ActionEvent actionEvent) {
        String sales_period = null;
        String path = null;
        String url = null;
        if (saleperiod.getValue()!=null) {
            sales_period = saleperiod.getValue().toString();
        }
        else {
            sales_period="59";
        }
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "SS");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
        url = path + "?P_File_Type=xlsx"+"&P_Sales_Period=" + sales_period;
        System.err.println("URL--> "+url);
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        String taskflowURL = url;
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setSaleperiod(RichInputText saleperiod) {
        this.saleperiod = saleperiod;
    }

    public RichInputText getSaleperiod() {
        return saleperiod;
    }

    public void onClickPriceList(ActionEvent actionEvent) {
        System.err.println("Enter into the download method");
        String path = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "UPL");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
               String url = null;
               ViewObject vo    = ADFUtils.findIterator("PRICE_LIST_NEW_ROVO1Iterator").getViewObject();
            String proj_id=vo.getCurrentRow().getAttribute("Projects")==null?null:vo.getCurrentRow().getAttribute("Projects").toString();
               String prop_id=vo.getCurrentRow().getAttribute("Property")==null?"59":vo.getCurrentRow().getAttribute("Property").toString();
       String unitstatus=vo.getCurrentRow().getAttribute("UnitStatus")==null?"All":vo.getCurrentRow().getAttribute("UnitStatus").toString();
               String format=vo.getCurrentRow().getAttribute("Format")==null?"0":vo.getCurrentRow().getAttribute("Format").toString();

               System.err.println("PROJECT_ID--->"+proj_id);
               System.err.println("PROPERTY_ID--->"+prop_id);
               System.err.println("FORMAT--->"+format);
    if(prop_id.equalsIgnoreCase("59")&&proj_id.equalsIgnoreCase("300000002552882"))  {
        url = path + "?P_File_Type="+format+"&P_PROP_ID=" + prop_id+"&P_PROJ_ID=null&P_UNIT_STATUS="+unitstatus;
    }
    else{
  url = path + "?P_File_Type="+format+"&P_PROP_ID=" + prop_id+"&P_PROJ_ID="+proj_id+"&P_UNIT_STATUS="+unitstatus;
        }
           System.err.println(url);
               FacesContext fctx = FacesContext.getCurrentInstance(); 
        String taskflowURL = url;
               ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
               StringBuilder script = new StringBuilder();
               script.append("window.open(\"" + taskflowURL + "\");");
               erks.addScript(fctx, script.toString());
        
    }

//    public void projectValueChangeListener(ValueChangeEvent valueChangeEvent) {
//        String pid = valueChangeEvent.getNewValue().toString().equalsIgnoreCase("59") ? "59" : valueChangeEvent.getNewValue().toString();
//        System.err.println(pid);
//        if(pid.equalsIgnoreCase("59")){
//            priceProjectId.setValue(null);
//        }
//      
//    }

    public void setPriceProjectId(RichSelectOneChoice priceProjectId) {
        this.priceProjectId = priceProjectId;
    }

    public RichSelectOneChoice getPriceProjectId() {
        return priceProjectId;
    }

    public void onClickArDetailedReport(ActionEvent actionEvent) throws ParseException {
        System.err.println("Enter into the download method");
        String path = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "AR_N");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
               String url = null;
               ViewObject vo    = ADFUtils.findIterator("AR_DETAILED_NEW_ROVO1Iterator").getViewObject();
            String proj_id=vo.getCurrentRow().getAttribute("Projects")==null?null:vo.getCurrentRow().getAttribute("Projects").toString();
            String prop_id=vo.getCurrentRow().getAttribute("Property")==null?"59":vo.getCurrentRow().getAttribute("Property").toString();
            String amt_due=vo.getCurrentRow().getAttribute("Amount_Due")==null?"59":vo.getCurrentRow().getAttribute("Amount_Due").toString();
            String aDate=arDate.getValue()==null?"0":arDate.getValue().toString();
            String SelectedDate = getFormattedDate(aDate);
            String format=vo.getCurrentRow().getAttribute("Format")==null?"0":vo.getCurrentRow().getAttribute("Format").toString();

               System.err.println("PROJECT_ID--->"+proj_id);
               System.err.println("PROPERTY_ID--->"+prop_id);
               System.err.println("FORMAT--->"+format);
       
        url = path + "?P_File_Type="+format+"&P_PROP_ID=" + prop_id+"&P_PROJ_ID="+proj_id+"&P_DATE="+SelectedDate+"&P_AMOUNT="+amt_due;
           System.err.println(url);
               FacesContext fctx = FacesContext.getCurrentInstance(); 
        String taskflowURL = url;
               ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
               StringBuilder script = new StringBuilder();
               script.append("window.open(\"" + taskflowURL + "\");");
               erks.addScript(fctx, script.toString());
        
        }

    public void setArDate(RichInputDate arDate) {
        this.arDate = arDate;
    }

    public RichInputDate getArDate() {
        return arDate;
    }

    public void setSuDate(RichInputDate suDate) {
        this.suDate = suDate;
    }

    public RichInputDate getSuDate() {
        return suDate;
    }

    public void onClickARSummaryReport(ActionEvent actionEvent) throws ParseException {
        System.err.println("Enter into the download method");
        String path = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "AS_N");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
               String url = null;
               ViewObject vo    = ADFUtils.findIterator("AR_SUMMARY_ROVO1Iterator").getViewObject();
            String proj_id=vo.getCurrentRow().getAttribute("Projects")==null?null:vo.getCurrentRow().getAttribute("Projects").toString();
            String prop_id=vo.getCurrentRow().getAttribute("Property")==null?"59":vo.getCurrentRow().getAttribute("Property").toString();
           String amt_due=vo.getCurrentRow().getAttribute("Amount_Due")==null?"59":vo.getCurrentRow().getAttribute("Amount_Due").toString();
           String asDate=suDate.getValue()==null?"0":suDate.getValue().toString();
            String SelectedDate = getFormattedDate(asDate);
            String format=vo.getCurrentRow().getAttribute("Format")==null?"0":vo.getCurrentRow().getAttribute("Format").toString();

               System.err.println("PROJECT_ID--->"+proj_id);
               System.err.println("PROPERTY_ID--->"+prop_id);
               System.err.println("FORMAT--->"+format);
        
        url = path + "?P_File_Type="+format+"&P_PROP_ID=" + prop_id+"&P_PROJ_ID="+proj_id+"&P_DATE="+SelectedDate+"&P_AMOUNT="+amt_due;;
           System.err.println(url);
               FacesContext fctx = FacesContext.getCurrentInstance(); 
        String taskflowURL = url;
               ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
               StringBuilder script = new StringBuilder();
               script.append("window.open(\"" + taskflowURL + "\");");
               erks.addScript(fctx, script.toString());
    }

    public void setSrFromDate(RichInputDate srFromDate) {
        this.srFromDate = srFromDate;
    }

    public RichInputDate getSrFromDate() {
        return srFromDate;
    }

    public void setSrTodate(RichInputDate srTodate) {
        this.srTodate = srTodate;
    }

    public RichInputDate getSrTodate() {
        return srTodate;
    }

    public void onClickSalesReportNew(ActionEvent actionEvent) throws ParseException {
        String fromDateS = null;
        String toDateS = null;
        String url = null;
        ViewObject vo = ADFUtils.findIterator("SALEA_REPORT_NEW_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String orgId = r.getAttribute("BusinessUnit") == null ? "1" : r.getAttribute("BusinessUnit").toString();
        String unitType = r.getAttribute("UnitType") == null ? null : r.getAttribute("UnitType").toString();
        String format = r.getAttribute("Format") == null ? null : r.getAttribute("Format").toString();
        
        if (srFromDate.getValue() != null) {
            String fdate = srFromDate.getValue().toString();
            fromDateS = getFormattedDate(fdate);
        }
        if (srTodate.getValue() != null) {
            String tdate = srTodate.getValue().toString();
            toDateS = getFormattedDate(tdate);
        }
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "SR_N");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url =path+ "?P_File_Type="+format+ "&P_ORG=" + orgId + "&P_UNIT_TYPE=" + unitType + "&P_FROMDATE=" + fromDateS + "&P_TODATE=" + toDateS;
            System.err.println(url);

        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
        }


    public void onClickCollectionSummaryReport(ActionEvent actionEvent) throws ParseException {
        String fromDateS = null;
        String toDateS = null;
        String url = null;
        ViewObject vo = ADFUtils.findIterator("COLLECTION_SUMMARY_NEW_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String orgId = r.getAttribute("BusinessUnit") == null ? null : r.getAttribute("BusinessUnit").toString();
        String format = r.getAttribute("Format") == null ? null : r.getAttribute("Format").toString();
        
        if (coFromdate.getValue() != null) {
            String fdate = coFromdate.getValue().toString();
            fromDateS = getFormattedDate(fdate);
        }
        if (coToDate.getValue() != null) {
            String tdate = coToDate.getValue().toString();
            toDateS = getFormattedDate(tdate);
        }
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "CS_N");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url = path+ "?P_File_Type="+format+ "&P_ORG_ID=" + orgId + "&P_FROM_DATE=" + fromDateS + "&P_TO_DATE=" + toDateS;
        System.err.println(url);

        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setCoFromdate(RichInputDate coFromdate) {
        this.coFromdate = coFromdate;
    }

    public RichInputDate getCoFromdate() {
        return coFromdate;
    }

    public void setCoToDate(RichInputDate coToDate) {
        this.coToDate = coToDate;
    }

    public RichInputDate getCoToDate() {
        return coToDate;
    }

    public void onClickCancellationReport(ActionEvent actionEvent) throws ParseException {
        System.err.println("Enter into the download method");
        String path = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "CR_N");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
               String url = null;
               ViewObject vo    = ADFUtils.findIterator("CANCELLATION_REPORT_ROVO1Iterator").getViewObject();
            String prop_id=vo.getCurrentRow().getAttribute("Property")==null?"59":vo.getCurrentRow().getAttribute("Property").toString();
            String cancellationDate=canDate.getValue()==null?"0":canDate.getValue().toString();
            String SelectedDate = getFormattedDate(cancellationDate);
            String format=vo.getCurrentRow().getAttribute("Format")==null?"0":vo.getCurrentRow().getAttribute("Format").toString();
               System.err.println("PROPERTY_ID--->"+prop_id);
               System.err.println("FORMAT--->"+format);
        
        url = path + "?P_File_Type="+format+"&P_PROP_ID=" + prop_id+"&P_DATE="+SelectedDate;
           System.err.println(url);
               FacesContext fctx = FacesContext.getCurrentInstance(); 
        String taskflowURL = url;
               ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
               StringBuilder script = new StringBuilder();
               script.append("window.open(\"" + taskflowURL + "\");");
               erks.addScript(fctx, script.toString());
        
        }
        // Add event code here...

    public void setCanDate(RichInputDate canDate) {
        this.canDate = canDate;
    }

    public RichInputDate getCanDate() {
        return canDate;
    }

    public void onClickInventoryNewReport(ActionEvent actionEvent) {
        System.err.println("Enter into the download method");
        String path = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "IR_N");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
               String url = null;
               ViewObject vo    = ADFUtils.findIterator("SeperateReport_ROVOIterator").getViewObject();
            String build_id=vo.getCurrentRow().getAttribute("Building")==null?null:vo.getCurrentRow().getAttribute("Building").toString();
            String prop_id=vo.getCurrentRow().getAttribute("Property4")==null?"59":vo.getCurrentRow().getAttribute("Property4").toString();
            String format=vo.getCurrentRow().getAttribute("Format")==null?"0":vo.getCurrentRow().getAttribute("Format").toString();

               System.err.println("PROPERTY_ID--->"+prop_id);
               System.err.println("FORMAT--->"+format);
        
         url = path + "?P_PROP_ID=" + prop_id + "&P_BUILD_ID=" + build_id + "&P_File_Type=" + format;
           System.err.println(url);
               FacesContext fctx = FacesContext.getCurrentInstance(); 
        String taskflowURL = url;
               ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
               StringBuilder script = new StringBuilder();
               script.append("window.open(\"" + taskflowURL + "\");");
               erks.addScript(fctx, script.toString());
    }

    public void onClicknewPriceListReport(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void newpropertyvaluechangelistener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void onClickOldPriceListReport(ActionEvent actionEvent) {
        System.err.println("Enter into the download method");
        String path = null;
        ViewObject funVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "UPL_O");
        vc.addRow(vcRow);
        funVo.applyViewCriteria(vc);
        funVo.executeQuery();
        if (funVo.first() != null) {
            Row r = funVo.first();
            path = r.getAttribute("PagePath") == null ? "" : r.getAttribute("PagePath").toString();
        }
        funVo.applyViewCriteria(null);
               String url = null;
               ViewObject vo    = ADFUtils.findIterator("PropertyMasterROVO1Iterator").getViewObject();
            String proj_id=vo.getCurrentRow().getAttribute("ProjectId")==null?null:vo.getCurrentRow().getAttribute("ProjectId").toString();
               String prop_id=vo.getCurrentRow().getAttribute("PropertyId")==null?"59":vo.getCurrentRow().getAttribute("PropertyId").toString();
        String unitstatus=vo.getCurrentRow().getAttribute("Unit_Status")==null?"All":vo.getCurrentRow().getAttribute("Unit_Status").toString();
               String format=vo.getCurrentRow().getAttribute("Format")==null?"0":vo.getCurrentRow().getAttribute("Format").toString();

               System.err.println("PROJECT_ID--->"+proj_id);
               System.err.println("PROPERTY_ID--->"+prop_id);
               System.err.println("FORMAT--->"+format);
        if(prop_id.equalsIgnoreCase("59")&&proj_id.equalsIgnoreCase("300000002552882"))  {
        url = path + "?P_File_Type="+format+"&P_PROP_ID=" + prop_id+"&P_PROJ_ID=null&P_UNIT_STATUS="+unitstatus;
        }
        else{
        url = path + "?P_File_Type="+format+"&P_PROP_ID=" + prop_id+"&P_PROJ_ID="+proj_id+"&P_UNIT_STATUS="+unitstatus;
        }
           System.err.println(url);
               FacesContext fctx = FacesContext.getCurrentInstance(); 
        String taskflowURL = url;
               ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
               StringBuilder script = new StringBuilder();
               script.append("window.open(\"" + taskflowURL + "\");");
               erks.addScript(fctx, script.toString());
    }

    public void setPriceprojectid2(RichSelectOneChoice priceprojectid2) {
        this.priceprojectid2 = priceprojectid2;
    }

    public RichSelectOneChoice getPriceprojectid2() {
        return priceprojectid2;
    }

    public void setDailyfrom(RichInputDate dailyfrom) {
        this.dailyfrom = dailyfrom;
    }

    public RichInputDate getDailyfrom() {
        return dailyfrom;
    }

    public void setDailyto(RichInputDate dailyto) {
        this.dailyto = dailyto;
    }

    public RichInputDate getDailyto() {
        return dailyto;
    }

    public void onClickDailyCollectionNew(ActionEvent actionEvent) throws ParseException {
        String fromDateS = null;
        String toDateS = null;
        String url = null;
        ViewObject vo = ADFUtils.findIterator("DAILY_COLLECTION_NEW_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String orgId = r.getAttribute("BusinessUnit") == null ? "1" : r.getAttribute("BusinessUnit").toString();
        String format = r.getAttribute("Format") == null ? null : r.getAttribute("Format").toString();
        
        if (dailyfrom.getValue() != null) {
            String fdate = dailyfrom.getValue().toString();
            fromDateS = getFormattedDate(fdate);
        }
        if (dailyto.getValue() != null) {
            String tdate = dailyto.getValue().toString();
            toDateS = getFormattedDate(tdate);
        }
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "DC_N");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url = path+ "?P_File_Type="+format+ "&P_ORG_ID=" + orgId + "&P_FROM_DATE=" + fromDateS + "&P_TO_DATE=" + toDateS;
        System.err.println(url);

        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
        }

    public String onClickDLDStatus() {
        // Add event code here...
        return null;
    }

    public void onClickDLDStatus(ActionEvent actionEvent) {
        String url = null;
        ViewObject vo = ADFUtils.findIterator("DLD_STATUS_REPORT1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String pId = r.getAttribute("Project") == null ? "1" : r.getAttribute("Project").toString();
        String format = r.getAttribute("Format") == null ? null : r.getAttribute("Format").toString();
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "DLD");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url = path+ "?P_File_Type="+format+ "&P_PROJ_ID=" + pId;
        System.err.println(url);

        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
        }

    public void setFcastFromDate(RichInputDate fcastFromDate) {
        this.fcastFromDate = fcastFromDate;
    }

    public RichInputDate getFcastFromDate() {
        return fcastFromDate;
    }

    public void setFcastToDate(RichInputDate fcastToDate) {
        this.fcastToDate = fcastToDate;
    }

    public RichInputDate getFcastToDate() {
        return fcastToDate;
    }

    public void onClickForecastReport(ActionEvent actionEvent) throws ParseException {
        String fromDateS = null;
        String toDateS = null;
        String url = null;
        ViewObject vo = ADFUtils.findIterator("FORCAST_REPORT_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String orgId = r.getAttribute("BusinessUnit") == null ? "1" : r.getAttribute("BusinessUnit").toString();
        String format = r.getAttribute("Format") == null ? null : r.getAttribute("Format").toString();
        
        if (fcastFromDate.getValue() != null) {
            String fdate = fcastFromDate.getValue().toString();
            fromDateS = getFormattedDate(fdate);
        }
        if (fcastToDate.getValue() != null) {
            String tdate = fcastToDate.getValue().toString();
            toDateS = getFormattedDate(tdate);
        }
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "FCR_P");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url = path+ "?P_File_Type="+format+ "&P_ORG_ID=" + orgId + "&P_FROM_DATE=" + fromDateS + "&P_TO_DATE=" + toDateS;
        System.err.println(url);

        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setSalessummaryFromDate(RichInputDate salessummaryFromDate) {
        this.salessummaryFromDate = salessummaryFromDate;
    }

    public RichInputDate getSalessummaryFromDate() {
        return salessummaryFromDate;
    }

    public void setSalessummaryToDate(RichInputDate salessummaryToDate) {
        this.salessummaryToDate = salessummaryToDate;
    }

    public RichInputDate getSalessummaryToDate() {
        return salessummaryToDate;
    }

    public void onClickSalesSummaryNew(ActionEvent actionEvent) throws ParseException {
        String fromDateS = null;
        String toDateS = null;
        String url = null;
        ViewObject vo = ADFUtils.findIterator("SALES_SUMMARY_REPORT_NEW_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String orgId = r.getAttribute("BusinessUnit") == null ? "1" : r.getAttribute("BusinessUnit").toString();
        String unitType = r.getAttribute("UnitType") == null ? null : r.getAttribute("UnitType").toString();
        String format = r.getAttribute("Format") == null ? null : r.getAttribute("Format").toString();
        
        if (salessummaryFromDate.getValue() != null) {
            String fdate = salessummaryFromDate.getValue().toString();
            fromDateS = getFormattedDate(fdate);
        }
        if (salessummaryToDate.getValue() != null) {
            String tdate = salessummaryToDate.getValue().toString();
            toDateS = getFormattedDate(tdate);
        }
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "SS_N");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url =path+ "?P_File_Type="+format+ "&P_ORG_ID=" + orgId + "&P_UNIT_TYPE=" + unitType + "&P_FROM_DATE=" + fromDateS + "&P_TO_DATE=" + toDateS;
            System.err.println(url);

        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
    }

    public void setFcrSummaryFromDate(RichInputDate fcrSummaryFromDate) {
        this.fcrSummaryFromDate = fcrSummaryFromDate;
    }

    public RichInputDate getFcrSummaryFromDate() {
        return fcrSummaryFromDate;
    }

    public void setFcrSummaryToDate(RichInputDate fcrSummaryToDate) {
        this.fcrSummaryToDate = fcrSummaryToDate;
    }

    public RichInputDate getFcrSummaryToDate() {
        return fcrSummaryToDate;
    }

    public void onClickForecastSummaryReport(ActionEvent actionEvent) throws ParseException {
        String fromDateS = null;
        String toDateS = null;
        String url = null;
        ViewObject vo = ADFUtils.findIterator("FORECAST_SUMMARY_ROVO1Iterator").getViewObject(); 
        Row r = vo.getCurrentRow();
        String orgId = r.getAttribute("BusinessUnit") == null ? "1" : r.getAttribute("BusinessUnit").toString();
        String format = r.getAttribute("Format") == null ? null : r.getAttribute("Format").toString();
        
        if (fcrSummaryFromDate.getValue() != null) {
            String fdate = fcrSummaryFromDate.getValue().toString();
            fromDateS = getFormattedDate(fdate);
        }
        if (fcrSummaryToDate.getValue() != null) {
            String tdate = fcrSummaryToDate.getValue().toString();
            toDateS = getFormattedDate(tdate);
        }
        String path = null; 
        ViewObject funcVo = ADFUtils.getApplicationModuleForDataControl("PrismReport_AMDataControl").findViewObject("Functions_VO");
        ViewCriteria vc = funcVo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("FuncShortCode", "FCRS-P");
        vc.addRow(vcRow);
        funcVo.applyViewCriteria(vc);
        funcVo.executeQuery();
        if (funcVo.getEstimatedRowCount()!=0) {
            Row funRow = funcVo.first();
            path = funRow.getAttribute("PagePath") == null ? "" : funRow.getAttribute("PagePath").toString();
        }      
        
        url = path+ "?P_File_Type="+format+ "&P_ORG_ID=" + orgId + "&P_FROM_DATE=" + fromDateS + "&P_TO_DATE=" + toDateS;
        System.err.println(url);

        FacesContext fctx = FacesContext.getCurrentInstance(); 
        ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + url + "\");");
        erks.addScript(fctx, script.toString());
    }
}


