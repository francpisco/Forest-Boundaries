package almeida.francisco.forestboundaries.service;

import android.content.Context;

import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by Francisco Almeida on 20/12/2017.
 */

public class PropertyService {

    private static final String TAG = PropertyService.class.getName();

    private Context context;

    public PropertyService(Context context) {
        this.context = context;
    }

    public Property findById(long id) {
        return null;
    }
}
