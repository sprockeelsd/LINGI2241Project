import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
        String[] Requests = {"2;CONTENTS\n","1;CANADA\n","1;CONTENTS\n"};
        MyClient client1 = new MyClient(1,Requests);
        client1.connect("LAPTOP-TQFF0SRJ",1234);
    }
}
