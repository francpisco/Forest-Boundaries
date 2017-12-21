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
        PropertyDAO propertyDAO = new PropertyDAO(context);
        OwnerDAO ownerDAO = new OwnerDAO(context);




        return null;
    }

    public List<Marker> findAll() {
        MarkerDAO markerDAO = new MarkerDAO(context);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        OwnerDAO ownerDAO = new OwnerDAO(context);
        List<Marker> markers = markerDAO.findAll();
        for (Marker m : markers) {
            if (m.getProperty() != null)
                if (m.getProperty().getOwner() != null)
                    continue;

            long propId = markerDAO.getPropertyId(m.getId());
            Property property = ServiceObjects.PROPERTIES.get(propId);
            if (property == null) {
                property = propertyDAO.findById(propId);
                long ownerId = propertyDAO.getOwnerId(propId);
                Owner owner = ServiceObjects.OWNERS.get(ownerId);
                if (owner == null) {
                    owner = ownerDAO.findById(ownerId);
                    ServiceObjects.OWNERS.put(ownerId, owner);
                }
                property.setOwner(owner);
                ServiceObjects.PROPERTIES.put(propId, property);
            }
            m.setProperty(property);
        }
        return markers;
    }
}
