package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @implSpec It is expected that every class linked to a database implements {@link #initialiseConnection()},
 *          this method must detail the process of logging into the database.
 *
 * @apiNote This interface was added with the intention to clearly define what having database capability entails.
 */
public interface iDatabaseInterface
{
    Connection initialiseConnection();
    Connection setNewConnection(String database, String username, String password);
    void dropTable(String tableName) throws SQLException;
    void createTable() throws SQLException;
    String query();

}
