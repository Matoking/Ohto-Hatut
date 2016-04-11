
package ohtuhatut.controller;

import ohtuhatut.domain.BookReference;
import ohtuhatut.domain.Reference;
import ohtuhatut.repository.BookReferenceRepository;
import ohtuhatut.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling references
 * 
 * @author tuomokar
 */
@Controller
@RequestMapping("/references")
public class ReferenceController {
    
    @Autowired
    private ReferenceRepository referenceRepository;
    @Autowired
    private BookReferenceRepository bookReferenceRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReference(Model model, @PathVariable Long id){
        
        model.addAttribute("reference", referenceRepository.findOne(id));

        return "reference";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReference(Model model){
        Reference reference = new BookReference();
        model.addAttribute("reference", reference);
        
        return "reference_choose";
    }
    
    @RequestMapping(value = "/bookreferences/new", method = RequestMethod.GET)
    public String newBookReference(Model model){
        Reference reference = new BookReference();
        model.addAttribute("reference", reference);
        
        return "reference_new";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newReferenceCreate(@ModelAttribute Reference reference, RedirectAttributes attr) {
        referenceRepository.save(reference);
        
        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }
    
    @RequestMapping(value = "/bookreferences/new", method = RequestMethod.POST)
    public String newBookReferenceCreate(@ModelAttribute BookReference reference, RedirectAttributes attr) {
        bookReferenceRepository.save(reference);
        
        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }
    
}



   
