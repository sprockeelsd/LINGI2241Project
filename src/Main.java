import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] Requests = {"1,2;CONTENTS\n","1,2;CANADA\n","1,2;CONTENTS\n"};
        MyClient client1 = new MyClient(1,Requests);
        client1.connect("LAPTOP-I9J1EU77",1234);
    }
}
