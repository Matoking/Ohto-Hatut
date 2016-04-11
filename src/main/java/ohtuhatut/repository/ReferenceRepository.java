
package ohtuhatut.repository;

import ohtuhatut.domain.Reference;
import ohtuhatut.domain.ReferenceList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for saving references
 * 
 * @author tuomokar
 */
public interface ReferenceRepository extends JpaRepository<Reference, Long>  {
    
}
