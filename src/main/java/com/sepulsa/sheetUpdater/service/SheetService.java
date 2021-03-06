package com.sepulsa.sheetUpdater.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
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
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.MoveDimensionRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.sepulsa.sheetUpdater.constant.AppConstant;
import com.sepulsa.sheetUpdater.object.ApiResponse;
import com.sepulsa.sheetUpdater.object.Content;
import com.sepulsa.sheetUpdater.object.SheetDefinition;
import com.sepulsa.sheetUpdater.object.SheetDefinitionDetail;
import com.sepulsa.sheetUpdater.object.SheetRowValues;
import com.sepulsa.sheetUpdater.object.WebHook;
import com.sepulsa.sheetUpdater.util.DateTool;
import com.sepulsa.sheetUpdater.util.ReflectionUtil;
import com.sepulsa.sheetUpdater.util.StringTool;

@Component
public class SheetService {
	private Logger log = Logger.getLogger(SheetService.class);
	
	/** Sheet repository */
//	@Autowired
//	private SheetRepository sheetRepo;
	
	@Autowired
	private JsonService jsonService;
	
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-sepulsa-sheetUpdater");


    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    
    private Sheets sheet;
    
    @Value("${server.port}")
    private String appPort;
    

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS);


    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     * @throws GeneralSecurityException 
     */
    public  Credential authorize() throws IOException, GeneralSecurityException {
    	
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

        FileDataStoreFactory DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        // Build flow and trigger user authorization request.
        if(HTTP_TRANSPORT == null) HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
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
     * @throws GeneralSecurityException 
     */
    public Sheets getSheetsService() throws IOException {
    	try {
			if(HTTP_TRANSPORT == null) HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			if(sheet == null) {
			    Credential credential = authorize();
			    this.sheet = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
			            .setApplicationName(APPLICATION_NAME)
			            .build();
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
    	return sheet;
    }
    
    private  List<List<Object>> getRangeValues(Sheets service , String spreadSheetId, String range) throws IOException {
        ValueRange response = service.spreadsheets().values()
                .get(spreadSheetId, range)
                .execute();
            List<List<Object>> values = response.getValues();
            if(values == null) {
            	values = new ArrayList<List<Object>>();
            }
            return values;
    }
    
    private Map<String,SheetRowValues> convertRowValuesToMap (SheetDefinition sd, List<List<Object>> rowList) throws IOException {
    	int id = 0;
    	int rowNum = 0;

    	Map<String,SheetRowValues> valuesMap = new HashMap<String,SheetRowValues>();
    	for(SheetDefinitionDetail sdd : sd.getSheetDefinitionDetailListSorted()) {
    		if(sdd.getFieldName().equals("id")) {
    			id = StringTool.getAlphabetPosition('A', sdd.getColumn().charAt(0));
    			break;
    		}
    	}
    	
    	for(List<Object> cols : rowList) {
    		SheetRowValues rowVal = new SheetRowValues();
    		rowVal.setColListValues(cols);
    		rowVal.setRowNum(rowNum);
    		if(id >= cols.size()) {
    			valuesMap.put("-1", rowVal);    			
    		} else {
    			valuesMap.put((String) cols.get(id), rowVal);
    		}
    		rowNum++;
    	}
    	return valuesMap;
    }
    
    public Content getStoryChanges(List<Content> changes) {      
        // search changes with kind story
        for(Content change : changes) {
        	if(AppConstant.KIND_STORY.equals(change.getKind())) {
        		return change;
        	}
        }        
        return null;
    }
    
    private UpdateValuesResponse writeToSheet(Sheets service,String spreadSheetId, String writeRange, List<List<Object>> rowValues) throws IOException {
        ValueRange vr = new ValueRange().setValues(rowValues).setMajorDimension("ROWS");
        return service.spreadsheets().values()
                .update(spreadSheetId, writeRange, vr)
                .setValueInputOption("RAW")
                .execute();
    }
    
	public Integer getSheetId(Spreadsheet spreadSheet) {
		Integer sheetId = null;
		com.google.api.services.sheets.v4.model.Sheet sheet = spreadSheet.getSheets().get(0);
		sheetId = sheet.getProperties().getSheetId();
		return sheetId;
	}
    
    public BatchUpdateSpreadsheetResponse moveRow(Spreadsheet spreadsheet, Integer startIndex,
    			Integer endIndex, Integer destinationIndex) throws IOException {
    	Sheets service = getSheetsService();
		List<Request> requests = new ArrayList<>();
		MoveDimensionRequest mdr = new MoveDimensionRequest();
		
		DimensionRange drange = new DimensionRange();
		drange.setDimension("ROWS");
		drange.setStartIndex(startIndex);
		drange.setEndIndex(endIndex);
		drange.setSheetId(getSheetId(spreadsheet));
		
		mdr.setSource(drange);
		mdr.setDestinationIndex(destinationIndex);

		requests.add(new Request().setMoveDimension(mdr));

		BatchUpdateSpreadsheetRequest body =
		        new BatchUpdateSpreadsheetRequest().setRequests(requests);
		BatchUpdateSpreadsheetResponse response =
				service.spreadsheets().batchUpdate(spreadsheet.getSpreadsheetId(), body).execute();
		return response;
    }
    
    private Integer moveStory(Spreadsheet spreadsheet,WebHook webHook, Map<String,SheetRowValues> rowValuesMap) throws IOException {
    	Content storyChanges = getStoryChanges(webHook.getChanges());
    	
    	String storyId = webHook.getPrimaryResources().get(0).getId();
    	String afterId = storyChanges.getNewValues().getAfterId();
    	String beforeId = storyChanges.getNewValues().getBeforeId();
    	SheetRowValues currentIdStory = rowValuesMap.get(storyId);
		SheetRowValues afterIdStory = rowValuesMap.get(afterId);
       	SheetRowValues beforeIdStory = rowValuesMap.get(beforeId);
    	
    	log.info("Story row position : "+currentIdStory.getRowNum());
    	// Used move row rather than swaping update data
    	if(StringTool.isEmpty(afterId) && !StringTool.isEmpty(beforeId)) {
    		log.info("Move beforeId "+beforeId);
    		moveRow(spreadsheet, currentIdStory.getRowNum(), currentIdStory.getRowNum()+1, beforeIdStory.getRowNum());
        	
    		// Placed at the first index
//    		rowValues.add(0,rowValue.getColListValues());
//			// Remove current position
//    		rowNum++;
//			rowValues.remove(rowNum);
		// Placed on the bottom of the list in tracker
    	} else if (!StringTool.isEmpty(afterId) && StringTool.isEmpty(beforeId)) {
    		log.info("Move afterId "+afterId);
    		moveRow(spreadsheet, currentIdStory.getRowNum(), currentIdStory.getRowNum()+1, afterIdStory.getRowNum()+1);
//    		// Placed at the last index
//    		rowValues.add(rowValue.getColListValues());
//			// Remove current position
//			rowValues.remove(rowNum);
//    	// Placed in the middle, between story
    	} else if (!StringTool.isEmpty(afterId) && !StringTool.isEmpty(beforeId)) {
    		log.info("Move after id "+afterId+ "| before id "+beforeId);

    		moveRow(spreadsheet, currentIdStory.getRowNum(), currentIdStory.getRowNum()+1, afterIdStory.getRowNum()+1);
//    		int position = afterIdStory.getRowNum() + 1;

//    		// Placed after / below afterId
//    		rowValues.add(position,rowValue.getColListValues());			
//    		// Move up, current position located below the target position
//    		if(rowNum >= position) {
//    			rowNum++;
//    		}
//			// Remove current position
//			rowValues.remove(rowNum);
    	} else {
    		log.info("No afterId or beforeId defined, not moving anything"	);
    	}
    	return currentIdStory.getRowNum();
    }
    
    private String getRangeFromSheetDefinition(SheetDefinition sheetDefinition, Integer startRow,Boolean allRow) {
    	List<SheetDefinitionDetail> sheetDefinitionDetails = sheetDefinition.getSheetDefinitionDetailListSorted();
//    	String startColumn = sheetDefinitionDetails.get(0).getColumn();
    	String startColumn = "A";
    	String endColumn = sheetDefinitionDetails.get(sheetDefinitionDetails.size()-1).getColumn();
    	String readRange = sheetDefinition.getSpreadSheetName()+"!"+startColumn+startRow+":"+endColumn;
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
				// use prefix project_
				String[] projectFieldNames = sdd.getFieldName().split("_");
				sdd.setFieldName(projectFieldNames.length > 1 ? projectFieldNames[1] : projectFieldNames[0]);
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
				// Last field is empty, list of column returned from google sheet is null rather than empty index list
				// ex : in definition has 6 column defined,but in sheet the last column is empty, so the returned list
				// is only 5 rather than 6, it will throw IndexOutOfBoundException
				if(index >= colList.size()) {
					colList.add(new Object());
				}
				
				// Uppercase A start in 65 , A should be index 0
				int colPos = StringTool.getAlphabetPosition('A',sdd.getColumn().charAt(0));
				
				if(date != null) {
					colList.set(colPos,date); 
				} else {
					// The value is collection, for example like labels
					if(rf.getFieldValue(classFieldName) instanceof Collection<?>) {
						List<Object> valueList = (List<Object>) rf.getFieldValue(classFieldName);
						colList.set(colPos,StringTool.replaceEmpty(StringTool.joinListDelimited(valueList,","),"-"));
					} else {
						colList.set(colPos,StringTool.replaceEmpty(rf.getFieldValue(classFieldName),"-")); 	
					}
				}
			}

			index++;
    	}
    	return colList;
    }
    
    
    private Boolean writeCheckEmptyHeader (Sheets service, SheetDefinition sheetDefinition) throws IOException {
    	String readRange = getRangeFromSheetDefinition(sheetDefinition,1,false);
    	List<SheetDefinitionDetail> sddList = sheetDefinition.getSheetDefinitionDetailListSorted();
    	
        if(sheetDefinition.getSheetIsEmpty()) {
            List<List<Object>> rowValues = new ArrayList<List<Object>>();
        	List<Object> colValues = new ArrayList<Object>();
        	Object [] newDataArray =  new Object[StringTool.getAlphabetPosition('A',sddList.get(sddList.size()-1).getColumn().charAt(0))+1];
        	Arrays.fill(newDataArray, "");
        	colValues = Arrays.asList(newDataArray);
        	for(SheetDefinitionDetail sdd : sheetDefinition.getSheetDefinitionDetailListSorted()) {
				// Uppercase A start in 65 , A should be index 0
				int colPos = StringTool.getAlphabetPosition('A',sdd.getColumn().charAt(0));
        		colValues.set(colPos,sdd.getViewName());
        	}
        	rowValues.add(colValues);
        	writeToSheet(service, sheetDefinition.getSpreadSheetId(), readRange, rowValues);
        	return true;
        }
        return false;
    }
    
    public SheetDefinition getCurrentSheetDefinition(String sheetMappingJson) throws IOException {
//    	Sheet sheet = sheetRepo.findById(new Long(1));
    	SheetDefinition sd = null;
//    	// Save if empty
//    	if(sheet == null) {
    		sd = jsonService.convertToObject(sheetMappingJson, SheetDefinition.class);
//    		log.info("Sheet is empty, insert into db");
//    		sheet = new Sheet();
//    		sheet.setId(new Long(1));
//    		sheet.setSheetId(sd.getSpreadSheetId());
//    		sheet.setStructure(sheetMappingJson);
//    		sheetRepo.save(sheet);
//    	} else {
//        	sd = jsonService.convertToObject(sheet.getStructure(), SheetDefinition.class);
//    	}
//    	
    	Sheets service = getSheetsService();
        List<List<Object>> rowValues = getRangeValues(service, sd.getSpreadSheetId(), sd.getSpreadSheetName()+"!A1:Z1");
//    	
//    	// Update if different, it means there's change in the sheetMapping.json
//    	// and sheet must be empty, if not, nothing changed
    	if(!sheetMappingJson.equals(rowValues.isEmpty())) {
    		log.info("Header is empty");
//    		sheet.setStructure(sheetMappingJson);
//    		sheetRepo.save(sheet);
      		sd.setSheetIsEmpty(true);
//        	sd = jsonService.convertToObject(sheet.getStructure(), SheetDefinition.class);
    	} 
//    	
//    	if(rowValues.isEmpty()) {
//    		sd.setSheetIsEmpty(true);
//    	}
    	
    	sd.setStartColumn("A");
    	return sd;
    }
    
    public ApiResponse addUpdateStory(WebHook webHook, SheetDefinition sheetDefinition) throws IOException {
    	String kind = webHook.getKind();
    	Sheets service = getSheetsService();
    	
    	// Write header if not yet write in sheet
    	writeCheckEmptyHeader(service, sheetDefinition);
    	
    	List<SheetDefinitionDetail> sheetDefinitionDetails = sheetDefinition.getSheetDefinitionDetailListSorted();
    	String readRange = getRangeFromSheetDefinition(sheetDefinition, 1,true);
    	log.info("readRange : "+readRange);
    	String storyId = webHook.getPrimaryResources().get(0).getId();
    	
        List<List<Object>> rowValues = getRangeValues(service, sheetDefinition.getSpreadSheetId(), readRange);
        log.info("rowValues :"+rowValues);
        
        List<Content> changes = webHook.getChanges();
        List<Object> colList = null;
        
    	Map<String,SheetRowValues> rowValuesMap = convertRowValuesToMap(sheetDefinition,rowValues);
        log.info("rowValuesMap :"+rowValues);

        getStoryChanges(changes);
        // for update activity
    	SheetRowValues updatedStory = rowValuesMap.get(storyId);

        if(AppConstant.ACTIVITY_CREATE.equals(kind)) {
        	Object [] newDataArray =  new Object[StringTool.getAlphabetPosition
        	                                     ('A',sheetDefinitionDetails.get(sheetDefinitionDetails.size()-1).getColumn().charAt(0))+1];
        	Arrays.fill(newDataArray, "");
        	colList = Arrays.asList(newDataArray);
            colList = fillColumnValue(sheetDefinitionDetails, colList, webHook);
            
        	// placed at the top of icebox, after the latest story in current / backlog
//            if(!StringTool.isEmpty(content.getNewValues().getAfterId())) {
//
//            	SheetRowValues afterIdSheet = rowValuesMap.get(content.getNewValues().getAfterId());
//            	int newStoryPosition = afterIdSheet.getRowNum() + 1;
//            	rowValues.add(newStoryPosition,colList);
//            // no afterId defined, which means no story in current / backlog
//            } else {
            // Placed at the bottom
            rowValues.add(colList);
//            }

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
//        	rowValues = moveStory(webHook, rowValues, rowValuesMap);
        }

        log.info("Write to spreadsheetId" +sheetDefinition.getSpreadSheetId()+" readRange : "+readRange);
        UpdateValuesResponse response = writeToSheet(service,sheetDefinition.getSpreadSheetId(),readRange,rowValues);
        log.info("response : "+response);
    	return getApiResponse(response,rowValues);
    }

    
    public ApiResponse moveStory(WebHook webHook, SheetDefinition sheetDefinition) throws IOException {
    	Sheets service = getSheetsService();
        String readRange =  getRangeFromSheetDefinition(sheetDefinition, 1,true);
        List<List<Object>> rowValues = getRangeValues(service, sheetDefinition.getSpreadSheetId(),readRange);
    	Map<String,SheetRowValues> rowValuesMap = convertRowValuesToMap(sheetDefinition,rowValues);
    	
    	Spreadsheet spreadsheet = service.spreadsheets().get(sheetDefinition.getSpreadSheetId()).execute();

    	Integer rowUpdate = moveStory(spreadsheet,webHook, rowValuesMap);
    	
    	UpdateValuesResponse response = new UpdateValuesResponse();
    	response.setSpreadsheetId(spreadsheet.getSpreadsheetId());
    	response.setUpdatedRows(rowUpdate);
    	
    	return getApiResponse(response,rowValues);
    }
    
    private ApiResponse getApiResponse(UpdateValuesResponse response,List<List<Object>> rowValues) {
    	ApiResponse apiResponse = new ApiResponse();
    	if(response != null) {
			apiResponse.setStatus(true);
			apiResponse.setMessage("SUCESS");
			apiResponse.setSpreadsheetId(response.getSpreadsheetId());
			apiResponse.setUpdatedCells(response.getUpdatedCells());
			apiResponse.setUpdatedColumns(response.getUpdatedColumns());
			apiResponse.setUpdatedRange(response.getUpdatedRange());
			apiResponse.setUpdatedRows(response.getUpdatedRows());
			apiResponse.setUpdatedData(rowValues);
		} else {
	
			apiResponse.setStatus(false);
			apiResponse.setMessage("##### ERROR, failed to get response from google");
		}
    	return apiResponse;
    }


	public String getAppPort() {
		return appPort;
	}

	public void setAppPort(String appPort) {
		this.appPort = appPort;
	}
    

}
