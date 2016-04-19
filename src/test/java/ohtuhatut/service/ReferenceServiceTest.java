package ohtuhatut.service;

import java.util.ArrayList;
import ohtuhatut.Main;
import ohtuhatut.domain.ArticleReference;
import ohtuhatut.domain.BookReference;
import ohtuhatut.domain.Reference;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author tuomokar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
public class ReferenceServiceTest {

    @Autowired
    private ReferenceService referenceService;

    public ReferenceServiceTest() {
    }

    @Test
    public void errorMessageIsCorrectWithOneEmptyField() {
        Reference ref = new BookReference();
        ref.setAuthor("");
        ref.setTitle("title1");
        ref.setPublisher("publisher1");
        ref.setYear(1900);

        assertEquals("author is empty!", referenceService.getErrorMessages(ref.getEmptyMandatoryFields()));
    }

    @Test
    public void errorMessageIsCorrectWithTwoEmptyFields() {
        Reference ref = new ArticleReference();
        ref.setAuthor("");
        ref.setTitle("");
        ref.setJournal("journal1");
        ref.setVolume("vol1");
        ref.setYear(2001);

        assertEquals("author and title are empty!", referenceService.getErrorMessages(ref.getEmptyMandatoryFields()));
    }

    @Test
    public void errorMessageIsCorrectWithThreeEmptyFields() {
        Reference ref = new BookReference();
        ref.setAuthor("");
        ref.setTitle("");
        ref.setPublisher("");
        ref.setYear(1900);

        assertEquals("author, title and publisher are empty!", referenceService.getErrorMessages(ref.getEmptyMandatoryFields()));
    }

    @Test
    public void errorMessageIsCorrectWithOverThreeEmptyFields() {
        Reference ref = new BookReference();
        ref.setAuthor("");
        ref.setTitle("");
        ref.setPublisher("");
        ref.setYear(null);

        assertEquals("author, title, publisher and year are empty!", referenceService.getErrorMessages(ref.getEmptyMandatoryFields()));
    }

    @Test
    public void nullIsReturnedIfEmptyListIsGiven() {
        assertNull(referenceService.getErrorMessages(new ArrayList<>()));
    }

}
