package com.kimia.converter.spire;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SpireWatermarkRemover {
    private static final String watermarkText = "Evaluation Warning: The document was created with Spire.Doc for JAVA.";

    public static void removePDFWatermark(String path) {

        try (PDDocument document = PDDocument.load(new File(path))) {
            for (PDPage page : document.getPages()) {
                PDFStreamParser parser = new PDFStreamParser(page);
                parser.parse();
                List<Object> tokens = parser.getTokens();
                List<Object> cleanedTokens = new ArrayList<>();

                boolean insideTextBlock = false;
                boolean isWatermarkBlock = false;
                List<Object> tempBlock = new ArrayList<>();

                for (Object token : tokens) {
                    if (token instanceof Operator op) {

                        if (op.getName().equals("BT")) {
                            insideTextBlock = true;
                            isWatermarkBlock = false;
                            tempBlock.clear();
                            tempBlock.add(op);
                            continue;
                        }

                        if (insideTextBlock) {
                            tempBlock.add(op);

                            if (op.getName().equals("TJ")) {
                                Object prev = tempBlock.get(tempBlock.size() - 2);
                                if (prev instanceof COSArray cosArray) {
                                    for (COSBase cosBase : cosArray) {
                                        //not checking for "Attention Warning" since the complete phrase is no in a single cosBase
                                        if (cosBase.toString().contains("tion Warning")) {
                                            isWatermarkBlock = true;
                                        }
                                    }

                                }
                            }

                            if (op.getName().equals("ET")) {
                                insideTextBlock = false;
                                if (!isWatermarkBlock) {
                                    cleanedTokens.addAll(tempBlock);
                                }
                                tempBlock.clear();
                                continue;
                            }

                            continue;
                        }
                    }

                    if (insideTextBlock) {
                        tempBlock.add(token);
                    } else {
                        cleanedTokens.add(token);
                    }
                }

                PDStream newStream = new PDStream(document);
                try (OutputStream out = newStream.createOutputStream()) {
                    ContentStreamWriter writer = new ContentStreamWriter(out);
                    writer.writeTokens(cleanedTokens);
                }

                page.setContents(newStream);
            }

            document.save(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to remove watermark: " + e.getMessage(), e);
        }
    }

    public static void removeWordWatermark(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Optionally scan body paragraphs too
            for (XWPFParagraph para : document.getParagraphs()) {
                if (para.getText().contains(watermarkText)) {
                    para.removeRun(0); // crude but effective if watermark is a single run
                }
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                document.write(fos);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to remove watermark: " + e.getMessage(), e);
        }
    }

}