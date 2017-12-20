package almeida.francisco.forestboundaries.service;

import android.content.Context;

import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.model.Marker;

/**
 * Created by Francisco Almeida on 20/12/2017.
 */

public class MarkerService {

    private Context context;

    public MarkerService(Context context) {
        this.context = context;
    }

    public Marker findById(long id) {
        PropertyDAO propertyDAO = new PropertyDAO(context);
        return null;
    }
}
