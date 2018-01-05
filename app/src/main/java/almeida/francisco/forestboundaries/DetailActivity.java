package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_prop:
                return true;
            case R.id.print_prop:


                return true;
            case R.id.settings_action:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
