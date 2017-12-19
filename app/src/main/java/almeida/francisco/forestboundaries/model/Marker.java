package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmpap on 19/12/2017.
 */

public class Marker {

    private Property property;
    private double avgLatitude;
    private double avgLongitude;
    private List<Reading> readings = new ArrayList<>();

    public Property getProperty() {
        return property;
    }

    public double getAvgLatitude() {
        return avgLatitude;
    }

    public double getAvgLongitude() {
        return avgLongitude;
    }

}
