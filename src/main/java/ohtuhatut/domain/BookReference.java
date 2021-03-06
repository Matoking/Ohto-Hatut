package ohtuhatut.domain;

import java.util.ArrayList;

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
        setType("book");
        
        mandatoryFields = new ArrayList<>();
        mandatoryFields.add("author");
        mandatoryFields.add("title");
        mandatoryFields.add("publisher");
        mandatoryFields.add("year");
        
        optionalFields = new ArrayList<>();
        optionalFields.add("note");
        optionalFields.add("month");
        optionalFields.add("edition");
        optionalFields.add("address");
        optionalFields.add("series");
        optionalFields.add("volume");
        optionalFields.add("number");
        
        populateFields();
    }
}
