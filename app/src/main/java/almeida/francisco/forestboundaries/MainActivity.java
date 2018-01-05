package almeida.francisco.forestboundaries;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.service.OwnerService;
import almeida.francisco.forestboundaries.service.PropertyService;


public class MainActivity extends AppCompatActivity implements PropertyListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolBar);
        new PopulateTables().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new_prop:
                Intent intent = new Intent(this, CreateNewPropActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings_action:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(long id) {
        View container = findViewById(R.id.detail_frag_container);
        if (container != null) {
            PropertyDetailFragment fragment = new PropertyDetailFragment();
            fragment.setPropertyId(id);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_frag_container, fragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            Intent intent = new Intent(this, PropertyDetailActivity.class);
            intent.putExtra(PropertyDetailActivity.PROP_ID, (int) id);
            startActivity(intent);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        ((PropertyListFragment)getFragmentManager()
                .findFragmentById(R.id.prop_list_frag)).myOnRestart();
    }

    private class PopulateTables extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //popular tabelas aqui
            OwnerService ownerService = new OwnerService(MainActivity.this);
            if (ownerService.findAll().size() <= 0){
                ownerService.loadOwners();
            }

            PropertyService propertyService = new PropertyService(MainActivity.this);
            PropertyDAO pDAO = new PropertyDAO(MainActivity.this);
            if (propertyService.findAll().size() <= 0){
                propertyService.loadProperties();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            ((PropertyListFragment)getFragmentManager()
                    .findFragmentById(R.id.prop_list_frag)).myOnRestart();
        }

    }
}
