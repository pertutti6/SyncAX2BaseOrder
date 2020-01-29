package AX;

import java.sql.*;

/************************************************************
 * DOKUMENTATION
 *
 * SQL Server Access
 * https://www.remwebdevelopment.com/blog/java-jdbc-code-examples-for-mssql-259.html
 *
 */


public class AXDB {
    static String sServer = "";
    static String sDB = "";
    static String sUser = "";
    static String sPassword = "";

    Connection conn = null;
    ResultSet rs = null;

    protected void setupConnection(String sEnvironment)
    {
            switch (sEnvironment)
            {
                case "live" :
                    sServer = "src-axprod-01";
                    sDB = "AX2012_Prod";
                    sUser = "dwh";
                    sPassword = "Sirocco2dwh";
                    break;
                case "dev" :
                    sServer = "src-axdev\\dev";
                    sDB = "AX2012_DEV";
                    sUser = "dwh";
                    sPassword = "Sirocco2dwh";
                    break;
                default:
                    sServer = "src-axdev\\dev";
                    sDB = "AX2012_Prod";
                    sUser = "dwh";
                    sPassword = "Base2dwh";
                    break;
            }
    }

    protected String openConnection()
    {
        String sErr = "";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            if (conn == null) {
                //    String dbURL = "jdbc:sqlserver://src-axprod-01\\AX2012_Prod;user=dwh;password=Sirocco2dwh";
                //  String connectionUrl = "jdbc:sqlserver://src-dwh-01:1433;" +
                //          "databaseName=stg;user=dwh;password=Base2dwh";
            //    String connectionUrl = "jdbc:sqlserver://[" + sServer + "]:1433;" +
              //          "databaseName=" + sDB + ";user=" + sUser + ";password=" + sPassword;

                String connectionUrl = "jdbc:sqlserver://src-axdev\\dev;" +
                        "databaseName=AX2012_DEV;user=dwh;password=Sirocco2dwh";

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(connectionUrl);
                if (conn != null) {
                    System.out.println("Connected");
                }
            }
        } catch (ClassNotFoundException e) {
            sErr = e.toString();
        } catch (SQLException e) {
            sErr = e.toString();
        }
        return sErr;
    }

    protected void closeConnection()
    {
        if(conn != null) try { conn.close(); } catch(Exception e) {}
    }

    protected String readTable(String sAttr,String sTables,String sWhere)
    {
        String sErr = "";
        Statement stmt = null;

        String sql = "SELECT " + sAttr + " FROM " + sTables;
        if (!sWhere.equals(""))
        {
            sql = sql + " where " + sWhere;
        }

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            rs = null;
            sErr = e.toString();
        }
        return sErr;
    }

    protected String updateTable(String valueset,String sTable,long RecID)
    {
        String sErr = "";
        Statement stmt = null;

        String sql = "UPDATE " + sTable + " SET " + valueset + " WHERE RECID = " +RecID;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            rs = null;
            sErr = e.toString();
        }
        return sErr;
    }

    protected ResultSet getDataTable()
    {
        return rs;
    }


}
