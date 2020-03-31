
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {

    // Range of random values to be inserted and removed from BST
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 300;

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {

        // An example Metric defined
        Counter numberOfIterations = Counter.build()
                .namespace("java")
                .name("number_of_iterations")
                .help("Counts the number of attempted inserts and removes")
                .register();
        Counter failedAdds = Counter.build()
                .namespace("java")
                .name("number_of_failed_adds")
                .help("Counts the number of failed adds")
                .register();
        Counter failedRemoves = Counter.build()
                .namespace("java")
                .name("number_of_failed_removes")
                .help("Counts the number of failed removes")
                .register();
        Gauge numberOfNodes = Gauge.build()
                .namespace("java")
                .name("number_of_nodes")
                .help("Counts Num Nodes")
                .register();
        Summary insertionTimer = Summary.build()
                .namespace("java")
                .name("time_to_add")
                .help("returns add time")
                .register();


        BST<Integer> tree = new BST<>();

        // Define the thread that contains all the processes that the server runs through
        Thread bgThread = new Thread(() -> {
            while (true) {
                try {
                    // this increments the numberOfIterations by one
                    numberOfIterations.inc();

                    // Two random values to insert or remove from the BST are selected
                    int randomAdd = randomNumber();
                    int randomRemove = randomNumber();

                    /*
                     The server attempts to add the random value.
                     Returns true if the server added a node in the BST.
                     Returns false if a node was not added.
                     */
                    Summary.Timer timer;

                    timer= insertionTimer.startTimer();
                    if(tree.add(randomAdd)){
                        numberOfNodes.inc();
                    }else{
                        failedAdds.inc();
                    }
                    timer.observeDuration();



                    /*
                    The server attempts to remove the random given value
                    IF IT FAILS, it will throw a FailedRemoveException
                    IF IT SUCCEEDS it won't throw any exception
                    */
                    try{
                        tree.remove(randomRemove);
                        numberOfNodes.dec();
                    }catch (FailedRemoveException e){
                        failedRemoves.inc();

                    }


                    // The server waits for 1000 milliseconds
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        bgThread.start();

        // start the server on the specified port
        try {
            HTTPServer server = new HTTPServer(SERVER_PORT);
            System.out.println("Prometheus exporter running on port " + SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Prometheus exporter was unable to start");
            e.printStackTrace();
        }
    }

    /**
     * Function that selects a random number.
     * @return the randomly selected integer between MIN_VALUE and MAX_VALUE
     */
    private static int randomNumber() {
        return (int)(Math.random() * ((MAX_VALUE - MIN_VALUE) + 1)) + MIN_VALUE;
    }

}