package mainpanel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public abstract class DataBaseCore {   // Dosya oluşturur veya siler
	static String getDBvalues(String tablename) {
		String str = "";
		try {
			Statement stmt = Main.conn.createStatement();
			ResultSet filesdb = stmt.executeQuery("SELECT * FROM "+ tablename +";");
			while(filesdb.next()) {
				str = filesdb.getString("filepath")+'\n'+filesdb.getString("name")+'\n'+str;
			}
		}catch(SQLException e) {
			//TODO error
		}
		return str;
	}
}

class ImgDataBase extends DataBaseCore{
	public static String makeProjTable(String name){
    	System.out.println("ok");
    	String nam ="";
    	
    	for (String str : name.split("[^a-z]+")) {
    		nam= nam+str;
    	}
    	if(nam == "") {
    		JOptionPane.showMessageDialog(null, "bad name");
    	}
    	try {
			Statement stmt = Main.conn.createStatement();
			stmt.execute("CREATE TABLE "+name+"(filepath varchar(255),name varchar(255));");
		}catch(SQLException e){
			
		}
    	return nam;
    }
}

class FunctionDataBase extends DataBaseCore{
	public void Parse(Path filepath){
		try {
			Scanner sc = new Scanner(new File(filepath.toString()));
			this.Parse(sc.useDelimiter("\\Z").next());
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Parse(String text){
		Stack<String> progstack = new Stack<String>();
		Stack<Mat> imgpassingbuff = new Stack<Mat>();
		enum state{
			CODE,COMMENT
		} 
		state status=state.CODE;
		
		for(int i =0; i<text.length()-1;i++){
			switch(status) {
				case CODE:
					switch(text.charAt(i)) {
						case '/':
							//checks for comment block opening
							if(text.charAt(i+1)=='*'){
								status=state.COMMENT;
								i++;
								break;
							}
						break;
						case '#':{
							//for pushing images onto the stack for functions
							//this is in the form of:
							//# "IMGPATH"
							//Farklı versyonları vardır
							// quotation version
							i++;
							int start=i,stop=i;
							while(text.charAt(start)!='"') {
								//For dropping white space before path
								start++;
								i++;
								if(text.charAt(i)=='\n') {
									//TODO erroneous code found -> bad image path -> exited without giving path
									System.err.println("ERROR");
									break;
								}
							}
							start++;
							stop = start;
							while(text.charAt(stop)!='"') {
								stop++;
								i++;
								if(text.charAt(i)=='\n') {
									//TODO erroneous code found -> bad image path -> exited without giving path
									System.err.println("ERROR");
									break;
								}
							}
							progstack.push(text.substring(start, stop));
						}break;
						case '%':{
							//% denotes functions in the form of 
							//% "function name"
							i++;
							int start=i,stop=i;
							while(text.charAt(start)!='"') {
								//For dropping white space before path
								start++;
								i++;
								if(text.charAt(i)=='\n') {
									//TODO erroneous code found -> bad image path -> exited without giving path
									System.err.println("ERROR");
									break;
								}
							}
							start++;
							stop = start;
							while(text.charAt(stop)!='"') {
								stop++;
								i++;
								if(text.charAt(i)=='\n') {
									//TODO erroneous code found -> bad image path -> exited without giving path
									System.err.println("ERROR");
									break;
								}
							}
							// This switch case matches a function and runs it
							switch (text.substring(start, stop)){
								case "ToGrayScale":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										//dest = in0; // DEBUG NO-OP function
										dest = ToGray.wMat(in0);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Dominant Color":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										int in1 =0;
										if(progstack.peek().matches("\\d{1,3}")){
											in1 = Integer.parseInt(progstack.peek());
											progstack.pop();
										}else {
											System.err.println("ERROR: input number.");
											break;
										}
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = Dominant_Color.wMat(in0,in1);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Canny":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										Double in1=0.;
										Double in2=0.;
										if(progstack.peek().matches("\\d{1,8}(\\.?\\d{0,8}),\\d{1,8}(\\.?\\d{0,8})")){
											String[] nums =progstack.peek().split(",");
											//The gaussian kernel can not be a modulo of 2
											in1= Double.parseDouble(nums[0]);
											in2= Double.parseDouble(nums[1]);
											progstack.pop();
										}else {
											System.err.println("ERROR: Bad treshold values");
											break;
										}
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										//This is a more granular version of canny edge detection, default value is 100, 200
										//To Grayscale before applying canny
										List<Mat> color = new ArrayList<Mat>(3);
										Imgproc.cvtColor(in0, in0, Imgproc.COLOR_RGB2HSV);
										Core.split(in0, color);
										color.set(1, color.get(1).setTo(new Scalar(0)));
										Core.merge(color, in0);
										Imgproc.cvtColor(in0, in0, Imgproc.COLOR_HSV2RGB);
										
										
										Imgproc.Canny(in0,dest, in1, in2);
										
										//dest = Canny.wMat(in0);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Bright and Contrast":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = BrightnessContrast.wMat(in0, 1.5, 50.);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Sketch Effect":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = SketchEffect.wMat(in0);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Sobel":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = SobelEdge.wMat(in0);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Cartoon":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = CartoonPrepEffect.wMat(in0);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "RGB Manipulation":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										Double R = 0.;
										Double G = 0.;
										Double B = 0.;
										if(progstack.peek().matches("\\d{1,8}(\\.?\\d{0,8}),\\d{1,8}(\\.?\\d{0,8}),\\d{1,8}(\\.?\\d{0,8})")){
											String[] nums =progstack.peek().split(",");
											//The gaussian kernel can not be a modulo of 2
											R = Double.parseDouble(nums[0]);
											G = Double.parseDouble(nums[1]);
											B = Double.parseDouble(nums[2]);
											progstack.pop();
										}else {
											System.err.println("ERROR: Bad rgb values");
											break;
										}
										
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = RgbFilter.wMat(in0,R,G,B);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Gaussian Blur":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										Size in1 ;
										double in2 = 0;
										if(progstack.peek().matches("\\d{1,8}(\\.?\\d{0,8})")){
											in2 = Double.parseDouble(progstack.pop());
										}else {
											System.err.println("ERROR: Bad  sigma");
											break;
										}
										if(progstack.peek().matches("\\d{1,8}(\\.?\\d{0,8}),\\d{1,8}(\\.?\\d{0,8})")){
											String[] nums =progstack.peek().split(",");
											//The gaussian kernel can not be a modulo of 2
											in1= new Size(Double.parseDouble(nums[0]),Double.parseDouble(nums[1]));
											if(in1.height%2==0||in1.width%2==0){
												System.err.println("ERROR: Bad size. Size can not be a modulo of 2");
												break;
											}
											progstack.pop();
										}else {
											System.err.println("ERROR: Bad size");
											break;
										}
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										
										Imgproc.GaussianBlur(in0, dest, in1, in2);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Median Blur":{
									Mat dest = new Mat();
									Mat in0 = new Mat();
									if(!progstack.peek().equals("stack")){
										in0 = Imgcodecs.imread(progstack.pop());
									}else{
										progstack.pop();
										in0 = imgpassingbuff.pop();
									}
									dest = MedianBlur.wMat(in0);
									if(!progstack.peek().equals("stack")){
										Imgcodecs.imwrite(progstack.pop(), dest);
									}else{
										progstack.pop();
										imgpassingbuff.push(dest);
									}
								}break;
								case "Color Inversion":{
									Mat dest = new Mat();
									Mat in0 = new Mat();
									if(!progstack.peek().equals("stack")){
										in0 = Imgcodecs.imread(progstack.pop());
									}else{
										progstack.pop();
										in0 = imgpassingbuff.pop();
									}
									dest = ColorInversion.wMat(in0);
									if(!progstack.peek().equals("stack")){
										Imgcodecs.imwrite(progstack.pop(), dest);
									}else{
										progstack.pop();
										imgpassingbuff.push(dest);
									}
								}break;
								case "Motion Blur Effect":{
									Mat dest = new Mat();
									Mat in0 = new Mat();
									if(!progstack.peek().equals("stack")){
										in0 = Imgcodecs.imread(progstack.pop());
									}else{
										progstack.pop();
										in0 = imgpassingbuff.pop();
									}
									dest = MotionBlurEffect.wMat(in0,15);
									if(!progstack.peek().equals("stack")){
										Imgcodecs.imwrite(progstack.pop(), dest);
									}else{
										progstack.pop();
										imgpassingbuff.push(dest);
									}
								}break;
								case "Vignette Effect":{
									Mat dest = new Mat();
									Mat in0 = new Mat();
									if(!progstack.peek().equals("stack")){
										in0 = Imgcodecs.imread(progstack.pop());
									}else{
										progstack.pop();
										in0 = imgpassingbuff.pop();
									}
									dest = VignetteEffect.wMat(in0,15);
									if(!progstack.peek().equals("stack")){
										Imgcodecs.imwrite(progstack.pop(), dest);
									}else{
										progstack.pop();
										imgpassingbuff.push(dest);
									}
								}break;
								case "Pixel Effect":{
									Mat dest = new Mat();
									Mat in0 = new Mat();
									Size in1;
									if(progstack.peek().matches("\\d{1,8},\\d{1,8}")){
										String[] nums =progstack.peek().split(",");
										//The gaussian kernel can not be a modulo of 2
										in1= new Size(Double.parseDouble(nums[0]),Double.parseDouble(nums[1]));
										progstack.pop();
									}else {
										System.err.println("ERROR: Bad size");
										break;
									}
									if(!progstack.peek().equals("stack")){
										in0 = Imgcodecs.imread(progstack.pop());
									}else{
										progstack.pop();
										in0 = imgpassingbuff.pop();
									}
									dest = PixelEffect.wMat(in0,in1.width,in1.height);
									if(!progstack.peek().equals("stack")){
										Imgcodecs.imwrite(progstack.pop(), dest);
									}else{
										progstack.pop();
										imgpassingbuff.push(dest);
									}
								}break;
								case "Color Tint Effect":{
									Mat dest = new Mat();
									Mat in0 = new Mat();
									if(!progstack.peek().equals("stack")){
										in0 = Imgcodecs.imread(progstack.pop());
									}else{
										progstack.pop();
										in0 = imgpassingbuff.pop();
									}
									dest = ColorTintEffect.wMat(in0);
									if(!progstack.peek().equals("stack")){
										Imgcodecs.imwrite(progstack.pop(), dest);
									}else{
										progstack.pop();
										imgpassingbuff.push(dest);
									}
								}break;
							}
						}break;
					}
					break;
				case COMMENT:
					if(text.charAt(i)=='*'){
						if(text.charAt(i+1)=='/'){
							status=state.CODE;
							i++;
							//System.out.append('\n');
							break;
						}
					}
					//System.out.append(text.charAt(i));
					break;
			default:
				break;
			}
			
		}
		while(!progstack.empty()) {
			System.out.println( progstack.pop());
		}
		
		System.out.println("Done!");
		
		text =null;
		System.gc();
	}
}

