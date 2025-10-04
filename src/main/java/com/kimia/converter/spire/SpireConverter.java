package com.kimia.converter.spire;

import com.spire.doc.*;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;
import com.spire.doc.formatting.CharacterFormat;
import com.spire.doc.formatting.ParagraphFormat;

public class SpireConverter {
    public static void convert(String mdFileName, String fileName, FileFormat fileFormat) {
        Document document = new Document();
        document.loadFromFile(mdFileName, FileFormat.Markdown);

        setRtlDirection(fileName, document);

        if (fileFormat == FileFormat.PDF) {
            ToPdfParameterList toPdfParameterList = new ToPdfParameterList();
            toPdfParameterList.isEmbeddedAllFonts(true);
            document.saveToFile(fileName, toPdfParameterList);
        } else {
            document.saveToFile(fileName, fileFormat);
        }

        document.dispose();
    }

    private static void setRtlDirection(String fileName, Document document) {
        if (fileName.contains("-fa.")) {
            String rtlFont = "B Nazanin"; // Use a reliable Farsi-compatible font

            Iterable<Section> sections = document.getSections();
            for (Section section : sections) {

                Iterable<Paragraph> paragraphs = section.getParagraphs();
                for (Paragraph paragraph : paragraphs) {
                    ParagraphFormat paragraphFormat = paragraph.getFormat();
                    //when this is set ot true fully English text is not shown (for example: - DevOps)
                    String text = paragraph.getText();
                    if (containsFarsi(text))
                        paragraphFormat.isBidi(true);
                    Iterable<DocumentObject> childObjects = paragraph.getChildObjects();
                    for (DocumentObject obj : childObjects) {
                        if (obj instanceof TextRange textRange) {
                            CharacterFormat format = textRange.getCharacterFormat();
                            format.setFontName(rtlFont);
                            if (containsFarsi(text))
                                format.setBidi(true);
                        }
                    }
                }
            }
        }
    }

    private static boolean containsFarsi(String text) {
        for (char c : text.toCharArray()) {
            if (c >= 0x0600 && c <= 0x06FF) {
                return true;
            }
        }
        return false;
    }
}

