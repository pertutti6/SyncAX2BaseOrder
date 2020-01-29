package Model;

import AX.PickingOrder;
import AX.PickingOrderLine;
import AX.PickingOrders;
import narino.*;

import java.util.Hashtable;

public class BaseOrders {

    Stammdaten stammdaten;

    public BaseOrders() {
        stammdaten = Stammdaten.getInstance();
    }

    public String saveOrders2Base(PickingOrders orders, Stammdaten stammdaten)
    {
        String sErr = "";
        String sHeader;
        int iOrderPos;
        int iOrderlinePos;

        for (int o = 0; o < orders.axorders.size(); o++) {
            PickingOrder axorder = orders.axorders.get(o);

            Order baseorder = new Order();
            baseorder.sAXPickingSlipId = axorder.PickingRouteId;
            try
            {
                // does order exist already?
                int[] arrOrd = baseorder.search();
                if (arrOrd.length > 0)
                {
                    // order exist already
                    iOrderPos = arrOrd[0];
                }
                else {
                    baseorder.dateCreation = axorder.ActivationDateTime;
                    //    baseorder.iDeliveryTypePos;
                    baseorder.iSalesChannelPos = stammdaten.getSalesChannelType("AX");
                    baseorder.sComment = "";
                    sHeader = axorder.DeliveryName;
                    if (!axorder.DeliveryContact.equals("")) {
                        sHeader = sHeader + "\n" + axorder.DeliveryContact;
                    }
                    baseorder.iDeliveryAdressPos = saveAddress(sHeader, axorder.AdressStreet, axorder.AdressZIP, axorder.AdressPlace, axorder.AdressCountry);
                    baseorder.iInvoiceAdressPos = baseorder.iDeliveryAdressPos;
                    baseorder.dDlvDate = axorder.DlvDate;
                    baseorder.sAXPickingSlipId = axorder.PickingRouteId;
                    baseorder.iCustomerPos = axorder.BaseCustomerId;
                    iOrderPos = baseorder.add();
                    stammdaten.infoMsg("Speicher in Base Rüstliste " + axorder.PickingRouteId);
                    if (iOrderPos > 0) {
                        // Save sync data into AX
                        orders.saveOrderSyncDateId(iOrderPos, axorder.RecId);
                    }
                }
                // CREATE ORDERPOSITIONS
                if (iOrderPos > 0) {
                    for (int l = 0; l < axorder.axorderlines.size(); l++) {
                        PickingOrderLine axLine = new PickingOrderLine();
                        axLine = axorder.axorderlines.get(l);
                        // create orderlines
                        OrderPosition baseLine = new OrderPosition();
                        // Check if Orderline doesn't exist already
                        baseLine.lErpOrderlineID = axLine.RECID;
                        int[] intLin = baseLine.search();
                        if (intLin.length == 0)
                        {
                            baseLine.iOrderPos = iOrderPos;
                            if (axLine.ProductionArticle) {
                                // customized article
                                Article article = new Article();
                                article.sNumber = axLine.ItemId;
                                int[] intArt = article.search();
                                if (intArt.length == 0) {
                                    stammdaten.errorMsg("Artikel in Base nicht gefunden. Artikel Nr : " + axLine.ItemId + ". AX Rüstliste " + axorder.PickingRouteId);
                                    continue;
                                }
                                if (intArt.length > 1) {
                                    stammdaten.errorMsg("Mehrere Artikel in Base mit gleicher Artikel Nr : " + axLine.ItemId + ". AX Rüstliste " + axorder.PickingRouteId);

                                    // TODo  wieder aktivieren continue;
                                }
                                baseLine.iArticlePos = intArt[0];
                                baseLine.sChargeRequest = axLine.Charge;
                            }
                            else {
                                int iBarcodePos = Barcode.getBarcodePos(axLine.Barcode);
                                if (iBarcodePos == 0) {
                                    stammdaten.errorMsg("Kein Artikel " + axLine.ItemId + " Barcode " + axLine.Barcode + " vorhanden. AX Rüstliste " + axorder.PickingRouteId);
                                    continue;
                                } else {
                                    baseLine.iArticlePos = Barcode.getBarcode(iBarcodePos).iArticlePos;
                                }
                            }
                            baseLine.fQuantity = (float) axLine.OrderQty;
                            iOrderlinePos = baseLine.add();
                            stammdaten.infoMsg("Speicher in Base Rüstliste " + axorder.PickingRouteId + " Artikelbarcode " + axLine.Barcode);
                            if (iOrderlinePos > 0) {
                                // Save sync data into AX
                                orders.saveOrderLineSyncDateId(iOrderlinePos, axLine.RECID);
                            }

                        }
                        else
                        {
                            // Orderline exist already. do nothing
                        }
                    }
                }
                else {
                    stammdaten.errorMsg("ERROR FAILING TO CREATE ORDER IN BASE");
                }

            } catch (Exception e) {
                stammdaten.errorMsg("ERROR CREATE ORDER IN BASE : " + e.toString());
                return "ERROR CREATE ORDER IN BASE : " + e.toString();
                //e.printStackTrace();
            }
        }
        return sErr;
    }

    public int saveAddress(String sHeader,String sStreet,String sPLZ,String sPlace,String sCountry)
    {
        int iAddrId = 0;
        int iCountryId = 0;
        int iPostalId = 0;

        try
        {
            Country country = new Country();
            country.sShortName = sCountry;
            int[] intCou = country.search();
            if (intCou.length > 0) {
                iCountryId = intCou[0];
            } else {
                // create country
                iCountryId = country.add();
            }
            if (iCountryId == 0) { return 0; }

            // Create Postlocation. Die Adress muss unveränderbar sein.
            PostalLocation postal = new PostalLocation();
            postal.iCountryPos = iCountryId;
            postal.sZipCode = sPLZ;
            postal.sCity = sPlace;
            iPostalId = postal.add();

            narino.Address address = new narino.Address();
            address.sName = sHeader;
            address.sStreet = sStreet;
            address.iPostalLocationPos = iPostalId;
            iAddrId = address.add();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return iAddrId;
    }
}
