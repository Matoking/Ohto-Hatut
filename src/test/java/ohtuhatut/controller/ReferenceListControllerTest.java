package ohtuhatut.controller;

import javax.transaction.Transactional;
import ohtuhatut.domain.BookReference;
import ohtuhatut.domain.Reference;
import ohtuhatut.domain.ReferenceList;
import ohtuhatut.repository.ReferenceListRepository;
import ohtuhatut.repository.ReferenceRepository;
import org.junit.After;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author tuomokar
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ohtuhatut.Main.class)
@WebAppConfiguration
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
    

    @Transactional
    @Test
    public void postingANewReferenceListSavesTheListToDatabase() throws Exception {
        
        mockMvc.perform(post(API_URI + "/new")
                .param("name", "testName"));
        
        assertTrue(referenceListRepository.count() == 1);
    }
    
    @Transactional
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

}