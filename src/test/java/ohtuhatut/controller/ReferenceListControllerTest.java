package ohtuhatut.controller;

import java.util.List;
import javax.transaction.Transactional;
import ohtuhatut.domain.BookReference;
import ohtuhatut.domain.ManualReference;
import ohtuhatut.domain.ReferenceList;
import ohtuhatut.repository.ReferenceListRepository;
import ohtuhatut.repository.ReferenceRepository;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 *
 * @author tuomokar
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ohtuhatut.Main.class)
@WebAppConfiguration
@Transactional
public class ReferenceListControllerTest {
    
    private final String API_URI ="/referencelists";
    
    @Autowired
    private ReferenceListRepository referenceListRepository;
    
    @Autowired
    private ReferenceRepository referenceRepository;
    
    @Autowired
    private WebApplicationContext webAppContext;
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        this.referenceListRepository.deleteAll();
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get(API_URI)).andExpect(status().isOk());
    }
    
    @Test
    public void modelHasAttributeReferenceListWhenCreatingANewList() throws Exception {
        MvcResult res = mockMvc.perform(get(API_URI + "/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("referenceList"))
                .andReturn();
    }
    
    @Test
    public void postingANewReferenceListSavesTheListToDatabase() throws Exception {
        
        mockMvc.perform(post(API_URI + "/new")
                .param("name", "testName"));
        
        assertTrue(referenceListRepository.count() == 1);
    }
    
    @Test
    public void postingAReferenceListWithEmptyNameDoesNotSaveItToTheDatabase() throws Exception {
        mockMvc.perform(post(API_URI + "/new")
                .param("name", ""));
        
        assertTrue(referenceListRepository.count() == 0);
    }
    
    @Test
    public void addingAReferenceToTheListSavesItToTheListAndRedirectsToTheListsPage() throws Exception { 
        BookReference reference = new BookReference();
        reference = referenceRepository.save(reference);
        ReferenceList list = new ReferenceList();
        list.setName("list1");
        list = referenceListRepository.save(list);

        mockMvc.perform(post(API_URI + "/" + list.getId() + "/references")
                .param("referenceId", reference.getId().toString()))
                .andExpect(redirectedUrl(API_URI + "/" + list.getId()));

        assertTrue(list.getReferences().size() == 1);
    }

    @Test
    public void addingAReferenceTwiceToTheListDoesNotSaveItToTheList() throws Exception {
        BookReference reference = new BookReference();
        reference = referenceRepository.save(reference);
        ReferenceList list = new ReferenceList();
        list.setName("list1");
        list = referenceListRepository.save(list);

        mockMvc.perform(post(API_URI + "/" + list.getId() + "/references")
                .param("referenceId", reference.getId().toString()))
                .andExpect(redirectedUrl(API_URI + "/" + list.getId()));

        mockMvc.perform(post(API_URI + "/" + list.getId() + "/references")
                .param("referenceId", reference.getId().toString()))
                .andExpect(redirectedUrl(API_URI + "/" + list.getId()));

        assertTrue(list.getReferences().size() == 1);
    }
    
    @Test
    public void getRequestToSeeingAllReferenceLists() throws Exception {
        saveListWithAReference();
        MvcResult res = mockMvc.perform(get(API_URI))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("referenceLists"))
                .andExpect(view().name("referencelists/referencelists"))
                .andReturn();
        
        List<ReferenceList> referenceLists = (List<ReferenceList>) res.getModelAndView().getModel()
                .get("referenceLists");

        assertTrue(referenceLists.size() == 1);
        assertTrue(referenceLists.get(0).getReferences().size() == 1);
    }
    
    private void saveListWithAReference() {

        ManualReference reference = new ManualReference();
        reference.setTitle("test");
        reference.setKey("keyyy");
        referenceRepository.save(reference);
        
        ReferenceList list = new ReferenceList();
        list.setName("list1");
        list.getReferences().add(reference);
        referenceListRepository.save(list);
    }

}