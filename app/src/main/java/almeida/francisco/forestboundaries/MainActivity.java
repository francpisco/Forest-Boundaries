package almeida.francisco.forestboundaries;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import almeida.francisco.forestboundaries.dbhelper.OwnerDAO;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.model.Property;


public class MainActivity extends AppCompatActivity implements PropertyListFragment.Listener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolBar);

        //popular tabelas aqui
        OwnerDAO oDAO = new OwnerDAO(this);
        if (oDAO.findAll().size() <= 0){
            oDAO.loadOwners();
            System.out.println("a popular owners");
        }

        PropertyDAO pDAO = new PropertyDAO(this);
        if (pDAO.findAll().size() <= 0){
            pDAO.loadProperties(oDAO);
            System.out.println("a popular props");
        }
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
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.PROP_ID, (int) id);
            startActivity(intent);
        }
    }
}
