package com.kimia.converter.spire;

import com.spire.doc.Document;
import com.spire.doc.DocumentObject;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.collections.DocumentObjectCollection;
import com.spire.doc.collections.ParagraphCollection;
import com.spire.doc.collections.SectionCollection;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;
import com.spire.doc.formatting.CharacterFormat;
import com.spire.doc.formatting.ParagraphFormat;

public class SpireConverter {
    public static void convert(String mdFileName, String wordFileName, FileFormat fileFormat) {
        Document document = new Document();

        document.loadFromFile(mdFileName, FileFormat.Markdown);

        setRtlDirection(wordFileName, document);

        document.saveToFile(wordFileName, fileFormat);
        document.dispose();
    }

    private static void setRtlDirection(String wordFileName, Document document) {
        if (wordFileName.contains("-fa.")) {
            String rtlFont = "Vazir"; // Use a Farsi-compatible font installed on your system

            SectionCollection sections = document.getSections();
            for (Object sectionObj : sections) {
                Section section = (Section) sectionObj;
                ParagraphCollection paragraphs = section.getParagraphs();
                for (Object paragraphObj : paragraphs) {
                    Paragraph paragraph = (Paragraph) paragraphObj;
                    // Enable right-to-left layout
                    ParagraphFormat format = paragraph.getFormat();
                    format.isBidi(true);

                    // Apply font to all text ranges in the paragraph
                    DocumentObjectCollection childObjects = paragraph.getChildObjects();

                    for (Object childObjectObj : childObjects) {
                        DocumentObject childObject = (DocumentObject) childObjectObj;
                        if (childObject instanceof TextRange) {
                            TextRange textRange = (TextRange) childObject;
                            CharacterFormat characterFormat = textRange.getCharacterFormat();
                            characterFormat.setFontName("B Nazanin");
                        }
                    }
                }
            }
        }
    }
}
