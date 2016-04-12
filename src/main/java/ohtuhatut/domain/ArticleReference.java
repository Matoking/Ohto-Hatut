
package ohtuhatut.domain;

import java.util.ArrayList;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Article reference
 * 
 * Required fields: author, title, journal, year, volume
 * Optional fields: number, pages, month, note, key
 * 
 * @author tuomokar
 */

@Table(name = "Reference")
@DiscriminatorValue("article")
@Entity
public class ArticleReference extends Reference {
    
    public ArticleReference() {
        mandatoryFields = new ArrayList<>();
        mandatoryFields.add("author");
        mandatoryFields.add("title");
        mandatoryFields.add("journal");
        mandatoryFields.add("year");
        mandatoryFields.add("volume");
        
        optionalFields = new ArrayList<>();
        
        populateFields();
    }
    
    public String getField(String field) {
        if (field.equals("author")) {
            return author;
        } else if (field.equals("title")) {
            return title;
        } else if (field.equals("journal")) {
            return journal;
        } else if (field.equals("volume")) {
            return volume;
        }
        
        return "" + year;
    }
}