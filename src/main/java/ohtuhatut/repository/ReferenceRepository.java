
package ohtuhatut.repository;

import java.util.List;
import ohtuhatut.domain.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for saving references
 * 
 * @author tuomokar
 */
public interface ReferenceRepository extends JpaRepository<Reference, Long>  {
    List<Reference> findByKey(String key);
}
