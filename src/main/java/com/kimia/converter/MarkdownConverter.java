package com.kimia.converter;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

public class MarkdownConverter {
    public static void convert(String mdFileName, String wordFileName, FileFormat fileFormat) {
        // Create an instance of Document
        Document doc = new Document();

        // Load a Markdown file
        doc.loadFromFile(mdFileName, FileFormat.Markdown);


        // Save the Markdown file as Word document
        doc.saveToFile(wordFileName, fileFormat);
        doc.dispose();

    }

}