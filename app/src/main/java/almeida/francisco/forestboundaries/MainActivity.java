package almeida.francisco.forestboundaries;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import almeida.francisco.forestboundaries.dbhelper.MyHelper;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.service.OwnerService;
import almeida.francisco.forestboundaries.service.PropertyService;


public class MainActivity extends AppCompatActivity implements MainRecyclerFragment.Listener {

    private static final String OWNER_ID = "owner_id";

    private ListView ownersList;
    private Cursor cursor;
    private SQLiteDatabase db;
    private long ownerId = -1;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {
            ownerId = savedInstanceState.getLong(OWNER_ID);
        }

        new PopulateTables().execute();
        db = MyHelper.getHelper(this).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_OWNERS, null);
        ownersList = (ListView) findViewById(R.id.owners_list);
        ownersList.setAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_selectable_list_item,
                cursor,
                new String[]{MyHelper.O_NAME},
                new int[]{android.R.id.text1},
                0));
        ownersList.setOnItemClickListener(new OwnersOnItemClickListener());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loadRecyclerFragment();

    }

    //not sure this is needed
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        drawerToggle.onConfigurationChanged(config);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onSaveInstanceState(Bundle saveThisState) {
        super.onSaveInstanceState(saveThisState);
        saveThisState.putLong(OWNER_ID, ownerId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
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
    public void onItemClicked(long propertyId) {
        View container = findViewById(R.id.detail_frag_container);
        if (container != null) {
            PropertyDetailFragment fragment = new PropertyDetailFragment();
            fragment.setPropertyId(propertyId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_frag_container, fragment, "visible fragment")
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            Intent intent = new Intent(this, PropertyDetailActivity.class);
            intent.putExtra(PropertyDetailActivity.PROP_ID, propertyId);
            startActivity(intent);
        }
    }

    private void selectOwner(long ownerId) {
        this.ownerId = ownerId;
        loadRecyclerFragment();
        drawerLayout.closeDrawer(findViewById(R.id.main_drawer));
    }

    // TODO: 30/01/2018 o backstack nao esta a funcionar bem 
    private void loadRecyclerFragment() {
        MainRecyclerFragment fragment = new MainRecyclerFragment();
        fragment.setOwnerId(ownerId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycler_view_frame, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
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
            if (propertyService.findAll().size() <= 0){
                propertyService.loadProperties();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            loadRecyclerFragment();
        }

    }
    private class OwnersOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            selectOwner(id);
        }
    }
}
