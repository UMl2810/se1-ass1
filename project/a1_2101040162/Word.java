package engine;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
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
        char[] chars = rawText.toCharArray();
        String prefix = "";
        String text = "";
        String suffix = "";

        int i = 0;
        boolean foundText = false;

        while (i < chars.length) {
            char currentChar = chars[i];
            if (!foundText && !isValidWord(currentChar)) {
                prefix += currentChar;
            } else {
                foundText = true;

                if (isValidWord(currentChar)) {
                    if (currentChar == ',' && !text.isEmpty()) {
                        break;
                    } else if (currentChar == '.' && !text.isEmpty() && !Character.isDigit(chars[i - 1])) {
                        if (chars.length - i < 3) {
                            break;
                        }
                    }
                    text += currentChar;
                } else {
                    break;
                }
            }
            i++;
        }
        if (i < chars.length) {
            suffix = new String(chars, i, chars.length - i); // Everything left is suffix
        }

        return new Word(prefix, text, suffix);
    }
    public static boolean isValidWord(char ch) {
        return String.valueOf(ch).matches("[a-zA-Z0-9,.-]");
    }
    public boolean isKeyword() {
        return !stopWords.contains(text.toLowerCase()) && text.matches("[a-zA-Z]+[a-zA-Z'-.,]*");
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
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String stopWord = scanner.nextLine().trim();
                if (!stopWord.isEmpty()) {
                    stopWords.add(stopWord);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Word) {
            Word otherWord = (Word) o;
             return this.text.equalsIgnoreCase(otherWord.text);
        }
        return false;
    }
    @Override
    public String toString() {
        return getPrefix() + getText() + getSuffix();
    }

}
