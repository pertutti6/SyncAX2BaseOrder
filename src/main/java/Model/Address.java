package Model;

import annaik.db.server.Database;
import narino.Country;
import narino.PostalLocation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Address {
    private int iMagAdrID;
    private int iBaseAdrID;

    private String sName;
    private String sFirstName;
    private String sLastName;
    private String sCompany;
    private String sCustName;
    private String sStreet;
    private String sZIP;
    private String sPlace;
    public String sTelefon;
    private String sCountry;
    Stammdaten stammdaten;

    public Address()
    {
        stammdaten = Stammdaten.getInstance();
    }

    public Address(String sName, String sStreet, String sZip, String sPlace, String sCountry,String sTelefon)
    {
        sFirstName = "";
        sLastName = "";
        sCompany = "";
        this.sCustName = Globals.CleanString(sName);;
        this.sStreet = Globals.CleanString(sStreet);
        this.sZIP = sZip;
        this.sPlace = Globals.CleanString(sPlace);
        this.sCountry = sCountry;
        this.sTelefon = Globals.CleanString(sTelefon);

    }

    public boolean readAddressFromMag(String sAddressTable, int iMagAdrID, Databases database)
    {
        // customer_address_entity
        String sRes = "";
        String sSQL = "select * from " + sAddressTable + " where entity_id = " + iMagAdrID;

        try
        {
            ResultSet results = database.executeMagQuery(sSQL);
            while((results!=null) && (results.next()))
            {
                sFirstName = Globals.CleanString(results.getString("firstname"));
                sLastName = Globals.CleanString(results.getString("lastname"));

                if (iMagAdrID == 27284)
                {
                    String sbugstop = "stop";
                }
                if (results.getString("company") != null)
                {
                    sCompany = Globals.CleanString(results.getString("company"));
                }
                else
                {
                    sCompany = "";
                }
                sCustName = sFirstName + " " + sLastName;
                if (sCompany != "")
                    sCustName = sCustName + "\n" + sCompany;

                sStreet = Globals.CleanString(results.getString("street"));
                sZIP = Globals.CleanString(results.getString("postcode"));
                sPlace = Globals.CleanString(results.getString("city"));
                sTelefon = Globals.CleanString(results.getString("telephone"));
                sCountry = results.getString("country_id");
            }
        }
        catch (Exception e) {
            Globals.message(Globals.MsgTyp.ERROR,Globals.MsgSystem.ESHOP, Globals.MsgArea.DATABASE,"address reading:  " + e.toString());
            return false;
        }
        return true;
    }


    public int saveAddress2Base()
    {
        int iCountryPos = 0;
        int iPostalCodePos;
        int iAddressPos = 0;

        try {
            // PLZ und Ort abspeichern
            PostalLocation postal = new PostalLocation();
            postal.sZipCode = sZIP;
            postal.sCity = sPlace;
            int[] arrInt = postal.search();

            if (arrInt.length > 0)
            {
                iPostalCodePos =  arrInt[0];
            }
            else
            {
                Country country = new Country();
                country.sShortName = sCountry;
                int[] arrCount = country.search();
                if (arrCount.length == 0)
                {
                    iCountryPos = country.add();
                }
                else {
                    iCountryPos = Country.getCountry(arrCount[0]).getId();
                }
                postal.iCountryPos = iCountryPos;
                iPostalCodePos = postal.add();
            }
            // Adresse abspeichern
            narino.Address address = new narino.Address();
            address.iPostalLocationPos = iPostalCodePos;
            address.sName = sCustName;
            address.sStreet = sStreet;
            iAddressPos = address.add();
        }
        catch (Exception e) {
            return 0;
        }
        return iAddressPos;
    }
}
