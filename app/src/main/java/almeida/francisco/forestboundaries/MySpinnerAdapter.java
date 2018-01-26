package almeida.francisco.forestboundaries;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Francisco Almeida on 25/01/2018.
 */

public class MySpinnerAdapter<T> extends ArrayAdapter<T> {

    private Context context;
    private T[] objects;
    private int resource;
    private String hint;

    public MySpinnerAdapter(@NonNull Context context, int resource, @NonNull T[] objects, String hint) {
        super(context, resource, objects);
        this.context = context;
        List<T> auxList = new ArrayList<>(objects.length + 1);
        auxList.add(null);
        Collections.addAll(auxList, objects);
        this.objects = ((T[]) auxList.toArray());
        this.resource = resource;
        this.hint = hint;
    }

    public MySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects, String hint) {
        super(context, resource, objects);
        List<T> auxList = new ArrayList<>(objects.size() + 1);
        auxList.add(null);
        auxList.addAll(objects);
        this.objects = (T[]) auxList.toArray();
        this.context = context;
        this.resource = resource;
        this.hint = hint;
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

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    private View helperGetView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }
        TextView text = (TextView) v.findViewById(android.R.id.text1);
        if (position == 0 ){
            text.setTextColor(Color.GRAY);
            text.setText(hint);
            return v;
        }
        text.setTextColor(Color.BLACK);
        text.setText(objects[position].toString());
        return v;
    }
}
