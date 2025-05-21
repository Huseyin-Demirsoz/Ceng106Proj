package mainpanel;

public class ApplyChosenFilter {

    public static void Applying(String komut){


        switch(komut) {

            case "Sobel":

                ApplyFunctionUI.applySobelEdge(Main.selectedfile.getAbsolutePath(), Main.selectedfile.getAbsolutePath());

                break;

            case "SketchEffect":

                ApplyFunctionUI.applySketchEffect(Main.selectedfile.getAbsolutePath(), Main.selectedfile.getAbsolutePath());

                break;

            case "BrightAndContrastAdjustment":

                ApplyFunctionUI.adjustBrightnessContrast(Main.selectedfile.getAbsolutePath(), Main.selectedfile.getAbsolutePath(), 50, 50);

                break;

            case "Cartoon":

                ApplyFunctionUI.applyCartoonPrepEffect(Main.selectedfile.getAbsolutePath(), Main.selectedfile.getAbsolutePath());


                break;

            case "DominantColor":

                ApplyFunctionUI.applyDominantColorKMeans(Main.selectedfile.getAbsolutePath(), Main.selectedfile.getAbsolutePath(), 5);


                break;

        }

    }

}



