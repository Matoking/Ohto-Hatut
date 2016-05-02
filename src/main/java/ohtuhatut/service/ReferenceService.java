package ohtuhatut.service;

import java.util.HashMap;
import ohtuhatut.domain.*;
import ohtuhatut.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author sofiak
 * @author iilumme
 * @author tuomokar
 */

@Service
public class ReferenceService {

    @Autowired
    private ReferenceRepository referenceRepository;
    
    private Map<String, String> allTypes;
    
    public ReferenceService() {
        allTypes = new HashMap<>();
        populateTypes();
    }
    
    private void populateTypes() {
        allTypes.put("manual", "manual");
        allTypes.put("inproceedings", "inproceedings");
        allTypes.put("booklet", "booklet");
        allTypes.put("book", "book");
        allTypes.put("article", "article");
    }

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
     * Get mandatory fields for viewing reference_new.html. The method gets
     * the mandatory fields through creating a new reference and binding that
     * to the type given as the parameter.
     * 
     * @param type type of the reference
     * @return fields
     */
    public List<String> getMandatoryFields(String type) {
        
        Reference reference = new Reference();
        reference.setType(type);
        
        reference = bindReference(reference);
        return reference.getMandatoryFields();

    }

    /**
     * Get optional fields for viewing reference_new.html. The method gets
     * the optional fields through creating a new reference and binding that
     * to the type given as the parameter.
     * 
     * @param type type of the reference
     * @return fields
     */
    public List<String> getOptionalFields(String type) {
        
        Reference reference = new Reference();
        reference.setType(type);
        
        reference = bindReference(reference);
        return reference.getOptionalFields();       
    }
    
    public boolean typeIsNotKnown(String type) {
        return allTypes.get(type) == null;
    }
    
    public boolean referenceKeyAlreadyUsed(String key) {
        return !referenceRepository.findByKey(key).isEmpty();
    }
    
    /**
     * Returns an error message if key has already been used. If the key hasn't
     * been used, then null is returned.
     * 
     * @param key A reference's Bibtex key
     * @return error message if key has been used, or null if it has not been
     * used
     */
    public String keyNotUniqueErrorMessage(String key) {
        if (referenceKeyAlreadyUsed(key)) {
            return "That key has already been used, please use another key";
        }
        return null;
    }
    
    /**
     * Returns an error message if a given reference's key is in use on some
     * other reference. If it is not, then null is returned
     * 
     * @param reference a reference to be compared to
     * @return error message if key has been used on some other reference, or 
     * null if it has not been used
     */
    public String keyIsInUseOnSomeOtherReferenceErrorMessage(Reference reference) {
        if (referenceKeyAlreadyUsedOnSomeOtherReference(reference)) {
            return "That key is in use on another reference, please use another key";
        }
        return null;
    }
    
    /**
     * Checks if a key has already been used, and if it has, checks if the
     * reference having that key is the same reference as the one given as the
     * parameter. This method can be used for editing references - if the 
     * reference is the same one, then it must be able to be resaved with the
     * key it already has, and the check must be applied only to other
     * references.
     * 
     * @param reference Reference to be compared to the ones in the database
     * @return true if reference's key has been used on some other reference
     * than the one received as the parameter. Otherwise false is returned.
     */
    public boolean referenceKeyAlreadyUsedOnSomeOtherReference(Reference reference) {
        List<Reference> references = referenceRepository.findByKey(reference.getKey());       
        
        for (Reference ref : references) {
            if (referencesAreTheSame(ref, reference)) {
                continue;
            }
            
            if (keysAreTheSame(ref.getKey(), reference.getKey())) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean referencesAreTheSame(Reference ref1, Reference ref2) {
        return ref1.getId().equals(ref2.getId());
    }
    
    private boolean keysAreTheSame(String key1, String key2) {
        return key1.equals(key2);
    }
    
    
}
