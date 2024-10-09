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
        int i = 0;
        while (i < rawText.length() && !isValidWord(rawText.charAt(i))) {
            prefix.append(rawText.charAt(i));
            i++;
        }
        for (int j = i; j < rawText.length(); j++) {
            char ch = rawText.charAt(j);
            if (isValidWord(ch)) {
                if (ch == ',' && j > i + 1) {
                    break;
                } else if (ch == '.' && (rawText.charAt(j - 1) < '0' || rawText.charAt(j - 1) > '9') && (rawText.length() - j < 3)) {
                    break;
                } else {
                    text.append(ch);
                }
            } else {
                break;
            }
        }
        for (int k = i + text.length(); k < rawText.length(); k++) {
            suffix.append(rawText.charAt(k));
        }

        return new Word(prefix.toString(), text.toString(), suffix.toString());
    }

    public static boolean isValidWord(char ch) {
        if (Character.isLetter(ch) || Character.isDigit(ch)) {
            return true;
        }
        return ch == ',' || ch == '.' || ch == '-';
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
