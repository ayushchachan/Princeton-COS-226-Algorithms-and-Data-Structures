import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


/**
 * @author Ayush Chachan
 */
public class UnionFindTester {
    public static void main(String[] args) {
        Scanner sc = null;
        String path = "/home/ayushchachan/Documents/myproject/percolation/src/";

        String filename = path + "largeUF.txt";
        try {
            sc = new Scanner(new FileReader(filename));
            int N = Integer.parseInt(sc.nextLine());
            WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);

            while (sc.hasNext()) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                uf.union(u, v);

            }
            System.out.println("number of connected components = " + uf.count());

        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        finally {
            if (sc != null) sc.close();
        }


    }
}
