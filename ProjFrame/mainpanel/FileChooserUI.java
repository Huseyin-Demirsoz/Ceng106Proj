package mainpanel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;

public class FileChooserUI {
	public static void open(FileListDataModel filelistmodel, List<JList<FileListDataModel>> super_list_1){
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
			
			super_list_1.addLast(new JList<FileListDataModel>());
			
			List<DefaultListModel> projlistmodel = new ArrayList<DefaultListModel>();
			
			projlistmodel.addLast(new DefaultListModel<Object>());
			projlistmodel.getLast().add(0,file.getName());
			super_list_1.getLast().setModel(projlistmodel.getLast());
			//projlistmodel=null;
			//System.gc();
			
		}
	}
}
