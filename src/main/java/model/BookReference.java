package model;

import javax.persistence.DiscriminatorValue;
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
public class BookReference extends Reference {
}
