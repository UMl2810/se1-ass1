package engine;

public class Match implements Comparable<Match> {
    private Doc document;
    private Word word;
    private int frequency;
    private int firstIndex;

    public Match(Doc document, Word word, int frequency, int firstIndex) {
        this.document = document;
        this.word = word;
        this.frequency = frequency;
        this.firstIndex = firstIndex;
    }

    public int getFreq() {
        return frequency;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    @Override
    public int compareTo(Match other) {
        return Integer.compare(this.firstIndex, other.firstIndex);
    }
    public Word getWord() {
        return word;
    }

}
