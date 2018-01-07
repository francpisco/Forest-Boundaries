package almeida.francisco.forestboundaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
}
