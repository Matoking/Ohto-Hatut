package model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * The base Reference class that includes whatever fields necessary to support categories;
 * the said categories will decide which fields are mandatory/optional
 * 
 * @author matoking
 */
@Entity
@Table(name = "Reference")
public class Reference extends AbstractPersistable<Long> {
    // Category of this reference (eg. book, article...)
    // This is set by the category (eg. BookReference) extending this base class
    private String category;
    
    // Author of the work
    private String author;

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
    
    // Title of the work
    private String title;
    
    // Publisher's name
    private String publisher;
    
    // The year of publication
    private Integer year;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}