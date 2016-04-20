
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
        setType("article");
        
        mandatoryFields = new ArrayList<>();
        mandatoryFields.add("author");
        mandatoryFields.add("title");
        mandatoryFields.add("journal");
        mandatoryFields.add("year");
        mandatoryFields.add("volume");
        
        optionalFields = new ArrayList<>();
        optionalFields.add("number");
        optionalFields.add("pages");
        optionalFields.add("month");
        optionalFields.add("note");
        
        populateFields();
    }
}