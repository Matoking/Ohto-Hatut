package ohtuhatut.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import org.hibernate.annotations.DiscriminatorOptions;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * The base Reference class that includes whatever fields necessary to support categories;
 * the said categories will decide which fields are mandatory/optional
 * 
 * @author matoking
 */
@Entity
@Inheritance
@DiscriminatorColumn(name="category")
@DiscriminatorValue("generic")
@DiscriminatorOptions(force=true)
@Table(name = "Reference")
public class Reference extends AbstractPersistable<Long> {
    // Author of the work
    private String author;
    
    // Title of the work
    private String title;
    
    // Publisher's name
    private String publisher;
    
    // The year of publication
    private Integer year;
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }  
}