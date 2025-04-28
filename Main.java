import java.util.concurrent.CompletableFuture;
import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;

class Main {
    static CompletableFuture<BusRoutes> processQuery(String query) {
        Scanner sc = new Scanner(query);
        BusStop srcBusStop = new BusStop(sc.next());
        String searchString = sc.next();
        sc.close();
        return BusSg.findBusServicesBetween(srcBusStop, searchString);
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n").tokens()
            .map(s -> processQuery(s))
            .toList()
            .stream()
            .forEach(routes -> routes
                .thenCompose(r -> r.description())
                .thenAccept(x -> System.out.println(x))
                .join());
        sc.close();
        Instant stop = Instant.now();
        System.out.printf("Took %, dms\n", Duration
            .between(start, stop).toMillis()); 
    }
}