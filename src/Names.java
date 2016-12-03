/**
 * Created by johnsonhj on 11/30/16.
 */
public class Names {
    private int nameID;
    private String firstName;
    private String lastName;

    Names(int nameID, String firstName, String lastName)
    {
        this.nameID = nameID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getNameID() {
        return nameID;
    }

    public void setNameID(int nameID) {
        this.nameID = nameID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Names{" +
                "nameID=" + nameID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

