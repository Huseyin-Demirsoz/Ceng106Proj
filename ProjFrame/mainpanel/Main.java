package mainpanel;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
import javax.swing.SwingConstants;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
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

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane_1, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane_1.addTab("File view", null, scrollPane, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab("Project view", null, scrollPane_1, null);
		//TODO: change "DefaultListModel" with unique model extending DefaultListModel
		List<DefaultListModel> projlistmodel = new ArrayList<DefaultListModel>();
		List<JList<DefaultListModel>> super_list_1 = new ArrayList<JList<DefaultListModel>>();
		
		
		FileListDataModel filelistmodel = new FileListDataModel();
		JList<FileListDataModel> list = new JList<>(filelistmodel);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//list_1.setModel(new AbstractListModel() {String[] values = new String[] {};public int getSize() {return values.length;}public Object getElementAt(int index) {return values[index];}});
				//projlistmodel.addElement(list.getSelectedIndex());
				

				scrollPane_1.setViewportView(super_list_1.get(list.getSelectedIndex()));
			}
		});
		scrollPane.setViewportView(list);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		JMenuBar menuBar = new JMenuBar();
		tabbedPane.addTab("Home", null, menuBar, null);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		//IMPORTANT because of the lambda expression, local variable outside can not be changed 
		var xfile_wrapper = new Object(){File xfile=null;};
		mntmOpen.addActionListener(e ->{//new ActionListener() {
			//public void actionPerformed(ActionEvent e) {
				
				JFileChooser xfile_chooser= new JFileChooser();
				int response =xfile_chooser.showOpenDialog(null);
				
				
				if(response == JFileChooser.APPROVE_OPTION) {
					xfile_wrapper.xfile = new File(xfile_chooser.getSelectedFile().getAbsolutePath());
					try {
						String xfile_content = new Scanner(xfile_wrapper.xfile).useDelimiter("\\Z").next();
						
						System.out.printf("%s\n",xfile_content);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//filelistmodel.addfile(xfile_wrapper.xfile);
					
					filelistmodel.addElement(filelistmodel.addfile(xfile_wrapper.xfile));
					
					super_list_1.addLast(new JList());
					projlistmodel.addLast(new DefaultListModel());
					projlistmodel.getLast().add(0,xfile_wrapper.xfile.getName());
					super_list_1.getLast().setModel(projlistmodel.getLast());
					
				}
				
				
			//}
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
