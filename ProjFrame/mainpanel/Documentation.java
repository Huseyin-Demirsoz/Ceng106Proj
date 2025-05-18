package mainpanel;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class Documentation {

    public static List<FilterClassifier.Document> DocumateTheWords() throws Exception {
        Path csvPath = Paths.get("C:\\Users\\my\\Documents\\Filter NLP Data Set\\Preprocessed DataSet20.csv");

        List<FilterClassifier.Document> documents = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
        } catch (Exception e) {

                System.out.println("Dosya bulunamadı." + e.getMessage());
        }

        if(reader != null) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                } // başlık geç
                String[] parts = line.split(",", 2);
                if (parts.length < 2) continue;
                String text = parts[0].trim();
                String label = parts[1].trim().replace("\"", ""); // bazı label'lar tırnaklı olabilir
                documents.add(new FilterClassifier.Document(text, label));
            }
        }
        return documents;
    }


}
