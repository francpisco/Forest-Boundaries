package almeida.francisco.forestboundaries.service;

import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import almeida.francisco.forestboundaries.dbhelper.MarkerDAO;
import almeida.francisco.forestboundaries.dbhelper.OwnerDAO;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.model.MyMarker;
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

    public long createMarker(MyMarker marker) {
        MarkerDAO markerDAO = new MarkerDAO(context);
        return markerDAO.createMarker(marker);
    }

    public MyMarker findById(long id) {
        MarkerDAO markerDAO = new MarkerDAO(context);
        MyMarker marker = markerDAO.findById(id);

        long propertyId = markerDAO.getPropertyId(id);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        Property property = propertyDAO.findById(propertyId);

        long ownerId = propertyDAO.getOwnerId(propertyId);
        OwnerDAO ownerDAO = new OwnerDAO(context);
        Owner owner = ownerDAO.findById(ownerId);

        if (property != null)
            property.setOwner(owner);
        if (marker != null)
            marker.setProperty(property);

        return marker;
    }

    public List<MyMarker> findAll() {
        MarkerDAO markerDAO = new MarkerDAO(context);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        OwnerDAO ownerDAO = new OwnerDAO(context);
        List<MyMarker> markers = markerDAO.findAll();
        for (MyMarker m : markers) {
            long propertyId = markerDAO.getPropertyId(m.getId());
            Property property = propertyDAO.findById(propertyId);

            long ownerId = propertyDAO.getOwnerId(propertyId);
            Owner owner = ownerDAO.findById(ownerId);

            if (property != null)
                property.setOwner(owner);
            m.setProperty(property);
        }
        return markers;
    }

    public List<MyMarker> findListByPropertyId(long propId) {
        MarkerDAO markerDAO = new MarkerDAO(context);
        List<MyMarker> markers = markerDAO.findByPropertyId(propId);
        Collections.sort(markers);
        return markers;
    }

    public boolean deleteById(long id) {
        MarkerDAO markerDAO = new MarkerDAO(context);
        return markerDAO.deleteById(id);
    }

    public boolean updateIndex(MyMarker marker) {
        MarkerDAO markerDAO = new MarkerDAO(context);
        return markerDAO.updateIndex(marker);
    }
}
