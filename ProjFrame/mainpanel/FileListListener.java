package mainpanel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class FileListListener {
	public static void listListener(JScrollPane scrollPane_1,JList<String> sublist,imgpanel mainImage,JPanel contentPane,DefaultListModel<String> activefiles) {
		if(sublist.getSelectedIndex()+1>0) {
			Main.selectedfile = new File(activefiles.get(sublist.getSelectedIndex()));
		}else {
			return;
		}
		if(Main.selectedfile.getName().endsWith(".jpg") || Main.selectedfile.getName().endsWith(".png")){
		Mat image_tmp = org.opencv.imgcodecs.Imgcodecs.imread(Main.selectedfile.getAbsolutePath());
		Image bufImage = HighGui.toBufferedImage(image_tmp);
		//TODO
		//mainImage.setimg(bufImage);
		//mainImage.setBounds(mainImage.getX(),mainImage.getY(),image_tmp.width(),image_tmp.height());
		//mainImage.setVisible(true);
		//mainImage.setBounds(10,10,(int)this.contentPane.getLayout().preferredLayoutSize(mainImage).getWidth()*2+1,(int)this.contentPane.getLayout().preferredLayoutSize(mainImage).getHeight()*2+1);
		//mainImage.setLayout(null);
		
		contentPane.add(mainImage, BorderLayout.CENTER);
		//contentPane.getcomponen
		//mainImage.updateUI();

		if(mainImage.getHeight()!=0 && mainImage.getWidth()!=0){
		int scalex=10 ,scaley=10;
		if(mainImage.getWidth()>mainImage.getHeight()){
			scalex=mainImage.getHeight()*(bufImage.getWidth(null)/bufImage.getHeight(null));
			scaley=mainImage.getHeight();
		}else {
			scalex=mainImage.getWidth()*(bufImage.getWidth(null)/bufImage.getHeight(null));
			scaley=mainImage.getWidth();
		}
		
		/*mainImage.setBounds(10,10,scalex,scaley);
		mainImage.setLayout(null);
		
		mainImage.setBounds(mainImage.getX(),mainImage.getY(),scalex,scaley);
		*/
		mainImage.setimg(bufImage.getScaledInstance(
				scalex
				//bufImage.getWidth(null)
				,scaley 
				//bufImage.getHeight(null)
				,Image.SCALE_SMOOTH ));
		}
		//this.contentPane.add(mainImage, BorderLayout.CENTER);
		
		mainImage.updateUI();
		
		//mainImage.imageUpdate(bufImage,FRAMEBITS, mainImage.getX(), mainImage.getY(), scalex, scaley);
		/*
		mainImage.addComponentListener(new ComponentListener() {
		public void componentResized(ComponentEvent e) {
			int scalex=10 ,scaley=10;
			if (bufImage.getWidth(null)  > mainImage.getWidth() || bufImage.getHeight(null) > mainImage.getHeight()) {
				scalex = bufImage.getWidth(null)  / mainImage.getWidth();
				scaley = bufImage.getHeight(null) / mainImage.getHeight();
	        }

	        else if (mainImage.getWidth() < bufImage.getWidth(null) && mainImage.getHeight() < bufImage.getHeight(null)) {
	        	scalex = mainImage.getWidth() / bufImage.getWidth(null);
	        	scaley = mainImage.getHeight() / bufImage.getHeight(null);
	        }
	        else {
	        	scalex = 1;
	        	scaley = 1;
	        	
	        }
			mainImage.imageUpdate(bufImage
					.getScaledInstance(scalex,scaley,Image.SCALE_SMOOTH), ALLBITS,
					mainImage.getX(), mainImage.getY(),
					scalex, scaley);
			
		}
			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		});*/
		image_tmp.release();
		bufImage.flush();
		}
		ApplyFunctionUI.selectedfile = Main.selectedfile;
	}
}
