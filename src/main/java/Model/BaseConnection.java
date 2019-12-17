package Model;

import annaik.db.client.DatabaseConnection;
import annaik.db.server.Database;
import imulik.base.BaseObjectWithChilds;

public class BaseConnection {

    public void setConnection(String System)
    {
        String HOST_AND_PORT = "";
       if (System.equals("live"))
       {
          HOST_AND_PORT = "base8716a.sirocco.ch:8091"; // Prod
       }
       else {
           HOST_AND_PORT = "192.168.236.15:8092"; // Test
       }
        DatabaseConnection dbConn = DatabaseConnection.getDbConn(HOST_AND_PORT, Database.DB_SYNCHRONIZER, getPw());
        BaseObjectWithChilds.setDbConn(dbConn);
    }
    static String getPw(){return new String(new byte[]{(byte) 114, (byte) 101, (byte) 110, (byte) 97, (byte) 114, (byte) 100});}

}
