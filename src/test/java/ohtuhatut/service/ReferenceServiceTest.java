package ohtuhatut.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import ohtuhatut.Main;
import ohtuhatut.domain.ArticleReference;
import ohtuhatut.domain.BookReference;
import ohtuhatut.domain.BookletReference;
import ohtuhatut.domain.InproceedingsReference;
import ohtuhatut.domain.ManualReference;
import ohtuhatut.domain.Reference;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
        ref.setKey("key1");

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
        ref.setKey("key2");

        assertEquals("author and title are empty!", referenceService.getErrorMessages(ref.getEmptyMandatoryFields()));
    }

    @Test
    public void errorMessageIsCorrectWithThreeEmptyFields() {
        Reference ref = new BookReference();
        ref.setAuthor("");
        ref.setTitle("");
        ref.setPublisher("");
        ref.setYear(1900);
        ref.setKey("key3");

        assertEquals("author, title and publisher are empty!", referenceService.getErrorMessages(ref.getEmptyMandatoryFields()));
    }

    @Test
    public void errorMessageIsCorrectWithOverThreeEmptyFields() {
        Reference ref = new BookReference();
        ref.setAuthor("");
        ref.setTitle("");
        ref.setPublisher("");
        ref.setKey("");
        ref.setYear(null);
        

        assertEquals("author, title, publisher, year and key are empty!", referenceService.getErrorMessages(ref.getEmptyMandatoryFields()));
    }

    @Test
    public void nullIsReturnedIfEmptyListIsGiven() {
        assertNull(referenceService.getErrorMessages(new ArrayList<String>()));
    }
    
    @Test
    public void correctMandatoryFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithBookAsParameter() {
        Reference reference = new BookReference();
        List<String> types = referenceService.getMandatoryFields("book");
        
        assertEquals(types, reference.getMandatoryFields());
    }
    
    @Test
    public void correctMandatoryFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithArticleAsParameter() {
        Reference reference = new ArticleReference();
        List<String> types = referenceService.getMandatoryFields("article");
        
        assertEquals(types, reference.getMandatoryFields());
    }
    
    @Test
    public void correctMandatoryFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithBookletAsParameter() {
        Reference reference = new BookletReference();
        List<String> types = referenceService.getMandatoryFields("booklet");
        
        assertEquals(types, reference.getMandatoryFields());
    }
    
    @Test
    public void correctMandatoryFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithInproceedingsAsParameter() {
        Reference reference = new InproceedingsReference();
        List<String> types = referenceService.getMandatoryFields("inproceedings");
        
        assertEquals(types, reference.getMandatoryFields());
    }
    
    @Test
    public void correctMandatoryFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithManualAsParameter() {
        Reference reference = new ManualReference();
        List<String> types = referenceService.getMandatoryFields("manual");
        
        assertEquals(types, reference.getMandatoryFields());
    }
    
    @Test
    public void correctOptionalFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithBookAsParameter() {
        Reference reference = new BookReference();
        List<String> types = referenceService.getOptionalFields("book");
        
        assertEquals(types, reference.getOptionalFields());
    }
    
    @Test
    public void correctOptionalFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithArticleAsParameter() {
        Reference reference = new ArticleReference();
        List<String> types = referenceService.getOptionalFields("article");
        
        assertEquals(types, reference.getOptionalFields());
    }
    
    @Test
    public void correctOptionalFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithBookletAsParameter() {
        Reference reference = new BookletReference();
        List<String> types = referenceService.getOptionalFields("booklet");
        
        assertEquals(types, reference.getOptionalFields());
    }
    
    @Test
    public void correctOptionalFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithInproceedingsAsParameter() {
        Reference reference = new InproceedingsReference();
        List<String> types = referenceService.getOptionalFields("inproceedings");
        
        assertEquals(types, reference.getOptionalFields());
    }
    
    @Test
    public void correctOptionalFieldsAreReturnedWhenCallingMethodGetMandatoryFieldsWithManualAsParameter() {
        Reference reference = new ManualReference();
        List<String> types = referenceService.getOptionalFields("manual");
        
        assertEquals(types, reference.getOptionalFields());
    }
    
    @Test
    public void theMethodTypeIsNotKnownReturnsTrueWhenNoReferenceHasTheGivenType() {
        assertTrue(referenceService.typeIsNotKnown("randomTypeHere"));
    }
    
    @Test
    public void theMethodTypeIsNotKnownReturnsFalseWhenGivingTheTypeOfAManualReference() {
        assertFalse(referenceService.typeIsNotKnown("manual"));
    }
    
    @Test
    public void theMethodTypeIsNotKnownReturnsFalseWhenGivingTheTypeOfAnInproceedingsReference() {
        assertFalse(referenceService.typeIsNotKnown("inproceedings"));
    }
    
    @Test
    public void theMethodTypeIsNotKnownReturnsFalseWhenGivingTheTypeOfABookReference() {
        assertFalse(referenceService.typeIsNotKnown("book"));
    }
    
    @Test
    public void theMethodTypeIsNotKnownReturnsFalseWhenGivingTheTypeOfABookletReference() {
        assertFalse(referenceService.typeIsNotKnown("booklet"));
    }
    
    @Test
    public void theMethodTypeIsNotKnownReturnsFalseWhenGivingTheTypeOfAnArticleReference() {
        assertFalse(referenceService.typeIsNotKnown("article"));
    }
    
    @Test
    public void tryingToFindAReferenceByKeyThatIsAlreadyUsedReturnsTrue() {
        Reference ref = new Reference();
        ref.setKey("key1");
        
        referenceService.saveReference(ref);
        
        assertTrue(referenceService.referenceKeyAlreadyUsed(ref.getKey()));   
    }
    
    @Test
    public void tryingToFindByReferenceByKeyThatHasNotBeenUsedReturnsFalse() {    
        assertFalse(referenceService.referenceKeyAlreadyUsed("keyNotUsed"));   
    }
    
    @Test
    public void errorMessageIsReturnedCorrectlyWhenKeyHasBeenUsed() {
        Reference ref = new Reference();
        ref.setKey("key2");
        
        referenceService.saveReference(ref);
        
        assertEquals("That key has already been used, please use another key", 
                referenceService.keyNotUniqueErrorMessage(ref.getKey()));
    }
    
    @Test
    public void errorMessageIsReturnedCorrectlyWhenKeyHasNotBeenUsed() {
        assertNull(referenceService.keyNotUniqueErrorMessage("keyNotUsed"));
    }
    
    @Test
    public void tryingToFindByReferenceKeyUsedOnSomeOtherReferenceReturnsTrue() {
        Reference ref1 = new Reference();
        ref1.setKey("key3");
        Reference ref2 = new Reference();
        ref2.setKey("key4");
        
        referenceService.saveReference(ref1);
        referenceService.saveReference(ref2);
        
        ref2.setKey("key3");
        
        assertTrue(referenceService.referenceKeyAlreadyUsedOnSomeOtherReference(ref2));
    }
    
    @Test
    public void tryingToFindByReferenceKeyThatIsNotUsedOnAnyOtherReferenceReturnsFalse() {
        Reference ref1 = new Reference();
        ref1.setKey("key5");
        Reference ref2 = new Reference();
        ref2.setKey("key6");
        
        referenceService.saveReference(ref1);
        referenceService.saveReference(ref2);
        
        assertFalse(referenceService.referenceKeyAlreadyUsedOnSomeOtherReference(ref2));
    }
    
    @Test
    public void seeingIfAnyOtherReferenceHasTheSameKeyReturnsFalseWhenThereAreNoOtherReferences() {
        Reference ref = new Reference();
        ref.setKey("key7");

        referenceService.saveReference(ref);
        
        assertFalse(referenceService.referenceKeyAlreadyUsedOnSomeOtherReference(ref));
    }
    
    @Test
    public void whenKeyIsInUseBySomeOtherReferenceTheErrorMessageIsReturned() {
        Reference ref = new Reference();
        ref.setKey("key8");
        
        referenceService.saveReference(ref);
        
        Reference ref2 = new Reference();
        ref2.setKey("key8");
        
        assertEquals("That key is in use on another reference, please use another key", 
                referenceService.keyIsInUseOnSomeOtherReferenceErrorMessage(ref2));
    }
    
    @Test
    public void whenKeyIsNotInUseByAnyOtherReferenceNullIsReturned() {
        Reference ref = new Reference();
        ref.setKey("key9");
        
        referenceService.saveReference(ref);
        
        assertNull(referenceService.keyIsInUseOnSomeOtherReferenceErrorMessage(ref));
    }

}
