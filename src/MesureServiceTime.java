import java.io.IOException;

public class MesureServiceTime {

    public static void main(String[] args) throws IOException, InterruptedException {

        for (int i = 0 ; i < 100;i++) {
            Main.main(args);
            Thread.sleep(2000);
        }
    }
    }

