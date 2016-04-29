package ohtuhatut.service;

import java.util.ArrayList;
import ohtuhatut.domain.ReferenceList;
import ohtuhatut.repository.ReferenceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import ohtuhatut.domain.Reference;


/**
 * @author iilumme
 */

@Service
public class ReferenceListService {

    @Autowired
    private ReferenceListRepository referenceListRepository;

    public ReferenceList getReferenceList(Long id) { return referenceListRepository.findOne(id); }

    public void save(ReferenceList referenceList) {
        referenceListRepository.save(referenceList);
    }
    
    public List<ReferenceList> getAllReferenceLists() {
        return referenceListRepository.findAll();
    }

    public void removeReference(Reference reference) {
        for (ReferenceList referenceList : referenceListRepository.findAll()) {
            if (referenceList.getReferences().contains(reference)) {
                referenceList.removeReference(reference);
            }
        }
    }
    
    public void deleteReferenceList(ReferenceList referenceList) {
        referenceList.setReferences(new ArrayList<Reference>());
        referenceListRepository.delete(referenceList);
    }
}
