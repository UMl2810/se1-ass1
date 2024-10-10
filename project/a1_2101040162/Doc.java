package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        String[] parts = content.split("\n", 2);
        title = createWordList(parts[0]);
        body = createWordList(parts[1]);
    }

    private List<Word> createWordList(String text) {
        List<Word> words = new ArrayList<>();
        for (String rawWord : text.split(" ")) {
            words.add(Word.createWord(rawWord));
        }
        return words;
    }
    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doc)) return false;
        Doc doc = (Doc) o;
        return title.equals(doc.title) && body.equals(doc.body);
    }

}