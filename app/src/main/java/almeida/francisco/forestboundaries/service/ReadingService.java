package almeida.francisco.forestboundaries.service;

import android.content.Context;


import java.util.List;

import almeida.francisco.forestboundaries.dbhelper.MarkerDAO;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.dbhelper.ReadingDAO;
import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.model.Reading;

/**
 * Created by Francisco Almeida on 09/01/2018.
 */

public class ReadingService {

    private static final String LOG_TAG = ReadingService.class.getName();

    private Context context;

    public ReadingService(Context context) {
        this.context = context;
    }

    public long createReading(Reading reading) {
        ReadingDAO readingDAO = new ReadingDAO(context);
        return readingDAO.createReading(reading);
    }

    public Reading findById(long id) {
        ReadingDAO readingDAO = new ReadingDAO(context);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        MarkerDAO markerDAO = new MarkerDAO(context);

        long propertyId = readingDAO.getPropertyId(id);
        Property property = propertyDAO.findById(propertyId);

        long markerId = readingDAO.getMarkerId(id);
        MyMarker marker = markerDAO.findById(markerId);

        Reading reading = readingDAO.findById(id);

        if (reading != null){
            reading.setProperty(property);
            reading.setMarker(marker);
        }

        return reading;
    }

    public List<Reading> findAll() {
        ReadingDAO readingDAO = new ReadingDAO(context);
        PropertyDAO propertyDAO = new PropertyDAO(context);
        MarkerDAO markerDAO = new MarkerDAO(context);
        List<Reading> readings = readingDAO.findAll();
        for (Reading r : readings) {
            long propertyId = readingDAO.getPropertyId(r.getId());
            Property property = propertyDAO.findById(propertyId);

            long markerId = readingDAO.getMarkerId(r.getId());
            MyMarker myMarker = markerDAO.findById(markerId);

            r.setProperty(property);
            r.setMarker(myMarker);
        }
        return readings;
    }

    public List<Reading> findListByPropertyId(long propId) {
        ReadingDAO readingDAO = new ReadingDAO(context);
        return readingDAO.findByPropertyId(propId);
    }

    public List<Reading> findListByMarkerId(long markerId) {
        ReadingDAO readingDAO = new ReadingDAO(context);
        return readingDAO.findByMarkerId(markerId);
    }
}
