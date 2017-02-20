package con.sepulsa.sheetUpdater.test.repository;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sepulsa.sheetUpdater.SheetUpdaterApplication;
import com.sepulsa.sheetUpdater.entity.Sheet;
import com.sepulsa.sheetUpdater.repository.SheetRepository;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes={SheetUpdaterApplication.class})
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
    

    @After
    public void tearDown() {
    	this.em = null;
    	this.sheetRepo = null;
    }
}
