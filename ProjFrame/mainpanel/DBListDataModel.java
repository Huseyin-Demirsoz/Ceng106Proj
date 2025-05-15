package mainpanel;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class DBListDataModel extends DefaultListModel<Object> implements ListModel<Object> {
	List<String> dbnames = new ArrayList<String>();
	public int getSize() {
		return dbnames.size();
	}
	public String getNameAt(int index) {
		return dbnames.get(index);
	}
	public String getNameAt(int index,int index2) {
		return dbnames.get(index);
	}
	public String addDB(String name){
		dbnames.add(name);
		return name;
	}
	public File getFileAt(int indexdb,int index2) {
		File file = null;
		try {
			Statement stmt = Main.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT filepath FROM "+dbnames.get(indexdb)+" WHERE rowid = "+index2+";");
			file = new File(rs.getString("filepath"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(file==null){
			return null;
		}
		return file;
	}
	
	public String addfile(int indexdb,File filecurr) {
		
		PreparedStatement pstmt;
		try {
			pstmt = Main.conn.prepareStatement("INSERT INTO "+dbnames.get(indexdb)+"(filepath,name) VALUES(?,?)");
			pstmt.setString(1, filecurr.getAbsolutePath());
			pstmt.setString(2, filecurr.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filecurr.getName();
	}
	/*public String addfile(String dbname,File filecurr) {
		
		PreparedStatement pstmt;
		try {
			pstmt = Main.conn.prepareStatement("INSERT INTO "+dbname+"(filepath,name) VALUES(?,?)");
			pstmt.setString(1, filecurr.getAbsolutePath());
			pstmt.setString(2, filecurr.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filecurr.getName();
	}*/
}
