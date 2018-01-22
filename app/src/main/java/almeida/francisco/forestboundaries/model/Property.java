package almeida.francisco.forestboundaries.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Francisco Almeida on 08/12/2017.
 */

public class Property {
    
    private long id;
    private Owner owner;
    private String locationAndDescription;
    private int approxSizeInSquareMeters;
    private List<MyMarker> markers = new ArrayList<>();
    private List<Reading> readings = new ArrayList<>();

    private int calculatedSize;

    private String note;
    private String landUse;
    private GregorianCalendar dateOfLastCut;
    private GregorianCalendar dateOfLastCleaning;
    private static List<String> landUseList;

    static {
        landUseList = new ArrayList<>();
        landUseList.add("Eucaliptal");
        landUseList.add("Pinhal");
        landUseList.add("Florestal-outro");
        landUseList.add("Agrícola");
        landUseList.add("Outro");
    }

    public List<LatLng> fromMarkersToLatLng() {
        List<LatLng> points = new ArrayList<>();
        for (MyMarker m : markers) {
            points.add(new LatLng(m.getMarkedLatitude(), m.getMarkedLongitude()));
        }
        return points;
    }

    public List<LatLng> fromReadingsToLatLng() {
        List<LatLng> points = new ArrayList<>();
        for (Reading r : readings) {
            points.add(new LatLng(r.getLatitude(), r.getLongitude()));
        }
        return points;
    }

    public long getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getLocationAndDescription() {
        return locationAndDescription;
    }

    public int getApproxSizeInSquareMeters() {
        return approxSizeInSquareMeters;
    }

    public List<MyMarker> getMarkers() {
        return markers;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public Property setOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public Property setLocationAndDescription(String locationAndDescription) {
        this.locationAndDescription = locationAndDescription;
        return this;
    }

    public Property setApproxSizeInSquareMeters(int approxSizeInSquareMeters) {
        this.approxSizeInSquareMeters = approxSizeInSquareMeters;
        return this;
    }

    public Property setMarkers(List<MyMarker> markers) {
        this.markers = markers;
        return this;
    }

    public Property setReadings(List<Reading> readings) {
        this.readings = readings;
        return this;
    }

    public void setCalculatedSize(int calculatedSize) {
        this.calculatedSize = calculatedSize;
    }

    public Property setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return locationAndDescription +
                "\narea aprox.: " + Integer.toString(approxSizeInSquareMeters);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Property) {
            if (((Property) o).getLocationAndDescription().equals(locationAndDescription)
                    && ((Property) o).getOwner().getName().equals(owner.getName())
                    && ((Property) o).getApproxSizeInSquareMeters() == approxSizeInSquareMeters
                    && ((Property) o).getId() == id) {
                return true;
            }
            return false;
        }
        return false;
    }
}
