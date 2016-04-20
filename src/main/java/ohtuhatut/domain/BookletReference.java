package ohtuhatut.domain;

import java.util.ArrayList;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Booklet reference
 * 
 * Required fields: title
 * Optional fields: author, howpublished, address, month, year, note, key
 * 
 * @author sofiak
 */

@Table(name = "Reference")
@DiscriminatorValue("booklet")
@Entity
public class BookletReference extends Reference {
    
    public BookletReference() {
        setType("book");
        
        mandatoryFields = new ArrayList<>();
        mandatoryFields.add("title");
        
        optionalFields = new ArrayList<>();
        optionalFields.add("author");
        optionalFields.add("howpublished");
        optionalFields.add("address");
        optionalFields.add("month");
        optionalFields.add("year");
        optionalFields.add("note");
        
        populateFields();
    }
}
