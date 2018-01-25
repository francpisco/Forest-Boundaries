package almeida.francisco.forestboundaries.model;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private int yearOfPlantation;
    private int yearOfLastCut;
    private int yearAndMonthOfLastCleaning;
    private static List<Integer> years;

    static {
        years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = currentYear; y >= 1900; y--) {
            years.add(y);
        }
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

    public int getCalculatedSize() {
        return calculatedSize;
    }

    public String getNote() {
        return note;
    }

    public String getLandUse() {
        return landUse;
    }

    public int getYearOfPlantation() {
        return yearOfPlantation;
    }

    public int getYearOfLastCut() {
        return yearOfLastCut;
    }

    public int getYearAndMonthOfLastCleaning() {
        return yearAndMonthOfLastCleaning;
    }

    public static List<Integer> getYears() {
        return years;
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

    public Property setId(long id) {
        this.id = id;
        return this;
    }

    public Property setNote(String note) {
        this.note = note;
        return this;
    }

    public Property setLandUse(String landUse) {
        this.landUse = landUse;
        return this;
    }

    public Property setYearOfPlantation(int yearOfPlantation) {
        this.yearOfPlantation = yearOfPlantation;
        return this;
    }

    public Property setYearOfLastCut(int yearOfLastCut) {
        this.yearOfLastCut = yearOfLastCut;
        return this;
    }

    public Property setYearAndMonthOfLastCleaning(int yearAndMonthOfLastCleaning) {
        this.yearAndMonthOfLastCleaning = yearAndMonthOfLastCleaning;
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
