package mainpanel;
import java.io.*;
import java.util.ArrayList;

public class Table implements Table_methods {

    public  int id = 1; // static olmayacak.

    public ArrayList<image_Obj> tbl ;

    //şimdilik debug publictir TODO
    public File file ;

    private  String inputAdd;

    private String outputAdd;

    public String get_input(){ return inputAdd;}


    public Table(File file, String inputAdd, String outputAdd) {

        tbl = new ArrayList<>();

        this.file = file;

        this.inputAdd = inputAdd;

        this.outputAdd = outputAdd;
    }

    public void Write(String content) throws IOException {

        tbl.add(new image_Obj(id)); // ArrayList'e yeni satırı ekle. Yani image obj'si ekle.

        FileWriter writer = new FileWriter(file,true);

        writer.write(content + System.lineSeparator());// Exception eklemeyi unutma.

        writer.close();


        id++; // Eleman silinmek istendiginde unique'liği sağlar.


    }

    public String Read() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        while((line = reader.readLine()) != null)

            System.out.println(line);


        return "Başarılı";

    }

    public String[] Read(int i) throws IOException { // i.satırdaki elemanı döndürür.


        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        while((line = reader.readLine()) != null && i != 0) {

            if(i == 0)
                return line.split(",");

            i--;

        }
        return null;

    }






}
