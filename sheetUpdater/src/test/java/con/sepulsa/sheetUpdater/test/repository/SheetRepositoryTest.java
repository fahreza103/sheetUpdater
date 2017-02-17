package con.sepulsa.sheetUpdater.test.repository;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;



import com.sepulsa.sheetUpdater.entity.Sheet;
import com.sepulsa.sheetUpdater.repository.SheetRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
public class SheetRepositoryTest {
	
    @Autowired
    private TestEntityManager em;
    
    @Autowired
    private SheetRepository sheetRepo;
	
    @Test
    public void testFindBySheetId() {
    	Sheet sheet = new Sheet();
    	sheet.setSheetId("testSheetId");
    	sheet.setStructure("testStructure");
        em.persist(sheet);
        
        Sheet result = sheetRepo.findBySheetId("testSheetId");
        assertEquals("testSheetId", result.getSheetId());
    }
    
    
//    @Test
//    public void contextLoads() {}
//
//    @Test
//    public void testSheetUpdate() throws Exception {
//        ResultActions result = this.mockMvc.perform(get("/Callback"));
//        //result.accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
//        result.andExpect(status().isOk());
//        //result.andExpect(content().contentType("application/json"));
//
//    }
    
    @After
    public void tearDown() {
    	this.em = null;
    	this.sheetRepo = null;
    }
}
