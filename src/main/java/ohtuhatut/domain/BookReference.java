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
        fields = new ArrayList<>();
        fields.add("author");
        fields.add("title");
        fields.add("publisher");
        fields.add("year");
    }
    
    public String getField(String field) {
        if (field.equals("author")) {
            return author;
        } else if (field.equals("title")) {
            return title;
        } else if (field.equals("publisher")) {
            return publisher;
        }
        
        return "" + year;
    }
}
