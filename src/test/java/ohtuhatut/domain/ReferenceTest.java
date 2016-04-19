package ohtuhatut.domain;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

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

        assertTrue(ref.getEmptyMandatoryFields().size() == 1);
        assertTrue(ref.getEmptyMandatoryFields().contains("year"));
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

    }

}
