package ohtuhatut.selenium;

import javax.transaction.Transactional;
import ohtuhatut.repository.ArticleReferenceRepository;
import ohtuhatut.repository.BookReferenceRepository;
import ohtuhatut.repository.BookletReferenceRepository;
import ohtuhatut.repository.ManualReferenceRepository;
import ohtuhatut.repository.ReferenceListRepository;
import ohtuhatut.repository.ReferenceRepository;
import org.fluentlenium.adapter.FluentTest;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
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
@Transactional
public class ReferenceTest extends FluentTest {

    @Autowired
    private ReferenceRepository referenceRepository;
    @Autowired
    private BookReferenceRepository bookReferenceRepository;
    @Autowired
    private ArticleReferenceRepository articleReferenceRepository;
    @Autowired
    private BookletReferenceRepository bookletReferenceRepository;
    @Autowired
    private ManualReferenceRepository manualReferenceRepository;
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
        referenceRepository.deleteAll();
        bookReferenceRepository.deleteAll();
        articleReferenceRepository.deleteAll();
        bookletReferenceRepository.deleteAll();
        manualReferenceRepository.deleteAll();
        referenceListRepository.deleteAll();
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
        submit(find("form").first());      

        assertTrue(pageSource().contains("testTitle"));
    }
    
    @Test
    public void manualReferenceCreationIsSuccessfulWhenGivenValidValues() {
        getToManualReferenceCreationPage();

        fill("#title").with("testTitle");
        submit(find("form").first());
               
        assertTrue(pageSource().contains("testTitle"));
    }

    private void getToReferenceCreationsChoosingPage() {
        goTo(getUrl());
        click(find("a", withText("New reference")));
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
}
