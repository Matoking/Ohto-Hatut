package ohtuhatut.controller;

import ohtuhatut.domain.BookletReference;
import ohtuhatut.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ohtuhatut.Main.class)
@WebAppConfiguration
@Transactional
public class ReferenceControllerTest {

    private final String API_URI ="/references";

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
    private InproceedingsReferenceRepository inproceedingsReferenceRepository;


    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get(API_URI)).andExpect(status().isOk());
    }

    @Test
    public void getRequestToCreateANewReferenceHasStatusOK() throws Exception {
        MvcResult result = mockMvc.perform(get(API_URI + "/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("reference_choose"))
                .andReturn();
    }

    @Test
    public void getRequestToASpesificReferenceWorks() throws Exception {

        BookletReference blr = new BookletReference();
        blr.setTitle("test");
        referenceRepository.save(blr);

        MvcResult result = mockMvc.perform(get(API_URI + "/" + blr.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(view().name("reference"))
                .andReturn();
    }

    @Test
    public void getRequestToCreateANewBookReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/bookreferences/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(model().attribute("referenceType", is("bookreferences")))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }


    @Test
    public void postRequestToBookreferencesNewSavesABookreferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/bookreferences/new")
                .param("author", "tester")
                .param("title", "test")
                .param("publisher", "testingplace")
                .param("year", "2016"));

        assertTrue(bookReferenceRepository.count() == 1);

    }

    @Test
    public void postToCreateNotValidBookreferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/bookreferences/new")
                .param("title", "test")
                .param("publisher", "testingplace")
                .param("year", "2016"));

        assertTrue(bookReferenceRepository.count() == 0);

    }


    @Test
    public void getRequestToCreateANewArticleReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/articlereferences/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(model().attribute("referenceType", is("articlereferences")))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }

    @Test
    public void postRequestToArticlereferencesNewSavesAArticlereferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/articlereferences/new")
                .param("author", "tester")
                .param("title", "test")
                .param("journal", "testingplace")
                .param("volume","1")
                .param("year", "2016"));

        assertTrue(articleReferenceRepository.count() == 1);

    }

    @Test
    public void postToCreateNotValidArticlereferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/articlereferences/new")
                .param("title", "test")
                .param("journal", "testingplace")
                .param("volume","1")
                .param("year", "2016"));

        assertTrue(articleReferenceRepository.count() == 0);

    }

    @Test
    public void getRequestToCreateANewBookletReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/bookletreferences/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(model().attribute("referenceType", is("bookletreferences")))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }

    @Test
    public void postRequestToBookletreferencesNewSavesABookletreferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/bookletreferences/new")
                .param("title", "test"));

        assertTrue(bookletReferenceRepository.count() == 1);

    }

    @Test
    public void postToCreateNotValidBookletreferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/bookletreferences/new"));

        assertTrue(bookletReferenceRepository.count() == 0);

    }

    @Test
    public void getRequestToCreateANewManualReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/manualreferences/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(model().attribute("referenceType", is("manualreferences")))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }

    @Test
    public void postRequestToManualreferencesNewSavesAManualreferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/manualreferences/new")
                .param("title", "test"));

        assertTrue(manualReferenceRepository.count() == 1);

    }

    @Test
    public void postToCreateNotValidManualreferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/manualreferences/new"));

        assertTrue(manualReferenceRepository.count() == 0);

    }

    @Test
    public void getRequestToCreateANewInproceedingsReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/inproceedingsreferences/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(model().attribute("referenceType", is("inproceedingsreferences")))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }

    @Test
    public void postRequestToInproceedingsreferencesNewSavesAManualreferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/inproceedingsreferences/new")
                .param("author", "testAuthor")
                .param("booktitle", "book")
                .param("year", "2016")
                .param("title", "test"));

        assertTrue(inproceedingsReferenceRepository.count() == 1);

    }

    @Test
    public void postToCreateNotValidInproceedingsreferenceDoesNotSave() throws Exception {
        mockMvc.perform(post(API_URI + "/inproceedingsreferences/new"));
        assertTrue(inproceedingsReferenceRepository.count() == 0);

    }

    @Test
    public void postToCreateBookreferenceWithNonNumericYearDoesNotWork() throws Exception {

        mockMvc.perform(post(API_URI + "/bookreferences/new")
                .param("title", "testauthor")
                .param("title", "test")
                .param("publisher", "testingplace")
                .param("year", "yeah"));

        assertTrue(bookReferenceRepository.count() == 0);

    }


}