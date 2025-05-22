package mainpanel;

public class ApplyChosenFilter {

    public static void Applying(String komut){


        switch(komut) {

            case "Sobel":

                ApplyFunctionUI.applySobelEdge(Main.getselectedfile().getAbsolutePath(), Main.getselectedfile().getAbsolutePath());

                break;

            case "SketchEffect":

                ApplyFunctionUI.applySketchEffect(Main.getselectedfile().getAbsolutePath(), Main.getselectedfile().getAbsolutePath());

                break;

            case "BrightAndContrastAdjustment":

                ApplyFunctionUI.adjustBrightnessContrast(Main.getselectedfile().getAbsolutePath(), Main.getselectedfile().getAbsolutePath(), 50, 50);

                break;

            case "Cartoon":

                ApplyFunctionUI.applyCartoonPrepEffect(Main.getselectedfile().getAbsolutePath(), Main.getselectedfile().getAbsolutePath());


                break;

            case "DominantColor":

                ApplyFunctionUI.applyDominantColorKMeans(Main.getselectedfile().getAbsolutePath(), Main.getselectedfile().getAbsolutePath(), 5);


                break;

        }

    }

}



