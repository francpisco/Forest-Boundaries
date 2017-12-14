package almeida.francisco.forestboundaries;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;


public class CreateNewPropFragment extends Fragment {

    public interface Listener {
        void onClick();
    }

    private Listener listener;

    private Spinner owners;
    private Button submit;
    private EditText description;
    private EditText approxSize;

    public CreateNewPropFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_new_prop, container, false);
    }

    // TODO: 14/12/2017 mudar isto para db
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        owners = (Spinner) view.findViewById(R.id.owner_spinner);
        submit = (Button) view.findViewById(R.id.submit_new_prop);
        description = (EditText) view.findViewById(R.id.description_edit_text);
        approxSize = (EditText) view.findViewById(R.id.approx_size_edit_text);

        if (isAdded()) {
            // TODO: 14/12/2017 passar para cursor adapter
            ArrayAdapter<Owner> adapter = new ArrayAdapter<Owner>(getActivity(),
                    android.R.layout.simple_list_item_1, Owner.ownerList);
            owners.setAdapter(adapter);
        }

        submit.setOnClickListener(new View.OnClickListener() {
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
                    if (approxSizeValue <= 0) {
                        Property.properties.add(new Property()
                                        .setOwnerId(owners.getSelectedItemPosition())
                                        .setLocationAndDescription(descripStr));
                    } else {
                        Property.properties.add(new Property()
                                        .setOwnerId(owners.getSelectedItemPosition())
                                        .setLocationAndDescription(descripStr)
                                        .setApproxSizeInSquareMeters(approxSizeValue));
                    }
                    listener.onClick();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }


}
