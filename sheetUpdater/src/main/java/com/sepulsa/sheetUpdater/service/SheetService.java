package com.sepulsa.sheetUpdater.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.sepulsa.sheetUpdater.constant.AppConstant;
import com.sepulsa.sheetUpdater.entity.Sheet;
import com.sepulsa.sheetUpdater.object.Content;
import com.sepulsa.sheetUpdater.object.SheetDefinition;
import com.sepulsa.sheetUpdater.object.SheetDefinitionDetail;
import com.sepulsa.sheetUpdater.object.SheetRowValues;
import com.sepulsa.sheetUpdater.object.WebHook;
import com.sepulsa.sheetUpdater.repository.SheetRepository;
import com.sepulsa.sheetUpdater.util.DateTool;
import com.sepulsa.sheetUpdater.util.ReflectionUtil;
import com.sepulsa.sheetUpdater.util.StringTool;

@Component
public class SheetService {
	private Logger log = Logger.getLogger(SheetService.class);
	
	/** Sheet repository */
	@Autowired
	private SheetRepository sheetRepo;
	
	@Autowired
	private JsonService jsonService;
	
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
    	
    	log.info("Default Port :"+AppConstant.DEFAULT_PORT);
    	if(appPort == null || "-1".equals(appPort)) {
    		log.info("appPort is empty or -1, use default port instead");
    		appPort = Integer.toString(AppConstant.DEFAULT_PORT);
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
	        this.sheet = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
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
    
    private Map<String,SheetRowValues> convertRowValuesToMap (List<List<Object>> rowList) throws IOException {
    	Map<String,SheetRowValues> valuesMap = new HashMap<String,SheetRowValues>();
    	int rowNum = 0;
    	for(List<Object> cols : rowList) {
    		SheetRowValues rowVal = new SheetRowValues();
    		rowVal.setColListValues(cols);
    		rowVal.setRowNum(rowNum);
    		valuesMap.put((String) cols.get(0), rowVal);
    		rowNum++;
    	}
    	return valuesMap;
    }
    
    private Content getStoryChanges(List<Content> changes) {      
        // search changes with kind story
        for(Content change : changes) {
        	if(AppConstant.KIND_STORY.equals(change.getKind())) {
        		return change;
        	}
        }        
        return null;
    }
    
    private UpdateValuesResponse writeToSheet(Sheets service, String writeRange, List<List<Object>> rowValues) throws IOException {
        ValueRange vr = new ValueRange().setValues(rowValues).setMajorDimension("ROWS");
        return service.spreadsheets().values()
                .update(spreadSheetId, writeRange, vr)
                .setValueInputOption("RAW")
                .execute();
    }
    
    private List<List<Object>> moveStory(WebHook webHook,List<List<Object>> rowValues, Map<String,SheetRowValues> rowValuesMap) {
    	Content storyChanges = getStoryChanges(webHook.getChanges());
    	
    	String storyId = webHook.getPrimaryResources().get(0).getId();
    	String afterId = storyChanges.getNewValues().getAfterId();
    	String beforeId = storyChanges.getNewValues().getBeforeId();
    	SheetRowValues rowValue = rowValuesMap.get(storyId);
    	int rowNum = rowValue.getRowNum();
    	
    	log.info("Story row position : "+rowNum);
    	// Placed on the top of the list in tracker
    	if(StringTool.isEmpty(afterId) && !StringTool.isEmpty(beforeId)) {
    		log.info("Move into first position");
    		// Placed at the first index
    		rowValues.add(0,rowValue.getColListValues());
			// Remove current position
    		rowNum++;
			rowValues.remove(rowNum);
		// Placed on the bottom of the list in tracker
    	} else if (!StringTool.isEmpty(afterId) && StringTool.isEmpty(beforeId)) {
    		log.info("Move into last position");
    		// Placed at the last index
    		rowValues.add(rowValue.getColListValues());
			// Remove current position
			rowValues.remove(rowNum);
    	// Placed in the middle, between story
    	} else if (!StringTool.isEmpty(afterId) && !StringTool.isEmpty(beforeId)) {
    		log.info("Move after id "+afterId);
    		SheetRowValues afterIdStory = rowValuesMap.get(afterId);
    		int position = afterIdStory.getRowNum() + 1;

    		// Placed after / below afterId
    		rowValues.add(position,rowValue.getColListValues());			
    		// Move up, current position located below the target position
    		if(rowNum >= position) {
    			rowNum++;
    		}
			// Remove current position
			rowValues.remove(rowNum);
    	} else {
    		log.info("No afterId or beforeId defined, not moving anything"	);
    	}
    	log.debug(rowValues);
    	return rowValues;
    }
    
    private String getRangeFromSheetDefinition(SheetDefinition sheetDefinition, Integer startRow,Boolean allRow) {
    	List<SheetDefinitionDetail> sheetDefinitionDetails = sheetDefinition.getSheetDefinitionDetailListSorted();
    	String endColumn = StringTool.getCharForNumber(sheetDefinitionDetails.size());
    	String readRange = sheetName+"!A"+startRow+":"+endColumn;
    	if(!allRow) {
    		// example , A1:F1 , only row 1
    		readRange += startRow;
    	}
    	return readRange;
    }
    
    @SuppressWarnings("unchecked")
	private List<Object> fillColumnValue(List<SheetDefinitionDetail> sheetDefinitionDetails, List<Object> colList, WebHook webHook) {
        ReflectionUtil rf = new ReflectionUtil(webHook);
        List<Content> changes = webHook.getChanges();
        Content content = getStoryChanges(changes);
        Object date = null;
        
        int index = 0;
    	for(SheetDefinitionDetail sdd : sheetDefinitionDetails) {
			if(StringTool.inArray(sdd.getFieldName(), AppConstant.PIVOTAL_FIELD_PRIMARY_RES)) {
				rf.setObject(webHook.getPrimaryResources().get(0));
			} else if(StringTool.inArray(sdd.getFieldName(), AppConstant.PIVOTAL_FIELD_PROJECT)) {
				rf.setObject(webHook.getProject());
			} else if(StringTool.inArray(sdd.getFieldName(), AppConstant.PIVOTAL_FIELD_MAIN)) {			
				rf.setObject(webHook);
			} else if(StringTool.inArray(sdd.getFieldName(), AppConstant.PIVOTAL_FIELD_DATETIME)) {
				rf.setObject(content.getNewValues());
				String classDateFieldName = rf.getFieldNameFromAnnotation(rf.getObject(), 
						JsonProperty.class, sdd.getFieldName());
				if(rf.getFieldValue(classDateFieldName)!= null) {
					Date insertDate = new Date((Long)rf.getFieldValue(classDateFieldName));
				    date = StringTool.replaceEmpty(DateTool.getDateDMYHHMM(insertDate),"-");
				}
			// GLobally on NEW VALUES
			} else {
				rf.setObject(content.getNewValues());
			}
			
			String classFieldName = rf.getFieldNameFromAnnotation(rf.getObject(), 
					JsonProperty.class, sdd.getFieldName());
			// null means no field passed from request, it can valued by empty string that means something changed to empty
			if(rf.getFieldValue(classFieldName) != null) {
				if(date != null) {
					colList.set(index,date); 
				} else {
					// The value is collection, for example like labels
					if(rf.getFieldValue(classFieldName) instanceof Collection<?>) {
						List<Object> valueList = (List<Object>) rf.getFieldValue(classFieldName);
						colList.set(index,StringTool.replaceEmpty(StringTool.joinListDelimited(valueList,","),"-"));
					} else {
						colList.set(index,StringTool.replaceEmpty(rf.getFieldValue(classFieldName),"-")); 	
					}
				}
			}

			index++;
    	}
    	return colList;
    }
    
    
    private Boolean writeCheckEmptyHeader (Sheets service, SheetDefinition sheetDefinition) throws IOException {
    	String readRange = getRangeFromSheetDefinition(sheetDefinition,1,false);
    	
        if(sheetDefinition.getSheetIsEmpty()) {
            List<List<Object>> rowValues = new ArrayList<List<Object>>();
        	List<Object> colValues = new ArrayList<Object>();
        	for(SheetDefinitionDetail sdd : sheetDefinition.getSheetDefinitionDetailListSorted()) {
        		colValues.add(sdd.getViewName());
        	}
        	rowValues.add(colValues);
        	writeToSheet(service, readRange, rowValues);
        	return true;
        }
        return false;
    }
    
    public SheetDefinition getCurrentSheetDefinition(String sheetMappingJson) throws IOException {
    	Sheet sheet = sheetRepo.findOne(new Long(1));
    	// Save if empty
    	if(sheet == null) {
    		sheet = new Sheet();
    		sheet.setId(new Long(1));
    		sheet.setSheetId(spreadSheetId);
    		sheet.setStructure(sheetMappingJson);
    		sheetRepo.save(sheet);
    	}
    	
    	Sheets service = getSheetsService();
        List<List<Object>> rowValues = getRangeValues(service, "!A1:B1");
    	
    	// Update if different, it means there's change in the sheetMapping.json
    	// and sheet must be empty, if not, nothing changed
    	if(!sheetMappingJson.equals(sheet.getStructure()) && rowValues.isEmpty()) {
    		sheet.setStructure(sheetMappingJson);
    		sheetRepo.save(sheet);
    	} 
    	
    	SheetDefinition sd = jsonService.convertToObject(sheet.getStructure(), SheetDefinition.class);
    	if(rowValues.isEmpty()) {
    		sd.setSheetIsEmpty(true);
    	}
    	return sd;
    }
    
    public void addUpdateStory(WebHook webHook, SheetDefinition sheetDefinition) throws IOException {
    	String kind = webHook.getKind();
    	Sheets service = getSheetsService();
    	
    	// Write header if not yet write in sheet
    	writeCheckEmptyHeader(service, sheetDefinition);
    	
    	List<SheetDefinitionDetail> sheetDefinitionDetails = sheetDefinition.getSheetDefinitionDetailListSorted();
    	String readRange = getRangeFromSheetDefinition(sheetDefinition, 2,true);
    	String storyId = webHook.getPrimaryResources().get(0).getId();
    	
        List<List<Object>> rowValues = getRangeValues(service, readRange);
        List<Content> changes = webHook.getChanges();
        List<Object> colList = null;
        
    	Map<String,SheetRowValues> rowValuesMap = convertRowValuesToMap(rowValues);

        Content content = getStoryChanges(changes);
        // for update activity
    	SheetRowValues updatedStory = rowValuesMap.get(storyId);

        if(AppConstant.ACTIVITY_CREATE.equals(kind)) {
        	Object [] newDataArray =  new Object[sheetDefinitionDetails.size()];
        	Arrays.fill(newDataArray, "");
        	colList = Arrays.asList(newDataArray);
            colList = fillColumnValue(sheetDefinitionDetails, colList, webHook);
            
        	// placed at the top of icebox, after the latest story in current / backlog
            if(!StringTool.isEmpty(content.getNewValues().getAfterId())) {

            	SheetRowValues afterIdSheet = rowValuesMap.get(content.getNewValues().getAfterId());
            	int newStoryPosition = afterIdSheet.getRowNum() + 1;
            	rowValues.add(newStoryPosition,colList);
            // no afterId defined, which means no story in current / backlog
            } else {
                rowValues.add(0,colList);
            }

        } else if (AppConstant.ACTIVITY_UPDATE.equals(kind)) {
        	colList = updatedStory.getColListValues();
        	log.info("OLD VALUES : "+colList);
            colList = fillColumnValue(sheetDefinitionDetails, colList, webHook);
        	log.info("NEW VALUES : "+colList);
        	
        	// Update the values
        	updatedStory.setColListValues(colList);
        	rowValues.set(updatedStory.getRowNum(), colList);
        	rowValuesMap.put(storyId, updatedStory);
        	// if there is an update to start the project, this story will move to the top
        	rowValues = moveStory(webHook, rowValues, rowValuesMap);
        }


        writeToSheet(service,readRange,rowValues);
    	
    }

    
    public void moveStory(WebHook webHook, SheetDefinition sheetDefinition) throws IOException {
    	Sheets service = getSheetsService();
        String readRange =  getRangeFromSheetDefinition(sheetDefinition, 2,true);
        List<List<Object>> rowValues = getRangeValues(service, readRange);
    	Map<String,SheetRowValues> rowValuesMap = convertRowValuesToMap(rowValues);

    	rowValues = moveStory(webHook, rowValues, rowValuesMap);
    	writeToSheet(service,readRange,rowValues);
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
        range = "TestSheet!A3"+lastRow+":E";
        
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