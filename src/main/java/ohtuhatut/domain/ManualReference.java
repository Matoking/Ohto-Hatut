
package ohtuhatut.domain;

import java.util.ArrayList;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Manual reference
 * 
 * Required fields: title
 * Optional fields: author, organization, address, edition, month, year, note, key
 * 
 * @author sofiak
 */

@Table(name = "Reference")
@DiscriminatorValue("article")
@Entity
public class ManualReference extends Reference {
    
    public ManualReference() {
        mandatoryFields = new ArrayList<>();
        mandatoryFields.add("title");
        
        optionalFields = new ArrayList<>();
        
        populateFields();
    }
    
}
