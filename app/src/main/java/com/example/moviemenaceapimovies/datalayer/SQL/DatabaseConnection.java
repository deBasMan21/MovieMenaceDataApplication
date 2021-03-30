package com.example.moviemenaceapimovies.datalayer.SQL;

import java.sql.*;

/**
 *
 * @author ppthgast
 */
public class DatabaseConnection {

    private Connection connection;

    // The Statement object has been defined as a field because some methods
    // may return a ResultSet object. If so, the statement object may not
    // be closed as you would do when it was a local variable in the query
    // execution method.
    private Statement statement;
    protected ResultSet rs = null;
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String username = "AdminB12";
    private static String password = "tom&jerry";
    private static String url = "jdbc:jtds:sqlserver://aei-sql2.avans.nl:1443/MovieMenaceB12";


    public DatabaseConnection() {
        connection = null;
        statement = null;
    }

    public boolean openConnection() {
        boolean result = false;

        if (connection == null) {
            try {
                // Try to create a connection with the library database
                Class.forName(Classes);
//                connection = DriverManager.getConnection(url, username, password);
                connection = DriverManager.getConnection(url, username, password);

                if (connection != null) {
                    statement = connection.createStatement();
                }

                result = true;
            } catch (Exception e) {
                System.out.println(e);
                result = false;
            }
        } else {
            // A connection was already initalized.
            result = true;
        }

        return result;
    }

    public boolean connectionIsOpen() {
        boolean open = false;

        if (connection != null && statement != null) {
            try {
                open = !connection.isClosed() && !statement.isClosed();
            } catch (SQLException e) {
                System.out.println(e);
                open = false;

            }
        }
        // Else, at least one the connection or statement fields is null, so
        // no valid connection.

        return open;
    }

    public void closeConnection() {
        try {
            connection.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet executeSQLSelectStatement(String query) {
        // First, check whether a some query was passed and the connection with
        // the database.
        if (query != null && connectionIsOpen()) {
            // Then, if succeeded, execute the query.
            try {
                rs = statement.executeQuery(query);
            } catch (SQLException e) {
                System.out.println(e);
                rs = null;
            }
        }
        return rs;
    }

    public boolean executeSQLStatement(String query) {
        boolean result = false;

        // First, check whether a some query was passed and the connection with
        // the database.
        if (query != null && connectionIsOpen()) {
            // Then, if succeeded, execute the query.
            try {
                statement.executeUpdate(query);
                result = true;
            } catch (SQLException e) {
                System.out.println(e);
                result = false;
            }
        }

        return result;
    }
}

