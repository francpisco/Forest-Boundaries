package almeida.francisco.forestboundaries.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.dbhelper.MarkerDAO;
import almeida.francisco.forestboundaries.dbhelper.OwnerDAO;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.dbhelper.ReadingDAO;
import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.model.Reading;

/**
 * Created by Francisco Almeida on 20/12/2017.
 */

public class PropertyService {

    private static final String TAG = PropertyService.class.getName();

    private Context context;

    public PropertyService(Context context) {
        this.context = context;
    }

    public long createProperty(Property property){
        PropertyDAO propertyDAO = new PropertyDAO(context);
        return propertyDAO.createProperty(property);
    }

    public Property findById(long id) {
        PropertyDAO propertyDAO = new PropertyDAO(context);
        MarkerDAO markerDAO = new MarkerDAO(context);
        ReadingDAO readingDAO = new ReadingDAO(context);
        Property property = propertyDAO.findById(id);
        List<MyMarker> markers = markerDAO.findByPropertyId(id);
        property.setMarkers(markers);
        List<Reading> readings = readingDAO.findByPropertyId(id);
        property.setReadings(readings);
        if (property.getOwner() != null)
            return property;
        long ownerId = propertyDAO.getOwnerId(id);
        OwnerDAO ownerDAO = new OwnerDAO(context);
        Owner owner = ownerDAO.findById(ownerId);
        property.setOwner(owner);
        return property;
    }

    public List<Property> findAll() {
        PropertyDAO propertyDAO = new PropertyDAO(context);
        OwnerDAO ownerDAO = new OwnerDAO(context);
        MarkerDAO markerDAO = new MarkerDAO(context);
        ReadingDAO readingDAO = new ReadingDAO(context);
        List<Property> properties = propertyDAO.findAll();
        for (Property p : properties) {
            List<MyMarker> markers = markerDAO.findByPropertyId(p.getId());
            p.setMarkers(markers);
            List<Reading> readings = readingDAO.findByPropertyId(p.getId());
            p.setReadings(readings);
            if (p.getOwner() != null)
                continue;
            long ownerId = propertyDAO.getOwnerId(p.getId());
            Owner owner = ownerDAO.findById(ownerId);
            p.setOwner(owner);
        }
        return properties;
    }

    public void loadProperties() {
        OwnerDAO ownerDAO = new OwnerDAO(context);
        List<Owner> owners = ownerDAO.findAll();
        Property property1 = new Property()
                .setOwner(owners.get(0))
                .setLocationAndDescription("Arneiro")
                .setApproxSizeInSquareMeters(650);
        createProperty(property1);
        Property property2 = new Property()
                .setOwner(owners.get(0))
                .setLocationAndDescription("Fonte")
                .setApproxSizeInSquareMeters(10000);
        createProperty(property2);
        Property property3 = new Property()
                .setOwner(owners.get(1))
                .setLocationAndDescription("Castelhanas")
                .setApproxSizeInSquareMeters(2000);
        createProperty(property3);
    }
}
