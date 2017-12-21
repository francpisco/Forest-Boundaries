package almeida.francisco.forestboundaries.service;

import android.util.LongSparseArray;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

import almeida.francisco.forestboundaries.model.Marker;
import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by Francisco Almeida on 21/12/2017.
 */

public class ServiceObjects {

    static final LongSparseArray<Owner> OWNERS = new LongSparseArray<>();
    static final LongSparseArray<Property> PROPERTIES = new LongSparseArray<>();
}
