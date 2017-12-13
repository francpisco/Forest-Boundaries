package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmpap on 11/12/2017.
 */

public class Owner {

    private long id;
    private String name;

    public static List<Owner> ownerList = new ArrayList<>();

    static {
        ownerList.add(new Owner("Manel"));
        ownerList.add(new Owner("Lourdes"));
    }

    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
