package com.HaritMitraBack.mitra.utils;

public class LanguageUtil {

    public static String detectLanguage(String text) {

        // Hindi
        if (text.matches(".*[अ-ह].*")) return "hi";

        // Marathi (same script but we treat as hi fallback)
        if (text.matches(".*[ऀ-ॿ].*")) return "mr";

        // Gujarati
        if (text.matches(".*[઀-૿].*")) return "gu";

        // Kannada
        if (text.matches(".*[ಅ-ಹ].*")) return "kn";

        // Malayalam
        if (text.matches(".*[അ-ഹ].*")) return "ml";

        // Default English
        return "en";
    }
}