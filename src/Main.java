import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] Requests = {"1,2;CONTENTS\n","1,2;CAN\n"};
        MyClient client1 = new MyClient(1,Requests);
        client1.connect("LAPTOP-TQFF0SRJ",1234);
    }
}
