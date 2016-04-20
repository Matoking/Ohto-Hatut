
package ohtuhatut.selenium;

import ohtuhatut.repository.ReferenceListRepository;
import ohtuhatut.repository.ReferenceRepository;
import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.adapter.FluentTest;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
/**
 * 
 * @author tuomokar
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ohtuhatut.Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ReferenceListTest extends FluentTest {
    
    @Autowired
    private ReferenceRepository referenceRepository;
    @Autowired
    private ReferenceListRepository referenceListRepository;

    @Value("${local.server.port}")
    private int serverPort;
    private WebDriver webDriver = new HtmlUnitDriver();

    private String getUrl() {
        return "http://localhost:" + serverPort;
    }
    
    @Before
    public void setUp() {
        // Spring doesn't respect @Transactional decorator,
        // so flush everything manually before every test
        referenceListRepository.deleteAll();
        referenceRepository.deleteAll();
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @Test
    public void mainPageIsShownWithNoSpecifiedAddress() {
        goTo(getUrl());
        assertTrue(pageSource().contains("Welcome to the reference app"));
        assertEquals("References", title());
    }

    @Test
    public void referenceListCreationWorksWithNonEmptyName() {
        getToReferenceListCreationPage();

        fill("#name").with("testList1");
        submit(find("form").first());

        assertTrue(pageSource().contains("testList1"));
        assertTrue(pageSource().contains("No references in the database at the moment for you to add"));

    }

    @Test
    public void referenceListCreationGivesErrorMessageWhenEnteringAnEmptyName() {
        getToReferenceListCreationPage();

        fill("#name").with("");
        submit(find("form").first());

        assertTrue(pageSource().contains("Create a new reference list"));
        assertTrue(pageSource().contains("may not be empty"));
        
    }
    
    @Test
    public void addingReferencesToAReferenceListIsSuccessful() {
        createAReference(1);
        createAReferenceList();
        
        fillSelect("#references").withIndex(0);
        
        submit(find("form").first());
        
        assertTrue(pageSource().contains("testTitle"));
        assertTrue(pageSource().contains("No references in the database at the moment for you to add"));
    }
    
    @Test
    public void linkToExportReferencesAppearsIfReferenceListContainsReferences() {
        createAReference(1);
        createAReferenceList();
        
        assertFalse(pageSource().contains("Export references"));
        
        fillSelect("#references").withIndex(0);
        
        submit(find("form").first());
        
        assertTrue(pageSource().contains("Export references"));
    }
    
    @Test
    public void exportingReferenceListOnlyExportsReferencesInTheList() {
        createAReference(1);
        createAReference(2);
        createAReferenceList();
        
        fillSelect("#references").withIndex(0);
        
        submit(find("form").first());
        submit(find("#export"));
        
        // Only one manual entry
        assertEquals(1, StringUtils.countMatches(pageSource(), "@manual"));
    }
    
    @Test
    public void referenceListsPageShowsExistingReferenceLists() {
        createAReferenceList();
        getToReferenceListsPage();
        
        assertTrue(pageSource().contains("testList1"));          
    }
    
    @Test
    public void referenceListsPageDoesNotShowAnyReferenceListsIfNoneExist() {
        getToReferenceListsPage();
        
        assertTrue(pageSource().contains("No reference lists in the database at the moment"));          
    }
    
    @Test
    public void userCanGetToThePageOfAReferenceListFromThePageShowingAllReferenceLists() {
        createAReferenceList();
        getToReferenceListsPage();
        
        click(find("a", withText("testList1")));
        
        assertTrue(pageSource().contains("No references in the database at the moment for you to add"));
    }
    
    private void createAReferenceList() {
        getToReferenceListCreationPage();

        fill("#name").with("testList1");
        submit(find("form").first());
    }
    
    private void createAReference(int i) {
        getToManualReferenceCreationPage();

        fill("#title").with("testTitle");
        fill("#key").with("key" + i);
        submit(find("form").first());
    }
    
    private void getToReferenceListsPage() {
        goTo(getUrl());
        click(find("#referencelist"));
    }

    private void getToReferenceListCreationPage() {
        goTo(getUrl());
        click(find("#referencelist-new"));
    }
    
    private void getToManualReferenceCreationPage() {
        getToReferenceCreationsChoosingPage();
        click(find("a", withText("Manual reference")));
    }
    
    private void getToReferenceCreationsChoosingPage() {
        goTo(getUrl());
        click(find("#reference-new"));
    }
}
