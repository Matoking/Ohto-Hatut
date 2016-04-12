package ohtuhatut.domain;

import java.util.ArrayList;
import ohtuhatut.domain.Reference;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Book reference
 * 
 * Required fields: author, title, publisher, year
 * Optional fields: volume/number, series, address, edition, month, note ,key
 * 
 * @author matoking
 */
@Table(name = "Reference")
@DiscriminatorValue("book")
@Entity
public class BookReference extends Reference {
    
    public BookReference() {
        mandatoryFields = new ArrayList<>();
        mandatoryFields.add("author");
        mandatoryFields.add("title");
        mandatoryFields.add("publisher");
        mandatoryFields.add("year");
        
        optionalFields = new ArrayList<>();
        
        populateFields();
    }
}
