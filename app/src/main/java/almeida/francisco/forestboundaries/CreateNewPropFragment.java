package almeida.francisco.forestboundaries;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


import almeida.francisco.forestboundaries.dbhelper.MyHelper;
import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.OwnerService;
import almeida.francisco.forestboundaries.service.PropertyService;


public class CreateNewPropFragment extends Fragment {

    public interface Listener {
        void onClick(long propId);
        void onUpClick();
    }
    private Listener listener;

    private Spinner owners;
    private EditText description;
    private EditText approxSize;
    private EditText note;
    private Spinner typeOfUseSpn;
    private Spinner yearOfPlantSpn;
    private Spinner yearOfLastCutSpn;
    private Spinner yearOfLastCleanSpn;
    private Spinner monthOfLastCleanSpn;
    private FloatingActionButton nextFAB;
    private MySpinnerAdapter<Integer> yearOfPlantAdapter;
    private MySpinnerAdapter<Integer> yearOfLastCutAdapter;
    private MySpinnerAdapter<Integer> yearOfLastCleanAdapter;
    private MySpinnerAdapter<String> monthOfLastCleanAdapter;

    private SQLiteDatabase db;
    private Cursor cursor;

    public CreateNewPropFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_new_prop, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        owners = (Spinner) view.findViewById(R.id.owner_spinner);
        description = (EditText) view.findViewById(R.id.description_edit_text);
        approxSize = (EditText) view.findViewById(R.id.approx_size_edit_text);
        note = (EditText) view.findViewById(R.id.note_text);
        typeOfUseSpn = (Spinner) view.findViewById(R.id.type_of_use_spinner);
        yearOfPlantSpn = (Spinner) view.findViewById(R.id.year_of_plantation_spinner);
        yearOfLastCutSpn = (Spinner) view.findViewById(R.id.year_of_last_cut_spinner);
        yearOfLastCleanSpn = (Spinner) view.findViewById(R.id.year_of_last_cleaning);
        monthOfLastCleanSpn = (Spinner) view.findViewById(R.id.month_of_last_cleaning);
        nextFAB = (FloatingActionButton) view.findViewById(R.id.save_property_floating_btn);

        if (isAdded()) {
            db = MyHelper.getHelper(getActivity()).getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " +
                            MyHelper.TABLE_OWNERS,
                            null);
            CursorAdapter adapter = new SimpleCursorAdapter(
                    getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    cursor,
                    new String[]{MyHelper.O_NAME},
                    new int[]{android.R.id.text1},
                    0);
            owners.setAdapter(adapter);

            MySpinnerAdapter<String> typeAdapter = new MySpinnerAdapter<String>(getActivity(),
                    R.layout.spinner_layout,
                    getResources().getStringArray(R.array.land_uses),
                    getResources().getString(R.string.type_spinner_hint),
                    "--");
            typeAdapter.setLocked(false);
            typeOfUseSpn.setAdapter(typeAdapter);
            typeOfUseSpn.setOnItemSelectedListener(new MyOnItemSelectedListener());

            yearOfPlantAdapter = new MySpinnerAdapter<Integer>(
                    getActivity(),
                    R.layout.spinner_layout, Property.getYears(),
                    getResources().getString(R.string.year_of_plantation_hint),
                    "--");
            yearOfPlantSpn.setAdapter(yearOfPlantAdapter);

            yearOfLastCutAdapter = new MySpinnerAdapter<Integer>(
                    getActivity(),
                    R.layout.spinner_layout, Property.getYears(),
                    getResources().getString(R.string.year_of_last_cut_hint),
                    "--");
            yearOfLastCutSpn.setAdapter(yearOfLastCutAdapter);

            yearOfLastCleanAdapter = new MySpinnerAdapter<Integer>(
                    getActivity(),
                    R.layout.spinner_layout, Property.getYears(),
                    getResources().getString(R.string.year_of_last_clean_hint),
                    "--");
            yearOfLastCleanSpn.setAdapter(yearOfLastCleanAdapter);

            monthOfLastCleanAdapter = new MySpinnerAdapter<String>(
                    getActivity(),
                    R.layout.spinner_layout,
                    getResources().getStringArray(R.array.months),
                    getResources().getString(R.string.month_of_last_clean_hint),
                    "--");
            monthOfLastCleanSpn.setAdapter(monthOfLastCleanAdapter);

            nextFAB.setOnClickListener(new MyOnClickListener());
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        Toolbar mToolBar = (Toolbar) getView().findViewById(R.id.create_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getActivity().setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                listener.onUpClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cursor.close();
        db.close();
    }

    private boolean save() {
        String descripStr = description.getText().toString();
        String approxSizeStr = approxSize.getText().toString();
        int approxSizeValue = 0;
        if (!approxSizeStr.isEmpty()) {
            approxSizeValue = Integer.valueOf(approxSizeStr);
        }
        if (descripStr.isEmpty()) {
            description.setError(getString(R.string.description_error));
        } else {
            OwnerService ownerService = new OwnerService(getActivity());
            PropertyService propertyService = new PropertyService(getActivity());
            Owner o = ownerService.findById(owners.getSelectedItemId());
            Property p = new Property()
                    .setOwner(o)
                    .setLocationAndDescription(descripStr)
                    .setLandUse(typeOfUseSpn.getSelectedItem().toString())
                    .setYearOfPlantation(Integer
                            .valueOf(yearOfPlantSpn.getSelectedItem().toString()))
                    .setYearOfLastCut(Integer
                            .valueOf(yearOfLastCutSpn.getSelectedItem().toString()))
                    .setYearAndMonthOfLastCleaning(dateAsValue(yearOfLastCleanSpn
                            .getSelectedItem(), monthOfLastCleanSpn.getSelectedItemPosition()))
                    .setNote(note.getText().toString());
            if (approxSizeValue > 0)
                p.setApproxSizeInSquareMeters(approxSizeValue);
            long propId = propertyService.createProperty(p);
            listener.onClick(propId);
        }
        return true;
    }

    private int dateAsValue(Object year, int month) {
        return Integer.valueOf(year.toString()) * 100 + month;
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i <= 0 || i > 3) {
                yearOfPlantAdapter.setLocked(true);
                yearOfPlantSpn.setEnabled(false);
                yearOfPlantAdapter.notifyDataSetChanged();

                yearOfLastCutAdapter.setLocked(true);
                yearOfLastCutSpn.setEnabled(false);
                yearOfLastCutAdapter.notifyDataSetChanged();

                yearOfLastCleanAdapter.setLocked(true);
                yearOfLastCleanSpn.setEnabled(false);
                yearOfLastCleanAdapter.notifyDataSetChanged();

                monthOfLastCleanAdapter.setLocked(true);
                monthOfLastCleanSpn.setEnabled(false);
                monthOfLastCleanAdapter.notifyDataSetChanged();
            } else {
                yearOfPlantAdapter.setLocked(false);
                yearOfPlantSpn.setEnabled(true);
                yearOfPlantAdapter.notifyDataSetChanged();

                yearOfLastCutAdapter.setLocked(false);
                yearOfLastCutSpn.setEnabled(true);
                yearOfLastCutAdapter.notifyDataSetChanged();

                yearOfLastCleanAdapter.setLocked(false);
                yearOfLastCleanSpn.setEnabled(true);
                yearOfLastCleanAdapter.notifyDataSetChanged();

                monthOfLastCleanAdapter.setLocked(false);
                monthOfLastCleanSpn.setEnabled(true);
                monthOfLastCleanAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            save();
        }
    }
}
