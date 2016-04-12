package ohtuhatut.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
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

    protected String author;
    protected String title;
    protected String publisher;
    protected Integer year;  
    protected String journal;
    protected String volume;
     
    @Column
    @ElementCollection(targetClass=String.class)
    protected List<String> fields;
    
    public List<String> getFields() {
        return fields;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getJournal() {
        return journal;
    }

    public String getVolume() {
        return volume;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {;
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