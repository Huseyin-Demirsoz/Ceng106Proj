package mainpanel;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static File selectedfile ;//TODO
	
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
	/*
	The main function dispatches the public constructor as a main thread //TODO ingilizce
	Main fonksiyonu "main"den public constructor programın ana fonksiyonu olarak açılır
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public Main() {
		
		JTextArea textarea = new JTextArea ();;
		ImgDataBase database = new ImgDataBase();
		//TODO DEBUG
		FunctionDataBase functions = new FunctionDataBase();
		functions.Parse(Paths.get("/home/hd/Desktop/projtestfile.txt"));// TODO DEBUG file
		this.contentPane = new JPanel();
		
		
		//since the public class inherits from the JFrame, methods can be called on with "this" but it is usually not necessary
		// Additional "this" added for clarity
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1200,900);
		this.setContentPane(contentPane);
		
		//Layout manager setup.
		BorderLayout MainBorderLayout= new BorderLayout();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		MainBorderLayout.setHgap(8);
		MainBorderLayout.setVgap(8);
		this.contentPane.setLayout(MainBorderLayout);
		
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane_1, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane_1.addTab("File view", null, scrollPane, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab("Project view", null, scrollPane_1, null);
		
		JTabbedPane functiontab = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(functiontab, BorderLayout.EAST);
		
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
        		applyMedianBlur("resources/images/image1.jpg", "output/image1.jpg")
    		);
			
    		JButton cannyButton = new JButton("Apply Canny Edge Detection");
    		cannyButton.setMinimumSize(new Dimension(200, 50));
    		cannyButton.setMaximumSize(new Dimension(200, 50));
    		cannyButton.setPreferredSize(new Dimension(200, 50));
    		cannyButton.addActionListener(_ -> 
        		applyCanny("resources/images/image2.jpg", "output/image2.jpg")
    		);
			
    		JButton brightnessContrastButton = new JButton("Adjust Brightness & Contrast");
    		brightnessContrastButton.setMinimumSize(new Dimension(200, 50));
    		brightnessContrastButton.setMaximumSize(new Dimension(200, 50));
    		brightnessContrastButton.setPreferredSize(new Dimension(200, 50));
    		
    		brightnessContrastButton.addActionListener(_ -> 
        		adjustBrightnessContrast("resources/images/image4.jpg", "output/image4.jpg", 1.5, 50)
    		);
			
    		JButton kMeansButton = new JButton("Apply K-Means Color Clustering");
    		kMeansButton.setMinimumSize(new Dimension(200, 50));
    		kMeansButton.setMaximumSize(new Dimension(200, 50));
    		kMeansButton.setPreferredSize(new Dimension(200, 50));
    			kMeansButton.addActionListener(_ -> 
        		applyDominantColorKMeans("resources/images/image3.jpg", "output/image3.jpg", 5)
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
    					applyRgbFilter(r, g, b);
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
				applySketchEffect()//applySketchEffect("resources/images/image1.jpg", "output/sketch.jpg")
    		);
			
    		// Cartoon Prep Effect butonu
    		JButton cartoonButton = new JButton("Apply Cartoon Prep Effect");
    		cartoonButton.setMinimumSize(new Dimension(200, 50));
    		cartoonButton.setMaximumSize(new Dimension(200, 50));
    		cartoonButton.setPreferredSize(new Dimension(200, 50));
    		cartoonButton.addActionListener(_ ->
				applyCartoonPrepEffect()//applyCartoonPrepEffect("resources/images/image2.jpg", "output/cartoon.jpg")
    		);
			
    		// Sobel Edge butonu
    		JButton sobelButton = new JButton("Apply Sobel Edge Detection");
    		sobelButton.setMinimumSize(new Dimension(200, 50));
    		sobelButton.setMaximumSize(new Dimension(200, 50));
    		sobelButton.setPreferredSize(new Dimension(200, 50));
    		sobelButton.addActionListener(_ ->
				applySobelEdge()//applySobelEdge("resources/images/image3.jpg", "output/sobel.jpg")
    		);
    		// Color Inversion butonu
    					JButton inversionButton = new JButton("Apply Color Inversion");
    					inversionButton.setMinimumSize(new Dimension(200, 50));
    					inversionButton.setMaximumSize(new Dimension(200, 50));
    					inversionButton.setPreferredSize(new Dimension(200, 50));
    					inversionButton.addActionListener(_ ->
    						applyColorInversion("resources/images/image4.jpg", "output/inverted.jpg")
    					);

    					// Color Tint Effect butonu
    					JButton tintButton = new JButton("Apply Color Tint Effect");
    					tintButton.setMinimumSize(new Dimension(200, 50));
    					tintButton.setMaximumSize(new Dimension(200, 50));
    					tintButton.setPreferredSize(new Dimension(200, 50));
    					tintButton.addActionListener(_ ->
    						applyColorTintEffect("resources/images/image1.jpg", "output/tinted_image.png")
    					);
    					// Vignette Effect butonu
    					JButton vignetteButton = new JButton("Apply Vignette Effect");
    					vignetteButton.setMinimumSize(new Dimension(200, 50));
    					vignetteButton.setMaximumSize(new Dimension(200, 50));
    					vignetteButton.setPreferredSize(new Dimension(200, 50));
    					vignetteButton.addActionListener(_ ->
    						applyVignetteEffect("resources/images/image1.jpg", "output/vignette_image.png")
    					);
    					
    					
    					// Motion Blur Effect butonu
    					JButton motionBlurButton = new JButton("Apply Motion Blur Effect");
    					motionBlurButton.setMinimumSize(new Dimension(200, 50));
    					motionBlurButton.setMaximumSize(new Dimension(200, 50));
    					motionBlurButton.setPreferredSize(new Dimension(200, 50));
    					motionBlurButton.addActionListener(_ ->
    						applyMotionBlurEffect("resources/images/image1.jpg", "output/motion_blurred_image.png", 15)
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
    								"resources/images/image1.jpg",
    								"output/pixelated.png"/* 
    								,new Size(size, size)*/
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
    	shapeButton.addActionListener(_ -> {
        	// Şekil filtresi ile ilgili butonları ekle
        	functionPanel.removeAll();  // Önceki butonları temizle
        	JButton shapeFilterButton = new JButton("Apply Shape Filter");
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
		
		//TODO ingilizce
		//super_list_1 is the list of the "list elements" that get displayed 
		//for each file for example the functions and image path contained in a database
		List<JList<FileListDataModel>> super_list_1 = new ArrayList<JList<FileListDataModel>>();
		
		//sets the JList in the project view tab to the contents of the selected file
		FileListDataModel filelistmodel = new FileListDataModel();
		JList<FileListDataModel> list = new JList(filelistmodel);
		imgpanel mainImage = new imgpanel();
		list.addListSelectionListener(_ -> {
			//Listenin seçim ihtiyaçları fazladan oduğundan yeni dosyaya atılmıştır
			selectedfile = LeftPanelList.listListener(selectedfile,filelistmodel,list,scrollPane_1,super_list_1,mainImage,this.contentPane);
			//Text editörde gözükmesi için seçilenin okunup yazılmasını sağlar
			if (selectedfile.getName().endsWith(".txt") || selectedfile.getName().endsWith(".fdb")) {
				contentPane.remove(mainImage);
				String text;
				try {
					Scanner sc = new Scanner(selectedfile);
					text = sc.useDelimiter("\\Z").next();
					sc.close();
					textarea.setText(text);
				} catch (FileNotFoundException er) {
					textarea.setText("");
				} catch(java.util.NoSuchElementException r) {
					textarea.setText("");
				}
				contentPane.repaint();
			}else {
				textarea.setText("");
			}
		});
		scrollPane.setViewportView(list);
		
		//input klasöründeki tüm dosyaları listeye almak için
		database.makeDB();
		database.readDBfromFolder();
		for(int i =0;i<database.tableObj.size();i++){
			filelistmodel.addElement(filelistmodel.addfile(database.tableObj.get(i).file));
			
			super_list_1.addLast(new JList<FileListDataModel>());
			
			List<DefaultListModel> projlistmodel = new ArrayList<DefaultListModel>();
			
			projlistmodel.addLast(new DefaultListModel<Object>());
			projlistmodel.getLast().add(0,database.tableObj.get(i).file.getName());
			super_list_1.getLast().setModel(projlistmodel.getLast());
		}
		
		
		
		
		//The NavBar
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		JMenuBar homebar = new JMenuBar();
		tabbedPane.addTab("Home", null, homebar, null);
		
		JMenu mnFile = new JMenu("File");
		homebar.add(mnFile);
		
		//Dosya sisteminden programa açılım butonu
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		mntmOpen.addActionListener(_ ->{
			FileChooserUI.open(filelistmodel, super_list_1);
		});
		
		//TODO kullanılmayan buton
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		//Yeni DataBase açılım butonu
		JMenuItem mntmNewDB = new JMenuItem("New DataBase");
		mnFile.add(mntmNewDB);
		mntmNewDB.addActionListener(_ ->{
			DataBaseOpenUI.open(this, database, filelistmodel, super_list_1, this.contentPane);
		});
		//TODO yorum
		//tema kısmı halloldu galiba
		//Programın temasını değiştirmek için buton
		JMenu mnEdit = new JMenu("Edit");
		homebar.add(mnEdit);
		
		JMenu mnTheme = new JMenu("Theme");
		mnEdit.add(mnTheme);
		
		JMenuItem mntmMedium = new JMenuItem("Medium");
		mnTheme.add(mntmMedium);
		
		JMenuItem mntmDark = new JMenuItem("Dark");
		mnTheme.add(mntmDark);
		
		JMenuItem mntmLight = new JMenuItem("Light");
		mnTheme.add(mntmLight);
		
		JMenuBar programbar = new JMenuBar();
		tabbedPane.addTab("Program", null, programbar, null);
		
		JButton runbttn = new JButton("Run");
		programbar.add(runbttn);
		JButton savebttn = new JButton("Save");
		programbar.add(savebttn);
		
		//Gezilebilir bir text editör için textarea scrollPane ile kaplanır
		textarea.setRows(10);
		JScrollPane textbox = new JScrollPane (textarea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		  
		runbttn.addActionListener(_->{
			functions.Parse(textarea.getText());
		});
		savebttn.addActionListener(_->{
			try {
				FileWriter writer = new FileWriter(selectedfile);
				writer.write(textarea.getText());
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		//Tab buttonlarına tıklandığını alabilmek için Listener yok.
		//Tabbed panede hangi tab'a tıklandığını alıp programda açılma ya da kapatılma işlemlerini yapar
		tabbedPane.addChangeListener(_->{
			if(tabbedPane.getSelectedIndex()==1) {
				this.contentPane.remove(functiontab);
				this.contentPane.add(textbox, BorderLayout.SOUTH);
				this.contentPane.repaint();
			}else if (tabbedPane.getSelectedIndex()==0) {
				this.contentPane.add(functiontab, BorderLayout.EAST);
				this.contentPane.remove(textbox);
				this.contentPane.repaint();
			}
		});
		
		mntmLight.addActionListener(_ -> {
    		contentPane.setBackground(Color.WHITE);
    		updateComponentColors(contentPane, Color.BLACK, Color.WHITE);
		});

		mntmDark.addActionListener(_ -> {
    		contentPane.setBackground(Color.DARK_GRAY);
    		updateComponentColors(contentPane, Color.WHITE, Color.DARK_GRAY);
		});


		mntmMedium.addActionListener(_ -> {
    		contentPane.setBackground(new Color(200, 200, 200));
    		updateComponentColors(contentPane, Color.BLACK, new Color(200, 200, 200));
		});
	}
	//TODO kullanılmayan kod?
	/*
	private void applyTheme(String theme) {
	Color background, foreground;

	switch (theme.toLowerCase()) {
		case "dark":
			background = new Color(45, 45, 45);
			foreground = new Color(220, 220, 220);
			break;
		case "medium":
			background = new Color(100, 100, 100);
			foreground = new Color(240, 240, 240);
			break;
		case "light":
		default:
			background = Color.WHITE;
			foreground = Color.BLACK;
			break;
	}

	UIManager.put("Panel.background", background);
	UIManager.put("Label.foreground", foreground);
	UIManager.put("Button.background", background);
	UIManager.put("Button.foreground", foreground);
	UIManager.put("Menu.background", background);
	UIManager.put("Menu.foreground", foreground);
	UIManager.put("MenuItem.background", background);
	UIManager.put("MenuItem.foreground", foreground);
	UIManager.put("List.background", background);
	UIManager.put("List.foreground", foreground);
	UIManager.put("TextField.background", background);
	UIManager.put("TextField.foreground", foreground);
	UIManager.put("TabbedPane.background", background);
	UIManager.put("TabbedPane.foreground", foreground);

	SwingUtilities.updateComponentTreeUI(this);
}*/

	private void updateComponentColors(JPanel panel, Color foreground, Color background) {
		for (java.awt.Component comp : panel.getComponents()) {
        	comp.setBackground(background);
        	comp.setForeground(foreground);
        	if (comp instanceof JPanel) {
            	updateComponentColors((JPanel) comp, foreground, background);
        	}
    	}
	}

	

	private void applyShapeFilter() {
    // Shape filtresi fonksiyonu
    	System.out.println("Applying shape filter...");
    // Burada istediğiniz işlemi yapabilirsiniz
	}


	public static void applyMedianBlur(String inputPath, String outputPath) {
		//TODO DO NOT REWRİTE THE input file
		System.out.println(selectedfile.getAbsolutePath());
        Mat src = Imgcodecs.imread(selectedfile.getAbsolutePath());
        Mat dst = new Mat();
        Imgproc.medianBlur(src, dst, 5);
        Imgcodecs.imwrite(selectedfile.getAbsolutePath(), dst);
    }

    public static void applyCanny(String inputPath, String outputPath) {
    	
        Mat src = Imgcodecs.imread(selectedfile.getAbsolutePath());
        Mat gray = new Mat();
        Mat edges = new Mat();

        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(gray, edges, 100, 200);
        Imgcodecs.imwrite(selectedfile.getAbsolutePath(), edges);
    }

    public static void adjustBrightnessContrast(String inputPath, String outputPath, double alpha, double beta) {
    	
        Mat src = Imgcodecs.imread(selectedfile.getAbsolutePath());
        Mat dst = new Mat();
        src.convertTo(dst, -1, alpha, beta);
        Imgcodecs.imwrite(selectedfile.getAbsolutePath(), dst);
    }

    public static void applyDominantColorKMeans(String inputPath, String outputPath, int k) {
    	
        Mat src = Imgcodecs.imread(selectedfile.getAbsolutePath());
        Mat reshaped = src.reshape(1, src.cols() * src.rows());
        Mat reshaped32f = new Mat();
        reshaped.convertTo(reshaped32f, CvType.CV_32F);

        Mat labels = new Mat();
        Mat centers = new Mat();
        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER, 10, 1.0);

        Core.kmeans(reshaped32f, k, labels, criteria, 3, Core.KMEANS_PP_CENTERS, centers);
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

        Imgcodecs.imwrite(selectedfile.getAbsolutePath(), colorBoxes);
        
    }
	 public static void applySketchEffect() {
	
       Mat image = Imgcodecs.imread(selectedfile.getAbsolutePath());
       if (image.empty()) {
           System.out.println("❌ Could not load image");
           return;
       }

       Mat gray = new Mat();
       Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

       Mat inverted = new Mat();
       Core.bitwise_not(gray, inverted);

       Mat blurred = new Mat();
       Imgproc.GaussianBlur(inverted, blurred, new Size(21, 21), 0);

       Mat invertedBlur = new Mat();
       Core.bitwise_not(blurred, invertedBlur);

       Mat sketch = new Mat();
       Core.divide(gray, invertedBlur, sketch, 256.0);

       Imgcodecs.imwrite(selectedfile.getAbsolutePath(), sketch);
       System.out.println("✅ Sketch effect saved to " + selectedfile.getAbsolutePath());
		
   }

   public static void applyCartoonPrepEffect() {
	
       Mat img = Imgcodecs.imread(selectedfile.getAbsolutePath());
       if (img.empty()) {
           System.out.println("❌ Could not load image");
           return;
       }

       Imgproc.resize(img, img, new Size(512, 512));

       Mat filtered = img.clone();
       for (int i = 0; i < 7; i++) {
           Mat temp = new Mat();
           Imgproc.bilateralFilter(filtered, temp, 9, 75, 75);
           filtered = temp;
       }

       Imgcodecs.imwrite(selectedfile.getAbsolutePath(), filtered);
       System.out.println("✅ Cartoon prep effect saved to " + selectedfile.getAbsolutePath());
	
   }

   public static void applySobelEdge() {
		
       Mat gray = Imgcodecs.imread(selectedfile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
       if (gray.empty()) {
           System.out.println("❌ Could not load image");
           return;
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

       Imgcodecs.imwrite(selectedfile.getAbsolutePath(), sobelOutput);
       System.out.println("✅ Sobel edge effect saved to " + selectedfile.getAbsolutePath());
	
   }
   public static void applyRgbFilter(int targetR, int targetG, int targetB) {
		try {
			// Değer sınaması
			if (targetR < 0 || targetR > 255 || targetG < 0 || targetG > 255 || targetB < 0 || targetB > 255) {
				JOptionPane.showMessageDialog(null, "⚠️ RGB değerleri 0 ile 255 arasında olmalıdır.");
				return;
			}

			int tolerance = 30;
			/* 
			Mat image = Imgcodecs.imread("resources/images/image4.jpg");

			if (image.empty()) {
				JOptionPane.showMessageDialog(null, "❌ Görsel yüklenemedi. Dosya yolu hatalı olabilir.");
				return;
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

			Imgcodecs.imwrite("output/rgb_filtered.jpg", result);
			JOptionPane.showMessageDialog(null, "✅ RGB filtre uygulandı!");
			*/
			
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "❌ Sayısal olmayan bir değer girdiniz. Lütfen sadece rakam kullanın.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "❌ Bir hata oluştu: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void applyColorInversion(String inputPath, String outputPath) {
		/* 
		Mat img = Imgcodecs.imread(inputPath);
		if (img.empty()) {
			System.out.println("❌ Could not load image from: " + inputPath);
			return;
		}

		Mat inverted = new Mat();
		Core.bitwise_not(img, inverted);

		boolean success = Imgcodecs.imwrite(outputPath, inverted);
		if (success) {
			System.out.println("✅ Color inversion saved to " + outputPath);
		} else {
			System.out.println("❌ Failed to save color inversion.");
		}
			*/

			//aşağısı try catch li ama kaydetme kısmı galiba try catchi hangisini beğenirseniz onu kullanabilirsiniz

		/*  
		try {
			Mat img = Imgcodecs.imread(inputPath);
			if (img.empty()) {
				System.out.println("❌ Could not load image from: " + inputPath);
				JOptionPane.showMessageDialog(null, "Resim yüklenemedi: " + inputPath, "Hata", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Mat inverted = new Mat();
			Core.bitwise_not(img, inverted);

			boolean success = Imgcodecs.imwrite(outputPath, inverted);
			if (success) {
				System.out.println("✅ Color inversion saved to " + outputPath);
				JOptionPane.showMessageDialog(null, "Ters renkli resim başarıyla kaydedildi:\n" + outputPath);
			} else {
				System.out.println("❌ Failed to save color inversion.");
				JOptionPane.showMessageDialog(null, "Resim kaydedilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e) {
			System.err.println("❌ applyColorInversion sırasında bir hata oluştu:");
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Bir hata oluştu:\n" + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
		}
		*/
	}

	public static void applyColorTintEffect(String inputPath, String outputPath) {
		/* 
       Mat src = Imgcodecs.imread(inputPath);
       if (src.empty()) {
           System.out.println("❌ Could not load image");
           return;
       }

       Mat tinted = new Mat();
       src.convertTo(tinted, -1, 1, 0); // Copy

       // Mavi ton ekle (mavi kanalını artır)
       Core.add(tinted, new Scalar(50, 0, 0), tinted); // BGR: (Blue, Green, Red)

       boolean success = Imgcodecs.imwrite(outputPath, tinted);
       if (success) {
           System.out.println("✅ Blue-tinted image saved!");
       } else {
           System.out.println("❌ Failed to save blue-tinted image.");
       }
		*/
   }

	public static void applyPixelEffect(String inputPath, String outputPath /* , Size pixelSize*/) {
		/* 
       Mat src = Imgcodecs.imread(inputPath);
       if (src.empty()) {
           System.out.println("❌ Could not load image");
           return;
       }

       // Küçült - sonra tekrar büyüt (piksel etkisi)
       Mat tmp = new Mat();
       Imgproc.resize(src, tmp, pixelSize, 0, 0, Imgproc.INTER_LINEAR);

       Mat pixelated = new Mat();
       Imgproc.resize(tmp, pixelated, src.size(), 0, 0, Imgproc.INTER_NEAREST);

       boolean success = Imgcodecs.imwrite(outputPath, pixelated);
       if (success) {
           System.out.println("✅ Pixelation effect saved to " + outputPath);
       } else {
           System.out.println("❌ Failed to save pixelated image.");
       }
		*/
   }



   //bu kısım gerekli vignette efekti için
	/*
	public static Mat createVignetteMask(Mat src) {
       int rows = src.rows();
       int cols = src.cols();

       Mat kernelX = Imgproc.getGaussianKernel(cols, cols / 2.0);
       Mat kernelY = Imgproc.getGaussianKernel(rows, rows / 2.0);
       Mat kernel = new Mat();
       Core.gemm(kernelY, kernelX.t(), 1, new Mat(), 0, kernel);

       Core.normalize(kernel, kernel, 0, 1, Core.NORM_MINMAX);
       Mat mask = new Mat();
       List<Mat> channels = new ArrayList<>();
       for (int i = 0; i < 3; i++) channels.add(kernel);
       Core.merge(channels, mask);
       return mask;
   }
	*/

	 public static void applyVignetteEffect(String inputPath, String outputPath) {/* 
       Mat src = Imgcodecs.imread(inputPath);
       if (src.empty()) {
           System.out.println("❌ Could not load image");
           return;
       }

       Mat mask = createVignetteMask(src);
       Mat vignette = new Mat();
       Core.multiply(src, mask, vignette, 1, CvType.CV_8UC3);

       boolean success = Imgcodecs.imwrite(outputPath, vignette);
       if (success) {
           System.out.println("✅ Vignette effect saved to " + outputPath);
       } else {
           System.out.println("❌ Failed to save vignette image.");
       }
		*/
   }





	public static void applyMotionBlurEffect(String inputPath, String outputPath, int kernelSize) {
		/* 
		Mat src = null;
		try {
			src = Imgcodecs.imread(inputPath);
			if (src.empty()) {
				System.out.println("❌ Could not load image");
				return;
			}
		} catch (Exception e) {
			System.out.println("❌ Error loading image: " + e.getMessage());
			return;
		}

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
			return;
		}

		// Çıkış dosyasını kaydetme işlemi
		try {
			boolean success = Imgcodecs.imwrite(outputPath, dst);
			if (success) {
				System.out.println("✅ Motion blur applied and saved to " + outputPath);
			} else {
				System.out.println("❌ Failed to save motion blurred image.");
			}
		} catch (Exception e) {
			System.out.println("❌ Error saving image: " + e.getMessage());
		}
		*/
	}
		


}
