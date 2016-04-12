package ohtuhatut.service;

import ohtuhatut.domain.Reference;
import ohtuhatut.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sofiak
 */

@Service
public class ReferenceService {
    
        @Autowired
    private ReferenceRepository referenceRepository;

    public Reference getReference(Long id) {
        return referenceRepository.findOne(id);
    }
}
