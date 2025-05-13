package mainpanel;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javax.swing.SwingUtilities;
import javax.smartcardio.CardChannel;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
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
import mainpanel.DataBase;


//bu yorum satırı olmicak 
/*
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
*/

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataBase database;

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
	
	public Main() {
		//since the public class inherits from the JFrame, methods can be called on with "this" but it is usually not necessary
		// Additional this added for clarity
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(500, 300, 1300, 800);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		database = new DataBase();
		
		this.setContentPane(contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane_1, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane_1.addTab("File view", null, scrollPane, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab("Project view", null, scrollPane_1, null);



		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane_2, BorderLayout.EAST);

		JScrollPane scrollPane2 = new JScrollPane();  // Yeni nesne!
		tabbedPane_2.addTab("Func type", null, scrollPane2, null);

		JScrollPane scrollPane3 = new JScrollPane();  // Yeni nesne!
		tabbedPane_2.addTab("Functions", null, scrollPane3, null);
		
		// Fonksiyonlar için panel oluşturuyoruz
    	JPanel functionPanel = new JPanel();
    	functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.Y_AXIS));
    	scrollPane3.setViewportView(functionPanel);
    
    	// Başlık butonları (Color ve Shape başlıkları)
    	JButton colorButton = new JButton("Color");
		colorButton.setMinimumSize(new Dimension(200, 50));
		colorButton.setMaximumSize(new Dimension(200, 50));
		colorButton.setPreferredSize(new Dimension(200, 50));
    	colorButton.addActionListener(e -> {
    		functionPanel.removeAll();  // Önceki butonları temizle

    		JButton medianBlurButton = new JButton("Apply Median Blur");
			medianBlurButton.setMinimumSize(new Dimension(200, 50));
			medianBlurButton.setMaximumSize(new Dimension(200, 50));
			medianBlurButton.setPreferredSize(new Dimension(200, 50));
    		medianBlurButton.addActionListener(evt -> 
        		applyMedianBlur("resources/images/image1.jpg", "output/image1.jpg")
    		);

    		JButton cannyButton = new JButton("Apply Canny Edge Detection");
			cannyButton.setMinimumSize(new Dimension(200, 50));
			cannyButton.setMaximumSize(new Dimension(200, 50));
			cannyButton.setPreferredSize(new Dimension(200, 50));
    		cannyButton.addActionListener(evt -> 
        		applyCanny("resources/images/image2.jpg", "output/image2.jpg")
    		);

    		JButton brightnessContrastButton = new JButton("Adjust Brightness & Contrast");
			brightnessContrastButton.setMinimumSize(new Dimension(200, 50));
			brightnessContrastButton.setMaximumSize(new Dimension(200, 50));
			brightnessContrastButton.setPreferredSize(new Dimension(200, 50));
    		brightnessContrastButton.addActionListener(evt -> 
        		adjustBrightnessContrast("resources/images/image4.jpg", "output/image4.jpg", 1.5, 50)
    		);

    		JButton kMeansButton = new JButton("Apply K-Means Color Clustering");
			kMeansButton.setMinimumSize(new Dimension(200, 50));
			kMeansButton.setMaximumSize(new Dimension(200, 50));
			kMeansButton.setPreferredSize(new Dimension(200, 50));
   			kMeansButton.addActionListener(evt -> 
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
			applyRgbBtn.addActionListener(evt -> {
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

    		functionPanel.revalidate();
    		functionPanel.repaint();
		});

		// Başlık butonları (Effect başlığı)
		JButton effectButton = new JButton("Effect");
		effectButton.setMinimumSize(new Dimension(200, 50));
		effectButton.setMaximumSize(new Dimension(200, 50));
		effectButton.setPreferredSize(new Dimension(200, 50));
		effectButton.addActionListener(e -> {
			functionPanel.removeAll();  // Önceki butonları temizle

			// Sketch Effect butonu
			JButton sketchButton = new JButton("Apply Sketch Effect");
			sketchButton.setMinimumSize(new Dimension(200, 50));
			sketchButton.setMaximumSize(new Dimension(200, 50));
			sketchButton.setPreferredSize(new Dimension(200, 50));
			sketchButton.addActionListener(evt ->
				applySketchEffect("resources/images/image1.jpg", "output/sketch.jpg")
			);

			// Cartoon Prep Effect butonu
			JButton cartoonButton = new JButton("Apply Cartoon Prep Effect");
			cartoonButton.setMinimumSize(new Dimension(200, 50));
			cartoonButton.setMaximumSize(new Dimension(200, 50));
			cartoonButton.setPreferredSize(new Dimension(200, 50));
			cartoonButton.addActionListener(evt ->
				applyCartoonPrepEffect("resources/images/image2.jpg", "output/cartoon.jpg")
			);

			// Sobel Edge butonu
			JButton sobelButton = new JButton("Apply Sobel Edge Detection");
			sobelButton.setMinimumSize(new Dimension(200, 50));
			sobelButton.setMaximumSize(new Dimension(200, 50));
			sobelButton.setPreferredSize(new Dimension(200, 50));
			sobelButton.addActionListener(evt ->
				applySobelEdge("resources/images/image3.jpg", "output/sobel.jpg")
			);

			// Color Inversion butonu
			JButton inversionButton = new JButton("Apply Color Inversion");
			inversionButton.setMinimumSize(new Dimension(200, 50));
			inversionButton.setMaximumSize(new Dimension(200, 50));
			inversionButton.setPreferredSize(new Dimension(200, 50));
			inversionButton.addActionListener(evt ->
				applyColorInversion("resources/images/image4.jpg", "output/inverted.jpg")
			);

			// Color Tint Effect butonu
			JButton tintButton = new JButton("Apply Color Tint Effect");
			tintButton.setMinimumSize(new Dimension(200, 50));
			tintButton.setMaximumSize(new Dimension(200, 50));
			tintButton.setPreferredSize(new Dimension(200, 50));
			tintButton.addActionListener(evt ->
				applyColorTintEffect("resources/images/image1.jpg", "output/tinted_image.png")
			);

			


			// Vignette Effect butonu
			JButton vignetteButton = new JButton("Apply Vignette Effect");
			vignetteButton.setMinimumSize(new Dimension(200, 50));
			vignetteButton.setMaximumSize(new Dimension(200, 50));
			vignetteButton.setPreferredSize(new Dimension(200, 50));
			vignetteButton.addActionListener(evt ->
				applyVignetteEffect("resources/images/image1.jpg", "output/vignette_image.png")
			);
			
			
			// Motion Blur Effect butonu
			JButton motionBlurButton = new JButton("Apply Motion Blur Effect");
			motionBlurButton.setMinimumSize(new Dimension(200, 50));
			motionBlurButton.setMaximumSize(new Dimension(200, 50));
			motionBlurButton.setPreferredSize(new Dimension(200, 50));
			motionBlurButton.addActionListener(evt ->
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

			pixelButton.addActionListener(evt -> {
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
			

			functionPanel.revalidate();
			functionPanel.repaint();
		});


    
    	JButton shapeButton = new JButton("Shape");
		shapeButton.setMinimumSize(new Dimension(200, 50));
		shapeButton.setMaximumSize(new Dimension(200, 50));
		shapeButton.setPreferredSize(new Dimension(200, 50));
    	shapeButton.addActionListener(e -> {
        	// Şekil filtresi ile ilgili butonları ekle
        	functionPanel.removeAll();  // Önceki butonları temizle
        	JButton shapeFilterButton = new JButton("Apply Shape Filter");
			shapeFilterButton.setMinimumSize(new Dimension(200, 50));
			shapeFilterButton.setMaximumSize(new Dimension(200, 50));
			shapeFilterButton.setPreferredSize(new Dimension(200, 50));
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
		
		List<JList<DefaultListModel>> super_list_1 = new ArrayList<JList<DefaultListModel>>();
		
		//sets the JList in the project view tab to the contents of the selected file
		FileListDataModel filelistmodel = new FileListDataModel();
		JList<FileListDataModel> list = new JList<FileListDataModel>(filelistmodel);
		list.addListSelectionListener(e -> {/*OLD ACTION CODE ->*/	//new ListSelectionListener() {
			//public void valueChanged(ListSelectionEvent e) {
			//list_1.setModel(new AbstractListModel() {String[] values = new String[] {};public int getSize() {return values.length;}public Object getElementAt(int index) {return values[index];}});
			//projlistmodel.addElement(list.getSelectedIndex());
			

			scrollPane_1.setViewportView(super_list_1.get(list.getSelectedIndex()));
		//}
		});
		scrollPane.setViewportView(list);
		
		//input klasöründeki tüm dosyaları listeye almak için
		DataBase.makeDB();
		DataBase.readDBfromFolder();
		for(int i =0;i<DataBase.tableObj.size();i++){
			filelistmodel.addElement(filelistmodel.addfile(DataBase.tableObj.get(i).file));
			
			super_list_1.addLast(new JList<DefaultListModel>());
			
			List<DefaultListModel> projlistmodel = new ArrayList<DefaultListModel>();
			
			projlistmodel.addLast(new DefaultListModel<Object>());
			projlistmodel.getLast().add(0,DataBase.tableObj.get(i).file.getName());
			super_list_1.getLast().setModel(projlistmodel.getLast());
		}
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		//The NavBar
		JMenuBar menuBar = new JMenuBar();
		tabbedPane.addTab("Home", null, menuBar, null);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		//IMPORTANT because of the lambda expression, local variable outside can not be changed because of scope conflict
		mntmOpen.addActionListener(e ->{
			//Opens the file chooser provided by swing
			JFileChooser xfile_chooser= new JFileChooser();
			int response =xfile_chooser.showOpenDialog(null);
			
			File file=null;
			
			if(response == JFileChooser.APPROVE_OPTION) {
				try{
					//file selected -> get path -> try to pull string until EOF(\\Z)
					file = new File(xfile_chooser.getSelectedFile().getAbsolutePath());
					try {
						//TODO: Parse file, prints out file for debug purposes
						String xfile_content = new Scanner(file).useDelimiter("\\Z").next();
						System.out.printf("%s\n",xfile_content);
					} catch (FileNotFoundException e1) {
						// TODO: Close file; handle error
						e1.printStackTrace();
					}
				}finally{
					if (file != null) {
						/*//TODO: handle file closing
						try {
							//TODO: close file
						}catch (IOException e2) {
							// This is unrecoverable. Just report it and move on
						e2.printStackTrace();
						}
						*/
					}
				}
				//filelistmodel.addfile(file);
				//TODO: Big 
				filelistmodel.addElement(filelistmodel.addfile(file));
				
				super_list_1.addLast(new JList<DefaultListModel>());
				
				List<DefaultListModel> projlistmodel = new ArrayList<DefaultListModel>();
				
				projlistmodel.addLast(new DefaultListModel<Object>());
				projlistmodel.getLast().add(0,file.getName());
				super_list_1.getLast().setModel(projlistmodel.getLast());
				
			}
		});
		JMenuItem mntmNewDB = new JMenuItem("New DataBase");
		mnFile.add(mntmNewDB);
		mntmNewDB.addActionListener(e ->{
			//TODO initializes new database 
			//JFrame newdbframe = new JFrame ("MyPanel");
			JDialog newdbframe = new JDialog(this, "Create a new database", JDialog.ModalityType.DOCUMENT_MODAL);
			//newdbframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			newdbframe.setBounds(100, 100, 450, 300);
			JPanel dbPane = new JPanel();
			
			dbPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			GridBagLayout gbl_dbPane = new GridBagLayout();
			gbl_dbPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_dbPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_dbPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_dbPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			dbPane.setLayout(gbl_dbPane);
			
			JLabel lblNewDatabaseName = new JLabel("New DataBase name:");
			GridBagConstraints gbc_lblNewDatabaseName = new GridBagConstraints();
			gbc_lblNewDatabaseName.anchor = GridBagConstraints.EAST;
			gbc_lblNewDatabaseName.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewDatabaseName.gridx = 3;
			gbc_lblNewDatabaseName.gridy = 2;
			dbPane.add(lblNewDatabaseName, gbc_lblNewDatabaseName);
			
			JTextField textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 4;
			gbc_textField.gridy = 2;
			dbPane.add(textField, gbc_textField);
			textField.setColumns(10);
			
			JButton btnCreate = new JButton("Create");
			GridBagConstraints gbc_btnCreate = new GridBagConstraints();
			gbc_btnCreate.insets = new Insets(0, 0, 5, 0);
			gbc_btnCreate.gridx = 5;
			gbc_btnCreate.gridy = 2;
			dbPane.add(btnCreate, gbc_btnCreate);
			//
			btnCreate.addActionListener(btn ->{
				try {
					File file = DataBase.makeDB(textField.getText());
					int j = 0;
					//TODO BAD CODE
					for(int i =0;DataBase.tableObj.get(i).file!=file && i <100;i++){
						j++;
					}
					//DataBase.tableObj.get(j).Write("asdf \n");
					
					filelistmodel.addElement(filelistmodel.addfile(file));
					
					super_list_1.addLast(new JList<DefaultListModel>());
					
					List<DefaultListModel> projlistmodel = new ArrayList<DefaultListModel>();
					
					projlistmodel.addLast(new DefaultListModel<Object>());
					projlistmodel.getLast().add(0,file.getName());
					super_list_1.getLast().setModel(projlistmodel.getLast());
				} catch (IOException e1) {
					// TODO:
					e1.printStackTrace();
				}

			});
			
			JRadioButton rdbtnNewRadioButton = new JRadioButton("image");
			GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
			gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnNewRadioButton.gridx = 4;
			gbc_rdbtnNewRadioButton.gridy = 3;
			dbPane.add(rdbtnNewRadioButton, gbc_rdbtnNewRadioButton);
			
			JButton btnCancel = new JButton("Cancel");
			GridBagConstraints gbc_btnCancel = new GridBagConstraints();
			gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
			gbc_btnCancel.gridx = 5;
			gbc_btnCancel.gridy = 3;
			dbPane.add(btnCancel, gbc_btnCancel);
			
			JRadioButton rdbtnFunction = new JRadioButton("function");
			GridBagConstraints gbc_rdbtnFunction = new GridBagConstraints();
			gbc_rdbtnFunction.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnFunction.gridx = 4;
			gbc_rdbtnFunction.gridy = 4;
			dbPane.add(rdbtnFunction, gbc_rdbtnFunction);
			
			JRadioButton rdbtnProject = new JRadioButton("project");
			GridBagConstraints gbc_rdbtnProject = new GridBagConstraints();
			gbc_rdbtnProject.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnProject.gridx = 4;
			gbc_rdbtnProject.gridy = 5;
			dbPane.add(rdbtnProject, gbc_rdbtnProject);
			
			/*
			dbPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			//text editor for fields
			JEditorPane editorPane = new javax.swing.JEditorPane();
			dbPane.add(editorPane, BorderLayout.SOUTH);
			*/
			newdbframe.setContentPane(dbPane);
			newdbframe.setVisible(true);
		});
		//tema kısmı halloldu galiba
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		mnEdit.setHorizontalAlignment(SwingConstants.LEFT);
		
		JMenu mnTheme = new JMenu("Theme");
		mnEdit.add(mnTheme);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Medium");
		mnTheme.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Dark");
		mnTheme.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Light");
		mnTheme.add(mntmNewMenuItem_2);


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


	public static void applyMedianBlur(String inputPath, String outputPath) {/* 
        Mat src = Imgcodecs.imread(inputPath);
        Mat dst = new Mat();
        Imgproc.medianBlur(src, dst, 5);
        Imgcodecs.imwrite(outputPath, dst);*/
    }

    public static void applyCanny(String inputPath, String outputPath) {/* 
        Mat src = Imgcodecs.imread(inputPath);
        Mat gray = new Mat();
        Mat edges = new Mat();

        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(gray, edges, 100, 200);
        Imgcodecs.imwrite(outputPath, edges);*/
    }

    public static void adjustBrightnessContrast(String inputPath, String outputPath, double alpha, double beta) {/* 
        Mat src = Imgcodecs.imread(inputPath);
        Mat dst = new Mat();
        src.convertTo(dst, -1, alpha, beta);
        Imgcodecs.imwrite(outputPath, dst);*/
    }

    public static void applyDominantColorKMeans(String inputPath, String outputPath, int k) {/* 
        Mat src = Imgcodecs.imread(inputPath);
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

            Rect rect = new Rect(i * boxWidth, 0, boxWidth, boxHeight);
            Imgproc.rectangle(colorBoxes, rect, scalarColor, -1);
        }

        Imgcodecs.imwrite(outputPath, colorBoxes);*/
    }

	 public static void applySketchEffect(String inputPath, String outputPath) {
		/* 
        Mat image = Imgcodecs.imread(inputPath);
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

        Imgcodecs.imwrite(outputPath, sketch);
        System.out.println("✅ Sketch effect saved to " + outputPath);
		*/
    }

    public static void applyCartoonPrepEffect(String inputPath, String outputPath) {
		/* 
        Mat img = Imgcodecs.imread(inputPath);
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

        Imgcodecs.imwrite(outputPath, filtered);
        System.out.println("✅ Cartoon prep effect saved to " + outputPath);
		*/
    }

    public static void applySobelEdge(String inputPath, String outputPath) {
		/* 
        Mat gray = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_GRAYSCALE);
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

        Imgcodecs.imwrite(outputPath, sobelOutput);
        System.out.println("✅ Sobel edge effect saved to " + outputPath);
		*/
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

