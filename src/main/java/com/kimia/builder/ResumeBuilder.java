package com.kimia.builder;

import com.kimia.service.CohereService;
import com.kimia.service.FileService;
import com.kimia.service.PromptService;

public class ResumeBuilder {
    public static void main(String[] args) {
        System.out.println("Starting Smart CV Generator...");
        System.out.println("Initializing services...");

        PromptService promptService = new PromptService();
        CohereService cohereService = new CohereService();

        System.out.println("Loading base prompt...");
        String basePrompt = promptService.getBasePrompt();

        System.out.println("Loading user information...");
        String information = promptService.getUserInformation();

        System.out.println("Generating resume content using Cohere...");
        String resumeContent = cohereService.generateResume(information, basePrompt);

        System.out.println("Generating file name from user info...");
        String fileName = cohereService.generateFileName(information);

        System.out.println("Creating markdown file...");
        String markdownPath = FileService.createMarkdown(fileName, resumeContent);

        System.out.println("Creating Word document...");
        FileService.createWordFile(fileName, markdownPath);

        System.out.println("Creating PDF document...");
        FileService.createPDFFile(fileName, markdownPath);

        System.out.println("Resume generation complete.");
    }
}