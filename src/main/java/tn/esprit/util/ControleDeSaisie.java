package tn.esprit.util;

import java.util.Arrays;
import java.util.List;

public class ControleDeSaisie {
    private static final List<String> badWords = Arrays.asList("esprit", "prepa", "badword");

    public static boolean containsBadWords(String input) {
        // Convertir l'entrée en minuscules pour éviter les casse
        String lowercaseInput = input.toLowerCase();

        // Diviser l'entrée en mots
        String[] words = lowercaseInput.split("\\s+"); // Séparer par des espaces, des tabulations, etc.

        // Vérifier chaque mot
        for (String word : words) {
            // Vérifier si le mot fait partie des mots interdits
            if (badWords.contains(word)) {
                return true; // Si un mot interdit est trouvé, retourner true
            }
        }
        return false; // Si aucun mot interdit n'est trouvé, retourner false
    }
}
