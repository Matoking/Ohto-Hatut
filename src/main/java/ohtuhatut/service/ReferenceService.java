package ohtuhatut.service;

import ohtuhatut.domain.*;
import ohtuhatut.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sofiak
 * @author iilumme
 * @author tuomokar
 */

@Service
public class ReferenceService {

    @Autowired
    private ReferenceRepository referenceRepository;

    public Reference getReference(Long id) {
        return referenceRepository.findOne(id);
    }

    public List<Reference> getAllReferences() {
        return referenceRepository.findAll();
    }
    
    public void saveReference(Reference reference) {
        referenceRepository.save(reference);
    }
    
    public void deleteReference(Reference reference) {
        referenceRepository.delete(reference);
    }
    
    /*
     * Bind reference to its correct type (eg. a "book" Reference will be returned
     * as a BookReference)
     */
    public Reference bindReference(Reference reference) {
        Reference newReference = null;
        
        switch(reference.getType()) {
            case "article":
                newReference = new ArticleReference();
                break;
                
            case "book":
                newReference = new BookReference();
                break;
                
            case "booklet":
                newReference = new BookletReference();
                break;
                
            case "inproceedings":
                newReference = new InproceedingsReference();
                break;
                
            case "manual":
                newReference = new ManualReference();
                break;
                
            default:
                return null;
        }
        
        newReference.copyFields(reference);
        return newReference;
    }
    
    /**
     * Receives a list of empty mandatory fields in a reference and
     * returns an error message listing the empty fields. If there are no
     * empty fields, a null value is returned.
     * 
     * @param emptyFields list of empty fields in a reference
     * @return error messages
     */
    public String getErrorMessages(List<String> emptyFields) {
        if (emptyFields.isEmpty()) {
            return null;
        } else if (emptyFields.size() == 1) {
            return oneFieldEmptyMessage(emptyFields);
        } else if (emptyFields.size() == 2) {
            return twoFieldsEmptyMessage(emptyFields);
        }

        return threeOrMoreFieldsEmptyMessage(emptyFields);
    }
    
    private String threeOrMoreFieldsEmptyMessage(List<String> emptyFields) {
        StringBuilder sb = new StringBuilder();
        
        int i = 0;
        while (i < emptyFields.size() - 2) {
            sb.append(emptyFields.get(i) + ", ");
            i++;
        }

        sb.append(emptyFields.get(i));
        sb.append(" and " + emptyFields.get(i + 1) + " are empty!");

        return sb.toString();
    }
    
    private String oneFieldEmptyMessage(List<String> emptyFields) {
        return emptyFields.get(0) + " is empty!";
    }
    
    private String twoFieldsEmptyMessage(List<String> emptyFields) {
        return emptyFields.get(0) + " and " + emptyFields.get(1) + " are empty!";
    }

    /**
     * Get mandatory fields for viewing reference_new.html.
     * @param type type of the reference
     * @return fields
     */
    public static List<String> getMandatoryFields(String type) {

        List<String> mandatoryFields = new ArrayList<>();

        switch (type) {
            case "article":
                mandatoryFields.add("author");
                mandatoryFields.add("title");
                mandatoryFields.add("journal");
                mandatoryFields.add("year");
                mandatoryFields.add("volume");
                break;

            case "book":
                mandatoryFields.add("author");
                mandatoryFields.add("title");
                mandatoryFields.add("publisher");
                mandatoryFields.add("year");
                break;

            case "booklet":
                mandatoryFields.add("title");
                break;

            case "inproceedings":
                mandatoryFields.add("author");
                mandatoryFields.add("title");
                mandatoryFields.add("booktitle");
                mandatoryFields.add("year");
                break;

            case "manual":
                mandatoryFields.add("title");
                break;
        }

        return mandatoryFields;
    }

    /**
     * Get optional fields for viewing reference_new.html.
     * @param type type of the reference
     * @return fields
     */
    public static List<String> getOptionalFields(String type) {

        List<String> optionalFields = new ArrayList<>();

        switch (type) {
            case "article":
                optionalFields.add("number");
                optionalFields.add("pages");
                optionalFields.add("month");
                optionalFields.add("note");
                break;

            case "book":
                optionalFields.add("note");
                optionalFields.add("month");
                optionalFields.add("edition");
                optionalFields.add("address");
                optionalFields.add("series");
                optionalFields.add("volume");
                optionalFields.add("number");
                break;

            case "booklet":
                optionalFields.add("author");
                optionalFields.add("howpublished");
                optionalFields.add("address");
                optionalFields.add("month");
                optionalFields.add("year");
                optionalFields.add("note");
                break;

            case "inproceedings":
                optionalFields.add("editor");
                optionalFields.add("volume");
                optionalFields.add("series");
                optionalFields.add("pages");
                optionalFields.add("address");
                optionalFields.add("month");
                optionalFields.add("organization");
                optionalFields.add("publisher");
                optionalFields.add("note");
                break;

            case "manual":
                optionalFields.add("author");
                optionalFields.add("organization");
                optionalFields.add("address");
                optionalFields.add("edition");
                optionalFields.add("month");
                optionalFields.add("year");
                optionalFields.add("note");
                break;
        }

        return optionalFields;
    }
}
