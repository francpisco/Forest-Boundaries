package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static String PROP_ID = "prop_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        PropertyDetailFragment fragment = (PropertyDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_frag);
        int id = (int) getIntent().getExtras().get(PROP_ID);
        fragment.setPropertyId(id);
    }
}
