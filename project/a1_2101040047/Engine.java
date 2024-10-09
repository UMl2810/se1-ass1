package a1_2101040047;

import java.io.*;
import java.util.*;

public class Engine {
    private Doc[] docs;

    public Engine(){

    }

    public int loadDocs(String dirname) {
        File directoryPath= new File(dirname);
        String[] files = directoryPath.list();
        docs = new Doc[files.length];
        for (int i = 0; i < files.length; i++) {
            String content = "";
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(dirname+"\\"+files[i]));
                String line = reader.readLine();
                while (line != null) {
                    content += line + "\n";
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            docs[i] = new Doc(content);
        }
        return files.length;
    }

    public Doc[] getDocs() {
        return this.docs;
    }

    public String htmlResult(List<Result> results) {
        StringBuilder html = new StringBuilder();
        results.stream()
                .filter(result -> result.htmlHighlight() != null)
                .forEach(result -> html.append(result.htmlHighlight()));
        return html.toString();
    }

    public List<Result> search(Query q) {
        List<Result> searchedResults = new ArrayList<>();
        int i = 0;
        while (i < docs.length) {
            Doc document = docs[i];
            List<Match> matchingResults = q.matchAgainst(document);
            Result documentResult = new Result(document, matchingResults);
            if (!matchingResults.isEmpty()) {
                searchedResults.add(documentResult);
            }
            i++;
        }
        Collections.sort(searchedResults);
        return searchedResults;
    }
}


