package com.jsrdev.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.jsrdev.screenmatch.utils.Env;

public class GeminiAIService {
    public static String getTranslation(String text) {
        Client client = new Client.Builder()
                .apiKey(Env.GEMINI_API_KEY)
                .build();

        String prompt = "Translate the following text into Spanish, only translate: " + text;

        try {
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",// used Gemini model
                            prompt,
                            null); // Added more configurations.

            if (response != null && response.text() != null) {
                return response.text();
            } else {
                System.err.println("Gemini API did not return text to translate.");
            }
        } catch (Exception e) {
            System.err.println("Text translation error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
