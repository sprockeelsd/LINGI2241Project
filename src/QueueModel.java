import javax.swing.text.MutableAttributeSet;

public class QueueModel {

    public static void main(String[] args) {

        //Modeling of lambda : the arrival rate of the clients,
        //double lambda = Main.arrival_rate;
        double lambda = 5;

        //Modeling of mu : 1/E[S] the service rate of one single service station
        double averageResponseTime = 0.754; // The average response time with no waiting time (Service Time) : E[S] in seconds.
        double mu = 1/averageResponseTime;


        int m = 4;
        double a = lambda/mu;   //utile pour le nombre moyen d'utilisateurs connectés
        double xi = lambda/(m*mu);     //utilisation

        System.out.println();
        System.out.println("Queue modelled as a M|M|m queue with m = " + m + " : ");
        System.out.println("average time between two connections (mu)[1/s] : " + 1/lambda);
        System.out.println("average number of new connections per second : " + lambda);
        System.out.println("xi : " + xi);

        double averageNOfClientsConnected;
        double averageResponseTime2;
        if(xi <= 1){
            double pi0 = 0.0;
            for(int i = 0; i < m; i++){     //calcul de pi0
                double temp = Math.pow(a,i)/factorial(i) + Math.pow(a,m)/(factorial(m)*(1-xi));
                pi0 += temp;
            }
            pi0 = Math.pow(pi0,-1);
            averageNOfClientsConnected = a + ((xi*Math.pow(a,m)*pi0)/(Math.pow((1-xi),2)*factorial(m)));    //average number of clients connected and sending requests
            averageResponseTime2 = 1/lambda * averageNOfClientsConnected;   //temps moyen de réponse en considérant l'attente dans la queue

            System.out.println("average number of clients connected per second : " + averageNOfClientsConnected);
            System.out.println("average response time of the server : " + averageResponseTime2);
            System.out.println("average response time for m M|M|1 queues : " + 1/(mu - (lambda/m)));
        }
        else{
            System.out.println("The queue is unstable because xi is greater than 1");
        }
        System.out.println("lambda max : " + m*mu);
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public static long factorial(int n) {
        if(n == 0)
            return 1;
        long fact = 1;
        for (int i = 1;i < n; i++){
            fact = fact*i;
        }
        return fact;
    }

}
