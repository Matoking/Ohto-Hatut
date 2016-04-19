/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuhatut.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ohtuhatut.domain.Reference;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXFormatter;
import org.jbibtex.Key;
import org.jbibtex.StringValue;
import org.springframework.stereotype.Service;

/**
 *
 * @author matoking
 */
@Service
public class ReferenceFormatService {
    public String formatReferencesToBibTeX(List<Reference> references) {
        StringWriter writer = new StringWriter();
        BibTeXDatabase database = new BibTeXDatabase();
        BibTeXFormatter formatter = new BibTeXFormatter();
        
        for (Reference reference : references) {
            database.addObject(getBibTeXEntry(reference));
        }
        
        try {
            formatter.format(database, writer);
        } catch (IOException e) {
            System.out.println("Failed");
            return "failed to format";
        }
        
        return writer.toString();
    }
    
    private BibTeXEntry getBibTeXEntry(Reference reference) {
        // TODO: Each entry contains a key used to cite or cross-reference the entry
        // For now, put title as that key
        BibTeXEntry bibEntry = new BibTeXEntry(getTypeKey(reference.getType()),
                                               new Key(reference.getField("title")));
        
        for (Map.Entry<String, String> entry : reference.getValueMap().entrySet()) {
            // TODO: Numbers (eg. year of publication) should not be quoted
            bibEntry.addField(getFieldKey(entry.getKey()), new StringValue(entry.getValue(), StringValue.Style.QUOTED));
        }
        
        return bibEntry;
    }
    
    private Key getFieldKey(String field) {
        Map<String, Key> keys = new HashMap<String, Key>();
        keys.put("author", BibTeXEntry.KEY_AUTHOR);
        keys.put("title", BibTeXEntry.KEY_TITLE);
        keys.put("journal", BibTeXEntry.KEY_JOURNAL);
        keys.put("volume", BibTeXEntry.KEY_VOLUME);
        keys.put("year", BibTeXEntry.KEY_YEAR);
        keys.put("publisher", BibTeXEntry.KEY_PUBLISHER);
        keys.put("booktitle", BibTeXEntry.KEY_BOOKTITLE);

        return keys.get(field);
    }
    
    private Key getTypeKey(String type) {
        Map<String, Key> keys = new HashMap<String, Key>();
        keys.put("article", BibTeXEntry.TYPE_ARTICLE);
        keys.put("book", BibTeXEntry.TYPE_BOOK);
        keys.put("booklet", BibTeXEntry.TYPE_BOOKLET);
        keys.put("manual", BibTeXEntry.TYPE_MANUAL);
        keys.put("inproceedings", BibTeXEntry.TYPE_INPROCEEDINGS);
        
        return keys.get(type);
    }
}
