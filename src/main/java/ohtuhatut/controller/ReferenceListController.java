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
 *
 * @author iilumme
 * @author tuomokar
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

    @RequestMapping(method = RequestMethod.GET)
    public String getAllReferenceLists(Model model) {
        model.addAttribute("referenceLists", referenceListService.getAllReferenceLists());
        return "referencelists";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReferenceList(Model model, @PathVariable Long id){
        model.addAttribute("referenceList", referenceListService.getReferenceList(id));
        model.addAttribute("references", referenceService.getAllReferences());

        return "referencelist_show";
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
        referenceListService.save(referenceList);
        
        attr.addAttribute("id", referenceList.getId().toString());
        return "redirect:/referencelists/{id}";
    }
    
    @RequestMapping(value = "/{referenceListId}/references", method = RequestMethod.POST)
    public String addReferenceToList(@PathVariable(value="referenceListId") Long id, 
            @RequestParam(value="referenceId") Long referenceId,
            RedirectAttributes redirectAttrs) {
        
        ReferenceList list = referenceListService.getReferenceList(id);
        Reference reference = referenceService.getReference(referenceId);
        
        list.getReferences().add(reference);
        referenceListService.save(list);
        
        redirectAttrs.addAttribute("id", id);
        
        return "redirect:/referencelists/{id}";
        
    }
    
    @RequestMapping(value = "/{referenceListId}/export", method = RequestMethod.GET)
    public void exportReferenceList(@PathVariable(value="referenceListId") Long id,
            HttpServletResponse response) throws IOException {
        ReferenceList list = referenceListService.getReferenceList(id);
        String formattedText = referenceFormatService.formatReferencesToBibTeX(list.getReferences());
        
        returnResponseWithBibTeXFile("references.bib", formattedText, response);
    }
    
    @RequestMapping(value = "/export_all", 
                    method = RequestMethod.GET)
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