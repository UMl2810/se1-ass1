package engine;

import java.util.ArrayList;
import java.util.List;

public class Query {
    private List<Word> keywords;

    public Query(String searchPhrase) {
        keywords = new ArrayList<>();
        for (String rawWord : searchPhrase.split(" ")) {
            Word word = Word.createWord(rawWord);
            if (word.isKeyword()) {
                keywords.add(word);
            }
        }
    }

    public List<Word> getKeywords() {
        return keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Word> combinedWords = new ArrayList<>();
        combinedWords.addAll(d.getTitle());
        combinedWords.addAll(d.getBody());

        List<Match> matches = new ArrayList<>();

        for (Word keyword : keywords) {
            int freq = 0;
            int firstIndex = -1;
            for (int i = 0; i < combinedWords.size(); i++) {
                if (keyword.equals(combinedWords.get(i))) {
                    freq++;
                    if (firstIndex == -1) {
                        firstIndex = i;
                    }
                }
            }
            if (freq > 0) {
                matches.add(new Match(d, keyword, freq, firstIndex));
            }
        }
        matches.sort(Match::compareTo);
        return matches;
    }
}
