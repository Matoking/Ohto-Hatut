package ohtuhatut.controller;

import ohtuhatut.domain.BookReference;
import ohtuhatut.domain.Reference;
import ohtuhatut.domain.ReferenceList;
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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model){

        BookReference testReference = new BookReference();
        testReference.setTitle("Testikirja");
        testReference.setPublisher("Testikirjailijat");
        testReference.setYear(2010);
        BookReference testReference2 = new BookReference();
        testReference2.setTitle("Testikirja2");
        testReference2.setPublisher("Testikirjailijat2");
        testReference2.setYear(2012);

        List<Reference> test = new ArrayList<>();
        test.add(testReference);
        test.add(testReference2);

        ReferenceList r = new ReferenceList();
        r.setReferences(test);

        model.addAttribute("referenceList",r.getReferences());

        return "referenceList";
    }
}
