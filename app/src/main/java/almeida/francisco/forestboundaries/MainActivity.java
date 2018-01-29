package almeida.francisco.forestboundaries;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
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

    private ListView ownersList;
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolBar);
        new PopulateTables().execute();
        db = MyHelper.getHelper(this).getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_OWNERS, null);
        ownersList = (ListView) findViewById(R.id.owners_list);
        ownersList.setAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{MyHelper.O_NAME},
                new int[]{android.R.id.text1},
                0));
        ownersList.setOnItemClickListener(new OwnersOnItemClickListener());
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
    public void onRestart() {
        super.onRestart();
        ((MainRecyclerFragment)getSupportFragmentManager()
                .findFragmentById(R.id.prop_list_frag)).myOnRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onItemClicked(long propertyId) {
        View container = findViewById(R.id.detail_frag_container);
        if (container != null) {
            PropertyDetailFragment fragment = new PropertyDetailFragment();
            fragment.setPropertyId(propertyId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_frag_container, fragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            Intent intent = new Intent(this, PropertyDetailActivity.class);
            intent.putExtra(PropertyDetailActivity.PROP_ID, propertyId);
            startActivity(intent);
        }
    }

    private void selectOwner(int position) {
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
            ((MainRecyclerFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.prop_list_frag)).myOnRestart();
        }

    }
    private class OwnersOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectOwner(position);
        }
    }
}
