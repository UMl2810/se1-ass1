package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private List<Doc> docs;

    public Engine() {
        docs = new ArrayList<>();
    }

    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                try (Scanner scanner = new Scanner(file)) {
                    StringBuilder content = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        content.append(scanner.nextLine()).append("\n");
                    }
                    docs.add(new Doc(content.toString()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return docs.size();
    }
    public String htmlResult(List<Result> resultList) {
        String resultHtml = "";
        for (Result result : resultList) {
            resultHtml += result.htmlHighlight();
        }
        return resultHtml;
    }

    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();

        for (Doc doc : docs) {
            List<Match> matches = q.matchAgainst(doc);
            if (!matches.isEmpty()) {
                results.add(new Result(doc, matches));
            }
        }
        results.sort(Result::compareTo);
        return results;
    }


}


