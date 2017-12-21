package almeida.francisco.forestboundaries.service;

import android.content.Context;

import java.util.List;

import almeida.francisco.forestboundaries.dbhelper.OwnerDAO;
import almeida.francisco.forestboundaries.model.Owner;

/**
 * Created by Francisco Almeida on 21/12/2017.
 */

public class OwnerService {

    private static final String TAG = OwnerService.class.getName();

    private Context context;

    public OwnerService(Context context) {
        this.context = context;
    }

    public long createOwner(Owner owner) {
        OwnerDAO ownerDAO = new OwnerDAO(context);
        return ownerDAO.createOwner(owner);
    }

    public Owner findById(long id) {
        OwnerDAO ownerDAO = new OwnerDAO(context);
        return ownerDAO.findById(id);
    }

    public List<Owner> findAll() {
        OwnerDAO ownerDAO = new OwnerDAO(context);
        return ownerDAO.findAll();
    }

    public void loadOwners() {
        Owner owner1 = new Owner().setName("Manel");
        Owner owner2 = new Owner().setName("Lourdes");
        createOwner(owner1);
        createOwner(owner2);
    }
}
