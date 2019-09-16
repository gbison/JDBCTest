package DBPackage;

import java.sql.*;

public class DBConnector {
    /*
        KEY CLASSES FOR JDBC
        java.sql.DriverManager
        java.sql.Connection
        java.sql.Statement
        java.sql.ResultSet
        java.sql.DataSource

     */
    //Member Declarations
    private String url = null;                          //Default URL Connection to database location
    private Connection conn = null;                     //SQL Connection Object
    private String query = null;                        //The query we are interested in running.
    private Statement statement = null;                 //Prepare a statement object.
    private ResultSet resultSet = null;                 //Prepare a result object.

    //Accessors

    //Setup database URL
    public void SetUrl(String url) {
        this.url = url;
    }

    //Return current database URL
    public String GetURL(){
        return this.url;
    }

    //Set the query to be run
    public void SetQuery(String query)
    {
        this.query = query;
    }

    //Get the results from the query
    public ResultSet GetResults(){
        //Return the result set so we can use it else where if needed.
        return this.resultSet;
    }

    //Connection Method returns boolean on success.
    public boolean connect() {

        try {
            //Initialize all our variables now the their respective objects.
            conn = DriverManager.getConnection("jdbc:sqlite:SQLiteDB.db");    //Use DriverManager to access the connection utility.
            try {

                statement = conn.createStatement();         //Use connection to prepare a statement object
                resultSet = statement.executeQuery(query);  //Feed statement object query then fire.
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void DBClose()
    {
        try {
            //close up shop once we are done.
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //testing and debugging.
    protected void ParseStringResults(ResultSet results, String columns[])
    {
        //As long as the result set shows we have another piece of data, keep iterating.
        try{
            while (results.next()) {
                for (String column:columns) {
                    System.out.println(column + " : " + results.getString(column));
                }
                System.out.println("-----------------------------------------------------------");
            }
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }
}
