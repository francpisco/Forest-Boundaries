package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class DetailActivity extends AppCompatActivity {

    public static String PROP_ID = "prop_id";

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        PropertyDetailFragment fragment = (PropertyDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_frag);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolBar);
        int id = (int) getIntent().getExtras().get(PROP_ID);
        fragment.setPropertyId(id);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_frag)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
