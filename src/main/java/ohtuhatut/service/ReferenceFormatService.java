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
import org.jbibtex.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author matoking
 * @author tuomokar
 * @author iilumme
 */
@Service
public class ReferenceFormatService {
    
    private Map<String, Key> keys;
    
    public ReferenceFormatService() {
        keys = new HashMap<>();
        
        fillAttributeKeys();
        fillTypeKeys();
    }
    
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

        BibTeXEntry bibEntry = new BibTeXEntry(getTypeKey(reference.getType()),
                                               new Key(reference.getKey()));
        
        for (Map.Entry<String, String> entry : reference.getValueMap().entrySet()) {
            addEntry(entry.getKey(), entry.getValue(), bibEntry);
        }
        
        return bibEntry;
    }
    
    private void addEntry(String field, String value, BibTeXEntry bibEntry) {
        if (value == null || value.isEmpty()) {
            return;
        }
        
        if (field.equals("year")) {
            bibEntry.addField(getFieldKey(field), new DigitStringValue(value));
        } else {
            String formattedValue = replaceSpecialCharactersToBibtex(value);
            bibEntry.addField(getFieldKey(field), new StringValue(formattedValue, StringValue.Style.QUOTED));
        }
    }
    
    private String replaceSpecialCharactersToBibtex(String value) {
        String lowerCaseA = value.replace("ä", "{\\\"a}");
        String upperCaseA = lowerCaseA.replace("Ä", "{\\\"A}");
        String lowerCaseO = upperCaseA.replace("ö", "{\\\"o}");
        String upperCaseO = lowerCaseO.replace("Ö", "{\\\"O}");
        String lowerCase2ndA = upperCaseO.replace("å", "{\\aa}");
        String upperCase2ndA = lowerCase2ndA.replace("Å", "{\\AA}");
        return upperCase2ndA;
    }
    
    private Key getFieldKey(String field) {
        return keys.get(field);
    }
    
    private Key getTypeKey(String type) {     
        return keys.get(type);
    }
    
    private void fillAttributeKeys() {
        keys.put("author", BibTeXEntry.KEY_AUTHOR);
        keys.put("title", BibTeXEntry.KEY_TITLE);
        keys.put("journal", BibTeXEntry.KEY_JOURNAL);
        keys.put("volume", BibTeXEntry.KEY_VOLUME);
        keys.put("year", BibTeXEntry.KEY_YEAR);
        keys.put("publisher", BibTeXEntry.KEY_PUBLISHER);
        keys.put("booktitle", BibTeXEntry.KEY_BOOKTITLE);
        keys.put("pages", BibTeXEntry.KEY_PAGES);
        keys.put("month", BibTeXEntry.KEY_MONTH);
        keys.put("note", BibTeXEntry.KEY_NOTE);
        keys.put("key", BibTeXEntry.KEY_KEY);
        keys.put("howpublished", BibTeXEntry.KEY_HOWPUBLISHED);
        keys.put("address", BibTeXEntry.KEY_ADDRESS);
        keys.put("series", BibTeXEntry.KEY_SERIES);
        keys.put("edition", BibTeXEntry.KEY_EDITION);
        keys.put("editor", BibTeXEntry.KEY_EDITOR);
        keys.put("number", BibTeXEntry.KEY_NUMBER);
        keys.put("organization", BibTeXEntry.KEY_ORGANIZATION);
    }
    
    private void fillTypeKeys() {
        keys.put("article", BibTeXEntry.TYPE_ARTICLE);
        keys.put("book", BibTeXEntry.TYPE_BOOK);
        keys.put("booklet", BibTeXEntry.TYPE_BOOKLET);
        keys.put("manual", BibTeXEntry.TYPE_MANUAL);
        keys.put("inproceedings", BibTeXEntry.TYPE_INPROCEEDINGS);
    }
}
