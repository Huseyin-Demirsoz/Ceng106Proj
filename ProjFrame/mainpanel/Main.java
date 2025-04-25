package mainpanel;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private JPanel contentPane;

	/*
	The main function dispatches the public constructor as a main thread
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main framex = new Main();
					frame = framex;
					framex.setVisible(true);
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

		this.setContentPane(contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		
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
		list.addListSelectionListener(e -> {/*OLD ACTION CODE ->*/	//new ListSelectionListener() {
			//public void valueChanged(ListSelectionEvent e) {
			//list_1.setModel(new AbstractListModel() {String[] values = new String[] {};public int getSize() {return values.length;}public Object getElementAt(int index) {return values[index];}});
			//projlistmodel.addElement(list.getSelectedIndex());
			

			scrollPane_1.setViewportView(super_list_1.get(list.getSelectedIndex()));
		//}
		});
		scrollPane.setViewportView(list);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		//The NavBar
		JMenuBar menuBar = new JMenuBar();
		tabbedPane.addTab("Home", null, menuBar, null);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
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
			//The lambda is usually e but since we already use e for opening the JFrame 
			//"mntmNewDB.addActionListener(e ->{" we have to use a different variable 
			//since it is in the scope that uses "e"
			btnCreate.addActionListener(btn ->{
				System.out.printf("");
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
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
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
		
		JMenuBar menuBar_1 = new JMenuBar();
		tabbedPane.addTab("Connection", null, menuBar_1, null);
		
		JButton btnConnect = new JButton("Connect");
		menuBar_1.add(btnConnect);
		
		JMenu mnConnectionSettings = new JMenu("Connection Settings");
		menuBar_1.add(mnConnectionSettings);
		
		JCheckBoxMenuItem chckbxmntmCancelConnectino = new JCheckBoxMenuItem("Cancel Connectino");
		mnConnectionSettings.add(chckbxmntmCancelConnectino);
	}

}
