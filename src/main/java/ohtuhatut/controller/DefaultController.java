package ohtuhatut.controller;

import ohtuhatut.domain.BookReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tuomokar
 */
@Controller
public class DefaultController {
    
    @RequestMapping("*")
    public String index() {
        return "index";
    }
    
    /**
     * Create a test book reference and try displaying it
     * @return 
     */
    @RequestMapping("/testReference")
    public String testReference(Model model) {
        BookReference testReference = new BookReference();
        
        testReference.setTitle("Testikirja");
        testReference.setPublisher("Testikirjailijat");
        testReference.setYear(2010);
        
        model.addAttribute("testReference", testReference);
        
        return "testReference";
    }
}
