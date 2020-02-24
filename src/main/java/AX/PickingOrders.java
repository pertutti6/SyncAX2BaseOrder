package AX;

import Model.Stammdaten;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickingOrders extends AXDB {
    String sEnvironment;
    Stammdaten stammdaten;

    public List<PickingOrder> axorders = new ArrayList<PickingOrder>();

    public PickingOrders(String sEnvironment) {

        this.setupConnection(sEnvironment);
        stammdaten = Stammdaten.getInstance();
        stammdaten.getStammdaten();
    }


    public String readOpenOrders() {
        String sErr;

        sErr = openConnection();
        if (!sErr.equals(""))
        {
            return sErr;
        }

        // and pickingRouteID = '2000053'

        String sFields = "shipmentId ,customer ,transRefId ,ActivationDateTime ,DlvModeId ,DlvTermId ,DlvDate ,DeliveryName,DeliveryPostalAddress ," +
                "SIRContactPersonName ,pick.SIRDeliveryContact,BaseID ,BaseSync,PICKINGROUTEID,STREET,ZIPCODE,CITY,COUNTRYREGIONID,pick.RecId,pick.SIREXPORTNOYES ";
        String sWhere = "pick.DeliveryPostalAddress = loc.RECID  and EXPEDITIONSTATUS >= 3 and EXPEDITIONSTATUS <= 10 and DATAAREAID='kus' and pick.PICKINGROUTEID = '2000065'";  //and BASEID = 0"; //and pick.PICKINGROUTEID = '2000062'";  // and BASEID = 0
        sErr = readTable(sFields, "WMSPickingRoute pick, LogisticsPostalAddress loc", sWhere);
        if (sErr.equals("")) {
            try {
                ResultSet rs = getDataTable();
                while (rs.next()) {
                    PickingOrder order = new PickingOrder();
                    order.ShipmentId = rs.getString(1);
                    order.AXCustomerId = rs.getString(2);
                    order.AuftragId = rs.getString(3);
                    order.ActivationDateTime = rs.getDate(4);
                    order.DlvModeId = rs.getString(5);
                    order.DlvTermId = rs.getString(6);
                    order.DlvDate = rs.getDate(7);
                    order.DeliveryName = rs.getString(8);
                    order.DeliveryPostalAddress = rs.getString(9);
                    order.ContactPersonName = rs.getString(10);
                    order.DeliveryContact = rs.getString(11);
                    order.BaseID = rs.getInt(12);
                    order.BaseSync = rs.getDate(13);
                    order.PickingRouteId = rs.getString(14);
                    order.AdressStreet = rs.getString(15);
                    order.AdressZIP = rs.getString(16);
                    order.AdressPlace = rs.getString(17);
                    order.AdressCountry = rs.getString(18);
                    order.RecId = rs.getLong(19);
                    boolean isExport = rs.getBoolean(20);
                    if (isExport)
                    {
                        order.iSalesChannelID = stammdaten.getSalesChannelType("AX Export");
                    }
                    else {
                        order.iSalesChannelID = stammdaten.getSalesChannelType("AX");
                    }
                    axorders.add(order);
                    stammdaten.infoMsg("read AX Rüstliste " + order.PickingRouteId);
                }
            } catch (SQLException e) {
                closeConnection();
                sErr = "ERROR READ AX ORDER;" + e.toString();
            }
        }
        return sErr;
    }


    public String readOrderLines()
    {
        String sErr = "";
        sErr = openConnection();
        if (!sErr.equals(""))
        {
            return sErr;
        }

        for (int i = 0; i < axorders.size(); i++) {
            PickingOrder order = axorders.get(i);

            String sWhere = "ROUTEID = '" + order.PickingRouteId + "' and INVENTTRANSTYPE=0 and ORDERTYPE=3 and DATAAREAID='kus' and itemID <> 'Titel'";
            String sFields = "RECID,SIRORDERQTY,SIRBARCODE,inventTransId ,itemID,SIRGroundId,SIRExpiryDate,SIRProductionNoYes,SirPackingLabel,BaseID,BaseSync";
            sErr = readTable(sFields, "WMSORDERTRANS", sWhere);
            if (sErr.equals("")) {
                try {
                    ResultSet rs = getDataTable();
                    while (rs.next()) {
                        PickingOrderLine orderline = new PickingOrderLine();
                        orderline.RECID = rs.getLong(1);
                        orderline.OrderQty = rs.getDouble(2);
                        orderline.Barcode = rs.getString(3);
                        orderline.Charge = rs.getString(4);
                        orderline.ItemId = rs.getString(5);
                        orderline.GrindSetup = rs.getString(6);
                        orderline.ExpiryDate = rs.getDate(7);
                        orderline.ProductionArticle = rs.getBoolean(8);
                        orderline.AddPackingInfo = rs.getString(9);
                        order.BaseID = rs.getInt(10);
                        order.BaseSync = rs.getDate(11);
                        order.axorderlines.add(orderline);
                        stammdaten.infoMsg("read AX Rüstliste " + order.PickingRouteId + " Artikelbarcode " + orderline.Barcode);
                    }
                } catch (SQLException e) {
                    closeConnection();
                    sErr = "ERROR READ AX ORDERLINE;" + e.toString();
                }
            }
        }
        return sErr;
    }

    public void saveOrderSyncDateId(int iId,long RecID)
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = format.format( new Date()   );
        updateTable("BASEID = " + iId + ",BASESYNC = '" + dateString + "'","WMSPickingRoute",RecID);
      //  updateTable(String valueset,String sTable,long RecID)
    }



    public void saveOrderLineSyncDateId(int iId,long RecID)
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = format.format( new Date()   );
        updateTable("BASEID = " + iId + ",BASESYNC = '" + dateString + "'","WMSORDERTRANS",RecID);
        //  updateTable(String valueset,String sTable,long RecID)
    }

}