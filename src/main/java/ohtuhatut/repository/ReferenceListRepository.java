package ohtuhatut.repository;

import ohtuhatut.domain.ReferenceList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for saving ReferenceLists.
 * Created by iilumme.
 */
public interface ReferenceListRepository extends JpaRepository<ReferenceList, Long> {
}
