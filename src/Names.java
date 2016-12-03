/**
 * Created by johnsonhj on 11/30/16.
 */
public class Names {
    private String nameID;
    private int democrat;
    private int republican;

    Names(String nameID, int democrat, int republican)
    {
        this.nameID = nameID;
        this.democrat = democrat;
        this.republican = republican;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public int getDemocrat() {
        return democrat;
    }

    public void setDemocrat(int democrat) {
        this.democrat = democrat;
    }

    public int getRepublican() {
        return republican;
    }

    public void setRepublican(int republican) {
        this.republican = republican;
    }

    @Override
    public String toString() {
        return nameID + ": " + "dem: " + democrat + "    rep: " + republican + "\n";
    }
}

