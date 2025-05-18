package mainpanel;

public class ApplyChosenFilter {

    public static void Applying(String komut){


        switch(komut) {

            case "Sobel":

                ApplyFunctionUI.applySobelEdge("C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme");

                break;

            case "SketchEffect":

                ApplyFunctionUI.applySketchEffect("C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme");

                break;

            case "BrightAndContrastAdjustment":

                ApplyFunctionUI.adjustBrightnessContrast("C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", 50, 50);

                break;

            case "Cartoon":

                ApplyFunctionUI.applyCartoonPrepEffect("C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme");


                break;

            case "DominantColor":

                ApplyFunctionUI.applyDominantColorKMeans("C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", 5);


                break;

        }

    }

}



