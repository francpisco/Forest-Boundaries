package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
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
    private int calculatedSize;

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
}
