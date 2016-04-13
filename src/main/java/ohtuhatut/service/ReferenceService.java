package ohtuhatut.service;

import ohtuhatut.domain.*;
import ohtuhatut.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sofiak
 * @author iilumme
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

}
