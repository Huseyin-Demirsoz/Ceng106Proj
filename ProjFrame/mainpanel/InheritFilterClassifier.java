package mainpanel;
import java.util.List;

public  class InheritFilterClassifier extends FilterClassifier.NaiveBayesModel{





    @Override
    void train(List<FilterClassifier.Document> documents) {

        super.train(documents);
    }

    @Override
    String predict(List<String> lemmatizedWords){

       String prediction = super.predict(lemmatizedWords);

       return prediction;


    }



}
