package database;

import java.sql.Connection;

/**
 * @implSpec It is expected that every class linked to a database implements {@link #initialiseConnection()},
 * this method must detail the process of logging into the database.
 */
public interface DatabaseInterface
{
    Connection initialiseConnection();
    Connection setNewConnection(String database, String username, String password);
}
