package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmpap on 08/12/2017.
 */

public class Property {

    private Owner owner;
    private String locationAndDescription;
    private int approxSizeInSquareMeters;
    private List<PropertyMarker> markers = new ArrayList<>();
    private int calculatedSize;

    public static List<Property> properties = new ArrayList<>();

    static {
        Property property = new Property(Owner.ownerList.get(0), "Arneiro", 650);
        property.markers.add(new PropertyMarker(40.165101, -8.862450));
        property.markers.add(new PropertyMarker(40.165228, -8.861933));
        property.markers.add(new PropertyMarker(40.165390, -8.862210));
        property.markers.add(new PropertyMarker(40.165203, -8.862398));
        property.markers.add(new PropertyMarker(40.165187, -8.862465));
        properties.add(property);

        Property otherProp = new Property(Owner.ownerList.get(0), "Fonte", 10000);
        otherProp.markers.add(new PropertyMarker(40.164876, -8.862490));
        otherProp.markers.add(new PropertyMarker(40.165093, -8.862482));
        otherProp.markers.add(new PropertyMarker(40.165066, -8.862680));
        otherProp.markers.add(new PropertyMarker(40.164841, -8.862694));
        properties.add(otherProp);

        Property anotherProp = new Property(Owner.ownerList.get(1), "Castelhanas", 2000);
        anotherProp.markers.add(new PropertyMarker(40.165957, -8.865170));
        anotherProp.markers.add(new PropertyMarker(40.166016, -8.864844));
        anotherProp.markers.add(new PropertyMarker(40.166246, -8.864903));
        anotherProp.markers.add(new PropertyMarker(40.166004, -8.865261));
        properties.add(anotherProp);
    }

    public Property(Owner owner, String locationAndDescription, int approxSizeInSquareMeters) {
        this.owner = owner;
        this.locationAndDescription = locationAndDescription;
        this.approxSizeInSquareMeters = approxSizeInSquareMeters;
    }

    public Property(Owner owner, String locationAndDescription) {
        this.owner = owner;
        this.locationAndDescription = locationAndDescription;
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

    @Override
    public String toString() {
        return locationAndDescription +
                "\nde: " + owner +
                "\narea aprox.: " + Integer.toString(approxSizeInSquareMeters);
    }
}
