package Model;

import java.sql.*;

public class Databases {

    String sEnvErp;
    String sEnvBase;
    String sEnvMag;
    String sEnvNews;

    Connection connMag;

    //ERP
    static String sErpServer = "";
    static String sErpDB = "";
    static String sErpUser = "";
    static String sErpPassword = "";
    // eShop
    static String sMagUserName = "";
    static String sMagPassword = "";
    static String sMagUrl = "";
    static String sMagDriver = "";
    // Base
    static String sBaseServer = "";
    static String sBaseDB = "";
    static String sBaseUser = "";
    static String sBasePassword = "";
    //Newsletter 2 Go
    static String sNewsServer = "";
    static String sNewsDB = "";
    static String sNewsUser = "";
    static String sNewsPassword = "";

    protected void setEnvironments(String sEnvErp,String sEnvBase, String sEnvMag, String sEnvNews)
    {
        this.sEnvErp = sEnvErp;
        this.sEnvBase = sEnvBase;
        this.sEnvMag = sEnvMag;
        this.sEnvNews = sEnvNews;

        setMagEnv();
    }

    private void setMagEnv()
    {
        if (sEnvMag.equalsIgnoreCase("live"))
        {
            sMagUserName="sirocco1_base";
            sMagPassword="GetMag4Base";
            sMagUrl="jdbc:mariadb://sirocco1.mysql.db.hostpoint.ch:3306/sirocco1_m2live";
            sMagDriver="org.mariadb.jdbc.Driver";
        }
    }

    protected boolean openMagDB()
    {
        Statement st;
        try {
            Class.forName(sMagDriver);
            connMag= DriverManager.getConnection(sMagUrl, sMagUserName, sMagPassword);
            st=connMag.createStatement();
            Globals.message(Globals.MsgTyp.INFO,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"connected to eShop database " + sEnvMag);
        } catch (Exception e) {
            Globals.message(Globals.MsgTyp.ERROR,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"no connection:  " + e.toString());
            return false;
        }
        return true;
    }

    protected ResultSet executeMagQuery(String sSQL)
    {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = (Statement) connMag.createStatement();
            rs=stmt.executeQuery(sSQL);
        } catch (SQLException e) {
            Globals.message(Globals.MsgTyp.ERROR,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"sql execution fail:  " + e.toString());
        }
        return rs;
    }
}
