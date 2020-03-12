package narino;
import java.util.Date;
public final class ArticleNeed extends imulik.base.BaseObjectWithChilds
{
	public int iTag;
	public int iYear;
	public int iMonth;
	public int dDay;
	public float fStockQty;
	public float fOrdered;
	public float fSaleTot;
	public float fSaleAdd;
	public float fSale;
	public int iArticlePos;
	public int iDays;
	public Date dDate;
	private static final long serialVersionUID = 1582290386L;
	public static ArticleNeed getArticleNeed(int iPos)throws Exception{return (ArticleNeed)new ArticleNeed().getObject(iPos);}
}