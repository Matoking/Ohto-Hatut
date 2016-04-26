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
        MvcResult result = mockMvc.perform(get(API_URI + "/choose"))
                .andExpect(status().isOk())
                .andExpect(view().name("reference_choose"))
                .andReturn();
    }

    @Test
    public void getRequestToASpesificReferenceWorks() throws Exception {

        BookletReference blr = new BookletReference();
        blr.setTitle("test");
        blr.setKey("test");
        referenceRepository.save(blr);

        MvcResult result = mockMvc.perform(get(API_URI + "/" + blr.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(view().name("reference"))
                .andReturn();
    }

    @Test
    public void getRequestToAllReferencesWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("references"))
                .andExpect(view().name("references"))
                .andReturn();
    }


    // -------------- book references

    @Test
    public void getRequestToCreateANewBookReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/new?type=book"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }


    @Test
    public void postRequestToCreateNewBookReferenceSavesTheReferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("author", "tester")
                .param("title", "test")
                .param("publisher", "testingplace")
                .param("year", "2016")
                .param("key", "key1")
                .param("type", "book"));

        assertTrue(referenceRepository.count() == 1);

    }

    @Test
    public void postToCreateNotValidBookreferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("title", "test")
                .param("publisher", "testingplace")
                .param("year", "2016")
                .param("type", "book"));

        assertTrue(referenceRepository.count() == 0);

    }

    // <-- book references

    // -------------- article references


    @Test
    public void getRequestToCreateANewArticleReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/new?type=article"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }


    @Test
    public void postRequestToCreateNewArticleReferenceSavesTheReferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("author", "tester")
                .param("title", "test")
                .param("journal", "testingplace")
                .param("volume","1")
                .param("year", "2016")
                .param("key", "key2")
                .param("type", "article"));

        assertTrue(referenceRepository.count() == 1);
    }

    @Test
    public void postToCreateNotValidArticleReferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("title", "test")
                .param("year", "2016")
                .param("type", "article"));

        assertTrue(referenceRepository.count() == 0);

    }

    // <-- article references

    // -------------- booklet references

    @Test
    public void getRequestToCreateANewBookletReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/new?type=booklet"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }

    @Test
    public void postRequestToCreateNewBookletReferenceSavesTheReferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("title", "test")
                .param("key", "key2")
                .param("type", "booklet"));

        assertTrue(referenceRepository.count() == 1);
    }

    @Test
    public void postToCreateNotValidBookletreferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("type", "booklet"));

        assertTrue(referenceRepository.count() == 0);

    }


    // <-- booklet references

    // -------------- manual references

    @Test
    public void getRequestToCreateANewManualReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/new?type=manual"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }

    @Test
    public void postRequestToCreateNewManualReferenceSavesTheReferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("title", "test")
                .param("key", "key4")
                .param("type", "manual"));

        assertTrue(referenceRepository.count() == 1);
    }

    @Test
    public void postToCreateNotValidManualreferenceDoesNotSave() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("type", "manual"));

        assertTrue(referenceRepository.count() == 0);

    }

    // <-- manual references

    // -------------- inproceedings references

    @Test
    public void getRequestToCreateANewInproceedingsReferenceWorks() throws Exception {

        MvcResult result = mockMvc.perform(get(API_URI + "/new?type=inproceedings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reference"))
                .andExpect(view().name("reference_new"))
                .andReturn();
    }


    @Test
    public void postRequestToCreateNewInproceedingsReferenceSavesTheReferenceToDatabase() throws Exception {

        mockMvc.perform(post(API_URI + "/new")
                .param("author", "testAuthor")
                .param("booktitle", "book")
                .param("year", "2016")
                .param("title", "test")
                .param("key", "key5")
                .param("type", "inproceedings"));

        assertTrue(referenceRepository.count() == 1);
    }

    @Test
    public void postToCreateNotValidInproceedingsreferenceDoesNotSave() throws Exception {
        mockMvc.perform(post(API_URI + "/new")
                .param("title", "test")
                .param("type", "inproceedings"));
        assertTrue(referenceRepository.count() == 0);

    }

    // <-- inproceedings references


    @Test
    public void postToCreateBookreferenceWithNonNumericYearDoesNotWork() throws Exception {

        mockMvc.perform(post(API_URI + "/bookreferences/new")
                .param("author", "testauthor")
                .param("title", "test")
                .param("publisher", "testingplace")
                .param("year", "yeah")
                .param("key", "key6")
                .param("type", "book"));

        assertTrue(referenceRepository.count() == 0);

    }


}