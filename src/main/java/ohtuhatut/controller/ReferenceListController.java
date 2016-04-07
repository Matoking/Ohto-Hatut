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

/**
 * Controller class for ReferenceList page.
 *
 * Created by iilumme.
 */

@Controller
@RequestMapping("/referenceList")
public class ReferenceListController {

    @Autowired
    private ReferenceListService referenceListService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getReferenceList(Model model, @PathVariable Long id){

        model.addAttribute("referenceList", referenceListService.getReferenceList(id));

        return "referenceList";
    }
}
