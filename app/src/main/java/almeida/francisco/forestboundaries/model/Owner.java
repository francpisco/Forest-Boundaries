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
        ownerList.add(new Owner().setName("Manel"));
        ownerList.add(new Owner().setName("Lourdes"));
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Owner setName(String name) {
        this.name = name;
        return this;
    }

    public Owner setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
