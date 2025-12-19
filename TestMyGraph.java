import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestMyGraph {

    public static void main(String[] args) {
        MyGraph<Integer, Integer> G = new MyGraph<>(false);

        Scanner sc = null;

        try {
            sc = new Scanner(new FileReader("test/graph/tinyG.txt"));

            int V = sc.nextInt();

            for (int j = 0; j < V; j++) {
                G.insertVertex(j);
            }

            int E = sc.nextInt();

            for (int i = 0; i < E; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                G.insertEdge(G.getVertex(u), G.getVertex(v), 0);
            }

            System.out.println("---------printing the graph---------");
            System.out.println(G);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

    }
}
