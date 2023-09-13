package com.dynamicreports.view.beans.filmStrip;

import com.view.beans.filmStrip.SessionState;
import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.event.PhaseId;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;
import com.octetstring.vde.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

public class FilmStripPhaseListener implements PagePhaseListener {
    
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final String CURRENCY_CODE = "AED";
    private static final String TIME_FORMAT = "HH24:MT";
    private static final String TIME_ZONE_FORMAT = "GT";
    private static final String NUMBER_FORMAT = "###,###,###,###";
    java.util.Map sessionMap = ADFContext.getCurrent().getSessionScope();
    
    public FilmStripPhaseListener() {
        super();
    }

    public void afterPhase(PagePhaseEvent phaseEvent) {
    }

    public void beforePhase(PagePhaseEvent phaseEvent) {
        if (phaseEvent.getPhaseId() == Lifecycle.PREPARE_RENDER_ID) {
            onPageLoad();
        }
    }

    public PhaseId getPhaseId() {
        return null;
    }
    public void onPageLoad(){
        if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                System.out.println("phase listener constructor called1213131");                                                                                       
//            String jwt = (String)ADFContext.getCurrent().getPageFlowScope().get("jwt");
              String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IlRBc0pMVXY0MFVuaWRJclVrRGFwRzhFXzlLOCJ9.eyJleHAiOjE0ODM1MzA3NzEsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiYXBpIiwiaWF0IjoxNDgzNTE2MzcxfQ.ALvDilyGj-VQUmRQnUc7L1tz895bxjiSfPetczwqbUhMTmBIIoyJd9tKFQTnYPg8esUiiym8RnXRisFXcWmdmcoYAg3bbhqQ877KBDdXg6_CAk5h4OSHG8jgXhWFSJsE-";
            System.out.println("jwt:"+jwt);
            if(jwt!=null){
                String inputEncodedText = jwt;
                try{                
                    String[] xIn = inputEncodedText.split("\\.");
                    byte b[] = Base64.decode(xIn[1]);
                    String tempPass = new String(b);
                    int chkOccurance = tempPass.lastIndexOf("}");
                    if (chkOccurance < 1) {
                        tempPass += "}";
                    }
                    JSONObject jo;

                    jo = new JSONObject(tempPass);
                    ADFContext.getCurrent().getSessionScope().put("userName", jo.getString("prn"));
                    System.out.println("userName:"+jo.getString("prn"));
                }
                catch(JSONException e){
                   e.printStackTrace(); 
                }
            }
            else{
                System.out.println("inside else user TESTREP");
                ADFContext.getCurrent().getSessionScope().put("userName", "TESTREP");
            }
                SessionState sessionState = (SessionState)JSFUtils.getManagedBeanValue("sessionScope.SessionState");
                if(sessionState==null){
                    sessionState = new SessionState();
                }
                sessionState.parseRootMenu();
                JSFUtils.setManagedBeanValue("sessionScope.SessionState", sessionState);
                
                String groupNodeId = "AdminModule";
                String itemNodeId =  sessionState.getHomePage();
                if(ADFContext.getCurrent().getSessionScope().get("selectedItemId")==null){
                    ADFContext.getCurrent().getSessionScope().put("selectedGroupId", groupNodeId);
                    ADFContext.getCurrent().getSessionScope().put("selectedItemId", itemNodeId);
                    ADFContext.getCurrent().getSessionScope().put("disableGoHome", "N");
                    if (groupNodeId.equalsIgnoreCase(itemNodeId)){
                        ADFContext.getCurrent().getSessionScope().put("hideStrip", true);
                        ADFContext.getCurrent().getSessionScope().put("hideStripToggle", true);
                    } else {
                        ADFContext.getCurrent().getSessionScope().put("hideStrip", false);
                        ADFContext.getCurrent().getSessionScope().put("hideStripToggle", false);
                    }
                }  
                System.out.println("called at last");
    }
}
}
