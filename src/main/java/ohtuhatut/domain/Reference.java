package ohtuhatut.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
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
 * @author iilumme
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

    protected String key;
    
    protected String author;
    protected String title;
    protected String booktitle;
    protected String publisher;
    protected Integer year;
    protected String journal;
    protected String volume;

    protected String pages;
    protected String month;
    protected String note;
    protected String howpublished;
    protected String address;
    protected String series;
    protected String edition;
    protected String editor;
    protected String number;
    protected String organization;


    @Transient
    protected List<String> mandatoryFields;

    @Transient
    protected List<String> optionalFields;

    // Fields = mandatory + optional fields
    @Transient
    protected List<String> fields;

    /**
     * Create the list 'fields' which contains both mandatory and optional.
     * The key value is added to the list of mandatory fields after populating
     * the 'fields' list, as we don't want it to go the bibtex file along
     * with the other values, but only as the key of the reference
     * fields
     */
    protected void populateFields() {
        fields = new ArrayList<String>(mandatoryFields);
        fields.addAll(optionalFields);
        mandatoryFields.add("key");
    }

    /**
     * Get the value of the named field
     *
     * @return The value of the field as String. If field doesn't belong to this
     * reference type, null is returned. The field 'key' is returned always
     * as every reference has it but it's not within the list of all the fields
     * so there's a separate check for it at the start.
     */
    public String getField(String field) {
        if (!fields.contains(field) && !field.equals("key")) {
            return null;
        }

        switch (field) {
            case "author":
                return getAuthor();

            case "title":
                return getTitle();

            case "booktitle":
                return getBooktitle();
                
            case "journal":
                return getJournal();

            case "volume":
                return getVolume();

            case "year":
                return getYear() == null ? "" : "" + getYear();

            case "publisher":
                return getPublisher();
              
            case "key":
                return getKey();

            case "pages":
                return getPages();

            case "month":
                return getMonth();

            case "note":
                return getNote();

            case "howpublished":
                return getHowpublished();

            case "address":
                return "" + getAddress();

            case "series":
                return getSeries();

            case "edition":
                return getEdition();

            case "editor":
                return getEditor();

            case "number":
                return "" + getNumber();

            case "organization":
                return getOrganization();


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
        HashMap<String, String> valueMap = new HashMap<>();

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
    
    /*
     * Copy fields from a given reference
     */
    public void copyFields(Reference reference) {
        for (String field : fields) {
            switch(field) {
                case "author":
                    this.setAuthor(reference.getAuthor());
                    break;

                case "title":
                    this.setTitle(reference.getTitle());
                    break;

                case "booktitle":
                    this.setBooktitle(reference.getBooktitle());
                    break;

                case "journal":
                    this.setJournal(reference.getJournal());
                    break;

                case "volume":
                    this.setVolume(reference.getVolume());
                    break;

                case "year":
                    this.setYear(reference.getYear());
                    break;

                case "publisher":
                    this.setPublisher(reference.getPublisher());
                    break;

                case "key":
                    this.setKey(reference.getKey());
                    break;

                case "pages":
                    this.setPages(reference.getPages());
                    break;

                case "month":
                    this.setMonth(reference.getMonth());
                    break;

                case "note":
                    this.setNote(reference.getNote());
                    break;

                case "howpublished":
                    this.setHowpublished(reference.getHowpublished());
                    break;

                case "address":
                    this.setAddress(reference.getAddress());
                    break;

                case "series":
                    this.setSeries(reference.getSeries());
                    break;

                case "edition":
                    this.setEdition(reference.getEdition());
                    break;

                case "editor":
                    this.setEditor(reference.getEditor());
                    break;

                case "number":
                    this.setNumber(reference.getNumber());
                    break;

                case "organization":
                    this.setOrganization(reference.getOrganization());
                    break;
            }
        }
        
        this.setKey(reference.getKey());
        this.setId(reference.getId());
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
    
    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
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
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getHowpublished() {
        return howpublished;
    }

    public void setHowpublished(String howpublished) {
        this.howpublished = howpublished;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
    
}
