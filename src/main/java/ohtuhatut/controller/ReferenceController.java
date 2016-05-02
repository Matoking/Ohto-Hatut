package ohtuhatut.controller;

import java.util.List;
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
 */
@Controller
@RequestMapping("/references")
public class ReferenceController {

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private ReferenceListService referenceListService;

    /**
     * Adds all the references to the model and returns the view showing them.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllReferences(Model model) {
        model.addAttribute("references", referenceService.getAllReferences());
        return "references/references";
    }

    /**
     * Adds a single reference (found by the given id) to the model and returns
     * the view for it.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReference(Model model, @PathVariable Long id) {
        model.addAttribute("reference", referenceService.getReference(id));
        return "references/reference_show";
    }

    /**
     * Handles the GET request for editing a reference. Adds the reference
     * corresponding to the given id to the model, and returns the editing view
     * for it. If the reference is null, returns the view informing the user on
     * it.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editReference(Model model, @PathVariable Long id) {
        Reference reference = referenceService.getReference(id);

        if (reference == null) {
            return "references/reference_does_not_exist";
        }

        model.addAttribute("reference", reference);
        return "references/reference_edit";
    }

    /**
     * Handles the POST request for editing a reference. The method takes the
     * reference as an instance of the superclass from which it is then bound to
     * the correct subclass type. The method checks if the given reference has
     * any mandatory values left empty, and if so, returns the editing view
     * along with the appropriate error message. Otherwise the edited reference
     * gets saved to the database and user gets redirected to the page of the
     * reference.
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String updateReference(@ModelAttribute Reference reference,
            @PathVariable Long id, RedirectAttributes attr, Model model) {

        reference = referenceService.bindReference(reference);

        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", referenceService.getReference(reference.getId()));
            return "references/reference_edit";
        }

        referenceService.saveReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }

    /**
     * Returns the view for the user to choose what kind of a reference they
     * want to create.
     */
    @RequestMapping(value = "/choose", method = RequestMethod.GET)
    public String chooseReferenceType() {
        return "references/reference_choose";
    }

    /**
     * Handles the GET request for creation of a new reference. If the type
     * parameter in the request isn't given or if it is a value that no
     * reference has as their type, the user is redirected to the choosing page
     * with the appropriate message. Otherwise a new reference is created and
     * that and the mandatory and optional fields for the type are added to the
     * model, and the view for the user to create a new reference is returned.
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReference(Model model, @RequestParam(value = "type", required = false) String type,
            RedirectAttributes redirectAttr) {

        if (type == null || referenceService.typeIsNotKnown(type)) {
            redirectAttr.addFlashAttribute("typeNotChosen", "Please choose a type first");
            return "redirect:/references/choose";
        }

        Reference ref = new Reference();
        ref.setType(type);
        model.addAttribute("reference", ref);
        
        List<String> fields = referenceService.getMandatoryFields(type);
        fields.addAll(referenceService.getOptionalFields(type));
        
        model.addAttribute("mandatoryFields", referenceService.getMandatoryFields(type));
        model.addAttribute("optionalFields", referenceService.getOptionalFields(type));
        model.addAttribute("fields", fields);

        return "references/reference_new";
    }

    /**
     * Handles the POST request for creating a new reference. If the user left
     * any mandatory fields empty, the method returns the view to create a new
     * reference along with an error message informing the user what fields were
     * left empty. Otherwise the reference is saved to the database and the user
     * is redirected to the newly created reference's page.
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newReferenceCreate(@ModelAttribute Reference reference,
            RedirectAttributes attr,
            Model model) {

        reference = referenceService.bindReference(reference);

        if (!reference.getEmptyMandatoryFields().isEmpty()) {
            model.addAttribute("emptyFields", referenceService.getErrorMessages(reference.getEmptyMandatoryFields()));
            model.addAttribute("reference", reference);
            model.addAttribute("mandatoryFields", referenceService.getMandatoryFields(reference.getType()));
            model.addAttribute("optionalFields", referenceService.getOptionalFields(reference.getType()));
            return "references/reference_new";
        }
        referenceService.saveReference(reference);

        attr.addAttribute("id", reference.getId());
        return "redirect:/references/{id}";
    }

    /**
     * Handles the deletion of a reference. If the reference searched by the id
     * is null, i.e. it doesn't exist, the method returns the view informing the
     * user on that. Otherwise the reference is removed from any reference lists
     * that may have had the reference, and then the reference is deleted from
     * the database, and the user is redirected to the page showing all the
     * references.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteReference(@PathVariable Long id) {

        Reference reference = referenceService.getReference(id);

        if (reference == null) {
            return "references/reference_does_not_exist";
        }

        referenceListService.removeReference(reference);

        referenceService.deleteReference(reference);

        return "redirect:/references/";
    }
}
