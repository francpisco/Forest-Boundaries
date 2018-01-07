package almeida.francisco.forestboundaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MarkInSituActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_in_situ);
        long propId = (long) getIntent().getExtras().get(PropertyDetailActivity.PROP_ID);
        MarkInSituFragment fragment = (MarkInSituFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mark_in_s_frag);
        fragment.setPropertyId(propId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mark_in_situ_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
