package fer.nenr.zad5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGreekTest extends JFrame {

    public ANN ann;

    private List<Point2D.Double> currentPoints;

    private static final int M = 10;

    public GUIGreekTest(ANN ann) {
        this.ann = ann;
        currentPoints = new ArrayList<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(400, 50);
        setSize(700, 700);
        setTitle("NENR 5");

        guiInit();

    }

    void guiInit(){
        JPanel panel = new JPanel();

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Graphics2D g2d = (Graphics2D) panel.getGraphics();
                g2d.setColor(new Color(255, 0, 0));
                g2d.setStroke(new BasicStroke(2.5F));
                g2d.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
                currentPoints.add(new Point2D.Double(e.getX(), e.getY()));
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                double distanceTotal = 0;
                List<Point2D.Double> normalized = normalize(currentPoints);

                for(int i = 1; i < normalized.size(); i++){
                    distanceTotal += normalized.get(i).distance(normalized.get(i - 1));
                }

                List<Point2D.Double> chosenPoints = new ArrayList<>();
                double distance;
                int c;

                for(int k = 0; k < M; k++){
                    c = 0;
                    Point2D.Double t = normalized.get(c);

                    distance = k * distanceTotal / (M - 1);
                    while(distance > 0){
                        distance -= t.distance(normalized.get(c + 1));
                        t = normalized.get(c + 1);
                        c++;
                        if(c == normalized.size() - 1) break;
                    }
                    chosenPoints.add(t);
                }

//                    System.out.println("Za klasifikaciju!");
                double[] input = new double[2 * M];
                int i = 0;
                for(Point2D.Double point : chosenPoints){
                    input[i++] = point.getX();
                    input[i++] = point.getY();
                }

                double[] result = ann.forwardPass(input);
                printLetter(result);

                currentPoints = new ArrayList<>();
                panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
            }
        });
        getContentPane().add(panel);
    }

    public void printLetter(double[] output){
        double max = Arrays.stream(output).max().getAsDouble();
        String[] letters = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon"};
        for(int i = 0; i < 5; i++){
            if(output[i] == max){
                System.out.println(letters[i]);
                break;
            }
        }
    }

    private List<Point2D.Double> normalize(List<Point2D.Double> points) {
        double avgX = 0, avgY = 0, maxX = 0, maxY = 0, max;
        int i = 0;
        for(Point2D.Double point : points){
            avgX += point.getX();
            avgY += point.getY();
        }
        avgX /= points.size(); avgY /= points.size();

        List<Point2D.Double> temp = new ArrayList<>();
        for(Point2D.Double point : points){
            Point2D.Double newPoint = new Point2D.Double(point.getX() - avgX, point.getY() - avgY);
            if (i != 0) {
                if(Math.abs(newPoint.getX()) > maxX){
                    maxX = Math.abs(newPoint.getX());
                }
                if(Math.abs(newPoint.getY()) > maxY){
                    maxY = Math.abs(newPoint.getY());
                }
            } else {
                maxX = Math.abs(newPoint.getX());
                maxY = Math.abs(newPoint.getY());
                i++;
            }
            temp.add(newPoint);
        }
        max = Double.max(maxX, maxY);
        List<Point2D.Double> normalized = new ArrayList<>();
        for(Point2D.Double point : temp){
            normalized.add(new Point2D.Double(point.getX() / max, point.getY() / max));
        }

        return normalized;
    }

    public static void start(ANN ann){
        SwingUtilities.invokeLater(() -> new GUIGreekTest(ann).setVisible(true));

    }

}
