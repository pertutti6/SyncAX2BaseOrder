package narino;


public final class Stock extends imulik.base.BaseObjectWithChilds
{
	public boolean bBrakeUpExits;
	public int iPriority;
	public String sDescription;
	public String sCode;
	private static final long serialVersionUID = 1529843023L;
	public static Stock getStock(int iPos)throws Exception{return (Stock)new Stock().getObject(iPos);}

	public static int getStockPos(String sStockCode)throws Exception
	{
		Stock s = new Stock();
		s.sCode=sStockCode;
		int[] arPos = s.search("sCode");
		if(arPos.length==0){return 0;}
		return arPos[0];
	}

}