package Model;

import imulik.base.BaseLanguage;
import narino.CustomerAttributeType;
import narino.OrderState;
import narino.SalesChannel;
import narino.Salutation;

import java.util.ArrayList;
import java.util.List;

public class Stammdaten {
    private static Stammdaten instance = null;
    private static List<String> errormsgs = new ArrayList<String>();

    public static Stammdaten getInstance() {
        if (instance == null) {
            instance = new Stammdaten();
        }
        return instance;
    }

    //
    List<SimpleTable> languages = new ArrayList<SimpleTable>();
    List<SimpleTable> salutations = new ArrayList<SimpleTable>();
    List<SimpleTable> customerstypes = new ArrayList<SimpleTable>();
    List<SimpleTable> saleschanneltypes = new ArrayList<SimpleTable>();
    List<SimpleTable> orderStates = new ArrayList<SimpleTable>();

    public String getStammdaten()
    {
        int[] arrInt;
        String sErr = "";
        try {
            // get Language entries
            BaseLanguage langs = new BaseLanguage();
            arrInt = langs.search("");
            for (int i = 0; i < arrInt.length ; i++) {
                langs = BaseLanguage.getLanguage(arrInt[i]);
                languages.add(new SimpleTable(langs.getId(), langs.sShortName));
            }

            // get salutation entries
            Salutation salu = new Salutation();
            arrInt = salu.search();
            for (int i = 0; i < arrInt.length ; i++) {
                salu = Salutation.getSalutation(arrInt[i]);
                salutations.add(new SimpleTable(salu.getId(), salu.sName));
            }

            // get customer attribut types entries
            CustomerAttributeType attr = new CustomerAttributeType();
            arrInt = attr.search();
            for (int i = 0; i < arrInt.length ; i++) {
                attr = CustomerAttributeType.getCustomerAttributeType(arrInt[i]);
                customerstypes.add(new SimpleTable(attr.getId(), attr.sName));
            }

            // get Sales Channel entries
            SalesChannel saleschannel = new SalesChannel();
            arrInt = saleschannel.search();
            for (int i = 0; i < arrInt.length ; i++) {
                saleschannel = SalesChannel.getSalesChannel(arrInt[i]);
                saleschanneltypes.add(new SimpleTable(saleschannel.getId(), saleschannel.sName));
            }

            //get order states
            OrderState orderState = new OrderState();
            arrInt = orderState.search();
            for (int i = 0; i < arrInt.length ; i++) {
                orderState = OrderState.getOrderState(arrInt[i]);
                orderStates.add(new SimpleTable(orderState.getId(), orderState.sName));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sErr;
    }

    public int getOrderStatePos(String sOrderState)
    {
        int iID = 0;
        for (int i = 0; i < orderStates.size(); i++) {
            if (orderStates.get(i).sCode.equalsIgnoreCase(sOrderState))
            {
                return orderStates.get(i).iBaseID;
            }
        }
        return iID;
    }


    public int getLanguage(String sLang)
    {
        String sDefaultLang = "DE";
        int iID = 0;
        for (int i = 0; i < languages.size(); i++) {
            if (languages.get(i).sCode.equalsIgnoreCase(sLang))
            {
                return languages.get(i).iBaseID;
            }
            else {
                if (languages.get(i).sCode.equalsIgnoreCase(sDefaultLang))
                {
                    iID = languages.get(i).iBaseID;
                }
            }
        }
        return iID;
    }

    public int getCustAttrType(String sType)
    {
        int iID = 0;
        for (int i = 0; i < customerstypes.size(); i++) {
            if (customerstypes.get(i).sCode.equalsIgnoreCase(sType))
            {
                return customerstypes.get(i).iBaseID;
            }
        }
        return iID;
    }

    public int getSalesChannelType(String sType)
    {
        int iID = 0;
        for (int i = 0; i < saleschanneltypes.size(); i++) {
            if (saleschanneltypes.get(i).sCode.equalsIgnoreCase(sType))
            {
                return saleschanneltypes.get(i).iBaseID;
            }
        }
        return iID;
    }

    public int getSalutation(String sSalu)
    {
        String sDefaultLang = "Frau";
        int iID = 0;
        for (int i = 0; i < salutations.size(); i++) {
            if (salutations.get(i).sCode.equalsIgnoreCase(sSalu))
            {
                return salutations.get(i).iBaseID;
            }
            else {
                if (salutations.get(i).sCode.equalsIgnoreCase(sDefaultLang))
                {
                    iID = salutations.get(i).iBaseID;
                }
            }
        }
        return iID;
    }

    public void errorMsg(String sErr) {
        errormsgs.add(sErr);
        System.out.println("ERROR" + sErr);
        // sendSMS(sErr)
    }

    public void infoMsg(String sInfo) {
        System.out.println(sInfo);
        // sendSMS(sErr)
    }

    public void cleanErrorList()
    {
        errormsgs.clear();
    }

    public List<String> getErrorlist()
    {
        return errormsgs;
    }

    private class SimpleTable
    {
        int iBaseID;
        String sCode;

        public SimpleTable(int iID,String sCode)
        {
            this.iBaseID = iID;
            this.sCode = sCode;
        }
    }



}
