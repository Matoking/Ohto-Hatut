package ohtuhatut.controller;

import ohtuhatut.domain.Reference;
import ohtuhatut.service.ReferenceListService;
import ohtuhatut.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private ReferenceService referenceService;
    
    @Autowired
    private ReferenceListService referenceListService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllReferences(Model model) {
        model.addAttribute("references", referenceService.getAllReferences());
        return "references";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReference(Model model, @PathVariable Long id) {
        model.addAttribute("reference", referenceService.getReference(id));
        return "reference";
    }

    // ----------- edit
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editReference(Model model, @PathVariable Long id) {
        Reference reference = referenceService.getReference(id);
        if (reference != null) {
            model.addAttribute("reference", reference);
            return "reference_edit";
        } else {
            return "reference_does_not_exist";
        }
    }
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String updateReference(@ModelAttribute Reference reference,
            @PathVariable Long id, RedirectAttributes attr, Model model) {
        reference = referenceService.bindReference(reference);
        
        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", referenceService.getReference(reference.getId()));
            return "reference_edit";
        }
        
        referenceService.saveReference(reference);
        
        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }

    // <--- edit

    // ----------- new

    @RequestMapping(value = "/choose", method = RequestMethod.GET)
    public String chooseReferenceType(Model model) {
        return "reference_choose";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReference(Model model, @RequestParam("type") String type) {

        Reference ref = new Reference();
        ref.setType(type);
        model.addAttribute("reference", ref);
        model.addAttribute("mandatoryFields", ReferenceService.getMandatoryFields(type));
        model.addAttribute("optionalFields", ReferenceService.getOptionalFields(type));

        return "reference_new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newReferenceCreate(@ModelAttribute Reference reference, RedirectAttributes attr,
                                         Model model) {

        reference = referenceService.bindReference(reference);

        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", reference);
            model.addAttribute("mandatoryFields", ReferenceService.getMandatoryFields(reference.getType()));
            model.addAttribute("optionalFields", ReferenceService.getOptionalFields(reference.getType()));
            return "reference_new";
        }
        referenceService.saveReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }

    // <--- new
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteReference(@PathVariable Long id) {
 
        Reference reference = referenceService.getReference(id);
        
        if (reference == null) {
            return "404";
        }
        
        referenceListService.removeReference(reference);
        
        referenceService.deleteReference(reference);
        
        return "redirect:/references/";
    }
}
