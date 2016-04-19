package ohtuhatut.service;

import ohtuhatut.domain.*;
import ohtuhatut.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private BookReferenceRepository bookReferenceRepository;
    @Autowired
    private ArticleReferenceRepository articleReferenceRepository;
    @Autowired
    private BookletReferenceRepository bookletReferenceRepository;
    @Autowired
    private ManualReferenceRepository manualReferenceRepository;
    @Autowired
    private InproceedingsReferenceRepository inproceedingsReferenceRepository;

    public Reference getReference(Long id) {
        return referenceRepository.findOne(id);
    }

    public List<Reference> getAllReferences() {
        return referenceRepository.findAll();
    }

    public void saveBookReference(BookReference reference) {
        bookReferenceRepository.save(reference);
    }

    public void saveArticleReference(ArticleReference reference) {
        articleReferenceRepository.save(reference);
    }

    public void saveBookletReference(BookletReference reference) {
        bookletReferenceRepository.save(reference);
    }

    public void saveManualReference(ManualReference reference) {
        manualReferenceRepository.save(reference);
    }
    
    public void saveInproceedingsReference(InproceedingsReference reference) {
        inproceedingsReferenceRepository.save(reference);
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
}
