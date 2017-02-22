package com.sepulsa.sheetUpdater.test.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.sepulsa.sheetUpdater.constant.AppConstant;
import com.sepulsa.sheetUpdater.object.ApiResponse;
import com.sepulsa.sheetUpdater.object.SheetDefinition;
import com.sepulsa.sheetUpdater.object.SheetDefinitionDetail;
import com.sepulsa.sheetUpdater.object.WebHook;
import com.sepulsa.sheetUpdater.service.JsonService;
import com.sepulsa.sheetUpdater.service.SheetService;
import com.sepulsa.sheetUpdater.util.FileTool;
import com.sepulsa.sheetUpdater.util.StringTool;

@RunWith(SpringRunner.class)
public class SheetServiceTest {

	private static final String UNIT_TEST_SHEET_FILE = "spreadsheet_testId.txt";
	private static final String UNIT_TEST_SPREADSHEET_SHEET_NAME = "TestSheet";
	private static final String[] STORY_IDS = new String[]{"100000000","200000000","300000000"};
	private Sheets googleSheet;
	private SheetService sheetService;
	private JsonService jsonService;
	private SheetDefinition sheetDefinition;
	private WebHook webHook;
	private Spreadsheet spreadsheet;

	@Before
	public void setUp() throws IOException {
		this.sheetService = new SheetService();
		this.jsonService = new JsonService();
		this.googleSheet = sheetService.getSheetsService();

		String webHookStr = FileTool.getStrFileContent("dummyRequest.json");
		this.webHook = jsonService.convertToObject(webHookStr, WebHook.class);
		
		// create spreadsheet for unit testing
    	String spreadSheetTestId = FileTool.getStrFileContent(UNIT_TEST_SHEET_FILE);
    	if(StringTool.isEmpty(spreadSheetTestId)) {
    		this.spreadsheet = createSpreadSheet();
    	} else {
    		try {
    			this.spreadsheet = googleSheet.spreadsheets().get(spreadSheetTestId).execute();
    		// Not found
    		} catch (Exception e) {
    			this.spreadsheet = createSpreadSheet();
    		}
    	}
		
		// initialize SheetMapping
    	this.sheetDefinition = new SheetDefinition();
    	sheetDefinition.setSheetIsEmpty(true);
    	sheetDefinition.setSpreadSheetId(spreadsheet.getSpreadsheetId());
    	sheetDefinition.setSpreadSheetName(UNIT_TEST_SPREADSHEET_SHEET_NAME);
    	sheetDefinition.setStartColumn("A");
    	
    	List<SheetDefinitionDetail> definitionDetails = new ArrayList<SheetDefinitionDetail>();
    	SheetDefinitionDetail sdd = new SheetDefinitionDetail();
    	sdd.setColumn("A");
    	sdd.setFieldName("id");
    	sdd.setViewName("Story Id");
    	definitionDetails.add(sdd);
    	
    	SheetDefinitionDetail sdd2 = new SheetDefinitionDetail();
    	sdd2.setColumn("B");
    	sdd2.setFieldName("name");
    	sdd2.setViewName("Story Name");
    	definitionDetails.add(sdd2);
    	sheetDefinition.setSheetDefinitionDetailList(definitionDetails);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Spreadsheet createSpreadSheet() throws IOException {
    	Spreadsheet spreadsheet = new Spreadsheet();
    	com.google.api.services.sheets.v4.model.Sheet sheets = new com.google.api.services.sheets.v4.model.Sheet();
    	SheetProperties sheetProp = new SheetProperties();
    	sheetProp.setTitle("TestSheet");
    	sheets.setProperties(sheetProp);
    	
    	List listsheet = new ArrayList();
    	listsheet.add(sheets);
    	
    	SpreadsheetProperties spreadSheetProp = new SpreadsheetProperties();
    	spreadSheetProp.setTitle("Spreadsheet For Unit Test");
    	
    	spreadsheet.setSheets(listsheet);
    	spreadsheet.setProperties(spreadSheetProp);
    	
    	spreadsheet = googleSheet.spreadsheets().create(spreadsheet).execute();
	
    	FileTool.writeFile("spreadsheet_testId.txt", spreadsheet.getSpreadsheetId());	
    	return spreadsheet;
	}

	@Test
	public void testAuthorizedShouldReturnCredentials() throws IOException, GeneralSecurityException {
		Credential credential = sheetService.authorize();
		assertNotNull(credential);
		
		// Test port, should return based on appConstant
		assertEquals(Integer.toString(AppConstant.DEFAULT_PORT) , sheetService.getAppPort());
	}
	
	@Test
	public void testGetSheetService() throws IOException, GeneralSecurityException {
		sheetService.setAppPort("8181");
		Sheets googleSheet = sheetService.getSheetsService();
		assertNotNull(googleSheet);
		assertEquals("8181" , sheetService.getAppPort());
	}
	

	
	@Test
	public void testAddUpdateMoveStory() throws IOException {
		// Test add story
		webHook.getPrimaryResources().get(0).setId(STORY_IDS[0]);
		webHook.getPrimaryResources().get(0).setName("test1");
		ApiResponse response = sheetService.addUpdateStory(webHook, sheetDefinition);
		assertNotNull("response should not null", response);
		assertEquals("response flag should be 1",AppConstant.FLAG_SUCCESS, response.getStatus());
		assertEquals("updated row shoule be 1",new Integer(1),response.getUpdatedRows());
		
		webHook.getPrimaryResources().get(0).setId(STORY_IDS[1]);
		webHook.getPrimaryResources().get(0).setName("test2");
		response = sheetService.addUpdateStory(webHook, sheetDefinition);
		assertEquals("response flag should be 1",AppConstant.FLAG_SUCCESS, response.getStatus());
		assertEquals("updated row shoule be 2",new Integer(2),response.getUpdatedRows());
		
		webHook.getPrimaryResources().get(0).setId(STORY_IDS[2]);
		webHook.getPrimaryResources().get(0).setName("test3");
		response = sheetService.addUpdateStory(webHook, sheetDefinition);
		assertEquals("response flag should be 1",AppConstant.FLAG_SUCCESS, response.getStatus());
		assertEquals("updated row shoule be 3",new Integer(3),response.getUpdatedRows());
		
		// Test update story
		webHook.setKind(AppConstant.ACTIVITY_UPDATE);
		webHook.getPrimaryResources().get(0).setId("300000000");
		webHook.getPrimaryResources().get(0).setName("test_change01");
		response = sheetService.addUpdateStory(webHook, sheetDefinition);
		assertEquals("response flag should be 1",AppConstant.FLAG_SUCCESS, response.getStatus());
		
		List<List<Object>> dataRow = response.getUpdatedData();
		List<Object> dataCol = dataRow.get(0);
		assertEquals("test_change01", dataCol.get(1));
		
		// Delete all values
		googleSheet.spreadsheets().values().clear(spreadsheet.getSpreadsheetId(), "!A1:B", new ClearValuesRequest()).execute();
	}
	
	@After
	public void tearDown() throws IOException {

		googleSheet = null;
		sheetService = null;
		jsonService = null;
		sheetDefinition = null;
		webHook = null;
		spreadsheet = null;
	}

}
