package com.kimia.converter;

import com.kimia.converter.spire.SpireConverter;
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

public class MarkdownConverter {
    public static void convert(String mdFileName, String wordFileName, FileFormat fileFormat) {
        SpireConverter.convert(mdFileName, wordFileName, fileFormat);
    }




}