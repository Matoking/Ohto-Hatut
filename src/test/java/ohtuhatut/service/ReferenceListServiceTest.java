package ohtuhatut.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import ohtuhatut.Main;
import ohtuhatut.domain.BookletReference;
import ohtuhatut.domain.Reference;
import ohtuhatut.domain.ReferenceList;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
public class ReferenceListServiceTest {

    @Autowired
    private ReferenceListService referenceListService;

    @Autowired
    private ReferenceService referenceService;

    public ReferenceListServiceTest() {
    }

    @Transactional
    @Test
    public void removeReferenceMethodWorksCorrectly() {
        ReferenceList list = new ReferenceList();
        list.setName("test");
        referenceListService.save(list);

        BookletReference br = new BookletReference();
        br.setKey("key");
        br.setTitle("title");
        referenceService.saveReference(br);
        list.getReferences().add(br);

        BookletReference br2 = new BookletReference();
        br2.setKey("key2");
        br2.setTitle("title2");
        referenceService.saveReference(br2);
        list.getReferences().add(br2);

        BookletReference br3 = new BookletReference();
        br3.setKey("key3");
        br3.setTitle("title3");
        referenceService.saveReference(br3);
        list.getReferences().add(br3);

        referenceListService.save(list);
        assertTrue(list.getReferences().size() == 3);

        referenceListService.removeReference(br);
        assertTrue(list.getReferences().size() == 2);
        assertFalse(list.getReferences().contains(br));

        referenceService.deleteReference(br);
        referenceService.deleteReference(br2);
        referenceService.deleteReference(br3);

        referenceListService.deleteReferenceList(list);
    }

    @Transactional
    @Test
    public void removeReferenceMethodDoesNotRemoveOtherReferences() {
        ReferenceList list = new ReferenceList();
        list.setName("test");
        referenceListService.save(list);

        BookletReference br = new BookletReference();
        br.setKey("key");
        br.setTitle("title");
        referenceService.saveReference(br);
        list.getReferences().add(br);

        BookletReference br2 = new BookletReference();
        br2.setKey("key2");
        br2.setTitle("title2");
        referenceService.saveReference(br2);
        list.getReferences().add(br2);

        BookletReference br3 = new BookletReference();
        br3.setKey("key3");
        br3.setTitle("title3");
        referenceService.saveReference(br3);

        referenceListService.save(list);
        assertTrue(list.getReferences().size() == 2);

        referenceListService.removeReference(br3);
        assertTrue(list.getReferences().size() == 2);

        referenceService.deleteReference(br);
        referenceService.deleteReference(br2);
        referenceService.deleteReference(br3);

        referenceListService.deleteReferenceList(list);
    }
}