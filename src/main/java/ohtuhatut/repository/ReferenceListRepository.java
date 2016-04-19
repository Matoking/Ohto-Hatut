package ohtuhatut.repository;

import ohtuhatut.domain.ReferenceList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for saving ReferenceLists.
 */
public interface ReferenceListRepository extends JpaRepository<ReferenceList, Long> {
}
