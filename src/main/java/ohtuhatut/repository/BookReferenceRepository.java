
package ohtuhatut.repository;

import ohtuhatut.domain.BookReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReferenceRepository extends JpaRepository<BookReference, Long> {
}
