package ohtuhatut.domain;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tuomokar
 */
public class ReferenceTest {

    @Test
    public void gettingEmptyFieldsWorksWithOneEmptyOrNullField() {
        Reference ref = new BookReference();
        ref.setAuthor("author1");
        ref.setTitle("title1");
        ref.setPublisher("publisher1");

        assertTrue(ref.getEmptyMandatoryFields().size() == 2);
        assertTrue(ref.getEmptyMandatoryFields().contains("year"));
        assertTrue(ref.getEmptyMandatoryFields().contains("key"));
    }

    @Test
    public void gettingEmptyFieldsWorksWhenAllFieldsAreEmpty() {
        Reference ref = new ArticleReference();

        assertTrue(ref.getEmptyMandatoryFields().size() == ref.getMandatoryFields().size());
        assertTrue(ref.getEmptyMandatoryFields().contains("author"));
        assertTrue(ref.getEmptyMandatoryFields().contains("title"));
        assertTrue(ref.getEmptyMandatoryFields().contains("journal"));
        assertTrue(ref.getEmptyMandatoryFields().contains("year"));
        assertTrue(ref.getEmptyMandatoryFields().contains("volume"));
        assertTrue(ref.getEmptyMandatoryFields().contains("key"));

    }

    @Test
    public void gettingFieldWhichIsNotAFieldOfTheReference() {
        Reference ref = new BookletReference();
        assertTrue(ref.getField("author") == null);
        assertTrue(ref.getField("publisher") == null);
        assertTrue(ref.getField("moon") == null);
    }

    @Test
    public void gettingMandatoryFieldsWorks() {
        Reference ref = new BookReference();

        assertTrue(ref.getMandatoryFields().size() == 5);
        assertTrue(ref.getMandatoryFields().contains("author"));
        assertTrue(ref.getMandatoryFields().contains("title"));
        assertTrue(ref.getMandatoryFields().contains("publisher"));
        assertTrue(ref.getMandatoryFields().contains("year"));
        assertTrue(ref.getMandatoryFields().contains("key"));
    }

    @Test
    public void settingMandatoryFieldsWorks() {
        Reference ref = new BookletReference();
        List<String> fields = new ArrayList<>();

        fields.add("testField");
        ref.setMandatoryFields(fields);

        assertTrue(ref.getMandatoryFields().size() == 1);
        assertTrue(ref.getMandatoryFields().contains("testField"));
    }

    @Test
    public void gettingAndSettingOptionalFieldsWorks() {

        Reference ref = new BookletReference();
        List<String> fields = new ArrayList<>();

        fields.add("testField");
        ref.setOptionalFields(fields);

        assertTrue(ref.getOptionalFields().size() == 1);
        assertTrue(ref.getOptionalFields().contains("testField"));

    }

    @Test
    public void settingFieldsWorks() {
        Reference ref = new BookletReference();
        List<String> fields = new ArrayList<>();

        fields.add("testField");
        ref.setFields(fields);

        assertTrue(ref.getFields().size() == 1);
        assertTrue(ref.getFields().contains("testField"));
    }
    
    @Test
    public void keyIsNotInTheListOfAllFields() {
        Reference ref = new BookletReference();
        List<String> fields = new ArrayList<>();

        fields.add("testField");
        ref.setFields(fields);

        assertTrue(ref.getFields().size() == 1);
        assertTrue(!ref.getFields().contains("key"));
    }
    
    @Test
    public void keyIsNotInTheListOfOptionalFields() {
        Reference ref = new BookletReference();
        List<String> fields = new ArrayList<>();

        fields.add("testField");
        ref.setOptionalFields(fields);

        assertTrue(ref.getOptionalFields().size() == 1);
        assertTrue(!ref.getOptionalFields().contains("key"));
    }
    
    @Test
    public void keyIsNotInTheListOfMandatoryFieldsAfterSettingTheMandatoryFields() {
        Reference ref = new BookletReference();
        List<String> fields = new ArrayList<>();

        fields.add("testField");
        ref.setMandatoryFields(fields);

        assertTrue(ref.getMandatoryFields().size() == 1);
        assertTrue(!ref.getMandatoryFields().contains("key"));
    }

}
