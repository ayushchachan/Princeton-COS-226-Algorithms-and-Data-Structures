import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Ayush Chachan
 */
public class CopyBytes {


    public static void main(String[] args) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream("test_case.txt");
            output = new FileOutputStream("byte_output.txt");
            int j = 0;
            int i;
            while ((i = input.read()) != -1) {
                System.out.println("i = " + i);
                output.write(i);
                System.out.println("j = " + ++j);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }

                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
