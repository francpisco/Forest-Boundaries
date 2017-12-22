package almeida.francisco.forestboundaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LocatePropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_property);
        long propId = (long) getIntent().getExtras().get(CreateNewPropActivity.PROPERTY_ID);
        LocatePropertyFragment fragment = (LocatePropertyFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locate_p_frag);
        fragment.setPropertyId(propId);
    }
}
