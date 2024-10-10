package a1_2101040047;


import java.util.List;
import java.util.stream.Collectors;

public class Result implements Comparable<Result> {
    private Doc d;
    private List<Match> matches;

    public Result(Doc d, List<Match> matches) {
        this.d = d;
        this.matches = matches;
    }

    public Doc getDoc() {
        return d;
    }

    public List<Match> getMatches() {
        return matches;
    }


    public int getTotalFrequency() {
        return getMatches().stream()
                .mapToInt(Match::getFreq)
                .sum();
    }

//    public double getAverageFirstIndex() {
//        float sum1stIndex = 0.0F;
//        for (Match match : matches) {
//            sum1stIndex += match.getFirstIndex();
//        }
//        return sum1stIndex / matches.size();
//    }

    public String htmlHighlight() {
        StringBuilder highlightedTitle = new StringBuilder();
        StringBuilder highlightedBody = new StringBuilder();
        List<Word> wordsInMatches = matches
                .stream()
                .map(Match::getWord)
                .collect(Collectors.toList());
        for (int i = 0; i < d.getTitleLength(); i++) {
            Word wTitle = d.getTitle().get(i);
            if (wordsInMatches.contains(wTitle)) {
                highlightedTitle.append("<u>").append(wTitle.getText()).append("</u>").append(wTitle.getSuffix());
            } else {
                highlightedTitle.append(wTitle.getPrefix()).append(wTitle.getText()).append(wTitle.getSuffix());
            }
            if (i < d.getTitleLength() - 1) {
                highlightedTitle.append(" ");
            }
        }
        for (int j = 0; j < d.getBodyLength(); j++) {
            Word wBody = d.getBody().get(j);
            if (wordsInMatches.contains(wBody)) {
                highlightedBody.append("<b>").append(wBody.getText()).append("</b>").append(wBody.getSuffix());
            } else {
                highlightedBody.append(wBody.getPrefix()).append(wBody.getText()).append(wBody.getSuffix());
            }
            if (j < d.getBodyLength() - 1) {
                highlightedBody.append(" ");
            }
        }
        return "<h3>" + highlightedTitle + "</h3>" + "<p>" + highlightedBody + "</p>";
    }

    @Override
    public int compareTo(Result o) {
        int compared = Integer.compare(o.getMatches().size(), this.getMatches().size());
        if (compared != 0) {
            return compared;
        }
        return Integer.compare(o.getTotalFrequency(), this.getTotalFrequency());
    }

}
