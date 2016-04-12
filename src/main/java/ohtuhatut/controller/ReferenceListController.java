package ohtuhatut.controller;

import javax.validation.Valid;
import ohtuhatut.service.ReferenceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ohtuhatut.domain.Reference;
import ohtuhatut.domain.ReferenceList;
import ohtuhatut.repository.ReferenceListRepository;
import ohtuhatut.repository.ReferenceRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for ReferenceList page.
 *
 * @author iilumme
 * @author tuomokar
 */

@Controller
@RequestMapping("/referencelists")
public class ReferenceListController {

    @Autowired
    private ReferenceListRepository referenceListRepository;
    
    @Autowired
    private ReferenceListService referenceListService;
    
    @Autowired
    private ReferenceRepository referenceRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReferenceList(Model model, @PathVariable Long id){
        model.addAttribute("referenceList", referenceListService.getReferenceList(id));
        model.addAttribute("references", referenceRepository.findAll());

        return "referenceList";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReferenceList(Model model){
        model.addAttribute("referenceList", new ReferenceList());
        
        return "referencelist_new";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newReferenceListCreate(@Valid @ModelAttribute ReferenceList referenceList, 
            BindingResult bindingResult,
            RedirectAttributes attr) {   
        
        if (bindingResult.hasErrors()) {
            return "referencelist_new";
        }
        
        referenceListRepository.save(referenceList);
        
        attr.addAttribute("id", referenceList.getId().toString());
        return "redirect:/referencelists/{id}";
    }
    
    @RequestMapping(value = "/{referenceListId}/references", method = RequestMethod.POST)
    public String addReferenceToList(@PathVariable(value="referenceListId") Long id, 
            @RequestParam(value="referenceId") Long referenceId,
            RedirectAttributes redirectAttrs) {
                 
        ReferenceList list = referenceListRepository.findOne(id);
        Reference reference = referenceRepository.findOne(referenceId);
        
        list.getReferences().add(reference);
        referenceListRepository.save(list);        
        
        redirectAttrs.addAttribute("id", id);
        
        return "redirect:/referencelists/{id}";
        
    }
}
