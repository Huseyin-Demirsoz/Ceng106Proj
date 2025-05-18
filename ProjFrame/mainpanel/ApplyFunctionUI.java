package mainpanel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ApplyFunctionUI {
	static File selectedfile;
	private static JTabbedPane functiontab;
	public static void Open() {//JTabbedPane functiontab,File selectedfilein) {
		
		functiontab = new JTabbedPane(JTabbedPane.TOP);
		Main.contentPane.add(functiontab, BorderLayout.EAST);
		selectedfile = Main.selectedfile;
		//TODO yorum
		JScrollPane TypeFuncScrPane = new JScrollPane();  // Yeni nesne!
		functiontab.addTab("Func. type", null, TypeFuncScrPane, null);
		
		JScrollPane FunctionScrPane = new JScrollPane();  // Yeni nesne!
		functiontab.addTab("Functions", null, FunctionScrPane, null);
		
		// Fonksiyonlar için panel oluşturuyoruz
		JPanel functionPanel = new JPanel();
		functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.Y_AXIS));
		FunctionScrPane.setViewportView(functionPanel);
		
		//Butonları tutacak JPanel oluşturuyoruz
		JPanel funcTypePanel = new JPanel();
		funcTypePanel.setLayout(new BoxLayout(funcTypePanel, BoxLayout.Y_AXIS));
		
		// Başlık butonları (Color ve Shape başlıkları)
		JButton colorButton = new JButton("Color");
		colorButton.setMinimumSize(new Dimension(200, 50));
		colorButton.setMaximumSize(new Dimension(200, 50));
		colorButton.setPreferredSize(new Dimension(200, 50));
		colorButton.addActionListener(_ -> {
			functionPanel.removeAll();  // Önceki butonları temizle
			//TODO Fix Image Paths
			JButton medianBlurButton = new JButton("Apply Median Blur");
			medianBlurButton.setMinimumSize(new Dimension(200, 50));
			medianBlurButton.setMaximumSize(new Dimension(200, 50));
			medianBlurButton.setPreferredSize(new Dimension(200, 50));
			medianBlurButton.addActionListener(_ -> 
				applyMedianBlur(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath())
			);
			
			JButton cannyButton = new JButton("Apply Canny Edge Detection");
			cannyButton.setMinimumSize(new Dimension(200, 50));
			cannyButton.setMaximumSize(new Dimension(200, 50));
			cannyButton.setPreferredSize(new Dimension(200, 50));
			cannyButton.addActionListener(_ -> 
				applyCanny(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath())
			);
			
			JButton brightnessContrastButton = new JButton("Adjust Brightness & Contrast");
			brightnessContrastButton.setMinimumSize(new Dimension(200, 50));
			brightnessContrastButton.setMaximumSize(new Dimension(200, 50));
			brightnessContrastButton.setPreferredSize(new Dimension(200, 50));
			
			brightnessContrastButton.addActionListener(_ -> 
				adjustBrightnessContrast(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath(), 1.5, 50)
			);
			
			JButton kMeansButton = new JButton("Apply K-Means Color Clustering");
			kMeansButton.setMinimumSize(new Dimension(200, 50));
			kMeansButton.setMaximumSize(new Dimension(200, 50));
			kMeansButton.setPreferredSize(new Dimension(200, 50));
				kMeansButton.addActionListener(_ -> 
				applyDominantColorKMeans(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath(), 5)
			);
				JPanel rgbPanel = new JPanel();
				rgbPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				rgbPanel.setPreferredSize(new Dimension(200, 200));
				rgbPanel.setMinimumSize(new Dimension(200, 200));
				rgbPanel.setMaximumSize(new Dimension(200, 200));
				rgbPanel.setBackground(Color.LIGHT_GRAY);
				
				
				rgbPanel.setLayout(new BoxLayout(rgbPanel, BoxLayout.Y_AXIS));
				
				JLabel rLabel = new JLabel("R:");
				JTextField rField = new JTextField("0-255 arası değer girin");
				rField.setMaximumSize(new Dimension(300, 35));
				
				JLabel gLabel = new JLabel("G:");
				JTextField gField = new JTextField("0-255 arası değer girin");
				gField.setMaximumSize(new Dimension(300, 35));
				
				JLabel bLabel = new JLabel("B:");
				JTextField bField = new JTextField("0-255 arası değer girin");
				bField.setMaximumSize(new Dimension(300, 35));
				
				JButton applyRgbBtn = new JButton("Apply RGB Filter");
				applyRgbBtn.setMinimumSize(new Dimension(200, 50));
				applyRgbBtn.setMaximumSize(new Dimension(200, 50));
				applyRgbBtn.setPreferredSize(new Dimension(200, 50));
				applyRgbBtn.addActionListener(_ -> {
					try {
						int r = Integer.parseInt(rField.getText());
						int g = Integer.parseInt(gField.getText());
						int b = Integer.parseInt(bField.getText());
						applyRgbFilter(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath(),r, g, b);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Geçerli RGB değerleri girin (0-255).");
					}
				});
				
				rgbPanel.add(rLabel); rgbPanel.add(rField);
				rgbPanel.add(gLabel); rgbPanel.add(gField);
				rgbPanel.add(bLabel); rgbPanel.add(bField);
				rgbPanel.add(applyRgbBtn);
			// Butonları panele ekle
			functionPanel.add(medianBlurButton);
			functionPanel.add(cannyButton);
			functionPanel.add(brightnessContrastButton);
			functionPanel.add(kMeansButton);
			functionPanel.add(new JSeparator());
			functionPanel.add(rgbPanel);
			//Paneli yenile
			functionPanel.revalidate();
			functionPanel.repaint();
		});
		// Başlık butonları (Effect başlığı)
		JButton effectButton = new JButton("Effect");
		effectButton.setMinimumSize(new Dimension(200, 50));
		effectButton.setMaximumSize(new Dimension(200, 50));
		effectButton.setPreferredSize(new Dimension(200, 50));
		effectButton.addActionListener(_ -> {
			functionPanel.removeAll();  // Önceki butonları temizle
			
			// Sketch Effect butonu
			JButton sketchButton = new JButton("Apply Sketch Effect");
			sketchButton.setMinimumSize(new Dimension(200, 50));
			sketchButton.setMaximumSize(new Dimension(200, 50));
			sketchButton.setPreferredSize(new Dimension(200, 50));
			sketchButton.addActionListener(_ ->
				applySketchEffect(selectedfile.getAbsolutePath(),selectedfile.getAbsolutePath())
			);
			
			// Cartoon Prep Effect butonu
			JButton cartoonButton = new JButton("Apply Cartoon Prep Effect");
			cartoonButton.setMinimumSize(new Dimension(200, 50));
			cartoonButton.setMaximumSize(new Dimension(200, 50));
			cartoonButton.setPreferredSize(new Dimension(200, 50));
			cartoonButton.addActionListener(_ ->
				applyCartoonPrepEffect(selectedfile.getAbsolutePath(),selectedfile.getAbsolutePath())
			);
			
			// Sobel Edge butonu
			JButton sobelButton = new JButton("Apply Sobel Edge Detection");
			sobelButton.setMinimumSize(new Dimension(200, 50));
			sobelButton.setMaximumSize(new Dimension(200, 50));
			sobelButton.setPreferredSize(new Dimension(200, 50));
			sobelButton.addActionListener(_ ->
				applySobelEdge(selectedfile.getAbsolutePath(),selectedfile.getAbsolutePath())
			);
			// Color Inversion butonu
						JButton inversionButton = new JButton("Apply Color Inversion");
						
						inversionButton.setMinimumSize(new Dimension(200, 50));
						inversionButton.setMaximumSize(new Dimension(200, 50));
						inversionButton.setPreferredSize(new Dimension(200, 50));
						inversionButton.addActionListener(_ ->
							applyColorInversion(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath())
						);
						
						// Color Tint Effect butonu
						JButton tintButton = new JButton("Apply Color Tint Effect");
						tintButton.setMinimumSize(new Dimension(200, 50));
						tintButton.setMaximumSize(new Dimension(200, 50));
						tintButton.setPreferredSize(new Dimension(200, 50));
						tintButton.addActionListener(_ ->
							applyColorTintEffect(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath())
						);
						// Vignette Effect butonu
						JButton vignetteButton = new JButton("Apply Vignette Effect");
						vignetteButton.setMinimumSize(new Dimension(200, 50));
						vignetteButton.setMaximumSize(new Dimension(200, 50));
						vignetteButton.setPreferredSize(new Dimension(200, 50));
						vignetteButton.addActionListener(_ ->
							applyVignetteEffect(selectedfile.getAbsolutePath(), selectedfile.getAbsolutePath())
						);
						
						
						// Motion Blur Effect butonu
						JButton motionBlurButton = new JButton("Apply Motion Blur Effect");
						motionBlurButton.setMinimumSize(new Dimension(200, 50));
						motionBlurButton.setMaximumSize(new Dimension(200, 50));
						motionBlurButton.setPreferredSize(new Dimension(200, 50));
						motionBlurButton.addActionListener(_ ->
							applyMotionBlurEffect(selectedfile.getAbsolutePath(),selectedfile.getAbsolutePath(), 15)
						);
						
						
						
						
						// Pixelation Effect label ve fieldı
						JLabel pixLabel = new JLabel("Pixel Size:");
						pixLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
						
						JTextField pixField = new JTextField("32");
						pixField.setMaximumSize(new Dimension(300, 35));
						pixField.setAlignmentX(Component.LEFT_ALIGNMENT);
						
						// Pixelation Effect butonu
						JButton pixelButton = new JButton("Apply Pixelation Effect");
						
						pixelButton.setMinimumSize(new Dimension(200, 50));
						pixelButton.setMaximumSize(new Dimension(200, 50));
						pixelButton.setPreferredSize(new Dimension(200, 50));
						pixelButton.setAlignmentX(Component.LEFT_ALIGNMENT);
						
						pixelButton.addActionListener(_ -> {
							try {
								int size = Integer.parseInt(pixField.getText().trim());
								if (size <= 0) throw new NumberFormatException();
								
								applyPixelEffect(
									selectedfile.getAbsolutePath(),
									selectedfile.getAbsolutePath()
									,new Size(size, size)
								);
								
								JOptionPane.showMessageDialog(null, "✅ Piksel efekti başarıyla uygulandı!");
							
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(null, "Lütfen geçerli bir pozitif tam sayı girin.");
							}
						});
			
			// Butonları panele ekle
			functionPanel.add(sketchButton);
			functionPanel.add(cartoonButton);
			functionPanel.add(sobelButton);
			functionPanel.add(inversionButton);
			functionPanel.add(tintButton);
			functionPanel.add(vignetteButton);
			functionPanel.add(motionBlurButton);
			
			functionPanel.add(pixLabel);
			functionPanel.add(pixField);
			functionPanel.add(pixelButton);
			//Paneli yenile
			functionPanel.revalidate();
			functionPanel.repaint();
		});
		
		JButton shapeButton = new JButton("Shape");
		shapeButton.setMinimumSize(new Dimension(200, 50));
		shapeButton.setMaximumSize(new Dimension(200, 50));
		shapeButton.setPreferredSize(new Dimension(200, 50));
		shapeButton.addActionListener(_ -> {
			// Şekil filtresi ile ilgili butonları ekle
			functionPanel.removeAll();  // Önceki butonları temizle
			JButton shapeFilterButton = new JButton("Apply Shape Filter");
			shapeFilterButton.setMinimumSize(new Dimension(200, 50));
			shapeFilterButton.setMaximumSize(new Dimension(200, 50));
			shapeFilterButton.setPreferredSize(new Dimension(200, 50));
			shapeFilterButton.addActionListener(_ -> 
				applyShapeFilter()
			);
			// Butonları panele ekle
			functionPanel.add(shapeFilterButton);
			//Paneli yenile
			functionPanel.revalidate();
			functionPanel.repaint();
		});
	
		// Func type başlığına butonları ekleyelim
		funcTypePanel.add(colorButton);
		funcTypePanel.add(shapeButton);
		funcTypePanel.add(effectButton);
		TypeFuncScrPane.setViewportView(funcTypePanel);


	}
	public static void Close() {
		Main.contentPane.remove(functiontab);
	}
	public static void Reopen() {
		Main.contentPane.add(functiontab, BorderLayout.EAST);
	}
	
	//TODO BOŞ
	private static void applyShapeFilter() {
		// Shape filtresi fonksiyonu
		System.out.println("Applying shape filter...");
		// Burada istediğiniz işlemi yapabilirsiniz
	}


	
	
	public static void applyMedianBlur(String inputPath, String outputPath) {
		MedianBlur.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());

	}
	
	public static void applyCanny(String inputPath, String outputPath) {
		Canny.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());
	}
	
	public static void adjustBrightnessContrast(String inputPath, String outputPath, double alpha, double beta) {
		BrightnessContrast.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath(),alpha,beta);
	}
	
	public static void applyDominantColorKMeans(String inputPath, String outputPath, int k) {
		Dominant_Color.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath(), (double)k);
	}
	
	public static void applySketchEffect(String inputPath, String outputPath) {
		SketchEffect.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());
	}
	
	public static void applyCartoonPrepEffect(String inputPath, String outputPath) {
		CartoonPrepEffect.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());
	}
	
	public static void applySobelEdge(String inputPath, String outputPath) {
		SobelEdge.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());
	}
	
	public static void applyRgbFilter(String inputPath, String outputPath,int targetR, int targetG, int targetB) {
		RgbFilter.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath(),(double) targetR, (double)targetG, (double)targetB);
	}
		
	public static void applyColorInversion(String inputPath, String outputPath) {
		ColorInversion.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());
	}
	
	public static void applyColorTintEffect(String inputPath, String outputPath) {
		ColorTintEffect.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());
	}
	
	public static void applyPixelEffect(String inputPath, String outputPath , Size pixelSize) {
		PixelEffect.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath(),pixelSize.width,pixelSize.height);
	}
	
	public static void applyVignetteEffect(String inputPath, String outputPath) {
		VignetteEffect.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath());
	}
	
	public static void applyMotionBlurEffect(String inputPath, String outputPath, int kernelSize) {
		MotionBlurEffect.wPath(Paths.get(inputPath).toAbsolutePath(), Paths.get(outputPath).toAbsolutePath(), (double)kernelSize);
	}
}
