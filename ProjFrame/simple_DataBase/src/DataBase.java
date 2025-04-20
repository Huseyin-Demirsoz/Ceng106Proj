import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class DataBase implements DB_methods{   // Dosya oluşturur veya siler

    public static ArrayList<Table> tableObj; // Referans dizisi de olabilir.

    public static void makeDB() throws IOException {

        //Table_name kullanıcıdan okunabilir
         String file_name = get_Tablename();

        File file = new File("C:\\Users\\my\\Desktop\\coverDB\\"+ file_name + ".txt");

        file.createNewFile(); // Boolean değer döner. Exception handling için kullanılabilir.


        tableObj = new ArrayList<>();
        tableObj.add( new Table(file,"C:\\Users\\my\\Desktop\\coverDB\\" + file_name,"C:\\Users\\my\\Desktop\\coverDB\\" + file_name));

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
