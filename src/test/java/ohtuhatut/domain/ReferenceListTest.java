package ohtuhatut.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

public class ReferenceListTest {

    @Test
    public void gettingAndSettingNameWorks() {
        ReferenceList list = new ReferenceList();
        list.setName("test");

        assertTrue(list.getName().equals("test"));
    }

    @Test
    public void gettingAndSettingReferencesWorks() {

        ReferenceList list = new ReferenceList();
        List<Reference> references = new ArrayList<>();

        BookletReference br = new BookletReference();
        br.setTitle("test");
        references.add(br);

        assertTrue(list.getReferences().size() == 0);


        list.setReferences(references);
        assertTrue(list.getReferences().size() == 1);

    }

    @Test
    public void removeReferenceMethodWorks() {

        ReferenceList list = new ReferenceList();
        List<Reference> references = new ArrayList<>();

        BookletReference br = new BookletReference();
        br.setTitle("test");
        references.add(br);
        list.setReferences(references);
        assertTrue(list.getReferences().size() == 1);

        list.removeReference(br);
        assertTrue(list.getReferences().size() == 0);
    }
}
