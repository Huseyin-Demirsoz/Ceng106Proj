package mainpanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.morphology.analysis.WordAnalysis;

public class KullaniciInput {

    static  TurkishMorphology morphology = TurkishMorphology.createWithDefaults();


    static List<String> Lemmatize(String sentence) {

        sentence = sentence.toLowerCase(Locale.forLanguageTag("tr")).replaceAll("[^a-zçğıöşü\\s]", ""); // Küçük harfe çevir ve noktalama işaretlerini kaldır.
        String[] tokens = sentence.split("\\s+");
        List<String> lemmas = new ArrayList<>();

        for (String token : tokens) {
            WordAnalysis analysis = morphology.analyze(token);
            List<SingleAnalysis> results = analysis.getAnalysisResults();
            if (!results.isEmpty()) {
                lemmas.add(results.get(0).getLemmas().get(0));
            } else {
                lemmas.add(token);
            }
        }
        return lemmas;
    }

}
