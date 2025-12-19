import java.util.Map;
import java.util.*;

public class TaxiService {

    MyGraph<String, Integer> G;
    HashMap<String, Taxi> taxiMap;

    public TaxiService() {
        G = new MyGraph<>();
        taxiMap = new HashMap<>();
    }

    public void performAction(String actionMessage) {
        System.out.println("action to be performed: " + actionMessage);

        String[] action = actionMessage.split(" ");

        if (action[0].equals("edge")) {
            String src = action[1];


            if (!G.containsVertex(src)) {
                G.insertVertex(src);
            }

            String dst = action[2];
            if (!G.containsVertex(dst)) {
                G.insertVertex(dst);
            }

            Vertex<String> u = G.getVertex(src);
            Vertex<String> v = G.getVertex(dst);

            Integer wt = Integer.parseInt(action[3]);
            G.insertEdge(u, v, wt);
        } else if (action[0].equals("taxi")) {
            Taxi newTaxi = new Taxi(action[1], action[2]);
            taxiMap.put(action[1], newTaxi);

        } else if (action[0].equals("customer")) {
            Vertex<String> src = G.getVertex(action[1]);
            Vertex<String> dest = G.getVertex(action[2]);
            int t = Integer.parseInt(action[3]);

            Map<Vertex<String>, Integer> d = new HashMap<>();
            Map<Vertex<String>, Vertex<String>> parent = G.dijkstra(src, d);
//            System.out.println("-----------------------------------");
//            System.out.println("ran dijkstra on " + src);
//            System.out.println("output of dijkstra:\n parent : " + parent);
//            System.out.println("d : " + d);
//            System.out.println("----------------------------------");


            int nearestTaxiDist = Integer.MAX_VALUE;
            Taxi nearestTaxi = null;

            System.out.println("Available taxis:");
            for (Taxi taxi : taxiMap.values()) {
                Vertex<String> taxiLoc = G.getVertex(taxi.getLocation());
                if (taxi.isAvailable() && parent.containsKey(taxiLoc)) {

                    System.out.print("Path of " + taxi + ": ");

                    List<String> path = this.pathOfTaxi(parent, taxiLoc);
                    System.out.print(String.join(", ", path));
                    System.out.println(". time taken is " + d.get(taxiLoc) + " units");
                    if (d.get(taxiLoc) < nearestTaxiDist) {
                        nearestTaxiDist = d.get(taxiLoc);
                        nearestTaxi = taxi;
                    }
                }
            }
            if (parent.containsKey(dest)) {

                System.out.println("**Choosing " + nearestTaxi + " to serve the customer request**");

                List<String> customerPath = this.pathOfTaxi(parent, dest);
                Collections.reverse(customerPath);
                System.out.print("Path of customer: " + String.join(", ", customerPath));
                System.out.println(". time taken is " + d.get(dest) + " units");
            } else {
                System.out.println("Not Reachable! No path exists from " + src + " to " + dest);
            }


        } else if (action[0].equals("printTaxiPosition")) {
            for (Taxi t : this.taxiMap.values()) {
                if (t.isAvailable()) {
                    System.out.println(t + ": " + t.getLocation());
                }
            }

        } else {
            throw new IllegalArgumentException("Error! This action cannot be performed");
        }
        //System.out.println("-------printing the graph:\n" + G);
    }

    public String shortestPathLengths(String s) {
        return G.dijkstra(G.getVertex(s), new HashMap<Vertex<String>, Integer>()).toString();
    }

    private List<String> pathOfTaxi(Map<Vertex<String>, Vertex<String>> parent, Vertex<String> taxiLoc) {
        List<String> path = new LinkedList<>();

        Vertex<String> x = taxiLoc;

        while (x != null) {
            path.add(x.getElement());
            x = parent.get(x);
        }

        return path;
    }
}
