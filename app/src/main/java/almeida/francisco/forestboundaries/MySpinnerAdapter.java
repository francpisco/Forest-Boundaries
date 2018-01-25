package almeida.francisco.forestboundaries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Francisco Almeida on 25/01/2018.
 */

public class MySpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] strings;
    private int resource;

    public MySpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] strings) {
        super(context, resource, strings);
        this.context = context;
        this.strings = strings;
        this.resource = resource;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return helperGetView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return helperGetView(position, convertView, parent);
    }

    private View helperGetView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }
        TextView text = (TextView) v.findViewById(android.R.id.text1);
        text.setText(strings[position]);
        return v;
    }

}
