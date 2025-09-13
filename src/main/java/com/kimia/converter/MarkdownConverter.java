package com.kimia.converter;

import com.kimia.converter.spire.SpireConverter;
import com.spire.doc.FileFormat;

public class MarkdownConverter {
    public static void convert(String mdFileName, String wordFileName, FileFormat fileFormat) {
        SpireConverter.convert(mdFileName, wordFileName, fileFormat);
    }
}