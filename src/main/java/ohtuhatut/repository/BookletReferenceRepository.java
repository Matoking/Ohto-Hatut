package ohtuhatut.repository;

import ohtuhatut.domain.BookletReference;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sofia
 */

public interface BookletReferenceRepository extends JpaRepository<BookletReference, Long> {
}