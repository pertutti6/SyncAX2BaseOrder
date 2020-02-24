import AX.PickingOrder;
import AX.PickingOrders;
import Model.*;
import narino.Customer;

import java.util.List;

public class SyncAX2BaseOrder {
    static Stammdaten stammdaten;

    public static void main(String[] args) {
        // Connect to Base
        BaseConnection base = new BaseConnection();
        base.setConnection("test");

        stammdaten = Stammdaten.getInstance();
        stammdaten.getStammdaten();

        System.out.println("***************************************************");
        System.out.println("** Start Synchronisation of Pickingslips V1.01  ***");
        System.out.println("***************************************************");


        String sErr;
        PickingOrders axorders = new PickingOrders("dev");
        sErr = axorders.readOpenOrders();
        if (!sErr.equals(""))
        {
            stammdaten.errorMsg(sErr);
            return;
        }
        sErr = axorders.readOrderLines();
        if (!sErr.equals(""))
        {
            stammdaten.errorMsg(sErr);
            return;
        }


        // check if customer exist in Base
        for (int i = 0; i < axorders.axorders.size(); i++) {
            checkAndCreateCust(axorders.axorders.get(i));
        }

        // Sync orders from AX to Base
        BaseOrders baseorders = new BaseOrders();
        sErr = baseorders.saveOrders2Base(axorders,stammdaten);
        if (!sErr.equals(""))
        {
            stammdaten.errorMsg(sErr);
            return;
        }

        //Send Error report if error happend
        List<String> errorMsgs = stammdaten.getErrorlist();
        if (errorMsgs.size()> 0)
        {
            String sErrorText = "";
            System.out.println("***************************************************");
            System.out.println("***************** ERROR REPORT ********************");
            System.out.println("***************************************************");
            //send eMail
            for (int i = 0; i < errorMsgs.size(); i++) {
                System.out.println(errorMsgs.get(i));
                sErrorText = sErrorText + errorMsgs.get(i) + "\n";
            }

            SendEmailOffice365 mail = new SendEmailOffice365();
            mail.sendEmail("syncax2base@sirocco.ch","sbaiz@sirocco.ch","","Error from SyncAX2BaseOrder",sErrorText,"sFilepath");

        }

        System.out.println("*************** FINISHED **************");
   //     base.createorders(PickingOrders);
    //    axorders.savesyncdata();
    }

    private static void checkAndCreateCust(PickingOrder pickingOrder) {
        try {
            String sErr;
            Customer customer = new Customer();
            customer.iErpNumber = Integer.parseInt(pickingOrder.AXCustomerId);
            int[] arrCust = customer.search();
            if (arrCust.length == 0)
            {
                // keine Kunde vorhanden
                BaseCustomer baseCust = new BaseCustomer();
                sErr = baseCust.getErpCust(pickingOrder.AXCustomerId);
                pickingOrder.BaseCustomerId = baseCust.saveCustomer2Base();
                if (!sErr.equals(""))
                {
                    stammdaten.errorMsg(sErr);
                    return;
                }
                else {
                    pickingOrder.BaseCustomerId = baseCust.iBaseID;
                }
            }
            else
            {
                pickingOrder.BaseCustomerId = Customer.getCustomer(arrCust[0]).getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
