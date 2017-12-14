package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.model.PropertyMarker;

public class PropertyDetailFragment extends Fragment {

    private long propertyId = 0;

    public PropertyDetailFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            propertyId = savedInstanceState.getLong("property_id");
        }
        return inflater.inflate(R.layout.fragment_property_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            TextView ownerView = (TextView) view.findViewById(R.id.owner_value);
            ownerView.setText(Long.toString(Property.properties.get((int)propertyId).getOwnerId()));

            TextView descriptionView = (TextView) view.findViewById(R.id.description_value);
            descriptionView.setText(Property.properties.get((int)propertyId).getLocationAndDescription());

            TextView firstMarkerView = (TextView) view.findViewById(R.id.marker_value);
            List<PropertyMarker> markers = Property.properties.get((int) propertyId).getMarkers();
            if (markers.size() > 0) {
                firstMarkerView.setText(Double.toString(markers.get(0).getAveragedLat()) +
                        " " + Double.toString(markers.get(0).getAveragedLong()));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        onSavedInstanceState.putLong("property_id", propertyId);
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
