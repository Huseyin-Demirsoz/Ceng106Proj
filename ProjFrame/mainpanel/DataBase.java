package mainpanel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class DataBase implements DB_methods{   // Dosya oluşturur veya siler

    public static ArrayList<Table> tableObj; // Referans dizisi de olabilir.
    
    public static void makeDB() {
    	if(!Files.exists(Path.of("input")) && !Files.exists(Path.of("output"))){
    		new File("input").mkdirs();
    		new File("output").mkdirs();
    	}
    	tableObj = new ArrayList<>();
    }
    public static File makeDB(String nameofdb) throws IOException {
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
    public static void readDBfromFolder(/*get path?*/) {
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
    public static void deleteDB(Table tb){

        tb = null;
        System.gc();

        //Dosyayı'da bilgisayardan sil.

    }

    public static  String get_Tablename(){

        Scanner input = new Scanner(System.in);

        System.out.print("Dosya adı giriniz");

        String dosAdi = input.nextLine();

        return dosAdi;


    }
}
