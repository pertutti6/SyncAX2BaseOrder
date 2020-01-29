package narino;
import imulik.base.BaseUser;

import java.util.Date;
import java.util.Hashtable;

import annaik.db.client.DatabaseConnection;
import annaik.db.client.IDatabase;
import annaik.util.HandledException;
public final class Order extends imulik.base.BaseObjectWithChilds
{
	public String sAXPackingSlipId;
	public Date dDlvDate;
	public String sAXPickingSlipId;
	public float fDeliveryCosts;
	public int iDeliveryTypePos;
	public int iLastSync;
	public int iEshopOrderId;
	public String sComment;
	public int iSalesChannelPos;
	public int iLockedByUserPos;
	public String sTransactionId;
	public String sEsrReference;
	public int iPaymentMethodPos;
	public boolean bClosed;
	public Date dateCreation;
	public int iInvoiceAdressPos;
	public int iDeliveryAdressPos;
	public int iCustomerPos;
	public int iOrderId;
	public int iOrderIDSync;
	private static final long serialVersionUID = 1529843025L;
	public static Order getOrder(int iPos)throws Exception{return (Order)new Order().getObject(iPos);}
	
	public static int[] getOrderPositionPos(int iUserPos, int iDocumentTypePos, int[] arOptionalOrderPos)throws Exception
	{
		IDatabase dbOp = IDatabase.Factory.getDatabase(OrderPosition.class);
		Hashtable ht = new Hashtable();
		if(iUserPos>0)
		{
			if(arOptionalOrderPos!=null){ht.put(".", arOptionalOrderPos);}
			ht.put("iLockedByUserPos", new int[]{0, iUserPos});
			int[] arOPos = IDatabase.Factory.getDatabase(Order.class).search(ht);
			ht.clear();
			ht.put("iOrderPos", arOPos);
			int[] arOpPos = dbOp.search(ht);
			ht.clear();
			ht.put("iOrderPositionPos", arOpPos);
		}
		
		ht.put("iDocumentTypePos", new int[]{iDocumentTypePos});
		ht.put(DatabaseConnection.SEARCH_FIELD_NAME, "iOrderPositionPos");
		int[] arToReturn = dbOp.getSortedPositions(dbOp.getUniqueExistingPositions(IDatabase.Factory.getDatabase(OrderPositionState.class).search(ht)), ".");;
		System.out.println("returning "+arToReturn.length+" op");
		return arToReturn;
	}
	
	public static int[] getOrderPositionPosByState(int iDocumentTypePos)throws Exception{return getOrderPositionPos(0, iDocumentTypePos, null);}
	
	public static int[] getOrderPos(int iUserPos, int iDocumentTypePos)throws Exception
	{
		IDatabase dbOp = IDatabase.Factory.getDatabase(OrderPosition.class);
		IDatabase dbO = IDatabase.Factory.getDatabase(Order.class);
		return dbO.getSortedPositions(dbO.getUniqueExistingPositions((int[])dbOp.getArrayFromPositions("iOrderPos", getOrderPositionPos(iUserPos, iDocumentTypePos, null))), "dateCreation");
	}
	
	public static void setLocked(int iOrderPos, int iUserPos, int iBoxStockPos)throws Exception
	{
		Order o = Order.getOrder(iOrderPos);
		if(o.iLockedByUserPos>0 && o.iLockedByUserPos!=iUserPos){throw new HandledException("Order "+o.iOrderId+" is locked by "+BaseUser.getUser(o.iLockedByUserPos).sName);}
		if(StockQty.getStockQtyByStock(iBoxStockPos)>0){throw new HandledException("Box "+Stock.getStock(iBoxStockPos).sCode+" is not empty");}
		
		o.iLockedByUserPos = iBoxStockPos>0?iUserPos:0;
		o.update("iLockedByUserPos");
	}
	
	public static int[] getOrderPositionPosByOrder(int iOrderPos)throws Exception
	{
		if(iOrderPos>0)
		{
			OrderPosition op = new OrderPosition();
			op.iOrderPos=iOrderPos;
			return op.search("", "iOrderPos");
		}
		return new int[0];
	}
}