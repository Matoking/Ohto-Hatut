package ohtuhatut.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    
    @Transient
    protected List<String> mandatoryFields;
    
    @Transient
    protected List<String> optionalFields;
    
    // Fields = mandatory + optional fields
    @Transient
    protected List<String> fields;

    /**
     * Create the list 'fields' which contains both mandatory and optional
     * fields
     */
    protected void populateFields() {
        fields = new ArrayList<String>(mandatoryFields);
        fields.addAll(optionalFields);
    }

    public List<String> getOptionalFields() {
        return optionalFields;
    }

    public void setOptionalFields(List<String> optionalFields) {
        this.optionalFields = optionalFields;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
    
    public List<String> getMandatoryFields() {
        return mandatoryFields;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setMandatoryFields(List<String> mandatoryFields) {
        this.mandatoryFields = mandatoryFields;
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