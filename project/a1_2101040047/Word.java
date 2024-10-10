package a1_2101040047;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Word {
    private String text;
    private String prefix;
    private String suffix;
    public static Set<String> stopWords = new HashSet<>();

    public Word(String prefix, String text, String suffix){
        this.prefix = prefix;
        this.text = text;
        this.suffix = suffix;
    }

    public static Word createWord(String rawText) {
        StringBuilder prefix = new StringBuilder();
        StringBuilder text = new StringBuilder();
        StringBuilder suffix = new StringBuilder();
        int i = extractPrefix(rawText, prefix);
        int j = extractWord(rawText, i, text);
        extractSuffix(rawText, j, suffix);
        return new Word(prefix.toString(), text.toString(), suffix.toString());
    }

    private static int extractPrefix(String rawText, StringBuilder prefix) {
        int i = 0;
        while (i < rawText.length() && !isValidWord(rawText.charAt(i))) {
            prefix.append(rawText.charAt(i));
            i++;
        }
        return i;
    }

    private static int extractWord(String rawText, int i, StringBuilder text) {
            int j = i;
            while (j < rawText.length()) {
                char ch = rawText.charAt(j);
                if (isValidWord(ch)) {
                    if (onComma(ch, j, i) || onDot(ch, j, rawText)) {
                        return j;
                    }
                    text.append(ch);
                } else {
                    return j;
                }
                j++;
            }
            return rawText.length();
        }

        private static void extractSuffix(String rawText, int j, StringBuilder suffix) {
        for (int k = j; k < rawText.length(); k++) {
            suffix.append(rawText.charAt(k));
        }
    }

    private static boolean onComma(char ch, int currentPos, int startPos) {
        return ch == ',' && currentPos > startPos + 1;
    }

    private static boolean onDot(char ch, int currentPos, String rawText) {
        return ch == '.' && !Character.isDigit(rawText.charAt(currentPos - 1)) && (rawText.length() - currentPos < 3);
    }

    public static boolean isValidWord(char ch) {
        return Character.isLowerCase(ch) ||
                Character.isUpperCase(ch) ||
                Character.isDigit(ch) ||
                ch == ',' ||
                ch == '.' ||
                ch == '-';
    }

    public boolean isKeyword() {
        String text = this.getText().toLowerCase();
        if (text.isEmpty() || stopWords.contains(text)) {
            return false;
        }
        int i = 0;
        boolean hasDigit = false;
        while (i < text.length() && !hasDigit) {
            hasDigit = Character.isDigit(text.charAt(i)) ? true : hasDigit;
            i++;
        }
        return !hasDigit;
    }

    public String getText() {
        return this.text;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public static boolean loadStopWords(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines()
                    .map(String::toLowerCase)
                    .forEach(stopWords::add);
        } catch (IOException e) {
            return false;
        }
        return !stopWords.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word o1 = (Word) o;
        return Objects.equals(text.toLowerCase(), o1.text.toLowerCase());
    }

    @Override
    public String toString() {
        return getPrefix() + getText() + getSuffix();
    }
}
