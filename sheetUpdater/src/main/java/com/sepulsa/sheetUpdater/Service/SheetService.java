package com.sepulsa.sheetUpdater.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.sepulsa.sheetUpdater.Object.Content;
import com.sepulsa.sheetUpdater.Object.WebHook;
import com.sepulsa.sheetUpdater.util.DateTool;

@Component
public class SheetService {
	private Logger log = Logger.getLogger(SheetService.class);
	
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-sepulsa-sheetUpdater");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    
    private Sheets sheet;
    
    private final int DEFAULT_PORT = 8181;
    
    @Value("${server.port}")
    private String appPort;
    
    @Value("${spreadsheet.id}")
    private String spreadSheetId;
    
    @Value("${spreadsheet.sheet.name}")
    private String sheetName;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public  Credential authorize() throws IOException {
    	
    	log.info("Default Port :"+DEFAULT_PORT);
    	if(appPort == null || "-1".equals(appPort)) {
    		log.info("appPort is empty or -1, use default port instead");
    		appPort = Integer.toString(DEFAULT_PORT);
    	}
    	
        // Load client secrets.
        InputStream in =
        		SheetService.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")          
                .build();
        
        LocalServerReceiver localReceiver = new LocalServerReceiver.
                Builder().setHost("localhost").setPort(Integer.parseInt(appPort)).build();
        
        log.info(appPort);
        log.info(localReceiver);
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, localReceiver).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public Sheets getSheetsService() throws IOException {
    	if(sheet == null) {
	        Credential credential = authorize();
	        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
	                .setApplicationName(APPLICATION_NAME)
	                .build();
    	}
    	return sheet;
    }
    
    private  List<List<Object>> getRangeValues(Sheets service , String range) throws IOException {
        ValueRange response = service.spreadsheets().values()
                .get(spreadSheetId, range)
                .execute();
            List<List<Object>> values = response.getValues();
            if(values == null) {
            	values = new ArrayList<List<Object>>();
            }
            return values;
    }
    
    public void addStory(WebHook webHook) throws IOException {
    	Sheets service = getSheetsService();
        String readRange = sheetName+"!A2:F";
        
        List<List<Object>> values = getRangeValues(service, readRange);
        long lastRow = values.size()+2;
        String writeRange = sheetName+"!A"+lastRow+":F";
        log.info("write range" + writeRange);
        
        List<List<Object>> rowList = new ArrayList<List<Object>>();
        List<Object> colList = new ArrayList<Object>();
        
        List<Content> changes = webHook.getChanges();
        Content content = changes.get(0);
        Content content2 = changes.get(1);
        
        Date updateDate = new Date(content.getNewValues().getUpdatedAt());
        
        colList.add(content.getNewValues().getStoryId());
        colList.add(content2.getNewValues().getName());
        colList.add(webHook.getPerformedBy().getName());
        colList.add(content2.getNewValues().getDescription());
        colList.add(webHook.getMessage());
        colList.add(DateTool.getDateDMY(updateDate));
        log.info("write row :"+colList);
        
        rowList.add(colList);

        ValueRange vr = new ValueRange().setValues(rowList).setMajorDimension("ROWS");
        service.spreadsheets().values()
                .update(spreadSheetId, writeRange, vr)
                .setValueInputOption("RAW")
                .execute();
    }
    
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
    	SheetService ss = new SheetService();
        Sheets service = ss.getSheetsService();

        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
        String spreadsheetId = "1fwCkPcAN2ZnsY-ytczC-L-IrKBm_kg-oJAPZMoekqbI";
        String range = "TestSheet!A2:E";
        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        List<List<Object>> values = response.getValues() == null ? new ArrayList<List<Object>>() : response.getValues() ;
        int lastRow = values.size()+2;
        range = "TestSheet!A"+lastRow+":E";
        
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> colList = new ArrayList<Object>();
        colList.add("tes1");
        colList.add("tes2");
        colList.add("tes3");
        colList.add("tes4");
        colList.add("tes5");
        dataList.add(colList);
        
        ValueRange vr = new ValueRange().setValues(dataList).setMajorDimension("ROWS");
        service.spreadsheets().values()
                .update(spreadsheetId, range, vr)
                .setValueInputOption("RAW")
                .execute();
    }

}
