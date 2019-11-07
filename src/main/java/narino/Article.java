package narino;

import annaik.db.client.DatabaseConnection;
import annaik.db.client.IDatabase;

public final class Article extends imulik.base.BaseObjectWithChilds
{
	public boolean bIsVirtual;
	public int iSupplierCustomerPos;
	public boolean bB2B;
	public boolean bB2C;
	public int iArticleStatePos;
	public float fPrice;
	public int iSalesCategoryPos;
	public int iSingleArticlePos;
	public int iQuantity;
	public String sDescription;
	public String sNumber;
	public int iArticleGroupPos;
	public String sName;
	private static final long serialVersionUID = 1529576167L;
	public static Article getArticle(int iPos)throws Exception{return (Article)new Article().getObject(iPos);}
	
	public static int[] getBarcodes(int iArticlePos)throws Exception{return getBarcodes(new int[]{iArticlePos});}
	
	public static int[] getBarcodes(int[] arArticlePos)throws Exception
	{
		if(isRunningOnServer())
		{
			return IDatabase.Factory.getDatabase(Barcode.class).getColumn("iArticlePos").searchPositions(arArticlePos, DatabaseConnection.COMPARATOR_EQUALS);
		}
		else{return (int[])getRMIClient().call(arArticlePos);}
	}
}