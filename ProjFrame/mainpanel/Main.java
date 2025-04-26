package mainpanel;

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

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataBase database;
	File selectedfile ;//TODO
	final class imgpanel extends JPanel{
		Image img;
		protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.setPaintMode();
	        if (img != null) {
	            Graphics2D g2d = (Graphics2D) g.create();
	            int x = (getWidth() - img.getWidth(null)) / 2;
	            int y = (getHeight() - img.getHeight(null)) / 2;
	            g2d.drawImage(img, x, y, this);
	            g2d.dispose();
	        }
	    }
		void setimg(Image imgx){
			img=imgx;
		}
	}
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
		this.setBounds(100, 100, 800, 500);
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
			selectedfile = filelistmodel.getElementAt(list.getSelectedIndex(),true);
			scrollPane_1.setViewportView(super_list_1.get(list.getSelectedIndex()));
			if(selectedfile.getName().endsWith(".jpg") || selectedfile.getName().endsWith(".png")){
			Mat image_tmp = org.opencv.imgcodecs.Imgcodecs.imread(selectedfile.getAbsolutePath());
			Image bufImage = HighGui.toBufferedImage(image_tmp);
			//TODO
			mainImage.setimg(bufImage);
			this.contentPane.add(mainImage, BorderLayout.CENTER);
			/*mainImage.setIcon((new ImageIcon(bufImage
					.getScaledInstance(mainImage.getBounds().width+1,mainImage.getBounds().height+1,Image.SCALE_SMOOTH)
					)));
			*/
			mainImage.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				int scalex=10 ,scaley=10;
				if(mainImage.getWidth()>mainImage.getHeight()){
					scalex=mainImage.getHeight()*(bufImage.getWidth(null)/bufImage.getHeight(null));
					scaley=mainImage.getHeight();
				}else {
					scalex=mainImage.getHeight()*(bufImage.getWidth(null)/bufImage.getHeight(null));
					scaley=mainImage.getHeight();
				}
				mainImage.imageUpdate(bufImage
						.getScaledInstance(scalex,scaley,Image.SCALE_SMOOTH), ALLBITS, EXIT_ON_CLOSE, ABORT, WIDTH, HEIGHT);
				mainImage.repaint();
				/*mainImage.paint(bufImage
					.getScaledInstance(scalex,scaley,Image.SCALE_SMOOTH)
				);*/
			}
				public void componentMoved(ComponentEvent e) {}
				public void componentShown(ComponentEvent e) {}
				public void componentHidden(ComponentEvent e) {}
			});
			
			}
			System.gc();
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
					/*try {
						//TODO: Parse file, prints out file for debug purposes
						String xfile_content = new Scanner(file).useDelimiter("\\Z").next();
						System.out.printf("%s\n",xfile_content);
					} catch (FileNotFoundException e1) {
						// TODO: Close file; handle error
						e1.printStackTrace();
					}*/
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
		
		
	}

}
