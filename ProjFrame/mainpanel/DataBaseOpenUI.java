package mainpanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DataBaseOpenUI {
	public static void open(JFrame mainf,JPanel contentPane,DefaultListModel<String> tables){//,ImgDataBase database,FileListDataModel filelistmodel, List<JList<FileListDataModel>> super_list_1) {
		//TODO initializes new database 
		//JFrame newdbframe = new JFrame ("MyPanel");
		JDialog newdbframe = new JDialog(mainf, "Create a new database", JDialog.ModalityType.DOCUMENT_MODAL);
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
		btnCreate.addActionListener(_ ->{
			tables.addElement(ImgDataBase.makeProjTable(textField.getText()));
		});
		
		
		dbPane.setBackground(contentPane.getBackground());
		dbPane.setForeground(contentPane.getForeground());
		for(Component comp :dbPane.getComponents()){
	    	comp.setBackground(contentPane.getBackground());
	    	comp.setForeground(contentPane.getForeground());
	    }
		newdbframe.setContentPane(dbPane);
		newdbframe.setVisible(true);
	}
}
