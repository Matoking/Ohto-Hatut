package ohtuhatut.controller;

import ohtuhatut.service.ReferenceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import ohtuhatut.domain.ReferenceList;
import ohtuhatut.repository.ReferenceListRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for ReferenceList page.
 *
 * Created by iilumme.
 */

@Controller
@RequestMapping("/referencelist")
public class ReferenceListController {

    @Autowired
    private ReferenceListRepository referenceListRepository;
    
    @Autowired
    private ReferenceListService referenceListService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReferenceList(Model model, @PathVariable Long id){

        model.addAttribute("referenceList", referenceListService.getReferenceList(id));

        return "referenceList";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReferenceList(Model model){
        model.addAttribute("referenceList", new ReferenceList());
        
        return "referencelist_new";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newReferenceListCreate(@ModelAttribute ReferenceList referenceList, RedirectAttributes attr){
        referenceListRepository.save(referenceList);
        
        attr.addAttribute("id", referenceList.getId().toString());
        return "redirect:/referencelist/{id}";
    }
}
