package almeida.francisco.forestboundaries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateNewPropActivity
        extends AppCompatActivity implements CreateNewPropFragment.Listener{

    public static final String PROPERTY_ID = "propId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_prop);
    }

    @Override
    public void onClick(long propId) {
        Intent intent = new Intent(this, LocatePropertyActivity.class);
        intent.putExtra(PROPERTY_ID, propId);
        startActivity(intent);
        finish();
    }
}
