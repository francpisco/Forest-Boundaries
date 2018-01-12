package almeida.francisco.forestboundaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditMarkersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_markers);
        long propId = (long) getIntent().getExtras().get(PropertyDetailActivity.PROP_ID);
        EditMarkersFragment fragment = (EditMarkersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.edit_markers_frag);
        fragment.setPropertyId(propId);
    }
}
