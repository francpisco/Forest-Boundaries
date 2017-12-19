package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmpap on 08/12/2017.
 */

public class Property {

    private long id;
    private Owner owner;
    private String locationAndDescription;
    private int approxSizeInSquareMeters;
    private List<PropertyMarker> markers = new ArrayList<>();
    private int calculatedSize;

    public static List<Property> properties = new ArrayList<>();

    static {
        Property property = new Property()
                .setOwner(Owner.ownerList.get(0))
                .setLocationAndDescription("Arneiro")
                .setApproxSizeInSquareMeters(650);
        property.markers.add(new PropertyMarker(40.165101, -8.862450));
        property.markers.add(new PropertyMarker(40.165228, -8.861933));
        property.markers.add(new PropertyMarker(40.165390, -8.862210));
        property.markers.add(new PropertyMarker(40.165203, -8.862398));
        property.markers.add(new PropertyMarker(40.165187, -8.862465));
        properties.add(property);

        Property otherProp = new Property()
                .setOwner(Owner.ownerList.get(0))
                .setLocationAndDescription("Fonte")
                .setApproxSizeInSquareMeters(10000);
        otherProp.markers.add(new PropertyMarker(40.164876, -8.862490));
        otherProp.markers.add(new PropertyMarker(40.165093, -8.862482));
        otherProp.markers.add(new PropertyMarker(40.165066, -8.862680));
        otherProp.markers.add(new PropertyMarker(40.164841, -8.862694));
        properties.add(otherProp);

        Property anotherProp = new Property()
                .setOwner(Owner.ownerList.get(1))
                .setLocationAndDescription("Castelhanas")
                .setApproxSizeInSquareMeters(2000);
        anotherProp.markers.add(new PropertyMarker(40.165957, -8.865170));
        anotherProp.markers.add(new PropertyMarker(40.166016, -8.864844));
        anotherProp.markers.add(new PropertyMarker(40.166246, -8.864903));
        anotherProp.markers.add(new PropertyMarker(40.166004, -8.865261));
        properties.add(anotherProp);
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

    public List<PropertyMarker> getMarkers() {
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

    public Property setMarkers(List<PropertyMarker> markers) {
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
