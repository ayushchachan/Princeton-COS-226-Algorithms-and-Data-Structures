/* *****************************************************************************
 *  Name:    Ayush Chachan
 *
 *
 * */

public class NBody {

    public static final double G = 6.67e-11;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: java-introcs NBody <T> <dt> < planets.txt");
            return;
        }

        double tau = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        System.out.println("t = " + tau);
        System.out.println("dt = " + dt);

        int n = StdIn.readInt();
        double radius = StdIn.readDouble();

        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];

        double[] mass = new double[n];
        String[] image = new String[n];


        System.out.println(n);
        System.out.printf("%.2e\n", radius);

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();


            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }


        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();



        // StdAudio.play("2001.wav");

        double[] newPx = new double[n];
        double[] newPy = new double[n];
        double[] newVx = new double[n];
        double[] newVy = new double[n];



        for (int step = 0; step * dt < tau; step++) {


            double[] fx = new double[n];
            double[] fy = new double[n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j ) {
                        continue;
                    }

                    double deltaX = px[j] - px[i];
                    double deltaY = py[j] - py[i];

                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                    double m1 = mass[i], m2 = mass[j];

                    double F = (G * m1 * m2) / (distance * distance);

                    double costheta = deltaX / distance;
                    double sintheta = deltaY / distance;

                    fx[i] += F * costheta;
                    fy[i] += F * sintheta;


                }
            }



            for (int i = 0; i < n; i++) {
                double ax_i = fx[i] / mass[i];
                double ay_i = fy[i] / mass[i];

                // Since ax = ay = 0 for now
                newVx[i] = vx[i] + ax_i * dt;
                newVy[i] = vy[i]+ ay_i * dt;

                newPx[i] = px[i] + (newVx[i] * dt);
                newPy[i] = py[i] + (newVy[i] * dt);

            }

            for (int i = 0; i < n; i++) {
                vx[i] = newVx[i];
                vy[i] = newVy[i];
                px[i] = newPx[i];
                py[i] = newPy[i];
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);
            }

            StdDraw.show();

            double t = step * dt;
            // System.out.println("t = " + t);

            StdDraw.pause(20);


        }


    }
}
