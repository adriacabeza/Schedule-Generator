/**
 * @author Aina Garcia
 */


package stubs;

import java.util.HashMap;

public class Grup {
    private int num;
    private int capacitat;
    private HashMap<Integer,Subgrup> subgrups = new HashMap<>();

    /* needed from Assignatura */
    public Grup(int num, int capacitat, int subgrups ) {
            this.num = 10;
            this.capacitat = 50;
            this.subgrups.put(11, new Subgrup(1));
            this.subgrups.put(12, new Subgrup(2));
    }

    /* needed from Assignatura getSubgrups()
            needed from Grup getSubgrups()
    */
    public HashMap<Integer, Subgrup> getSubgrups() {
        HashMap<Integer,Subgrup> s = new HashMap<>();
        s.put(11, new Subgrup(1));
        s.put(12, new Subgrup(2));
        return s;
    }
}
