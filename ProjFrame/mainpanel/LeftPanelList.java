package mainpanel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class LeftPanelList {
	public static void listListener(JList<String> list,
			JScrollPane scrollPane_1,DefaultListModel<String> activefiles) {
		
		Main.selectedindex=list.getSelectedIndex();
		activefiles.removeAllElements();
		try {
			Statement stmt = Main.conn.createStatement();
			ResultSet filesdb = stmt.executeQuery("SELECT * FROM "+ list.getSelectedValue() +";");
			while(filesdb.next()) {
				File file =new File(filesdb.getString("filepath"));
				activefiles.addElement(file.getAbsolutePath());
			}
		}catch(SQLException e) {
			//TODO error
		}
	}
}