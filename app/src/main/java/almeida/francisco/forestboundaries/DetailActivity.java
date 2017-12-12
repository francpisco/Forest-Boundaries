package almeida.francisco.forestboundaries;

import android.app.Activity;
import android.os.Bundle;

public class DetailActivity extends Activity {

    public static String PROP_ID = "prop_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        PropertyDetailFragment fragment = (PropertyDetailFragment) getFragmentManager()
                .findFragmentById(R.id.detail_frag);
        int id = (int) getIntent().getExtras().get(PROP_ID);
        fragment.setPropertyId(id);
    }
}
