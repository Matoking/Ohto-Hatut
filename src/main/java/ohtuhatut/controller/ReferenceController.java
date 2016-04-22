package ohtuhatut.controller;

import ohtuhatut.domain.ArticleReference;
import ohtuhatut.domain.BookReference;
import ohtuhatut.domain.BookletReference;
import ohtuhatut.domain.InproceedingsReference;
import ohtuhatut.domain.ManualReference;
import ohtuhatut.domain.Reference;
import ohtuhatut.service.ReferenceService;
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
    private ReferenceService referenceService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReference(Model model) {
        return "reference_choose";
    }

    // works for all kind of references
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReference(Model model, @PathVariable Long id) {
        model.addAttribute("reference", referenceService.getReference(id));
        return "reference";
    }
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editReference(Model model, @PathVariable Long id) {
        model.addAttribute("reference", referenceService.getReference(id));
        return "reference_edit";
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

    // -------------- book references
    @RequestMapping(value = "/bookreferences/new", method = RequestMethod.GET)
    public String newBookReference(Model model) {;
        model.addAttribute("reference", new BookReference());
        model.addAttribute("referenceType", "bookreferences");
        return "reference_new";
    }

    @RequestMapping(value = "/bookreferences/new", method = RequestMethod.POST)
    public String newBookReferenceCreate(@ModelAttribute BookReference reference, RedirectAttributes attr,
            Model model) {

        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", new BookReference());
            model.addAttribute("referenceType", "bookreferences");
            return "reference_new";
        }
        referenceService.saveBookReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }
    // <-- book references

    // -------------- article references
    @RequestMapping(value = "/articlereferences/new", method = RequestMethod.GET)
    public String newArticleReference(Model model) {
        model.addAttribute("reference", new ArticleReference());
        model.addAttribute("referenceType", "articlereferences");
        return "reference_new";
    }

    @RequestMapping(value = "/articlereferences/new", method = RequestMethod.POST)
    public String newArticleReferenceCreate(@ModelAttribute ArticleReference reference, RedirectAttributes attr,
            Model model) {
        
        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", new ArticleReference());
            model.addAttribute("referenceType", "articlereferences");
            return "reference_new";
        }   
        referenceService.saveArticleReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }
    // <-- article references

    // -------------- booklet references
    @RequestMapping(value = "/bookletreferences/new", method = RequestMethod.GET)
    public String newBookletReference(Model model) {
        model.addAttribute("reference", new BookletReference());
        model.addAttribute("referenceType", "bookletreferences");
        return "reference_new";
    }

    @RequestMapping(value = "/bookletreferences/new", method = RequestMethod.POST)
    public String newBookletReferenceCreate(@ModelAttribute BookletReference reference, RedirectAttributes attr,
            Model model) {
        
        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", new BookletReference());
            model.addAttribute("referenceType", "bookletreferences");
            return "reference_new";
        }
        referenceService.saveBookletReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }
    // <-- booklet references

    // -------------- manual references
    @RequestMapping(value = "/manualreferences/new", method = RequestMethod.GET)
    public String newManualReference(Model model) {
        model.addAttribute("reference", new ManualReference());
        model.addAttribute("referenceType", "manualreferences");
        return "reference_new";
    }

    @RequestMapping(value = "/manualreferences/new", method = RequestMethod.POST)
    public String newManualReferenceCreate(@ModelAttribute ManualReference reference, RedirectAttributes attr,
            Model model) {
        
        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", new ManualReference());
            model.addAttribute("referenceType", "manualreferences");
            return "reference_new";
        }
        referenceService.saveManualReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }
    // <-- manual references
    
    // -------------- inproceedings references
    @RequestMapping(value = "/inproceedingsreferences/new", method = RequestMethod.GET)
    public String newInproceedingsReference(Model model){
        model.addAttribute("reference", new InproceedingsReference());
        model.addAttribute("referenceType", "inproceedingsreferences");
        return "reference_new";
    }
    
    @RequestMapping(value = "/inproceedingsreferences/new", method = RequestMethod.POST)
    public String newInproceedingsReferenceCreate(@ModelAttribute InproceedingsReference reference, RedirectAttributes attr, Model model) {

        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", new InproceedingsReference());
            model.addAttribute("referenceType", "inproceedingsreferences");
            return "reference_new";
        }
        referenceService.saveInproceedingsReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }

    // <-- inproceedings references
    
    
}
