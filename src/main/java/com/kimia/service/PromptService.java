package com.kimia.service;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PromptService {


    public String getBasePrompt() {
        System.out.println("Base prompt resolved");
        return FileService.readResourceAsString(FileService.BASE_PROMPT_PATH);
    }

    public String getUserInformation() {
        String info = readFile(FileService.resolveExternalPath(FileService.INFO_EN_PATH));
        if (info.isEmpty()) {
            System.out.println("English info file is empty. Falling back to Farsi...");
            info = readFile(FileService.resolveExternalPath(FileService.INFO_FA_PATH));
        }
        return info;
    }

    private String readFile(String pathStr) {
        try {
            System.out.println("Reading file: " + pathStr);
            return Files.readString(Paths.get(pathStr));
        } catch (Exception e) {
            System.out.println("Failed to read file: " + pathStr);
            throw new RuntimeException(e);
        }
    }
}