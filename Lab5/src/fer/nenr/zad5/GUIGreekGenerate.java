package fer.nenr.zad5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GUIGreekGenerate extends JFrame {

    private List<Point2D.Double> currentPoints;
    private List<List<Point2D.Double>> samples;
    private final String path;

    private final int M;
    private final int numberOfGestures;

    private int counterOfGestures = 0;
    private int counterOfLetters = 0;


    public GUIGreekGenerate(int M, int numberOfGestures, String path) {
        this.M = M;
        this.numberOfGestures = numberOfGestures;
        this.path = path;
        currentPoints = new ArrayList<>();
        samples = new ArrayList<>();

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

                samples.add(chosenPoints);
                System.out.println(++counterOfGestures);
                StringBuilder stringBuilder = new StringBuilder();
                int letter = 0, gesture = 0;

                if(counterOfGestures == numberOfGestures){
                    counterOfLetters++;

                    if (counterOfLetters != 5) {
                        counterOfGestures = 0;
                        System.out.println("Ucrtaj sljedece slovo!!!");
                    } else {
                        for(List<Point2D.Double> sample : samples){
                            for(Point2D.Double point : sample){
                                stringBuilder.append(point.getX()).append(" ").append(point.getY()).append(" ");
                            }
                            for(int i = 0; i < 5; i++){
                                if (i != letter) {
                                    stringBuilder.append("0.0 ");
                                } else {
                                    stringBuilder.append("1.0 ");
                                }
                            }
                            stringBuilder.setLength(stringBuilder.length() - 1);
                            stringBuilder.append("\n");

                            gesture++;
                            if(gesture == numberOfGestures){
                                letter++;
                                gesture = 0;
                            }
                        }
                        stringBuilder.setLength(stringBuilder.length() - 1);

                        try {
                            Files.writeString(Paths.get(path), stringBuilder.toString());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.exit(0);
                    }
                }

                currentPoints = new ArrayList<>();
                panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
            }
        });
        getContentPane().add(panel);
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

    public static void generate(int M, int numberOfGestures, String path){
        SwingUtilities.invokeLater(() -> new GUIGreekGenerate(M, numberOfGestures, path).setVisible(true));

    }

}