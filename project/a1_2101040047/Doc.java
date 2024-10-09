package a1_2101040047;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        String[] titleWords = content.split("\n")[0].split(" ");
        List<Word> titleList = new ArrayList<>();
        for (String word : titleWords) {
            titleList.add(Word.createWord(word));
        }
        title = titleList;
        String[] bodyWords = content.split("\n")[1].split(" ");
        List<Word> bodyList = new ArrayList<>();
        for (String word : bodyWords) {
            bodyList.add(Word.createWord(word));
        }
        body = bodyList;
    }
    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }
    public int getBodyLength() {
        return body.size();
    }

    public int getTitleLength() {
        return title.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Doc doc = (Doc) o;
        if (!this.body.equals(doc.body)) {
            return false;
        }
        if (!this.title.equals(doc.title)) {
            return false;
        }
        return true;
    }

}