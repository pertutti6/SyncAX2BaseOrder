package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseCustomers {
    List<BaseCustomer> customers;
    public int iMagID = 0;
    int iBaseID;
    // customer data
    private String sCreationtime;
    private String sSalutation;
    private String sFirstname;
    private String sLastname;
    private Date dBirthday;
    private String sEmail;
    private int iAddressMagId;
    // Addition needed data
    private int iStoreId;
    Boolean bIsActiv;
    // SiroccoClub
    private int iCustStatus;
    private int iClubNr;
    private Date dClubActivation;
    private Date dClubRegistration;
    private Boolean isClubMember;
    private int iClubState;
    // Default Biling address
    private int iAddressBaseId;
    //socialmedia
    private Boolean bIsLead = false;
    private Boolean bIsFacebook = false;
    private Boolean bIsNewsletter = false;


private Databases database;
    private Address oAddress;

    public BaseCustomers() {
        database = new Databases();
        customers = new ArrayList<BaseCustomer>();
        database.setEnvironments("test","test","live", "test");
    }

    public void getCustFromMag(String sWhich)
    {
        String sSQL = "";

        if (database.openMagDB()) {
            try
            {
                if (sWhich.equalsIgnoreCase("open")) {
                    sSQL = "SELECT * FROM customer_entity cus where last_base_sync != updated_at or last_base_sync IS NULL order by updated_at ";
                }
                ResultSet results = database.executeMagQuery(sSQL);
                while((results!=null) && (results.next()))
                {
                    saveMagCustData(results);
                //    System.out.print(result.getString("CustomerID"));
                }
            }
            catch (Exception e) {
                Globals.message(Globals.MsgTyp.ERROR,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"no connection:  " + e.toString());
            }
        }
    }

    public boolean saveMagCustData(ResultSet result)
    {
        try
        {
            iMagID = result.getInt("entity_id");
            sSalutation = result.getString("prefix");
            sFirstname = result.getString("firstname");
            sLastname = result.getString("lastname");
            dBirthday = result.getDate("dob");
            sEmail = result.getString("email");
            iAddressMagId = result.getInt("default_billing");
            if (!saveMagCustAddr(iAddressMagId)) { return false;}
            iStoreId = result.getInt("store_id");
            bIsActiv = result.getBoolean("is_active");
            if (!saveMagCustAttrData("customer_entity_int")) { return false;}
            if (!saveMagCustAttrData("customer_entity_datetime")) { return false;}
            if (!saveMagCustAttrData("customer_entity_varchar")) { return false;}
            bIsLead = false;
            bIsFacebook = false;
            bIsNewsletter = false;
        } catch (SQLException e) {
            Globals.message(Globals.MsgTyp.ERROR,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"collect customer data from eShop:  " + e.toString());
            return false;
        }
        Globals.message(Globals.MsgTyp.INFO,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"get customer data from Magento  " + sFirstname + " " + sLastname);
        return true;
    }

    private boolean saveMagCustAddr(int iAddressMagId) {
        oAddress = new Address();
        return oAddress.readAddressFromMag("customer_address_entity",iAddressMagId,database);
    }


    private boolean saveMagCustAttrData(String sAttrTable) {
        String sSQL = "SELECT * FROM " + sAttrTable + " where entity_id = " + iMagID + " and attribute_id >= 144 and attribute_id <= 152 ";
        try
        {
            ResultSet results = database.executeMagQuery(sSQL);
            while((results!=null) && (results.next()))
            {
                filterAttribute(results.getInt("attribute_id"), results);
            }
        }
            catch (Exception e) {
            Globals.message(Globals.MsgTyp.ERROR,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"address reading:  " + e.toString());
            return false;
        }
        return true;
    }

    private boolean filterAttribute(int iAttrId,ResultSet row)
    {
        try
        {
            switch (iAttrId)
            {
                case 144:
                    iBaseID = row.getInt("value");
                    break;
                case 145:
                    iAddressBaseId = row.getInt("value");
                    break;
                case 146:
                    isClubMember = row.getBoolean("value");
                    break;
                case 147:
                    iClubNr = row.getInt("value");
                    break;
                case 148:
                    dClubRegistration = row.getDate("value");
                    break;
                case 149:
                    dClubActivation = row.getDate("value");
                    break;
                case 151:
                    iClubState = row.getInt("value");
                    break;
            }
        }
        catch (Exception e) {
            Globals.message(Globals.MsgTyp.ERROR,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"customer attribut int:  " + e.toString());
            return false;
        }
        return true;
    }
}
