package narino;

import annaik.db.client.IDatabase;

public final class StockQty extends imulik.base.BaseObjectWithChilds
{
	public float fReserved;
	public float fQuantity;
	public int iChargePos;
	public int iStockPos;
	public int iBarcodePos;
	private static final long serialVersionUID = 1529591491L;
	public static StockQty getStockQty(int iPos)throws Exception{return (StockQty)new StockQty().getObject(iPos);}
	
	public static float getStockQtyByStock(int iStockPos)throws Exception
	{
		if(iStockPos>0)
		{
			if(isRunningOnServer())
			{
				StockQty sq = new StockQty();
				sq.iStockPos=iStockPos;
				int[] arPos = sq.search();
				return (float)IDatabase.Factory.getDatabase(StockQty.class).getColumn("fQuantity").getSum(arPos);
			}
			else{((Float)getRMIClient().call(new Integer(iStockPos))).floatValue();}
		}
		return 0;
	}
}