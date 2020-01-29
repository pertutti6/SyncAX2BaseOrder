package AX;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickingOrder {
    public List<PickingOrderLine> axorderlines = new ArrayList<PickingOrderLine>();

    public long RecId = 0;
    public String ShipmentId = "";
    public String AXCustomerId = "";
    public int BaseCustomerId = 0;
    public String AuftragId = "";
    public Date ActivationDateTime;
    public String DlvModeId = "";
    public String DlvTermId = "";
    public Date DlvDate;
    public String DeliveryName = "";
    public String DeliveryPostalAddress = "";
    public String ContactPersonName = "";
    public String DeliveryContact = "";
    int BaseID = 0;
    Date BaseSync;
    public String PickingRouteId;
    public String AdressStreet;
    public String AdressZIP;
    public String AdressPlace;
    public String AdressCountry;
}
