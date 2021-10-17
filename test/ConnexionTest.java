import java.sql.*;

public class ConnexionTest {

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "root", "root");
//        System.out.println(connection.getSchema());

        if(!"PUBLIC".equals(connection.getSchema())) {
            throw new Exception("The connexion to the database failed ! ");
        }
        else System.out.println("Successfully Connected to the DB ! ");
        String createStatement = "CREATE TABLE IF NOT EXISTS QUESTION(ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(255), DIFFICULTY INT);";
        PreparedStatement cQuery = connection.prepareStatement(createStatement);
        System.out.println(cQuery.execute());
//        if (!isExecuted) {
//            throw new Exception("Failure: Cannot Create Table");
//        }
        // INSERT STATEMENT //
        PreparedStatement iQuery = connection.prepareStatement("INSERT INTO QUESTION(TITLE, DIFFICULTY) values (?,?)", Statement.RETURN_GENERATED_KEYS);
        iQuery.setString(1, "What is last version of CSS ? ");
        iQuery.setInt(2, 1);
        System.out.println(iQuery.execute());
        ResultSet generatedKeys = iQuery.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);

//         SELECT STATEMENT
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, TITLE, DIFFICULTY FROM question");
        ResultSet resultSet = sQuery.executeQuery();

        while(resultSet.next()) {
            System.out.println(resultSet.getInt("ID"));
            System.out.println(resultSet.getString("TITLE"));
            System.out.println(resultSet.getInt("DIFFICULTY"));

        }

        connection.close();
    }

}
