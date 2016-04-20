package ohtuhatut.domain;

import java.util.ArrayList;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "Reference")
@DiscriminatorValue("inproceedings")
@Entity
public class InproceedingsReference extends Reference{
    public InproceedingsReference() {
        setType("inproceedings");
        
        mandatoryFields = new ArrayList<>();
        mandatoryFields.add("author");
        mandatoryFields.add("title");
        mandatoryFields.add("booktitle");
        mandatoryFields.add("year");
        
        optionalFields = new ArrayList<>();
        optionalFields.add("editor");
        optionalFields.add("volume");
        optionalFields.add("series");
        optionalFields.add("pages");
        optionalFields.add("address");
        optionalFields.add("month");
        optionalFields.add("organization");
        optionalFields.add("publisher");
        optionalFields.add("note");
        
        populateFields();
    }
}
