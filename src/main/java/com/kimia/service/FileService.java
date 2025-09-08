package com.kimia.service;

import com.kimia.converter.MarkdownConverter;
import com.kimia.converter.SpireWatermarkRemover;
import com.spire.doc.FileFormat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class FileService {
    public static final String BASE_PROMPT_PATH = "com/kimia/prompt/base-prompt.txt";
    public static final String INFO_EN_PATH = "/info/info-en.txt";
    public static final String INFO_FA_PATH = "/info/info-fa.txt";

    public static String createMarkdown(String fileName, String content) {
        String path = "/generated-CVs/markdown/" + fileName + ".md";
        path = resolveExternalPath(path);
        deleteIfExists(path);

        System.out.println("Creating markdown file at: " + path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            try {
                Files.createFile(Paths.get(path));
            } catch (FileAlreadyExistsException fileAlreadyExistsException) {
                System.out.println("File already exists. Replacing it ...");
            }
            content = content.replace("–", "-");
            writer.write(content);
        } catch (Exception e) {
            System.out.println("Failed to create markdown file.");
            throw new RuntimeException(e);
        }

        System.out.println("Markdown file created.");
        return path;
    }

    public static void createWordFile(String fileName, String mdPath) {
        String path = "/generated-CVs/word/" + fileName + ".docx";
        path = resolveExternalPath(path);
        deleteIfExists(path);
        System.out.println("Creating Word document...");
        MarkdownConverter.convert(mdPath, path, FileFormat.Docx);
        System.out.println("Word file created.");
        SpireWatermarkRemover.removeWordWatermark(path);
    }

    public static void createPDFFile(String fileName, String mdPath) {
        String path = "/generated-CVs/pdf/" + fileName + ".pdf";
        path = resolveExternalPath(path);
        deleteIfExists(path);
        System.out.println("Creating PDF document...");
        MarkdownConverter.convert(mdPath, path, FileFormat.PDF);
        System.out.println("PDF file created.");

        SpireWatermarkRemover.removePDFWatermark(path);
    }

    private static void deleteIfExists(String pathStr) {
        File file = new File(pathStr);
        if (file.exists()) {
            System.out.println("Cleaning up existing file...");
            file.delete();
        }
    }

    public static String resolveExternalPath(String relativePath) {
        String jarDir = new File(System.getProperty("java.class.path")).getAbsoluteFile().getParent();
        try {
            return Paths.get(jarDir, relativePath).toString();
        } catch (InvalidPathException e) {
            //This is meant for debug mode
            return Paths.get("D://personal-projects/cv-builder/" + relativePath).toString();
        }
    }

    public static String readResourceAsString(String path) {
        try (InputStream inputStream = FileService.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + path);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + path, e);
        }
    }
}