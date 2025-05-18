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
import javax.swing.event.ListSelectionListener;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	static JPanel contentPane;
	static File selectedfile ;//TODO
	static int selectedindex;
	static Connection conn;


	
	/*static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }*/

	static{
		System.load("C:\\Users\\my\\Documents\\NewOpenCV\\opencv\\build\\java\\x64\\opencv_java4110.dll");
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
		/*
		if(conn != null) {
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/


	}

	public static void connect() {
        // connection string
		 conn = null;
		try {
			//var url = "jdbc:sqlite:database.db";
			String url = "jdbc:sqlite:C:\\Users\\my\\Documents\\SQL Lite for Java\\database.db";
			try {
				conn = DriverManager.getConnection(url);
				System.out.println("Connection to SQLite has been established.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			Statement stmt = conn.createStatement();
			try {
				//stmt.execute("CREATE TABLE Tablex(file varchar(255),inder integer);");
			}catch(Exception e){

			}
			/*
			for(int i =0;i<10;i++) {
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Tablex(filepath,name) VALUES(?,?)");
				pstmt.setString(1, "OK");
				pstmt.setInt(2, 5);
				pstmt.executeUpdate();
			}
			*/
			//stmt.execute("DROP TABLE Tablex");
			ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type='table';");
			while(rs.next()) {
				System.out.println("OK: "+rs.getString(2));
				//stmt.execute("DROP TABLE ");
				//System.out.println("Num: "+rs.getInt("inder"));
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			System.out.println(e.getMessage());
			//e.printStackTrace();

		}
		//System.exit(0);
    }

	public Main() throws Exception {

		List<FilterClassifier.Document> docDataSet = Documentation.DocumateTheWords(); // İlk model eğitimi için önceden işlenmiş data set'ini text ve albel olarak ayırıyoruz.

		FilterClassifier.NaiveBayesModel obj = new InheritFilterClassifier(); // Polymorphism

		obj.train(docDataSet);
//NLP Eğitimi yapılır.

		connect();
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			try {
				stmt.execute("CREATE TABLE Tablex(filepath varchar(255),name varchar(255));");
			}catch(Exception e){
				
			}
			//stmt.execute("DROP TABLE Tablex");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		JTextArea textarea = new JTextArea ();
		ImgDataBase database = new ImgDataBase();
		//TODO DEBUG
		FunctionDataBase functions = new FunctionDataBase();
		//functions.Parse(Paths.get("/home/hd/Desktop/projtestfile.txt"));// TODO DEBUG file
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
		
		//Fonksyonlar başlangıç
		/*
		JTabbedPane functiontab = new JTabbedPane(JTabbedPane.TOP);
		this.contentPane.add(functiontab, BorderLayout.EAST);
		*/
		ApplyFunctionUI applyFunctionui = new ApplyFunctionUI();
		applyFunctionui.Open();//functiontab,selectedfile);
		//fonksyonlar bitiş
    	
		//TODO ingilizce
		//super_list_1 is the list of the "list elements" that get displayed 
		//for each file for example the functions and image path contained in a database
		//List<JList<FileListDataModel>> super_list_1 = new ArrayList<JList<FileListDataModel>>();
		
		//sets the JList in the project view tab to the contents of the selected file
		//DBListDataModel dblistmodel = new DBListDataModel();
		//FileListDataModel filelistmodel = new FileListDataModel();
		DefaultListModel<String> activefiles = new DefaultListModel<String>();
		DefaultListModel<String> tables = new DefaultListModel<String>();
		JList<String> sublist = new JList<String>(activefiles);
		JList<String> list = new JList<String>(tables);
		imgpanel mainImage = new imgpanel();
		list.addListSelectionListener(_ -> {
			//Listenin seçim ihtiyaçları fazladan oduğundan yeni dosyaya atılmıştır
			
			
			 LeftPanelList.listListener(list,scrollPane_1,activefiles);
			//Text editörde gözükmesi için seçilenin okunup yazılmasını sağlar
			
			 sublist.addListSelectionListener(_ ->{
				FileListListener.listListener(scrollPane_1,sublist,mainImage,this.contentPane,activefiles);
				if (selectedfile != null &&(selectedfile.getName().endsWith(".txt") || selectedfile.getName().endsWith(".fdb"))) {
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
		});
		scrollPane.setViewportView(list);
		scrollPane_1.setViewportView(sublist);
		
		/*
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
		*/

		try {
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type='table';");
			while(rs.next()) {
				String DBname =rs.getString(2);
				System.out.println("OK: "+DBname);
				tables.addElement(DBname);
				
				//super_list_1.addLast(new JList<FileListDataModel>());
				ResultSet filesdb = stmt2.executeQuery("SELECT * FROM "+ DBname +";");
				while(filesdb.next()) {
					File filex =new File(filesdb.getString(2));
					activefiles.addElement(filex.getAbsolutePath());
					//super_list_1.getLast().add(filex.getName(), null);
					//super_list_1.getLast().add( filesdb.getString("name"));
					//filelistmodel.addfile(filex);
					//dblistmodel.addfile(DBname, filex);
				}/*
				System.out.println("OK: ");
				List<DefaultListModel> projlistmodel = new ArrayList<DefaultListModel>();
				
				projlistmodel.addLast(new DefaultListModel<Object>());
				projlistmodel.getLast().add(0,DBname);
				super_list_1.getLast().setModel(projlistmodel.getLast());
				*/
				//stmt.execute("DROP TABLE ");
				//System.out.println("Num: "+rs.getInt("inder"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
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
			FileChooserUI.open(tables, activefiles);
		});
		
		//TODO kullanılmayan buton
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		//Yeni DataBase açılım butonu
		JMenuItem mntmNewDB = new JMenuItem("New Project");
		mnFile.add(mntmNewDB);
		mntmNewDB.addActionListener(_ ->{
			DataBaseOpenUI.open(this, this.contentPane,tables);
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
		runbttn.setMinimumSize(new Dimension(200, 50));
		runbttn.setMaximumSize(new Dimension(200, 50));
		runbttn.setPreferredSize(new Dimension(200, 50));
		programbar.add(runbttn);
		JButton savebttn = new JButton("Save");
		savebttn.setMinimumSize(new Dimension(200, 50));
		savebttn.setMaximumSize(new Dimension(200, 50));
		savebttn.setPreferredSize(new Dimension(200, 50));
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
				//this.contentPane.remove(functiontab);
				ApplyFunctionUI.Close();
				this.contentPane.add(textbox, BorderLayout.SOUTH);
				this.contentPane.repaint();
			}else if (tabbedPane.getSelectedIndex()==0) {
				//this.contentPane.add(functiontab, BorderLayout.EAST);
				ApplyFunctionUI.Reopen();
				this.contentPane.remove(textbox);
				this.contentPane.repaint();
			}
		});
		
		mntmLight.addActionListener(_ -> {
    		contentPane.setBackground(new Color(133,47,204));
    		updateComponentColors(contentPane, Color.BLACK, new Color(150,150,150));
		});

		mntmDark.addActionListener(_ -> {
    		contentPane.setBackground(new Color(39,6,66));
    		updateComponentColors(contentPane, Color.WHITE, new Color(39,6,66));
		});


		mntmMedium.addActionListener(_ -> {
    		contentPane.setBackground(new Color(72,22,112));
    		updateComponentColors(contentPane, Color.BLACK, new Color(200, 200, 200));
		});

		JTextField textField = new JTextField(30);// NLP için Text Box ekledim.
		JButton button = new JButton("Uygula");

		final String[] prediction = new String[1];
		button.addActionListener(e->{
								 prediction[0] = obj.predict(KullaniciInput.Lemmatize(textField.getText()));  // Lemmatize ve normalize ve sonra prediction ve uygula
								ApplyChosenFilter.Applying((prediction[0]).trim());  //Filtre Uygulanması
		});

		textField.setBounds(120,120,30,10);  // Text Box boyutları
		//contentPane.add(textField);
		programbar.add(textField);

		//contentPane.add(button);
		programbar.add(button);
		button.setBounds(150,120,15,10);  // Button boyutları




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
}
