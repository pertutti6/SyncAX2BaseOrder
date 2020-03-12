package narino;
public final class AX_DELIVERY4PICKING extends imulik.base.BaseObjectWithChilds
{
	public String SIRPACKINGLABEL;
	public long RECID;
	public String KeyAccount;
	public String CURRENCYCODE;
	public double restweigth;
	public double cafeweigth;
	public double weigthtot;
	public double revenue;
	public double QTY;
	public String SALESUNIT;
	public String category;
	public String categorynr;
	public String itemName;
	public String ITEMID;
	public String ITEMBARCODE;
	public String DELIVERYDATE;
	public String PACKINGSLIPID;
	public String accountNr;
	public String CREATEDDATETIME;
	private static final long serialVersionUID = 1561017088L;
	public static AX_DELIVERY4PICKING getAX_DELIVERY4PICKING(int iPos)throws Exception{return (AX_DELIVERY4PICKING)new AX_DELIVERY4PICKING().getObject(iPos);}
}