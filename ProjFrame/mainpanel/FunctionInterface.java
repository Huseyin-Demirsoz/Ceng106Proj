package mainpanel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


interface FunctionInterface {
	public static void wPath(Path input, Path output, double... args) {}
	public static Mat wMat(Mat input, double... args) {return input;}
}

abstract class ToGray implements FunctionInterface{
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	public static Mat wMat(Mat input, double... args) {
		// TODO Auto-generated method stub
		//Imgproc.cvtColor(in0, in0, Imgproc.COLOR_RGB2GRAY);// This is not used because it collapses channels 3 to 1
		List<Mat> color = new ArrayList<Mat>(3);
		Mat temp = new Mat();
		Imgproc.cvtColor(input, temp, Imgproc.COLOR_RGB2HSV);
		Core.split(temp, color);
		color.set(1, color.get(1).setTo(new Scalar(0)));
		Core.merge(color, temp);
		Imgproc.cvtColor(temp, temp, Imgproc.COLOR_HSV2RGB);
		return temp;
	}
}

abstract class Dominant_Color  implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
		int k = (int) Math.round(args[0]);
		if(k<2 && k>10) {
			//TODO error
			return input;
		}
		Mat src = input;
        Mat reshaped = src.reshape(1, src.cols() * src.rows());
        Mat reshaped32f = new Mat();
        reshaped.convertTo(reshaped32f, CvType.CV_32F);

        Mat labels = new Mat();
        Mat centers = new Mat();
        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER, 10, 1.0);

        Core.kmeans(reshaped32f, k , labels, criteria, 3, Core.KMEANS_PP_CENTERS, centers);
        centers.convertTo(centers, CvType.CV_8U);

        // Yeni görsel: genişlik = 100*k, yükseklik = 100
        int boxWidth = 100;
        int boxHeight = 100;
        Mat colorBoxes = new Mat(boxHeight, boxWidth * k, CvType.CV_8UC3);

        for (int i = 0; i < k; i++) {
            byte[] colorBytes = new byte[3];
            centers.get(i, 0, colorBytes);
            Scalar scalarColor = new Scalar(
                    Byte.toUnsignedInt(colorBytes[0]),
                    Byte.toUnsignedInt(colorBytes[1]),
                    Byte.toUnsignedInt(colorBytes[2])
            );

            org.opencv.core.Rect rect = new org.opencv.core.Rect(i * boxWidth, 0, boxWidth, boxHeight);
            Imgproc.rectangle(colorBoxes, rect, scalarColor, -1);
        }
        return colorBoxes;
        
		
	}
	
}
abstract class Canny implements FunctionInterface{
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
		//args unused
	    Mat gray = new Mat();
	    Mat edges = new Mat();
	    //Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
	    gray = ToGray.wMat(input);
	    Imgproc.Canny(gray, edges, 100, 200);
	    return edges;
	}
	
}
abstract class BrightnessContrast implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
		//alpha beta can be set to 1.5, 50
		Mat src = input;
	    Mat dst = new Mat();
	    src.convertTo(dst, -1, args[0], args[1]);
	    return dst;
	}
	
}

abstract class SketchEffect implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	    Mat image =input;
	    if (image.empty()) {
	        System.out.println("❌ Could not load image");
	        return input;
	    }
	    Mat gray = new Mat();
	    //Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
	    gray =  ToGray.wMat(image);
	    Mat inverted = new Mat();
	    Core.bitwise_not(gray, inverted);
	    Mat blurred = new Mat();
	    Imgproc.GaussianBlur(inverted, blurred, new Size(21, 21), 0);
	    Mat invertedBlur = new Mat();
	    Core.bitwise_not(blurred, invertedBlur);
	    Mat sketch = new Mat();
	    Core.divide(gray, invertedBlur, sketch, 256.0);
	    System.out.println("✅ Sketch effect saved to ");// + selectedfile.getAbsolutePath());
	    return sketch;
	}
	
}
abstract class MedianBlur implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	  	Mat src = input;
	  	Mat dst = new Mat();
	  	Imgproc.medianBlur(src, dst, 5);
	    return dst;
	}
	
}

abstract class CartoonPrepEffect implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	  	Mat img = input;
	  	if (img.empty()) {
			System.out.println("❌ Could not load image");
			return img;
		}
		
		Imgproc.resize(img, img, new Size(512, 512));
		
		Mat filtered = img.clone();
		for (int i = 0; i < 7; i++) {
			Mat temp = new Mat();
			Imgproc.bilateralFilter(filtered, temp, 9, 75, 75);
		    filtered = temp;
		}
		
	    return filtered;
	}
	
}
abstract class SobelEdge implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
		Mat gray = ToGray.wMat(input);
		if (gray.empty()) {
			System.out.println("❌ Could not load image");
			return input;
		}
		
		Mat gradX = new Mat();
		Mat gradY = new Mat();
		Imgproc.Sobel(gray, gradX, CvType.CV_16S, 1, 0);
		Imgproc.Sobel(gray, gradY, CvType.CV_16S, 0, 1);
		
		Mat absX = new Mat();
		Mat absY = new Mat();
		Core.convertScaleAbs(gradX, absX);
		Core.convertScaleAbs(gradY, absY);
		
		Mat sobelOutput = new Mat();
		Core.addWeighted(absX, 0.5, absY, 0.5, 0, sobelOutput);
	    return sobelOutput;
	}
	
}
abstract class RgbFilter implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	  	int targetR = (int) Math.round(args[0]);
	  	int targetG = (int) Math.round(args[1]);
	  	int targetB = (int) Math.round(args[2]);
	  	try {
			// Değer sınaması
			if (targetR < 0 || targetR > 255 || targetG < 0 || targetG > 255 || targetB < 0 || targetB > 255) {
				JOptionPane.showMessageDialog(null, "⚠️ RGB değerleri 0 ile 255 arasında olmalıdır.");
				return input;
			}
			
			int tolerance = 30;
			
			Mat image = input;
			
			if (image.empty()) {
				JOptionPane.showMessageDialog(null, "❌ Görsel yüklenemedi. Dosya yolu hatalı olabilir.");
				return input;
			}
			
			Mat result = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
			for (int y = 0; y < image.rows(); y++) {
				for (int x = 0; x < image.cols(); x++) {
					double[] rgb = image.get(y, x);
					if (rgb == null || rgb.length < 3) continue; // Veri eksikse geç
					
					int b = (int) rgb[0];
					int g = (int) rgb[1];
					int r = (int) rgb[2];
					
					if (Math.abs(r - targetR) < tolerance &&
						Math.abs(g - targetG) < tolerance &&
						Math.abs(b - targetB) < tolerance) {
						result.put(y, x, 255);
					} else {
						result.put(y, x, 0);
					}
				}
			}
			
			JOptionPane.showMessageDialog(null, "✅ RGB filtre uygulandı!");
			return result;
			
			
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "❌ Sayısal olmayan bir değer girdiniz. Lütfen sadece rakam kullanın.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "❌ Bir hata oluştu: " + ex.getMessage());
			ex.printStackTrace();
		}
		return input;
	}
	
}
abstract class ColorInversion implements FunctionInterface{
	
	/*MARK START*/
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		
		boolean success = Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
		if (success) {
			System.out.println("✅ Color inversion saved to " + output.toAbsolutePath().toString());
			JOptionPane.showMessageDialog(null, "Ters renkli resim başarıyla kaydedildi:\n" + output.toAbsolutePath().toString());
		} else {
			System.out.println("❌ Failed to save color inversion.");
			JOptionPane.showMessageDialog(null, "Resim kaydedilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
		}
	}
	/*MARK END*/
	
	public static Mat wMat(Mat input, double... args) {
			
			//aşağısı try catch li ama kaydetme kısmı galiba try catchi hangisini beğenirseniz onu kullanabilirsiniz
			
		
		try {
			Mat img = input;
			if (img.empty()) {
				System.out.println("❌ Could not load image");
				JOptionPane.showMessageDialog(null, "Resim yüklenemedi " , "Hata", JOptionPane.ERROR_MESSAGE);
				return input;
			}
			
			Mat inverted = new Mat();
			Core.bitwise_not(img, inverted);
			return inverted;
		} catch (Exception e) {
			System.err.println("❌ applyColorInversion sırasında bir hata oluştu:");
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Bir hata oluştu:\n" + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
		}
		
	    return input;
	}
	
}
abstract class MotionBlurEffect implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	  	Mat src = input;
	  	int kernelSize =(int) Math.round(args[0]);
		
		// Kernel oluşturuluyor
		Mat kernel = Mat.zeros(kernelSize, kernelSize, CvType.CV_32F);
		for (int i = 0; i < kernelSize; i++) {
			kernel.put(i, i, 1.0 / kernelSize);
		}
		
		Mat dst = new Mat();
		try {
			// Büyütülmüş kernel'i resme uyguluyoruz
			Imgproc.filter2D(src, dst, -1, kernel);
		} catch (Exception e) {
			System.out.println("❌ Error applying motion blur: " + e.getMessage());
			return input;
		}
		
	    return dst;
	}
	
}
abstract class VignetteEffect implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	  	Mat src = input;
		if (src.empty()) {
			System.out.println("❌ Could not load image");
			return input;
		}
		
		Mat mask = createVignetteMask(src);
		Mat vignette = new Mat();
		Core.multiply(src, mask, vignette, 1, CvType.CV_8UC3);
		
	    return vignette;
	}
	//bu kısım gerekli vignette efekti için
	
	private static Mat createVignetteMask(Mat src) {
		int rows = src.rows();
		int cols = src.cols();
		
		Mat kernelX = Imgproc.getGaussianKernel(cols, cols / 2.0);
		Mat kernelY = Imgproc.getGaussianKernel(rows, rows / 2.0);
		Mat kernel = new Mat();
		Core.gemm(kernelY, kernelX.t(), 1, new Mat(), 0, kernel);
		
		Core.normalize(kernel, kernel, 0, 1, Core.NORM_MINMAX);
		Mat mask = new Mat();
		List<Mat> channels = new ArrayList<>();
		for (int i = 0; i < 3; i++) { 
			channels.add(kernel);
			Core.merge(channels, mask);
		}
		
		return mask;
	}
	
}
abstract class PixelEffect implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	  	Mat src = input;
	  	Size pixelSize = new Size(args[0],args[1]);
		if (src.empty()) {
			System.out.println("❌ Could not load image");
			return input;
		}
		
		// Küçült - sonra tekrar büyüt (piksel etkisi)
		Mat tmp = new Mat();
		Imgproc.resize(src, tmp, pixelSize, 0, 0, Imgproc.INTER_LINEAR);
		
		Mat pixelated = new Mat();
		Imgproc.resize(tmp, pixelated, src.size(), 0, 0, Imgproc.INTER_NEAREST);
		
	    return pixelated;
	}
	
}
abstract class ColorTintEffect implements FunctionInterface{
	
	public static void wPath(Path input, Path output, double... args) {
		Mat inputmat = Imgcodecs.imread(input.toAbsolutePath().toString());
		Imgcodecs.imwrite(output.toAbsolutePath().toString(), wMat(inputmat, args));
	}
	
	public static Mat wMat(Mat input, double... args) {
	  	Mat src = input;
		if (src.empty()) {
			System.out.println("❌ Could not load image");
			return input;
		}
		
		Mat tinted = new Mat();
		src.convertTo(tinted, -1, 1, 0); // Copy
		
		// Mavi ton ekle (mavi kanalını artır)
		Core.add(tinted, new Scalar(50, 0, 0), tinted); // BGR: (Blue, Green, Red)
		
	    return tinted;
	}
	
}

