import java.io.IOException;

public class Test {


    public static void main(String[] args) throws IOException {


        DataBase.makeDB(); // Tablo oluşturma.


        Table obj = DataBase.tableObj.get(0);

        String content = obj.id + "," + obj.get_input();

        obj.Write(content);



        if(obj.id == 2)
            System.out.println("Kayıt başarılı");

        if(obj.Read() == "Başarılı")

            System.out.println("Okuma Başarılı");



    }











}
