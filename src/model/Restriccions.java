package model;
import exceptions.NotFoundException;
import model.*;

import java.util.ArrayList;


public abstract class Restriccions {

    private int id;

//que restriccions torni un arraylist dels dies i les horesque podria la susodicha asignatura

    public Restriccions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
