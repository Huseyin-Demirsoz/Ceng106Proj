package mainpanel;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListDataModel extends DefaultListModel {
	List<File> files = new ArrayList<File>();
	
	public int getSize() {
		return files.size();
	}
	public Object getElementAt(int index) {
		return files.get(index).getName();
	}
	public File getElementAt(int index,Boolean tr) {
		return files.get(index);
	}
	
	public String addfile(File filecurr) {
		if(this.files==null) {
			this.files.add(0, filecurr);
		}
		this.files.add(filecurr);
		return filecurr.getName();
	}
}
