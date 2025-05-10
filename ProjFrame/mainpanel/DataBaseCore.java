package mainpanel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

import org.opencv.core.Mat;
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

        return dosAdi;
    }
	public void Parse(String path){
		String reader;
		
		try {
			reader = new Scanner(new File(path)).useDelimiter("\\Z").next();
			//int current;
			Stack<String> progstack = new Stack<>();
			enum state{
				CODE,COMMENT
			} 
			state status=state.CODE;
			for(int i =0; i<reader.length()-1;i++){
				switch(status) {
					case CODE:
						switch(reader.charAt(i)) {
							case '/':
								//checks for comment block opening
								if(reader.charAt(i+1)=='*'){
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
								while(reader.charAt(start)!='"') {
									//For dropping white space before path
									start++;
									i++;
									if(reader.charAt(i)=='\n') {
										//TODO erroneous code found -> bad image path -> exited without giving path
										System.out.println("ERROR");
										break;
									}
								}
								start++;
								stop = start;
								while(reader.charAt(stop)!='"') {
									stop++;
									i++;
									if(reader.charAt(i)=='\n') {
										//TODO erroneous code found -> bad image path -> exited without giving path
										System.out.println("ERROR");
										break;
									}
								}
								progstack.push(reader.substring(start, stop));
							}break;
							case '%':{
								//% denotes functions in the form of 
								//% "function name"
								i++;
								int start=i,stop=i;
								while(reader.charAt(start)!='"') {
									//For dropping white space before path
									start++;
									i++;
									if(reader.charAt(i)=='\n') {
										//TODO erroneous code found -> bad image path -> exited without giving path
										System.out.println("ERROR");
										break;
									}
								}
								start++;
								stop = start;
								while(reader.charAt(stop)!='"') {
									stop++;
									i++;
									if(reader.charAt(i)=='\n') {
										//TODO erroneous code found -> bad image path -> exited without giving path
										System.out.println("ERROR");
										break;
									}
								}
								switch (reader.substring(start, stop)){
									case "ToGrayScale":
										Mat dest = new Mat();
										Imgproc.cvtColor(Imgcodecs.imread(progstack.pop()), dest, Imgproc.COLOR_RGB2GRAY);
										Imgcodecs.imwrite(progstack.pop(), dest);
										break;
								}
							}break;
						}
						break;
					case COMMENT:
						if(reader.charAt(i)=='*'){
							if(reader.charAt(i+1)=='/'){
								status=state.CODE;
								i++;
								//System.out.append('\n');
								break;
							}
						}
						//System.out.append(reader.charAt(i));
						break;
				default:
					break;
				}
				
			}
			while(!progstack.empty()) {
				System.out.println( progstack.pop());
			}
			
			reader =null;
			System.gc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		Scanner reader;
		try {
			reader = new Scanner(tableObj.get(0).file).useDelimiter("\\S");
			String current;
			enum state{
				CODE,COMMENT
			} 
			state status=state.CODE;
			while(reader.hasNext()) {
				current = reader.next();
				if(current=="/") {
					if(reader.) {
						reader.skip("\\*\\/");
					}
				}
			}
			reader.close();
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
class ImgDataBase extends DataBaseCore{
	
}

