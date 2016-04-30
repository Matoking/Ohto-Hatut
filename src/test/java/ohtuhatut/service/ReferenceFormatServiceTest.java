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

public class ReferenceFormatServiceTest {

    @Autowired
    private ReferenceFormatService referenceFormatService;

    public ReferenceFormatServiceTest() {

    }

    @Test
    public void specialCharactersAreFormattedProperly() {

        List<Reference> references = new ArrayList<>();
        Reference ref = new ManualReference();
        

        ref.setKey("key1");
        ref.setTitle("TäTöTå");
        references.add(ref);
        
        System.out.println(references);
        System.out.println(referenceFormatService.formatReferencesToBibTeX(references));
    }

}
