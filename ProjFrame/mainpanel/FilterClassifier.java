package mainpanel;
import java.util.*;


public class FilterClassifier{


    static class Document {
        String[] words;
        String label;

        Document(String text, String label) {
            this.words = text.split("\\s+");
            this.label = label;
        }
    }

    // Tüm sınıflar için kelime sayacı
    static class NaiveBayesModel {
        Map<String, Integer> labelCounts = new HashMap<>();
        Map<String, Map<String, Integer>> wordCounts = new HashMap<>();
        Set<String> vocabulary = new HashSet<>();
        int totalDocuments = 0;


        void train(List<Document> documents) {
            for (Document doc : documents) {
                labelCounts.put(doc.label, labelCounts.getOrDefault(doc.label, 0) + 1);
                totalDocuments++;

                Map<String, Integer> counts = wordCounts.computeIfAbsent(doc.label, k -> new HashMap<>());

                for (String word : doc.words) {
                    counts.put(word, counts.getOrDefault(word, 0) + 1);
                    vocabulary.add(word);
                }
            }
        }


        String predict(List<String> lemmatizedWords) {
            double bestScore = Double.NEGATIVE_INFINITY;
            String bestLabel = null;
            
            for (String label : labelCounts.keySet()) {
                double logProb = Math.log(labelCounts.get(label) / (double) totalDocuments);

                Map<String, Integer> counts = wordCounts.get(label);
                int totalWordsInClass = counts.values().stream().mapToInt(Integer::intValue).sum();

                for (String word : lemmatizedWords) {
                    int count = counts.getOrDefault(word, 0);
                    double prob = (count + 1.0) / (totalWordsInClass + vocabulary.size()); // Laplace smoothing
                    logProb += Math.log(prob);
                }

                if (logProb > bestScore) {
                    bestScore = logProb;
                    bestLabel = label;
                }
            }

            return bestLabel;
        }


    }

}
