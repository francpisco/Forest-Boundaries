package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MarkInSituFragment extends Fragment {

    private long propertyId;

    public MarkInSituFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mark_in_situ, container, false);
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
