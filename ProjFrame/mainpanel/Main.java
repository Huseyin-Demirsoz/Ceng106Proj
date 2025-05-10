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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javax.swing.SwingUtilities;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import javax.swing.ImageIcon;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

//import org.opencv.*;
/*
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
*/

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataBase database;
	File selectedfile ;//TODO
	
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
	
	public Main() {
		//since the public class inherits from the JFrame, methods can be called on with "this" but it is usually not necessary
		// Additional this added for clarity
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(500, 300, 1300, 800);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		database = new DataBase();
		
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



		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane_2, BorderLayout.EAST);

		JScrollPane scrollPane2 = new JScrollPane();  // Yeni nesne!
		tabbedPane_2.addTab("Func type", null, scrollPane2, null);

		JScrollPane scrollPane3 = new JScrollPane();  // Yeni nesne!
		tabbedPane_2.addTab("Functions", null, scrollPane3, null);

		
		//projlistmodel is changed to be created at file opening
		
		//TODO: change "DefaultListModel" with unique model extending DefaultListModel for both projlistmodel and super_list_1
		
		//super_list_1 is the list of the "list elements" that get displayed 
		//for each file for example the functions and image path contained in a database
		
		List<JList<DefaultListModel>> super_list_1 = new ArrayList<JList<DefaultListModel>>();
		
		//sets the JList in the project view tab to the contents of the selected file
		FileListDataModel filelistmodel = new FileListDataModel();
		JList<FileListDataModel> list = new JList<FileListDataModel>(filelistmodel);
		imgpanel mainImage = new imgpanel();
		list.addListSelectionListener(e -> {
			LeftPanelList.listListener(selectedfile,filelistmodel,list,scrollPane_1,super_list_1,mainImage,this.contentPane);
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
			FileChooserUI.open(filelistmodel, super_list_1);
		});
		JMenuItem mntmNewDB = new JMenuItem("New DataBase");
		mnFile.add(mntmNewDB);
		mntmNewDB.addActionListener(e ->{
			DataBaseOpenUI.open(this,filelistmodel, super_list_1,this.contentPane);
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

  */
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


}
