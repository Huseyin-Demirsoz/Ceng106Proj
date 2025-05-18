

import java.util.List;



public class Main {

    public static void main(String[] args) throws Exception {

        List<FilterClassifier.Document> docDataSet = Documentation.DocumateTheWords(); // İlk model eğitimi için önceden işlenmiş data set'ini text ve albel olarak ayırıyoruz.

        FilterClassifier.NaiveBayesModel obj = new InheritFilterClassifier(); // Polymorphism

        obj.train(docDataSet); // Text ve Label'lara göre eğitim gerçekleştiriliyor.

        String kullaniciInput = "Vignette Color efekti uygular mısın?";

        String prediction = obj.predict(KullaniciInput.Lemmatize(kullaniciInput)); // Kullanıcı İnputu uygun formata getirilip

        System.out.println(prediction); // Tahmin yapılıyor.

    }


}
