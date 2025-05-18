package mainpanel;

public class ApplyChosenFilter {

    public static void Applying(String komut){


        switch(komut) {

            case "Sobel":

                ApplyFunctionUI.applySobelEdge(Main.selectedfile.getAbsolutePath(), "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme");

                break;

            case "SketchEffect":

                ApplyFunctionUI.applySketchEffect(Main.selectedfile.getAbsolutePath(), "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme");

                break;

            case "BrightAndContrastAdjustment":

                ApplyFunctionUI.adjustBrightnessContrast(Main.selectedfile.getAbsolutePath(), "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", 50, 50);

                break;

            case "Cartoon":

                ApplyFunctionUI.applyCartoonPrepEffect(Main.selectedfile.getAbsolutePath(), "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme");


                break;

            case "DominantColor":

                ApplyFunctionUI.applyDominantColorKMeans(Main.selectedfile.getAbsolutePath(), "C:\\Users\\my\\Documents\\Filter NLP Data Set\\Deneme", 5);


                break;

        }

    }

}



