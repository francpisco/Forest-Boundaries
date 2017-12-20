package almeida.francisco.forestboundaries.service;

import android.content.Context;

import java.util.List;

import almeida.francisco.forestboundaries.dbhelper.MarkerDAO;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.model.Marker;
import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by Francisco Almeida on 20/12/2017.
 */

public class MarkerService {

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
        if (marker.getProperty() != null)
            return marker;
        long propId = markerDAO.getMarkerPropertyId(id);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        Property property = propertyDAO.findById(propId);
        marker.setProperty(property);
        return marker;
    }

    public List<Marker> findAll() {
        MarkerDAO markerDAO = new MarkerDAO(context);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        List<Marker> markers = markerDAO.findAll();
        for (Marker m : markers) {
            if (m.getProperty() != null) {
                continue;
            }
            long propId = markerDAO.getMarkerPropertyId(m.getId());
            Property property = propertyDAO.findById(propId);
            m.setProperty(property);
        }
        return markers;
    }
}
