public class Test {
    public static void main(String args[]){
        String S = "coucou";
        String s = "cou";
        boolean contains = S.contains(s);
        System.out.println(contains);
        MyClient client = new MyClient(0);
    }
}
