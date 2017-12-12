package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmpap on 08/12/2017.
 */

public class PropertyMarker {
    private double averagedLat; //Average of values so far obtained for precision
    private double averagedLong;
    private List<Double> latitudeOverTime; //list of values collected for the same marker
    private List<Double> longitudeOverTime;

    public PropertyMarker (Double lat, Double lon) {
        latitudeOverTime = new ArrayList<>();
        longitudeOverTime = new ArrayList<>();
        latitudeOverTime.add(lat);
        longitudeOverTime.add(lon);
        averagedLat = calculateAverage(latitudeOverTime);
        averagedLong = calculateAverage(longitudeOverTime);
    }

    private double calculateAverage(List<Double> list) {
        double sum = 0;
        for (Double d : list) {
            sum += d;
        }
        return sum / list.size();
    }

    public void addReading() {

    }

    public double getAveragedLat() {
        return averagedLat;
    }

    public double getAveragedLong() {
        return averagedLong;
    }
}
