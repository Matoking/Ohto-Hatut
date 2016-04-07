package ohtuhatut.service;

import ohtuhatut.domain.ReferenceList;
import ohtuhatut.repository.ReferenceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by iilumme.
 */

@Service
public class ReferenceListService {

    @Autowired
    private ReferenceListRepository referenceListRepository;

    public ReferenceList getReferenceList(Long id) {
        return referenceListRepository.findOne(id);
    }
}
