package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Globals {

    private static List<message> messages = new ArrayList<message>();

    public enum MsgTyp {
        INFO,
        WARNING,
        ERROR
    }

    public enum MsgSystem {
        ESHOP,
        ERP,
        BASE
    }

    public enum MsgArea {
        CUSTOMER,
        ORDER,
        ORDERLINE,
        DATABASE
    }

    public static void message(Globals.MsgTyp eTyp, Globals.MsgSystem eSystem, Globals.MsgArea eArea, String sMsg)
    {
        System.out.println(eTyp + ";" + eSystem + ";" + eArea + ";" + sMsg);
        messages.add(new message(eTyp, eSystem, eArea, sMsg));
    }

    public static String CleanString(String sText)
    {
     /*   sText = sText.replace("\"", " ");
        sText = sText.replace("'", " ");
        sText = sText.replace("&", " ");
        sText = sText.replace("+", " ");
        sText = sText.replace(",", " ");
        sText = sText.replace("#", " ");
        sText = sText.replace("`", "\'");
        sText = sText.replace("\'", "\\'");*/
        return sText;
    }
}

class message
{
    Date date;
    Globals.MsgTyp eTyp;
    Globals.MsgSystem eSystem;
    Globals.MsgArea eArea;
    String sMsg;

    public message(Globals.MsgTyp eTyp, Globals.MsgSystem eSystem, Globals.MsgArea eArea, String sMsg) {
        this.eTyp = eTyp;
        this.eSystem = eSystem;
        this.eArea = eArea;
        this.sMsg = sMsg;
    }
}