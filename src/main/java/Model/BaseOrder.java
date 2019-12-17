package Model;

import java.util.Date;

public class BaseOrder {

    // Order ID's
    public int iMagID;
    public int iBaseID;
    public int iErpID;
    public int iMagOrderNr;
    
    public int iCustMagID;
    public int iCustBaseID;
    public int iCustErpID;
    public String sCusteMail;

    //order
    private int iStoreId;
    public double dSubTotal;
    public double dDiscAbs;
    public double dGrandTotal;
    public String sComment;
    private String sLanguage;
    private Date dCreation;
    private int iSalesChannelID;

    // Discount
    private String sCouponName;
    private double dDiscountAmount;

    // status
    public String sState;
    public String sStatus;
    public String sLockedBy;
    //Address
    private int iDeliveryMagId;
    private int iBillingMagID;
    private int iDeliveryBaseId;
    private int iBillingBaseID;
    private Address oBilling;
    private Address oDelivery;
    //Billpay
    String sbillpayTransactionID;
    String sbillpayEsrRef;
    String sBillpayEsrCodeline;
    // Paymentmethods
    int iPaymentBaseId;
    String sPaymentmethod;

    // shipping
    private String sShippingtype;
    private double dShippingCost;
    private int iShippingMethodBaseId;

    //other settings
    private int iMagentoUserId;
    private int iAXUserId;
    private int iGuestID;
    private int iGuestDeID;
    private int iGuestEnID;

}
