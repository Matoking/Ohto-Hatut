package ohtuhatut.selenium;

import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;
import ohtuhatut.repository.ReferenceListRepository;
import ohtuhatut.repository.ReferenceRepository;
import org.fluentlenium.adapter.FluentTest;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
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
public class ReferenceTest extends FluentTest {

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
    public void bookReferenceCreationIsSuccessfulWhenGivenValidValues() {
        getToBookReferenceCreationPage();

        fill("#author").with("testAuthor");
        fill("#title").with("testTitle");
        fill("#publisher").with("testPublisher");
        fill("#year").with("2012");
        fill("#key").with("key1");
        submit(find("form").first());

        assertTrue(pageSource().contains("testAuthor"));
        assertTrue(pageSource().contains("testTitle"));
        assertTrue(pageSource().contains("testPublisher"));
        assertTrue(pageSource().contains("2012"));
    }

    @Test
    public void articleReferenceCreationIsSuccessfulWhenGivenValidValues() {
        getToArticleReferenceCreationPage();

        fill("#author").with("testAuthor");
        fill("#title").with("testTitle");
        fill("#journal").with("testJournal");
        fill("#year").with("2011");
        fill("#volume").with("testVolume");
        fill("#key").with("key2");
        submit(find("form").first());

        assertTrue(pageSource().contains("testAuthor"));
        assertTrue(pageSource().contains("testTitle"));
        assertTrue(pageSource().contains("testJournal"));
        assertTrue(pageSource().contains("2011"));
        assertTrue(pageSource().contains("testVolume"));
    }

    @Test
    public void bookletReferenceCreationIsSuccessfulWhenGivenValidValues() {
        getToBookletReferenceCreationPage();

        fill("#title").with("testTitle");
        fill("#key").with("key3");
        submit(find("form").first());

        assertTrue(pageSource().contains("testTitle"));
    }

    @Test
    public void manualReferenceCreationIsSuccessfulWhenGivenValidValues() {
        getToManualReferenceCreationPage();

        fill("#title").with("testTitle");
        fill("#key").with("key4");
        submit(find("form").first());

        assertTrue(pageSource().contains("testTitle"));
    }

    @Test
    public void inproceedingsReferenceCreationIsSuccessfulWhenGivenValidValues() {
        getToInproceedingsReferenceCreationPage();

        fill("#author").with("testAuthor");
        fill("#title").with("testTitle");
        fill("#booktitle").with("testBookTitle");
        fill("#year").with("2011");
        fill("#key").with("key5");
        submit(find("form").first());

        assertTrue(pageSource().contains("testAuthor"));
        assertTrue(pageSource().contains("testTitle"));
        assertTrue(pageSource().contains("testBookTitle"));
        assertTrue(pageSource().contains("2011"));
    }

    @Test
    public void articleReferenceCreationGivesErrorMessageWhenValidFieldsAreLeftEmpty() {
        getToArticleReferenceCreationPage();

        submit(find("form").first());

        assertTrue(pageSource().contains("author, title, journal, year, volume and key are empty!"));
    }

    @Test
    public void manualReferenceCreationGivesErrorMessageWhenValidFieldsAreLeftEmpty() {
        getToManualReferenceCreationPage();

        submit(find("form").first());

        assertTrue(pageSource().contains("title and key are empty!"));
    }

    @Test
    public void manualReferenceCanBeCreatedAndThenEdited() {
        createAReference(1);

        click(find("a", withText("Edit")));

        fill("#title").with("editedTitle");

        submit(find("form").first());

        // The edited title exists
        assertTrue(pageSource().contains("editedTitle"));
        assertFalse(pageSource().contains("testTitle"));
    }

    @Test
    public void manualReferenceCantBeUpdatedWhenValidFieldIsLeftEmpty() {
        createAReference(1);

        click(find("a", withText("Edit")));

        fill("#title").with("");

        submit(find("form").first());

        // The edited title exists
        assertTrue(pageSource().contains("title is empty"));
    }

    @Test
    public void nonexistingReferenceCantBeEdited() {
        goTo(getUrl() + "/references/1000/edit");

        assertTrue(pageSource().contains("Reference does not exist"));
    }

    @Test
    public void referencesPageShowsAllTheExistingReferences() {
        createAReference(1);
        createAReference(2);
        getToReferencesPage();

        assertTrue(pageSource().contains("References"));
        assertTrue(pageSource().contains("testTitle"));

    }

    @Test
    public void referencesPageShowsNoReferencesWhenThereAreNoReferences() {
        getToReferencesPage();

        assertTrue(pageSource().contains("References"));
        assertTrue(pageSource().contains("No references in the database at the moment"));

    }
    
    @Test
    public void tryingToEnterTheReferenceCreationPageWithoutHavingChosenATypeRedirectsTheUserToTheChoosingPage() {
        goTo(getUrl() + "/references/new");
        getDefaultDriver().get(getUrl() + "/references/new");
        
        assertTrue(pageSource().contains("Please choose a type first"));
    }
    
    @Test
    public void tryingToEnterTheReferenceCreationPageWhenGivingATypeThatNoReferenceHasRedirectsTheUserToTheChoosingPage() {
        goTo(getUrl() + "/references/new");
        getDefaultDriver().get(getUrl() + "/references/new/?type=randomTypeHere");
        
        assertTrue(pageSource().contains("Please choose a type first"));
    }
            
            /*
            if (type == null || referenceService.typeIsNotKnown(type)) {
            redirectAttr.addFlashAttribute("typeNotChosen", "Please choose a type first");
            return "redirect:/references/choose";
        }
            */

    private void createAReference(int i) {
        getToManualReferenceCreationPage();

        fill("#title").with("testTitle");
        fill("#key").with("key" + i);
        submit(find("form").first());
    }

    private void getToReferencesPage() {
        goTo(getUrl());
        click(find("#references"));
    }

    private void getToReferenceCreationsChoosingPage() {
        goTo(getUrl());
        click(find("#reference-choose"));
    }

    private void getToBookReferenceCreationPage() {
        getToReferenceCreationsChoosingPage();
        click(find("a", withText("Book reference")));
    }

    private void getToArticleReferenceCreationPage() {
        getToReferenceCreationsChoosingPage();
        click(find("a", withText("Article reference")));
    }

    private void getToBookletReferenceCreationPage() {
        getToReferenceCreationsChoosingPage();
        click(find("a", withText("Booklet reference")));
    }

    private void getToManualReferenceCreationPage() {
        getToReferenceCreationsChoosingPage();
        click(find("a", withText("Manual reference")));
    }

    private void getToInproceedingsReferenceCreationPage() {
        getToReferenceCreationsChoosingPage();
        click(find("a", withText("Inproceedings reference")));
    }
}
