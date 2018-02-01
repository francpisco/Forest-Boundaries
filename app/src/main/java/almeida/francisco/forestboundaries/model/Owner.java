package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Almeida on 11/12/2017.
 */

public class Owner {

    private long id;
    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Owner setName(String name) {
        this.name = name;
        return this;
    }

    public Owner setId(long id) {
        this.id = id;
        return this;
    }

    public Owner setEmail(String email) {
        this.email = email;
        return this;
    }

    public Owner setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
