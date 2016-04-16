package ohtuhatut.domain;

import java.util.ArrayList;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * The class for a list of references
 * Created by iilumme.
 */

@Entity
@Table(name = "ReferenceList")
public class ReferenceList extends AbstractPersistable<Long> {

    @OneToMany(fetch = FetchType.LAZY,
               cascade = CascadeType.REMOVE)
    private List<Reference> references;

    @NotEmpty
    private String name;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Reference> getReferences() {
        if (references == null) 
            references = new ArrayList<>();
        
        return this.references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }
}
