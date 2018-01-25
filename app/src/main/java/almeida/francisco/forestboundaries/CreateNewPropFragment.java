package almeida.francisco.forestboundaries;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    }

    private Listener listener;

    private Spinner owners;
    private Button submit;
    private EditText description;
    private EditText approxSize;
    private Spinner typeOfUseSpn;
    private Spinner yearOfPlantSpn;
    private Spinner yearOfLastCutSpn;
    private Spinner yearOfLastCleanSpn;
    private Spinner monthOfLastCleanSpn;

    private SQLiteDatabase db;
    private Cursor cursor;

    public CreateNewPropFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_new_prop, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        owners = (Spinner) view.findViewById(R.id.owner_spinner);
        submit = (Button) view.findViewById(R.id.submit_new_prop);
        description = (EditText) view.findViewById(R.id.description_edit_text);
        approxSize = (EditText) view.findViewById(R.id.approx_size_edit_text);
        typeOfUseSpn = (Spinner) view.findViewById(R.id.type_of_use_spinner);
        yearOfPlantSpn = (Spinner) view.findViewById(R.id.year_of_plantation_spinner);
        yearOfLastCutSpn = (Spinner) view.findViewById(R.id.year_of_last_cut_spinner);
        yearOfLastCleanSpn = (Spinner) view.findViewById(R.id.year_of_last_cleaning);
        monthOfLastCleanSpn = (Spinner) view.findViewById(R.id.month_of_last_cleaning);

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

            MySpinnerAdapter typeAdapter = new MySpinnerAdapter(getActivity(),
                    R.layout.spinner_layout,
                    getResources().getStringArray(R.array.land_uses));
            typeOfUseSpn.setAdapter(typeAdapter);

//            ArrayAdapter<String> typeOfUseAdapter = new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_spinner_dropdown_item,
//                    getResources().getStringArray(R.array.land_uses));
//            typeOfUseSpn.setAdapter(typeOfUseAdapter);

            ArrayAdapter<Integer> yearOfPlantAdapter = new ArrayAdapter<Integer>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, Property.getYears());
            yearOfPlantSpn.setAdapter(yearOfPlantAdapter);

            ArrayAdapter<Integer> yearOfLastCutAdapter = new ArrayAdapter<Integer>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, Property.getYears());
            yearOfLastCutSpn.setAdapter(yearOfLastCutAdapter);

            ArrayAdapter<Integer> yearOfLastCleanAdapter = new ArrayAdapter<Integer>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, Property.getYears());
            yearOfLastCleanSpn.setAdapter(yearOfLastCleanAdapter);

            ArrayAdapter<String> monthOfLastCleanAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    getResources().getStringArray(R.array.months));
            monthOfLastCleanSpn.setAdapter(monthOfLastCleanAdapter);
        }

        submit.setOnClickListener(new SubmitListener());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cursor.close();
        db.close();
    }

    private class SubmitListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
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
                        .setYearAndMonthOfLastCleaning(Integer.valueOf(yearOfLastCleanSpn
                                .getSelectedItem().toString().concat(monthOfLastCleanSpn
                                        .getSelectedItem().toString())));
                if (approxSizeValue > 0)
                    p.setApproxSizeInSquareMeters(approxSizeValue);
                long propId = propertyService.createProperty(p);
                listener.onClick(propId);
            }
        }
    }
}
