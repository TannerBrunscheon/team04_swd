import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDB {
    //private static final String URL = "jdbc:derby:/scratch/myDB";
    private static final String URL = "jdbc:postgresql://s-l112.engr.uiowa.edu:5432/postgres";
    private static final String USERNAME = "student2";
    private static final String PASSWORD = "hawkeys04";

    private Connection connection; // manages DB connection
    private PreparedStatement selectAllValue; // Silly statement for finding values in DB

    public static void main(String args[]) {
        TestDB tdb = new TestDB();
    }

    public TestDB() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            selectAllValue = connection.prepareStatement("SELECT * FROM presidentrace");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            System.exit(-1);
        }

        List<Names> results = null;
        ResultSet resultSet = null;
        try {
            resultSet = selectAllValue.executeQuery();
            results = new ArrayList<Names>();
            while (resultSet.next()) {
                results.add(new Names(resultSet.getInt("addressid"),
                        resultSet.getString("firstname"), resultSet.getString("lastname")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                //close();
            }
        }
        for (Names n : results) {
            System.out.print("NAME: " + n + "\n");
        }
    }
}