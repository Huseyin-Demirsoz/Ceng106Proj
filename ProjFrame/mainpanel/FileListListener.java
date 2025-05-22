package mainpanel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class FileListListener {
	public static void listListener(JScrollPane scrollPane_1,JList<String> sublist,imgpanel mainImage,JPanel contentPane,DefaultListModel<String> activefiles,JTextArea textarea) {
		if(sublist.getSelectedIndex()+1>0) {
			Main.setselectedfile(new File(activefiles.get(sublist.getSelectedIndex())));
		}else {
			return;
		}
		if(Main.getselectedfile().getName().endsWith(".jpg") || Main.getselectedfile().getName().endsWith(".png")){
		Mat image_tmp = org.opencv.imgcodecs.Imgcodecs.imread(Main.getselectedfile().getAbsolutePath());
		Image bufImage = HighGui.toBufferedImage(image_tmp);
		
		contentPane.add(mainImage, BorderLayout.CENTER);

		if(mainImage.getHeight()!=0 && mainImage.getWidth()!=0){
		int scalex=10 ,scaley=10;
		if(mainImage.getWidth()>mainImage.getHeight()){
			scalex=mainImage.getHeight()*(bufImage.getWidth(null)/bufImage.getHeight(null));
			scaley=mainImage.getHeight();
		}else {
			scalex=mainImage.getWidth()*(bufImage.getWidth(null)/bufImage.getHeight(null));
			scaley=mainImage.getWidth();
		}
		
		mainImage.setimg(bufImage.getScaledInstance(
				scalex
				//bufImage.getWidth(null)
				,scaley 
				//bufImage.getHeight(null)
				,Image.SCALE_SMOOTH ));
		}
		
		mainImage.updateUI();
		
		image_tmp.release();
		bufImage.flush();
		}
		//If the selectedfile is a text file or a function database
		if (Main.getselectedfile() != null &&(Main.getselectedfile().getName().endsWith(".txt") || Main.getselectedfile().getName().endsWith(".fdb"))) {
			contentPane.remove(mainImage);
			String text;
			try {
				Scanner sc = new Scanner(Main.getselectedfile());
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
	}
}
