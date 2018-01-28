package almeida.francisco.forestboundaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LocatePropertyActivity extends AppCompatActivity
        implements EditMarkersFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_property);
        long propId = (long) getIntent().getExtras().get(CreateNewPropActivity.PROPERTY_ID);

        EditMarkersFragment fragment = (EditMarkersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locate_p_frag);
        fragment.setPropertyId(propId);
    }

    @Override
    public void onUpButtonClick() {
        finish();
    }
}
