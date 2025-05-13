package mainpanel;


import java.awt.Color;
import javax.swing.UIManager;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.css.Rect;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import mainpanel.FileListDataModel;
import mainpanel.DataBaseCore;
import javax.swing.ImageIcon;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ImgDataBase database;
	private JTextArea textarea;
	static File selectedfile ;//TODO
	
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
	/*
	The main function dispatches the public constructor as a main thread
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
	
	//@SuppressWarnings("unused")
	public Main() {
		//since the public class inherits from the JFrame, methods can be called on with "this" but it is usually not necessary
		// Additional this added for clarity
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1200,900);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		database = new ImgDataBase();
		//TODO DEBUG
		FunctionDataBase functions = new FunctionDataBase();
		functions.Parse(Paths.get("/home/hd/Desktop/projtestfile.txt"));// TODO DEBUG file
		
		this.setContentPane(contentPane);
		BorderLayout mainborder= new BorderLayout();
		
		mainborder.setHgap(8);
		mainborder.setVgap(8);
		this.contentPane.setLayout(mainborder);

		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane_1, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane_1.addTab("File view", null, scrollPane, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab("Project view", null, scrollPane_1, null);



		JTabbedPane functiontab = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(functiontab, BorderLayout.EAST);

		JScrollPane scrollPane2 = new JScrollPane();  // Yeni nesne!
		functiontab.addTab("Func type", null, scrollPane2, null);

		JScrollPane scrollPane3 = new JScrollPane();  // Yeni nesne!
		functiontab.addTab("Functions", null, scrollPane3, null);

		// Fonksiyonlar için panel oluşturuyoruz
    	JPanel functionPanel = new JPanel();
    	functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.Y_AXIS));
    	scrollPane3.setViewportView(functionPanel);
    
    	// Başlık butonları (Color ve Shape başlıkları)
    	JButton colorButton = new JButton("Color");
    	colorButton.addActionListener(e -> {
    		functionPanel.removeAll();  // Önceki butonları temizle

    		JButton medianBlurButton = new JButton("Apply Median Blur");
    		medianBlurButton.addActionListener(evt -> 
        		applyMedianBlur("resources/images/image1.jpg", "output/image1.jpg")
    		);

    		JButton cannyButton = new JButton("Apply Canny Edge Detection");
    		cannyButton.addActionListener(evt -> 
        		applyCanny("resources/images/image2.jpg", "output/image2.jpg")
    		);

    		JButton brightnessContrastButton = new JButton("Adjust Brightness & Contrast");
    		brightnessContrastButton.addActionListener(evt -> 
        		adjustBrightnessContrast("resources/images/image4.jpg", "output/image4.jpg", 1.5, 50)
    		);

    		JButton kMeansButton = new JButton("Apply K-Means Color Clustering");
   			kMeansButton.addActionListener(evt -> 
        		applyDominantColorKMeans("resources/images/image3.jpg", "output/image3.jpg", 5)
    		);

    // Butonları panele ekle
    		functionPanel.add(medianBlurButton);
    		functionPanel.add(cannyButton);
    		functionPanel.add(brightnessContrastButton);
    		functionPanel.add(kMeansButton);

    		functionPanel.revalidate();
    		functionPanel.repaint();
		});
    	// Başlık butonları (Effect başlığı)
    			JButton effectButton = new JButton("Effect");
    			effectButton.addActionListener(e -> {
    				functionPanel.removeAll();  // Önceki butonları temizle

    				// Sketch Effect butonu
    				JButton sketchButton = new JButton("Apply Sketch Effect");
    				sketchButton.addActionListener(evt ->
    					//applySketchEffect("resources/images/image1.jpg", "output/sketch.jpg")
    				applySketchEffect()
    				);

    				// Cartoon Prep Effect butonu
    				JButton cartoonButton = new JButton("Apply Cartoon Prep Effect");
    				cartoonButton.addActionListener(evt ->
    					//applyCartoonPrepEffect("resources/images/image2.jpg", "output/cartoon.jpg")
    				applyCartoonPrepEffect()
    				);

    				// Sobel Edge butonu
    				JButton sobelButton = new JButton("Apply Sobel Edge Detection");
    				sobelButton.addActionListener(evt ->
    					//applySobelEdge("resources/images/image3.jpg", "output/sobel.jpg")
    				applySobelEdge()
    				);

    				// Butonları panele ekle
    				functionPanel.add(sketchButton);
    				functionPanel.add(cartoonButton);
    				functionPanel.add(sobelButton);

    				functionPanel.revalidate();
    				functionPanel.repaint();
    			});


    
    	JButton shapeButton = new JButton("Shape");
    	shapeButton.addActionListener(e -> {
        	// Şekil filtresi ile ilgili butonları ekle
        	functionPanel.removeAll();  // Önceki butonları temizle
        	JButton shapeFilterButton = new JButton("Apply Shape Filter");
        	shapeFilterButton.addActionListener(evt -> applyShapeFilter());
        	functionPanel.add(shapeFilterButton);
        	functionPanel.revalidate();
        	functionPanel.repaint();
    	});
    
    	// Func type başlığına butonları ekleyelim
    	JPanel funcTypePanel = new JPanel();
    	funcTypePanel.setLayout(new BoxLayout(funcTypePanel, BoxLayout.Y_AXIS));
    	funcTypePanel.add(colorButton);
    	funcTypePanel.add(shapeButton);
    	funcTypePanel.add(effectButton);
    	scrollPane2.setViewportView(funcTypePanel);


		

		
		//projlistmodel is changed to be created at file opening
		
		//TODO: change "DefaultListModel" with unique model extending DefaultListModel for both projlistmodel and super_list_1
		
		//super_list_1 is the list of the "list elements" that get displayed 
		//for each file for example the functions and image path contained in a database
		

    	
    	
		List<JList<FileListDataModel>> super_list_1 = new ArrayList<JList<FileListDataModel>>();
		
		//sets the JList in the project view tab to the contents of the selected file
		FileListDataModel filelistmodel = new FileListDataModel();
		JList<FileListDataModel> list = new JList<FileListDataModel>(filelistmodel);
		imgpanel mainImage = new imgpanel();
		list.addListSelectionListener(e -> {
			selectedfile = LeftPanelList.listListener(selectedfile,filelistmodel,list,scrollPane_1,super_list_1,mainImage,this.contentPane);
			if (selectedfile.getName().endsWith(".txt") || selectedfile.getName().endsWith(".fdb")) {
				contentPane.remove(mainImage);
				String text;
				try {
					text = new Scanner(selectedfile).useDelimiter("\\Z").next();
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
		
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		JMenuBar homebar = new JMenuBar();
		tabbedPane.addTab("Home", null, homebar, null);
		//JPanel menutabs = new JPanel();
		//this.contentPane.add(menutabs, BorderLayout.NORTH);
		//The NavBar
		
		//menutabs.add(homebar);
		
		
		JMenu mnFile = new JMenu("File");
		homebar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		//IMPORTANT because of the lambda expression, local variable outside can not be changed because of scope conflict
		mntmOpen.addActionListener(e ->{
			FileChooserUI.open(filelistmodel, super_list_1);
		});
		JMenuItem mntmNewDB = new JMenuItem("New DataBase");
		mnFile.add(mntmNewDB);
		mntmNewDB.addActionListener(e ->{
			DataBaseOpenUI.open(this, database, filelistmodel, super_list_1, this.contentPane);
		});
		//tema kısmı halloldu galiba
		JMenu mnEdit = new JMenu("Edit");
		homebar.add(mnEdit);
		mnEdit.setHorizontalAlignment(SwingConstants.LEFT);
		
		JMenu mnTheme = new JMenu("Theme");
		mnEdit.add(mnTheme);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Medium");
		mnTheme.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Dark");
		mnTheme.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Light");
		mnTheme.add(mntmNewMenuItem_2);
		
		BorderLayout programlayout = new BorderLayout();
		
		
		JMenuBar programbar = new JMenuBar();
		tabbedPane.addTab("Program", null, programbar, null);
		
		JButton runbttn = new JButton("Run");
		programbar.add(runbttn);
		runbttn.addActionListener(e->{
			functions.Parse(textarea.getText());
		});
		JButton savebttn = new JButton("Save");
		programbar.add(savebttn);
		savebttn.addActionListener(e->{
			try {
				FileWriter writer = new FileWriter(selectedfile);
				writer.write(textarea.getText());
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		textarea = new JTextArea ();
		textarea.setRows(10);
		JScrollPane textbox = new JScrollPane (textarea, 
		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tabbedPane.addChangeListener(e->{
			System.out.println("Tab: " + tabbedPane.getSelectedIndex());
			//for(Component c : this.contentPane.getComponents()){

			//Find the components you want to remove
			//Remove it
			if(tabbedPane.getSelectedIndex()==1) {
				System.out.println(functiontab);
				this.contentPane.remove(functiontab);
				this.contentPane.add(textbox, BorderLayout.SOUTH);
				this.contentPane.repaint();
			}else if (tabbedPane.getSelectedIndex()==0) {
				this.contentPane.add(functiontab, BorderLayout.EAST);
				this.contentPane.remove(textbox);
				this.contentPane.repaint();
			}
			//}
		});
		programbar.addPropertyChangeListener(e ->{
			
			//this.contentPane.setLayout(programlayout);
		});
		
		
		/*
		mntmNewMenuItem.addActionListener(e -> {
			this.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		    this.setMediumTheme();
		});

		mntmNewMenuItem_2.addActionListener(e -> {
			this.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			this.setLightTheme();
		});

		mntmNewMenuItem_1.addActionListener(e -> {
		    this.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		    this.setDarkTheme();
		});

		// Method to change LookAndFeel
	}
	
	private void setLookAndFeel(String lookAndFeel) {
	    try {
	        UIManager.setLookAndFeel(lookAndFeel);
	        SwingUtilities.updateComponentTreeUI(this); // Update the UI with the new LookAndFeel
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	}

	// Medium theme: #1c6362
	private void setMediumTheme() {

	    this.contentPane.setBackground(new java.awt.Color(0x1c, 0x63, 0x62));
	    this.contentPane.setForeground(java.awt.Color.WHITE);
	    for(Component comp :this.contentPane.getComponents()){
	    	comp.setBackground(new java.awt.Color(0x1c, 0x63, 0x62));
	    	comp.setForeground(java.awt.Color.WHITE);
	    }

	}

	// Dark theme: #242226
	private void setLightTheme() {

	    
	    this.contentPane.setBackground(new java.awt.Color(0x82, 0xb8, 0xb7));
	    this.contentPane.setForeground(java.awt.Color.BLACK);
	    for(Component comp :this.contentPane.getComponents()){
	    	comp.setBackground(new java.awt.Color(0x82, 0xb8, 0xb7));
	    	comp.setForeground(java.awt.Color.BLACK);
	    }
	    

	}

	// Light theme: #82b8b7
	private void setDarkTheme() {

	    this.contentPane.setBackground(new java.awt.Color(0x42, 0x45, 0x45));
	    this.contentPane.setForeground(java.awt.Color.WHITE);
	    for(Component comp :this.contentPane.getComponents()){
	    	comp.setBackground(new java.awt.Color(0x42, 0x45, 0x45));
	    	comp.setForeground(java.awt.Color.WHITE);
	    }

	}

}
*/
		mntmNewMenuItem_2.addActionListener(e -> {
    		contentPane.setBackground(Color.WHITE);
    		updateComponentColors(contentPane, Color.BLACK, Color.WHITE);
		});

		mntmNewMenuItem_1.addActionListener(e -> {
    		contentPane.setBackground(Color.DARK_GRAY);
    		updateComponentColors(contentPane, Color.WHITE, Color.DARK_GRAY);
		});


		mntmNewMenuItem.addActionListener(e -> {
    		contentPane.setBackground(new Color(200, 200, 200));
    		updateComponentColors(contentPane, Color.BLACK, new Color(200, 200, 200));
		});


		

		
	}

	@SuppressWarnings("unused")
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
}

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
		


}
