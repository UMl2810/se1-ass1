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

    public static boolean isValidWord(char ch) {
        return (ch <= 'z' && ch >= 'a') || (ch <= 'Z' && ch >= 'A') || (ch <= '9' && ch >= '0') || (ch == ',') || (ch == '.') || (ch == '-') ;
    }

    private static boolean onComma(char ch, int currentPos, int startPos) {
        return ch == ',' && currentPos > startPos + 1;
    }

    private static boolean onDot(char ch, int currentPos, String rawText) {
        char prevChar = rawText.charAt(currentPos - 1);
        return ch == '.' && !(prevChar >= '0' && prevChar <= '9') && (rawText.length() - currentPos < 3);
    }

    public boolean isKeyword() {
        if (this.getText().isEmpty()) {
            return false;
        }

        if (stopWords.contains(this.getText().toLowerCase())) {
            return false;
        }
        for (int i = 0; i < this.getText().length(); i++) {
            if (this.getText().charAt(i) <= '9' && this.getText().charAt(i) >= '0') {
                return false;
            }
        }
        return true;
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
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                stopWords.add(reader.readLine());
            }
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
