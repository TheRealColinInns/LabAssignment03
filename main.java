import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//gotta import a whole lotta stuff
public class main extends JPanel implements ActionListener {
    //gravity value
    double G = 6.67408e-11;
    //max values for x and y
    int maxX = 700;
    int maxY = 700;
    double scale;
    //the list of celestial bodies
    List<CelestialBody> celBodLis = null;
    Timer timer1 = new Timer(1,this);
    public void paintComponent(Graphics grpc) {
        //pull a celestail body from the list
        //begin print
        super.paintComponent(grpc);
        for(int i = 0; i < celBodLis.size(); i++) {
            //look ma im painting!
            CelestialBody body = celBodLis.get(i);
            grpc.fillOval((int)body.x, (int)body.y, body.radius*2, body.radius*2);
            grpc.drawString(Integer.toString(i), (int)(body.x + body.radius*2), (int)(body.y + body.radius*2));
            grpc.drawString(String.format("%.7f", (body.xVelocity)) + " " + String.format("%.7f", (body.yVelocity)), (int)(body.x + body.radius*2), (int)(body.y + body.radius*2 + 10));
            grpc.drawString(String.format("%.3f", (body.x)) + " " + String.format("%.3f", (body.y)), (int)(body.x + body.radius*2), (int)(body.y + body.radius*2 + 20));
        }
        timer1.start();
    }

    public double distance(double x, double y) {
        //ez distance formula :)
        return Math.sqrt(x*x + y*y);
    }

    public double gravityCalc(double mass1, double mass2, double dist) {
        //a little less ez gavitational formula
        return (G * mass1 * mass2)/(dist*dist);
    }

    public void addForce(CelestialBody body, CelestialBody body2) {
        //calculate distance
        //find the force, add it to the list
        double xloc = (body2.x - body.x) * scale;
        double yloc = (body2.y - body.y) * scale;
        double dist = distance(xloc, yloc);
        double force = gravityCalc(body.mass, body2.mass, dist);
        double xForce = force * xloc / dist;
        double yForce = force * yloc / dist;

        body.xfc.add(xForce);
        body.yfc.add(yForce);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //looping through all the bodies, avoiding current
        for (int i = 0; i < celBodLis.size(); i++) {
            CelestialBody body = celBodLis.get(i);
            double xNetForce = 0.0;
            double yNetForce = 0.0;
            for (int j = 0; j < celBodLis.size(); j++) {
                if (i != j) {
                    CelestialBody body2 = celBodLis.get(j);
                    addForce(body, body2);
                }
            }
            //Total net force exerted by the external bodies
            for (int k = 0; k < body.xfc.size(); k++) {
                xNetForce += body.xfc.get(k);
                yNetForce += body.yfc.get(k);
            }

            body.xfc = new ArrayList<Double>(celBodLis.size()-1);
            body.yfc = new ArrayList<Double>(celBodLis.size()-1);
            body.xVelocity += (xNetForce/body.mass);
            body.yVelocity += (yNetForce/body.mass);
            body.x += (body.xVelocity);
            body.y += (body.yVelocity);

            //delete it if its out of bounds
            if (body.x + body.radius < 0 || body.x - body.radius > maxX) {
                try {
                    celBodLis.remove(i);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if (body.y + body.radius < 0 || body.y - body.radius > maxY) {
                try {
                    celBodLis.remove(i);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        repaint();
    }

    public static class CelestialBody {
        String name;
        double mass;
        double x;
        double y;
        double xVelocity;
        double yVelocity;
        int radius;
        List<Double> xfc;
        List<Double> yfc;

        public CelestialBody(String namei, double massi, int xi, int yi, double xVelocityi, double yVelocityi, int radiusi) {
            name = namei;
            mass = massi;
            x = xi;
            y = yi;
            xVelocity = xVelocityi;
            yVelocity = yVelocityi;
            radius = radiusi;
            xfc = new ArrayList<>(10);
            yfc = new ArrayList<>(10);
        }

        @Override
        public String toString() {
            return "CelestialBody " + name + ", mass=" + mass + ", radius=" + radius + ", x=" + x + ", y=" + y + ", xVelocity=" + xVelocity + ", yVelocity=" + yVelocity + "\n";
        }
    }


    public static void main(String args[]) throws FileNotFoundException
     {
        main tester = new main();
        try {
            String fileName = "nbody_input.txt";
            //make sure a file is there
            if (args.length==0) {
                System.out.println("You must specify a file name");
            } else {
                fileName=args[0];
            }
            //create scanner for the file
            Scanner scanner = new Scanner(new File(fileName));
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.next();
                //the first line determines what kind of list we are going to be using
                if (line.equals("ArrayList")) {
                    //we are going to use an array list
                    tester.celBodLis = new ArrayList<CelestialBody>(4);
                }
                else if (line.equals("LinkedList")) {
                    //we are going to use a linked list
                    tester.celBodLis = new LinkedList<CelestialBody>();
                }
                else if (lineNum == 1) {
                    tester.scale = Double.parseDouble(line);
                }
                else {
                    String[] bData = line.split(",");
                    String name = bData[0];
                    double mass = Double.parseDouble(bData[1]);
                    int x = Integer.parseInt(bData[2]);
                    int y = Integer.parseInt(bData[3]);
                    double xVelocity = Double.parseDouble(bData[4]);
                    double yVelocity = Double.parseDouble(bData[5]);
                    int radius = Integer.parseInt(bData[6]);
                    tester.celBodLis.add(new CelestialBody(name, mass, x, y, xVelocity, yVelocity, radius));
                }
                lineNum++;
            }
            System.out.println("Body Count: " + tester.celBodLis.size());
            System.out.println("Scale: " + tester.scale);
            System.out.println(tester.celBodLis.toString());
        }

        catch (FileNotFoundException e){
            throw e;
        }

        JFrame jf = new JFrame();
        jf.setTitle("N-Body");
        jf.setSize(tester.maxX, tester.maxY);
        jf.add(tester);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
