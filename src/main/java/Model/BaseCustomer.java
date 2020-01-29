package Model;

import AX.AXDB;
import AX.PickingOrder;
import narino.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BaseCustomer  extends AXDB {

    public int iMagID = 0;
    public int iBaseID;
    private int iErpNumber;
    // customer data
    private String sCreationtime;
    private String sSalutation;
    private String sName;
    private String sFirstname;
    private String sLastname;
    private String sCompany;
    private String sStreet;
    private String sZip;
    private String sCity;
    private String sCountry;
    private int iLanguage;
    private Date dBirthday;
    private String sEmail;
    private int iAddressMagId;
    private boolean bHasBouillon;
    private boolean bHasCapsules;
    private boolean bHasCoffee;
    private boolean bHasTea;
    private int iCustGroup;
    private int iAddressBaseId;


    private Address oAddress;
    private int iCustStatus;

    static Stammdaten stammdaten;

    public BaseCustomer() {

        stammdaten = Stammdaten.getInstance();
    }


    public boolean saveDataFromMag(ResultSet result)
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



        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int saveCustomer2Base() {
        try {


            Customer customer = new Customer();
            if (sCompany.equals("")) {
                // Ax kennt keine Saolutation
                customer.sFirstName = sFirstname;
                customer.sLastName = sLastname;
                customer.sCompany = "";
            } else {
                customer.iSalutationPos = stammdaten.getSalutation("Firma");
                customer.sFirstName = sCompany;
                customer.sLastName = sCompany;
                customer.sCompany = sCompany;
            }
            customer.iLanguagePos = stammdaten.getLanguage("DE");
            customer.iErpNumber = iErpNumber;
            if (sEmail != null) customer.sEmail = sEmail;
            else customer.sEmail = "";
            Address address = new Address(sName, sStreet, sZip, sCity, sCountry, "");
            int iAddrPos = address.saveAddress2Base();
            if (iAddrPos == 0) {
                stammdaten.errorMsg("could not save address in base of " + sName);
                Globals.message(Globals.MsgTyp.ERROR, Globals.MsgSystem.BASE, Globals.MsgArea.DATABASE, "could not save address in base of " + sName);
            } else {
                customer.iDefaultAdressPos = iAddrPos;
            }
            return customer.add();
        } catch (Exception e) {
            stammdaten.errorMsg("could not save address in base of " + sName);
            Globals.message(Globals.MsgTyp.ERROR, Globals.MsgSystem.BASE, Globals.MsgArea.DATABASE, "could not save customer in base of " + sName);
            return 0;
        }

    }

    public String getErpCust(String axCustomerId) {
        String sErr;

        sErr = openConnection();
        if (!sErr.equals(""))
        {
            return sErr;
        }
        String sFields = "name,firstname,lastname,street,zipcode,address,city,countryregionid,accountnum, " +
                "sirhasBouillon,sirhasCapsules,sirhasCoffee,sirhasTea,custGroup ";
        String sWhere = "ACCOUNTNUM = " + axCustomerId;
        sErr = readTable(sFields, "SirDirPartyNamePrimaryAddressView", sWhere);
        if (sErr.equals("")) {
            try {
                ResultSet rs = getDataTable();
                while (rs.next()) {
                    sName = rs.getString("name");
                    sFirstname = rs.getString("firstname");
                    sLastname = rs.getString("lastname");
                    if (sFirstname == null || sFirstname.equals("")) {
                        sCompany = sName;
                    }
                    else {
                        sCompany = "";
                    }
                    sStreet = rs.getString("street");
                    sZip = rs.getString("zipcode");
                    sCity = rs.getString("city");
                    sCountry = rs.getString("countryregionid");
                    iErpNumber = rs.getInt("accountnum");
                    bHasBouillon = rs.getBoolean("sirhasBouillon");
                    bHasCapsules = rs.getBoolean("sirhasCapsules");
                    bHasCoffee = rs.getBoolean("sirhasCoffee");
                    bHasTea = rs.getBoolean("sirhasTea");
                    iCustGroup = rs.getInt("custGroup");
                }
            } catch (SQLException e) {
                closeConnection();
                sErr = "ERROR READ AX CUSTOMER DATA;" + e.toString();
            }
        }
        return sErr;
    }


}
