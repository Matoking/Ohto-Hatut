package ohtuhatut.domain;

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
}
