package almeida.francisco.forestboundaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateOwnerActivity extends AppCompatActivity implements CreateOwnerFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_owner);
    }

    @Override
    public void onUpClick() {
        finish();
    }

    @Override
    public void done() {
        finish();
    }
}
