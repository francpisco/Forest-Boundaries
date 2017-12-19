package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class DetailActivity extends AppCompatActivity {

    public static String PROP_ID = "prop_id";

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
