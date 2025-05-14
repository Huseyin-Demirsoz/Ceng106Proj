package mainpanel;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListDataModel extends DefaultListModel<Object> implements ListModel<Object> {
	private static final long serialVersionUID = 1L;
	List<File> files = new ArrayList<File>();
	
	public int getSize() {
		return files.size();
	}
	public Object getNameAt(int index) {
		return files.get(index).getName();
	}
	public File getFileAt(int index) {
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
