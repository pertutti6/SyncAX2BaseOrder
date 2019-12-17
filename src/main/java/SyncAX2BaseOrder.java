import AX.PickingOrders;
import Model.BaseConnection;
import Model.BaseOrders;
import Model.Stammdaten;

import java.util.List;

public class SyncAX2BaseOrder {

    public static void main(String[] args) {
        Stammdaten stammdaten = Stammdaten.getInstance();
        stammdaten.getStammdaten();

        String sErr;
        PickingOrders axorders = new PickingOrders("live");
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
        // Connect to Base
        BaseConnection base = new BaseConnection();
        base.setConnection("test");

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
            System.out.println("***************************************************");
            System.out.println("***************** ERROR REPORT ********************");
            System.out.println("***************************************************");
            //send eMail
            for (int i = 0; i < errorMsgs.size(); i++) {
                System.out.println(errorMsgs.get(i));
            }
        }

   //     base.createorders(PickingOrders);
    //    axorders.savesyncdata();
    }


}
