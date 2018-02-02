package almeida.francisco.forestboundaries;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.service.OwnerService;


public class CreateOwnerFragment extends Fragment {

    public interface Listener {
        void onUpClick();
        void done();
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

    private void done() {
        String nameStr = nameTxt.getText().toString();
        if (nameStr.isEmpty()) {
            nameTxt.setError(getString(R.string.owner_name_error));
            return;
        }
        String emailStr = emailTxt.getText().toString();
        if (emailStr.isEmpty() || !isEmailValid(emailStr)) {
            emailTxt.setError(getString(R.string.owner_email_error));
            return;
        }
        if (isEmailAlreadyInDB(emailStr)) {
            emailTxt.setError(getString(R.string.owner_email_already_exists_error));
            return;
        }
        String passwordStr = passwordTxt.getText().toString();
        if (passwordStr.isEmpty()) {
            passwordTxt.setError(getString(R.string.owner_password_error));
            return;
        }
        OwnerService ownerService = new OwnerService(getActivity());
        Owner o = new Owner().setName(nameStr).setEmail(emailStr).setPassword(passwordStr);
        ownerService.createOwner(o);
        listener.done();
    }

    private boolean isEmailAlreadyInDB(String emailStr) {
        Owner owner = null;
        OwnerService ownerService = new OwnerService(getActivity());
        owner = ownerService.findByEmail(emailStr);
        return owner != null;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            done();
        }
    }
}
