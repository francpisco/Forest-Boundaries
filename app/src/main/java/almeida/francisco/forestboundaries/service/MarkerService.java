package almeida.francisco.forestboundaries.service;

import android.content.Context;

import java.util.List;

import almeida.francisco.forestboundaries.dbhelper.MarkerDAO;
import almeida.francisco.forestboundaries.dbhelper.OwnerDAO;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.model.Marker;
import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by Francisco Almeida on 20/12/2017.
 */

public class MarkerService {

    private static final String TAG = MarkerService.class.getName();

    private Context context;

    public MarkerService(Context context) {
        this.context = context;
    }

    public long createMarker(Marker marker) {
        MarkerDAO markerDAO = new MarkerDAO(context);
        return markerDAO.createMarker(marker);
    }

    public Marker findById(long id) {
        MarkerDAO markerDAO = new MarkerDAO(context);
        Marker marker = markerDAO.findById(id);

        long propertyId = markerDAO.getPropertyId(id);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        Property property = propertyDAO.findById(propertyId);

        long ownerId = propertyDAO.getOwnerId(propertyId);
        OwnerDAO ownerDAO = new OwnerDAO(context);
        Owner owner = ownerDAO.findById(ownerId);

        property.setOwner(owner);
        marker.setProperty(property);

        return marker;
    }

    public List<Marker> findAll() {
        MarkerDAO markerDAO = new MarkerDAO(context);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        OwnerDAO ownerDAO = new OwnerDAO(context);
        List<Marker> markers = markerDAO.findAll();
        for (Marker m : markers) {
            long propertyId = markerDAO.getPropertyId(m.getId());
            Property property = propertyDAO.findById(propertyId);

            long ownerId = propertyDAO.getOwnerId(propertyId);
            Owner owner = ownerDAO.findById(ownerId);

            property.setOwner(owner);
            m.setProperty(property);
        }
        return markers;
    }
}
