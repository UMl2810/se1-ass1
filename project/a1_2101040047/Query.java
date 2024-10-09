package a1_2101040047;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query {
    private List<Word> keywords;

    public Query(String searchPhrase) {
        keywords = new ArrayList<>();
        for (String word : searchPhrase.split("\\s+")) {
            Word wordObj = Word.createWord(word);
            if (wordObj.isKeyword()) {
                keywords.add(wordObj);
            }
        }
    }

    public List<Word> getKeywords() {
        return keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        List<Word> wordInDoc = new ArrayList<>();
        wordInDoc.addAll(d.getTitle());
        wordInDoc.addAll(d.getBody());
        this.keywords.stream()
                .filter(keyword -> d != null && wordInDoc.contains(keyword))
                .forEach(keyword -> {
                    int frequency = 0;
                    int firstMatchIndex = -1;
                    for (int i = 0; i < wordInDoc.size(); i++) {
                        if (wordInDoc.get(i).equals(keyword)) {
                            frequency++;
                            if (firstMatchIndex == -1) {
                                firstMatchIndex = i;
                            }
                        }
                    }
                    if (frequency > 0) {
                        matches.add(new Match(d, keyword, frequency, firstMatchIndex));
                    }
                });
        matches.sort(Match::compareTo);
        return matches;
    }

}
