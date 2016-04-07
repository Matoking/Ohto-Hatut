package model;

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
public class BookReference extends Reference {
    public BookReference() {
        this.setCategory("book");
    }
}
