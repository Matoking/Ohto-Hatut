package ohtuhatut.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * The class for a list of references
 * Created by iilumme.
 */

@Entity
@Table(name = "ReferenceList")
public class ReferenceList extends AbstractPersistable<Long> {

    @OneToMany(fetch = FetchType.LAZY)
    private List<Reference> references;

    public List<Reference> getReferences() {
        return this.references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }
}
