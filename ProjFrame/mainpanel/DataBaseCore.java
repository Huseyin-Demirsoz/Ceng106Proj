package mainpanel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public abstract class DataBaseCore {   // Dosya oluşturur veya siler

    public ArrayList<Table> tableObj; // Referans dizisi de olabilir.
    
    public void makeDB() {
    	if(!Files.exists(Path.of("input")) && !Files.exists(Path.of("output"))){
    		new File("input").mkdirs();
    		new File("output").mkdirs();
    	}
    	tableObj = new ArrayList<>();
    }
    public File makeDB(String nameofdb)  {
    	/*
        //Table_name kullanıcıdan okunabilir
         String file_name = get_Tablename();

        File file = new File("C:\\Users\\my\\Desktop\\coverDB\\"+ file_name + ".txt");

        file.createNewFile(); // Boolean değer döner. Exception handling için kullanılabilir.


        
    	*/
    	//Creates new folders
    	//TODO: TEST ON WINDOWS
    	if(!Files.exists(Path.of("input")) && !Files.exists(Path.of("output"))){
    		new File("input").mkdirs();
    		new File("output").mkdirs();
    	}
    	
    	int i = 0;
    	while(Files.exists(Path.of("input/"+ nameofdb + i+".txt"))){
    		i++;
    	}
    	File file  = new File("input/"+nameofdb+ i +".txt");
    	
		
		try {
			file.createNewFile();
			System.out.printf(file.getAbsolutePath());
		} catch (IOException e1) {
			// TODO Handle file not being created
			e1.printStackTrace();
		}
		
		//Could error from not initialising the list !! TODO
		//tableObj = new ArrayList<>();
        tableObj.add( new Table(file,"input/" + file.getName(),"output/" + file.getName()));
        
        return file;
    }
    public void readDBfromFolder(/*get path?*/) {
    	//programımız ilk başladığında input klasörünün içindeki tüm dosyaları okuması için
    	if(!Files.exists(Path.of("input")) && !Files.exists(Path.of("output"))){
    		new File("input").mkdirs();
    		new File("output").mkdirs();
    	}

    	try {
    		List<File> filesInFolder = Files.walk(Paths.get("input"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
    		for(File file : filesInFolder){
    	        tableObj.add( new Table(file,"input/" + file.getName(),"output/" + file.getName()));
    		}
		} catch (IOException e) {
			// TODO Coulnd't reach file for either not being able access or file doesn't exist
			e.printStackTrace();
		}
    	
    }
    public void deleteDB(Table tb){

        tb = null;
        System.gc();

        //Dosyayı'da bilgisayardan sil.

    }

    public  String get_Tablename(){

        Scanner input = new Scanner(System.in);

        System.out.print("Dosya adı giriniz");

        String dosAdi = input.nextLine();

        input.close();
        return dosAdi;
    }
	
}
class ImgDataBase extends DataBaseCore{
	
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
		//String text;
		
		//text = new Scanner(new File(path)).useDelimiter("\\Z").next();
		//text =programtext;
		//int current;
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
							/* Bitmiş fonksyonlar:
								ToGrayScale
								Gaussian Blur
								Canny
							
							*/
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
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = Dominant_Color.wMat(in0); // DEBUG NO-OP function
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
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
										Double in1=0.,in2=0.;
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
										//dest = in0; // DEBUG NO-OP function
										
										//To Grayscale before applying canny
										List<Mat> color = new ArrayList<Mat>(3);
										Imgproc.cvtColor(in0, in0, Imgproc.COLOR_RGB2HSV);
										Core.split(in0, color);
										color.set(1, color.get(1).setTo(new Scalar(0)));
										Core.merge(color, in0);
										Imgproc.cvtColor(in0, in0, Imgproc.COLOR_HSV2RGB);
										
										
										Imgproc.Canny(in0,dest, in1, in2);
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
										dest = in0; // DEBUG NO-OP function
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
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
										dest = in0; // DEBUG NO-OP function
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
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
										dest = in0; // DEBUG NO-OP function
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
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
										dest = in0; // DEBUG NO-OP function
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
										if(!progstack.peek().equals("stack")){
											Imgcodecs.imwrite(progstack.pop(), dest);
										}else{
											progstack.pop();
											imgpassingbuff.push(dest);
										}
									}break;
								case "Text Detection":{
										Mat dest = new Mat();
										Mat in0 = new Mat();
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = in0; // DEBUG NO-OP function
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
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
										if(!progstack.peek().equals("stack")){
											in0 = Imgcodecs.imread(progstack.pop());
										}else{
											progstack.pop();
											in0 = imgpassingbuff.pop();
										}
										dest = in0; // DEBUG NO-OP function
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
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
										//dest = in0; // DEBUG NO-OP function
										Imgproc.GaussianBlur(in0, dest, in1, in2);
										//Imgproc.cvtColor(in0, dest, Imgproc.COLOR_RGB2GRAY);
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
		
		text =null;
		System.gc();
		
		
		/*
		Scanner text;
		try {
			text = new Scanner(tableObj.get(0).file).useDelimiter("\\S");
			String current;
			enum state{
				CODE,COMMENT
			} 
			state status=state.CODE;
			while(text.hasNext()) {
				current = text.next();
				if(current=="/") {
					if(text.) {
						text.skip("\\*\\/");
					}
				}
			}
			text.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}

