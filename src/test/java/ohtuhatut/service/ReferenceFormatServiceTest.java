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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
public class ReferenceFormatServiceTest {

    @Autowired
    private ReferenceFormatService referenceFormatService;

    public ReferenceFormatServiceTest() {

    }

    @Test
    public void umlautAIsFormatterProperly() {

        List<Reference> references = new ArrayList<>();
        Reference ref = new ManualReference();

        ref.setKey("key1");
        ref.setTitle("tÄtä");
        references.add(ref);

        assertTrue(referenceFormatService.formatReferencesToBibTeX(references).contains("t{\\\"A}t{\\\"a}"));
    }

    @Test
    public void umlautOIsFormatterProperly() {

        List<Reference> references = new ArrayList<>();
        Reference ref = new ManualReference();

        ref.setKey("key1");
        ref.setTitle("tÖtö");
        references.add(ref);

        assertTrue(referenceFormatService.formatReferencesToBibTeX(references).contains("t{\\\"O}t{\\\"o}"));
    }

    @Test
    public void swedishAIsFormatterProperly() {

        List<Reference> references = new ArrayList<>();
        Reference ref = new ManualReference();

        ref.setKey("key1");
        ref.setTitle("tÅtå");
        ref.setYear(1999);
        references.add(ref);

        assertTrue(referenceFormatService.formatReferencesToBibTeX(references).contains("t{\\AA}t{\\aa}"));
    }

}
