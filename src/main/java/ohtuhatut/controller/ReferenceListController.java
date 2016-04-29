package ohtuhatut.controller;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import ohtuhatut.service.ReferenceListService;
import ohtuhatut.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ohtuhatut.domain.Reference;
import ohtuhatut.domain.ReferenceList;
import ohtuhatut.service.ReferenceFormatService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for ReferenceList page.
 */
@Controller
@RequestMapping("/referencelists")
public class ReferenceListController {

    @Autowired
    private ReferenceListService referenceListService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private ReferenceFormatService referenceFormatService;

    /**
     * Handles the GET request for the page showing all the reference lists.
     * All the lists are added to the model and the view for them is returned.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllReferenceLists(Model model) {
        model.addAttribute("referenceLists", referenceListService.getAllReferenceLists());
        return "referencelists/referencelists";
    }

    /**
     * Handles the GET request for the page of a specific reference list.
     * The list is added to the model along with all the references in the
     * database (to make it possible for a user to add them to the list).
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReferenceList(Model model, @PathVariable Long id) {
        model.addAttribute("referenceList", referenceListService.getReferenceList(id));
        model.addAttribute("references", referenceService.getAllReferences());

        return "referencelists/referencelist_show";
    }

    /**
     * Handles the GET request for creating a new reference list.
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReferenceList(Model model) {
        model.addAttribute("referenceList", new ReferenceList());

        return "referencelists/referencelist_new";
    }

    /**
     * Handles the POST request for creating a new reference list. If the list's
     * name is left empty, returns the page again with the error message
     * informing the user on what went wrong. Otherwise the list is saved
     * into the database and the user gets redirected to the page of the newly
     * created list
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newReferenceListCreate(@Valid @ModelAttribute ReferenceList referenceList,
            BindingResult bindingResult,
            RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            return "referencelists/referencelist_new";
        }
        referenceListService.save(referenceList);

        attr.addAttribute("id", referenceList.getId().toString());
        return "redirect:/referencelists/{id}";
    }
    
    @RequestMapping(value = "/{referenceListId}/rename", method = RequestMethod.POST)
    public String renameReferenceList(@PathVariable(value = "referenceListId") Long id,
            @RequestParam(value = "name") String newName,
            RedirectAttributes attr, Model model) {
        ReferenceList referenceList = referenceListService.getReferenceList(id);
        
        if (newName.isEmpty()) {
            attr.addAttribute("id", referenceList.getId().toString());
            return "redirect:/referencelists/{id}";
        }
        
        referenceList.setName(newName);
        referenceListService.save(referenceList);
        
        attr.addAttribute("id", referenceList.getId().toString());
        return "redirect:/referencelists/{id}";
    }
    
    @RequestMapping(value = "/{referenceListId}/delete", method = RequestMethod.POST)
    public String renameReferenceList(@PathVariable(value = "referenceListId") Long id,
            RedirectAttributes attr) {
        ReferenceList referenceList = referenceListService.getReferenceList(id);
        
        referenceListService.deleteReferenceList(referenceList);
        
        return "redirect:/referencelists";
    }

    /**
     * Handles the POST request for adding references to a reference list.
     * The reference is added to the list only if the list doesn't have that 
     * reference yet. The user is then redirected back to the list's page.
     */
    @RequestMapping(value = "/{referenceListId}/references", method = RequestMethod.POST)
    public String addReferenceToList(@PathVariable(value = "referenceListId") Long id,
            @RequestParam(value = "referenceId") Long referenceId,
            RedirectAttributes redirectAttrs) {

        ReferenceList list = referenceListService.getReferenceList(id);
        Reference reference = referenceService.getReference(referenceId);

        if (!list.getReferences().contains(reference)) {
            list.getReferences().add(reference);
            referenceListService.save(list);
        }

        redirectAttrs.addAttribute("id", id);

        return "redirect:/referencelists/{id}";

    }

    /**
     * Handles the POST request for removing a reference from a list. Removes
     * the reference only if it is actually on the list. The user is then
     * redirected to the page of the list.
     */
    @RequestMapping(value = "/{referenceListId}/references/remove", method = RequestMethod.POST)
    public String removeReferenceFromList(@PathVariable(value = "referenceListId") Long id,
            @RequestParam(value = "referenceId") Long referenceId,
            RedirectAttributes redirectAttrs) {
        
        ReferenceList list = referenceListService.getReferenceList(id);
        Reference reference = referenceService.getReference(referenceId);

        if (list.getReferences().contains(reference)) {
            list.getReferences().remove(reference);
            referenceListService.save(list);
        }

        redirectAttrs.addAttribute("id", id);

        return "redirect:/referencelists/{id}";
    }

    /**
     * Exports all the references in the given reference list to a Bibtex file 
     * in the correct format. If no name is given for the file, then the 
     * list's name is given to it.
     */
    @RequestMapping(value = "/{referenceListId}/export", method = RequestMethod.GET)
    public void exportReferenceList(@PathVariable(value = "referenceListId") Long id,
            @RequestParam(value = "name", required = false) String name,
            HttpServletResponse response) throws IOException {       
        
        ReferenceList list = referenceListService.getReferenceList(id);

        if (name == null || name.isEmpty()) {
            name = list.getName();
        }
        
        String formattedText = referenceFormatService.formatReferencesToBibTeX(list.getReferences());

        returnResponseWithBibTeXFile(name + ".bib", formattedText, response);
    }

    /**
     * Exports all the references in the database to a Bibtex file. The file
     * gets the name "references"
     */
    @RequestMapping(value = "/export_all", method = RequestMethod.GET)
    public void exportAll(HttpServletResponse response) throws IOException {
        String formattedText = referenceFormatService.formatReferencesToBibTeX(referenceService.getAllReferences());

        returnResponseWithBibTeXFile("references.bib", formattedText, response);
    }

    private void returnResponseWithBibTeXFile(String filename, String content, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s", filename));
        ServletOutputStream out = response.getOutputStream();
        out.println(content);
        out.flush();
        out.close();
    }
}
