package almeida.francisco.forestboundaries;


import android.content.Context;
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
import android.widget.EditText;


public class CreateOwnerFragment extends Fragment {

    public interface Listener {
        public void onUpClick();
    }
    private Listener listener;

    private EditText nameTxt;
    private EditText emailTxt;
    private EditText passwordTxt;
    private FloatingActionButton doneFAB;

    public CreateOwnerFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_owner, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        nameTxt = (EditText) view.findViewById(R.id.owner_name);
        emailTxt = (EditText) view.findViewById(R.id.owner_email);
        passwordTxt = (EditText) view.findViewById(R.id.owner_password);
        doneFAB = (FloatingActionButton) view.findViewById(R.id.save_owner_floating_btn);
        doneFAB.setOnClickListener(new MyOnClickListener());
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        Toolbar mToolBar = (Toolbar) getView().findViewById(R.id.create_owner_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_owner_menu, menu);
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

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }
}
