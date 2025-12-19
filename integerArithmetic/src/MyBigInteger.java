import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author Ayush Chachan
 */
public class MyBigInteger implements MyInteger {
    private static final String ZERO = "0";
    String data;

    public MyBigInteger(String s) {
        this.data = s;
    }

    public String toString() {
        return this.data;
    }

    public String karatsubaMultiply(String x, String y) {

        if (x.length() <= 2 && y.length() <= 2) {
            return Integer.parseInt(x) * Integer.parseInt(y) + "";
        }

        // now we have x, y < 10^n
        int n1 = x.length();
        int n2 = y.length();

        String x1 = x.substring(0, n1 / 2);     // first half of x
        String x0 = x.substring(n1 / 2);        // next half of x

        String y1 = y.substring(0, n2 / 2);     // first half of y
        String y0 = y.substring(n2 / 2);        // next half of y

        String z1 = karatsubaMultiply(x1, y1);  // x1 * y1
        String z0 = karatsubaMultiply(x0, y0);  // x0 * y0

        String x1_plus_x0 = this.integerAddition(x1, x0);   // x1 + x0
        String y1_plus_y0 = this.integerAddition(y1, y0);   // y1 + y0

        String k = this.karatsubaMultiply(x1_plus_x0, y1_plus_y0);      // (x1 + x0)*(y1 + y0)

        String z2 = this.integerSubtraction(k, integerAddition(z1, z0));    // (x1 + x0)*(y1 + y0) - x1y1 - x0y0

        z1 = z1 + ZERO.repeat((n1 + n2) / 2);
        z2 =  z2 + ZERO.repeat((n1 + n2) / 4);
        return this.integerAddition(this.integerAddition(z1, z0), z2);

    }

    /**
     * @param x first integer
     * @param y second integer
     * @return x + y
     */
    public String integerAddition(String x, String y) {
        int n = Math.max(x.length(), y.length());

        char[] result = new char[n + 1];

        int i = x.length() - 1;     // char index for String x
        int j = y.length() - 1;     // char index for String y
        int k = n;                  // index for result

        int carry = 0;
        int xi_plus_yj = 0;
        char xi, yj;

        while (i >= 0 || j >= 0) {

            if (i >= 0) {
                xi = x.charAt(i);
            } else {
                xi = '0';
            }

            if (j >= 0) {
                yj = y.charAt(j);
            } else {
                yj = '0';
            }

            xi_plus_yj = Character.getNumericValue(xi) + Character.getNumericValue(yj) + carry;

            if (xi_plus_yj >= 10) {
                carry = 1;
            } else {
                carry = 0;
            }

            result[k] = (char) ((xi_plus_yj % 10) + '0');
//            System.out.println("result[k] = " + result[k]);

            i--;
            j--;
            k--;
        }
        result[k] = (char) (carry + '0');

        if (result[0] == '0') {
//            System.out.println("0 is there");
            return new String(Arrays.copyOfRange(result, 1, n + 1));
        }

//        System.out.println("0 is not there");
        return new String(result);
    }

    /**
     * @param x first integer
     * @param y second integer
     * @return x - y
     */
    public String integerSubtraction(String x, String y) {
        BigInteger a = new BigInteger(x);
        BigInteger b = new BigInteger(y);
        return a.subtract(b).toString();
    }

    @Override
    public MyInteger multiply(MyInteger other) {
        String x = this.toString();
        String y = other.toString();

        return new MyBigInteger(this.karatsubaMultiply(x, y));
    }

    @Override
    public MyInteger add(MyInteger other) {
        String x = this.toString();
        String y = other.toString();

        return new MyBigInteger(this.integerAddition(x, y));
    }

}
