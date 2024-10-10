package engine;

import java.util.List;

public class Result implements Comparable<Result> {
    private Doc doc;
    private List<Match> matches;

    public Result(Doc doc, List<Match> matches) {
        this.doc = doc;
        this.matches = matches;
    }

    public Doc getDoc() {
        return doc;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public String htmlHighlight() {
        return "<h3>" + highlight(doc.getTitle(), "<u>", "</u>") + "</h3><p>" +
                highlight(doc.getBody(), "<b>", "</b>") + "</p>";
    }

    private String highlight(List<Word> words, String openTag, String closeTag) {
        return words.stream()
                .map(word -> matches.stream()
                        .map(Match::getWord)
                        .filter(word::equals)
                        .findFirst()
                        .map(matchedWord -> word.getPrefix() + openTag + word.getText() + closeTag + word.getSuffix())
                        .orElse(word.toString()))
                .reduce((acc, wordStr) -> acc + " " + wordStr)
                .orElse("")
                .trim();
    }


    @Override
    public int compareTo(Result other) {
        int matchCountComparison = Integer.compare(other.matches.size(), this.matches.size());
        if (matchCountComparison != 0) {
            return matchCountComparison;
        }
        int totalFrequencyComparison = Integer.compare(other.matches.stream().mapToInt(Match::getFreq).sum(),
                this.matches.stream().mapToInt(Match::getFreq).sum());
        return totalFrequencyComparison != 0 ? totalFrequencyComparison :
                Integer.compare(this.matches.stream().mapToInt(Match::getFirstIndex).min().orElse(0),
                        other.matches.stream().mapToInt(Match::getFirstIndex).min().orElse(0));
    }
}
