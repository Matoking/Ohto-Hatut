package ohtuhatut.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DiscriminatorOptions;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * The base Reference class that includes whatever fields necessary to support
 * categories; the said categories will decide which fields are
 * mandatory/optional
 *
 * @author matoking
 * @author tuomokar
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "category")
@DiscriminatorValue("generic")
@DiscriminatorOptions(force = true)
@Table(name = "Reference")
public class Reference extends AbstractPersistable<Long> {

    @Transient
    protected String type;

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

    /**
     * Get the value of the named field
     *
     * @return The value of the field as String. If field doesn't belong to this
     * reference type, null is returned
     */
    public String getField(String field) {
        if (!fields.contains(field)) {
            return null;
        }

        switch (field) {
            case "author":
                return getAuthor();

            case "title":
                return getTitle();

            case "journal":
                return getJournal();

            case "volume":
                return getVolume();

            case "year":
                return "" + getYear();

            case "publisher":
                return getPublisher();

            default:
                return null;
        }
    }

    /**
     * Get a map of field and value pairs
     *
     * @return Map of field and value pairs Optional fields that are empty are
     * omitted
     */
    public Map<String, String> getValueMap() {
        HashMap<String, String> valueMap = new HashMap<String, String>();

        for (String field : getFields()) {
            String value = getField(field);
            if (value != null) {
                valueMap.put(field, value);
            }
        }

        return valueMap;
    }

    /**
     * Checks if any mandatory field is empty, and if there are any such, 
     * returns those empty fields. The year variable is an integer, so
     * for that there is a null check if it in the list of the mandatory 
     * attributes. Note that the first null check isn't enough to check for 
     * the integer, because the getField method returns it as a string
     * 
     * @return list of empty fields
     */
    public List<String> getEmptyMandatoryFields() {
        List<String> emptyFields = new ArrayList<>();

        getMandatoryFields().stream().forEach((field) -> {
            String value = getField(field);
            
            if (value == null || value.isEmpty() || (field.equals("year") && year == null)) {
                emptyFields.add(field);
            }
        });
        
        return emptyFields;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
